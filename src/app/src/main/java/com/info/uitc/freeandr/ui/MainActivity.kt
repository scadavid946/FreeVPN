package com.info.uitc.freeandr.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.info.uitc.freeandr.R
import com.info.uitc.freeandr.ui.auth.LoginActivity
import com.info.uitc.freeandr.ui.connect.ConnectActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // show login activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

//        // show connect activity
//        val intent = Intent(this, ConnectActivity::class.java)
//        startActivity(intent)
    }
}
