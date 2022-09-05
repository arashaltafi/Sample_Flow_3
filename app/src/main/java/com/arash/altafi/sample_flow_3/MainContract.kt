package com.arash.altafi.sample_flow_3

data class MainState(
    val watchState: WatchState = WatchState.IDLE,
    val seconds: Long = 0,
) {
    enum class WatchState {
        RUNNING,
        PAUSED,
        IDLE
    }
}

enum class MainAction {
    START,
    PAUSE,
    RESET
}