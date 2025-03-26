package com.example.livedata

/**
 * LiveData implementation without LifeCycle awareness
 */

/**
 * named this typealias **TypeObserver** as it was creating issue with Observer
 * that is defined in LiveData class file
 *
 */

typealias TestObserver<T> = (T?) -> Unit

/**
 * If not able to understand this, check this interface code
 * ```
 * interface Observer<T> {
 *     fun onChanged(value: T?)
 * }
 * ```
 *
 * ```
 * fun setValue(value: T) {
 *     data = value
 *     for (i in 0 until observers.size) {
 *          val observer = observers[i]
 *          observer.onChanged(data)
 *      }
 * }
 * ```
 *
 * ```
 * val liveData = LiveDataNoLifeCycle<Int>()
 *     val observer1 = object : Observer<Int> {
 *         override fun onChanged(value: Int?) {
 *             println("Observer 1 received: $value")
 *       }
 * }
 * ```
 *
 * above is JAVA way to implement that but we should always use Kotlin way to do things nowadays,
 * added above code for just understanding
 */

class LiveDataNoLifeCycle<T> {

    private var data: T? = null

    private var observers = arrayListOf<TestObserver<T>>()

    fun setValue(value: T) {
        data = value
        observers.forEach { observer ->
            observer(data)
        }
    }

    fun getValue(): T? {
        return data
    }

    fun observe(observer: TestObserver<T>) {
        observers.add(observer)
    }
}

fun tryLiveDataNoLifeCycle() {
    val liveData = LiveDataNoLifeCycle<Int>()

    liveData.observe { it ->

    }

    liveData.observe { it ->

    }

    liveData.setValue(10)
}