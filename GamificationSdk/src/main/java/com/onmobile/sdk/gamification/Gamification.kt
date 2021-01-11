package com.onmobile.sdk.gamification

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity


class Gamification private constructor(private val builder: Gamification.Builder) {
    companion object{
        const val SHOW_REQUEST_CODE = 10000;
    }

    class Builder {
        private var title: String? = null
            set(value) {
                field = value
            }

        fun build(): Gamification {
            return Gamification(this)
        }
    }

    fun show(activity: AppCompatActivity, inputData: GameInputData, callback: GameCallback) {
        callback.onPreOpen()
        val resultLauncher =
            activity.registerForActivityResult(StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val resultData: Intent? = result.data
                    val output = GameResultData()
                    output.data = resultData?.getStringExtra("data")
                    callback.onClose(output)
                } else {
                    callback.onClose(null)
                }
            }
        val intent = Intent(activity, GamificationPage::class.java)
        intent.putExtra("data", inputData)
        resultLauncher.launch(intent)
        callback.onOpen()
    }

    fun show(activity: Activity, inputData: GameInputData, callback: GameCallback) {
        callback.onPreOpen()
        val intent = Intent(activity, GamificationPage::class.java)
        intent.putExtra("data", inputData)
        activity.startActivityForResult(intent, SHOW_REQUEST_CODE)
        callback.onOpen()
    }


}