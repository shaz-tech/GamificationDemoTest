package com.onmobile.sdk.gamification

import java.io.Serializable

class GameInputData : Serializable {
    var title: String? = null
    var message: String? = null
    var callback: Callback? = null
}

interface Callback : Serializable {
    fun onSetData(value: String?)
    fun onClearData()
}