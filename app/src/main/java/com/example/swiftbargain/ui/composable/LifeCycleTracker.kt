package com.example.swiftbargain.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun LifeCycleTracker(
    event: Lifecycle.Event = rememberLifeCycleObserver(),
    tracker: (Lifecycle.Event) -> Unit
) {
    LaunchedEffect(event) {
        tracker(event)
    }
}

@Composable
fun rememberLifeCycleObserver(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
): Lifecycle.Event {
    var currentEvent by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            currentEvent = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
    return currentEvent
}