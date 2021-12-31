package com.avinash.mykmmchat.android

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.avinash.mykmmchat.android.view.LinkedInCompose
import com.avinash.mykmmchat.android.view.MyApplicationTheme


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContent {
            MyApplicationTheme {
                LinkedInCompose()
            }
        }
    }
}


