package com.diego.tweetssentimentsanalyzer.feature

open class ScreenState(val status: Int, val message: String) {
    fun isStatusOk(): Boolean = status == ScreenStatus.OK.status

    fun isLoading(): Boolean = status == ScreenStatus.LOADING.status

    fun isThereError(): Boolean = status == ScreenStatus.ERROR.status

    fun isInitialState(): Boolean = status == ScreenStatus.NORMAL.status
}