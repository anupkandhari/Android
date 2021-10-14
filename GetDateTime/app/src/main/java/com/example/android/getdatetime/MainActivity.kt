package com.example.android.getdatetime

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.android.mydatetime.MyDateTime
import com.example.android.mydatetime.MyExceptionHandler
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var mDateTextView: TextView
    private lateinit var mTimeTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupErrorHandling()

        setContentView(R.layout.activity_main)
        mDateTextView = findViewById(R.id.date_text_view)
        mTimeTextView = findViewById(R.id.time_text_view)

        mDateTextView.text = MyDateTime.getCurrentDate()
        mTimeTextView.text = MyDateTime.getCurrentTime()

    }

    private fun setupErrorHandling() {
        val path: File = applicationContext.filesDir
        if (Thread.getDefaultUncaughtExceptionHandler() !is MyExceptionHandler) {
            Thread.setDefaultUncaughtExceptionHandler(
                MyExceptionHandler(path.absolutePath, Thread.getDefaultUncaughtExceptionHandler())
            )
        }
    }

    fun refresh(view: View) {
        mDateTextView.text = MyDateTime.getCurrentDate()
        mTimeTextView.text = MyDateTime.getCurrentTime()
    }

    fun crashApp(view: View) {
        throw NullPointerException()
    }
}