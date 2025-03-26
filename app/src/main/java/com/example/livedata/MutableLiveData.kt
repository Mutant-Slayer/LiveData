package com.example.livedata

class MutableLiveData<T> : LiveData<T>() {

    override fun setValue(value: T) {
        super.setValue(value)
    }
}