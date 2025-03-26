package com.example.livedata

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

typealias Observer<T> = (T?) -> Unit

open class LiveData<T> {

    private var data: T? = null
    private val observerToWrapperMap: HashMap<Observer<T?>, LifecycleObserverWrapper> = HashMap()

    /**
     * here we set the data with new value
     * before notifying we check if lifecycle of that observer is in STARTED state or not
     */

    protected open fun setValue(value: T) {
        this.data = value
        observerToWrapperMap.values.forEach {
            if (it.shouldNotify()) {
                notifyChange(it)
            }
        }
    }

    fun getValue(): T? {
        return this.data
    }

    /**
     * **step 1** : we make a lifecycle that will be attached to that observer
     * **step 2** : we add an observer to that lifecycle, which triggers lifecycle callback functions whenever lifecycle changes (basically u can consider this as a listener)
     * **step 3** : we add that lifecycle to that observer
     */

    fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        val lifecycleObserverWrapper = LifecycleObserverWrapper(owner, observer)
        owner.lifecycle.addObserver(lifecycleObserverWrapper)
        observerToWrapperMap[observer] = lifecycleObserverWrapper
    }

    /**
     * **step 1** : we take out that observer from the map
     * **step 2** : we remove that observer from the lifecycle, preventing memory leaks (basically removing listener)
     */

    fun removeObserver(observer: Observer<T>) {
        val lifecycleObserverWrapper = observerToWrapperMap.remove(observer)
        lifecycleObserverWrapper?.owner?.lifecycle?.removeObserver(lifecycleObserverWrapper)
    }

    /**
     * we use this function, when we want to remove all the observers attached to that livedata
     */

    fun removeObservers(owner: LifecycleOwner) {
        val toBeRemovedObservers = arrayListOf<Observer<T>>()
        observerToWrapperMap.values.forEach {
            if (it.owner == owner) {
                toBeRemovedObservers.add(it.observer)
            }
        }
        toBeRemovedObservers.forEach {
            removeObserver(it)
        }
    }

    /**
     * here in notify change, we invoke observer with data, which triggers observer to update UI
     */

    private fun notifyChange(lifecycleObserverWrapper: LifecycleObserverWrapper) {
        lifecycleObserverWrapper.observer(this.data)
    }

    private inner class LifecycleObserverWrapper(
        val owner: LifecycleOwner,
        val observer: Observer<T>
    ) : DefaultLifecycleObserver {

        override fun onStart(owner: LifecycleOwner) {
            notifyChange(this)
        }

        override fun onResume(owner: LifecycleOwner) {
            notifyChange(this)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            removeObserver(observer)
        }

        fun shouldNotify(): Boolean {
            return owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        }
    }
}