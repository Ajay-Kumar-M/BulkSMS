package com.ajay.bulksms.components

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
fun <T> Flow<T>.scopeCollect(scope: CoroutineScope, action: (T) -> Unit) {
    scope.launch {
        this@scopeCollect.debounce(1000).collectLatest {
            action.invoke(it)
        }
    }
}