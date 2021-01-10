package com.onmobile.sdk.gamification

interface GameCallback {
    fun onPreOpen()
    fun onOpen()
    fun onClose(resultData: GameResultData?)
}