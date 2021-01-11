package com.onmobile.sdk.gamification

import java.io.Serializable

class GameInputData : Serializable {
    var title: String? = null
    var message: String? = null
}

interface Callback {
    fun onSetData(value: String?)
    fun onClearData()
}