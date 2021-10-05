package com.example.android.getdatetime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView
import android.view.View
import android.widget.TextView
import com.example.android.mydatetime.MyDateTime

class MainActivity : AppCompatActivity() {

    private lateinit var mDateTextView: TextView
    private lateinit var mTimeTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDateTextView = findViewById(R.id.date_text_view)
        mTimeTextView = findViewById(R.id.time_text_view)

        mDateTextView.text = MyDateTime.getCurrentDate()
        mTimeTextView.text = MyDateTime.getCurrentTime()

    }

    fun refresh(view: View) {
        mDateTextView.text = MyDateTime.getCurrentDate()
        mTimeTextView.text = MyDateTime.getCurrentTime()
    }
}