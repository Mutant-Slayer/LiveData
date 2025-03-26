package com.example.livedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val _liveData = MutableLiveData<Int>()
    val liveData: LiveData<Int> = _liveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        liveData.observe(this) {

        }
    }
}