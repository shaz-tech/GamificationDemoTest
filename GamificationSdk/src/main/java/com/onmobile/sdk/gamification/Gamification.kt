package com.onmobile.sdk.gamification

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity


class Gamification private constructor(private val builder: Gamification.Builder) {

    class Builder {
        private var title: String? = null
            set(value) {
                field = value
            }

        fun build(): Gamification {
            return Gamification(this)
        }
    }

    fun show(
        activity: AppCompatActivity,
        inputData: GameInputData,
        callback: GameCallback,
        dataCallback: Callback?
    ) {
        callback.onPreOpen()
        GamificationPage.dataCallback = dataCallback
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

    fun show(
        activity: Activity,
        inputData: GameInputData,
        callback: GameCallback,
        dataCallback: Callback?
    ) {
        callback.onPreOpen()
        GamificationPage.dataCallback = dataCallback
        val intent = Intent(activity, GamificationPage::class.java)
        intent.putExtra("data", inputData)
        activity.startActivity(intent)
        callback.onOpen()
    }

    fun showTranslucent(
        activity: Activity,
        inputData: GameInputData,
        callback: GameCallback,
        dataCallback: Callback?
    ) {
        callback.onPreOpen()
        GamificationPage.dataCallback = dataCallback
        //val intent = Intent(activity, GamificationTranslucentPage::class.java)
        val intent = Intent(activity, GamificationTranslucentDialog::class.java)
        intent.putExtra("data", inputData)
        activity.startActivity(intent)
        callback.onOpen()
    }


}