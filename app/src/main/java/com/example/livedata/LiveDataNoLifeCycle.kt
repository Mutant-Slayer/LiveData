package com.example.livedata

/**
 * named this typealias **TypeObserver** as it might create confusion with Android's own Observer class
 *
 */

typealias TestObserver<T> = (T?) -> Unit

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