package com.onmobile.gamification.demo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.onmobile.sdk.gamification.GameCallback
import com.onmobile.sdk.gamification.GameInputData
import com.onmobile.sdk.gamification.GameResultData
import com.onmobile.sdk.gamification.Gamification

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = GameInputData()
        data.title = "Game Page"
        findViewById<Button>(R.id.btn).setOnClickListener {
            Gamification.Builder().build().show(this, data, object : GameCallback {
                override fun onPreOpen() {
                    print("onPreOpen")
                    findViewById<TextView>(R.id.txt).text = "onPreOpen"
                }

                override fun onOpen() {
                    print("onOpen")
                }

                override fun onClose(resultData: GameResultData?) {
                    print("onClose, Data: ${resultData?.data}")
                    data.message = resultData?.data ?: ""
                    findViewById<TextView>(R.id.txt).text = "onClose, Data: ${resultData?.data}"
                }

            })
        }
    }

}