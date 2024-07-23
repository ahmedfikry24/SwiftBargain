package com.example.swiftbargain.ui.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private var job: Job? = null
fun <T> eventHandler(
    event: SharedFlow<T>,
    scope: CoroutineScope,
    body: (T) -> Unit
) {
    job?.cancel()
    job = scope.launch {
        event.collectLatest {
            delay(500)
            body(it)
        }
    }
}
