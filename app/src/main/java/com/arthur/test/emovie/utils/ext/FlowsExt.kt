package com.arthur.test.emovie.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Launch and repeat in lifecycle for current Fragment instance.
 *
 * @author Arturo Segura
 * @since 1.0
 */
fun Fragment.launchAndRepeatOnLifecycle(
    lifecycleCoroutineScope: LifecycleCoroutineScope = lifecycleScope,
    state: Lifecycle.State = Lifecycle.State.CREATED,
    block: CoroutineScope.() -> Unit,
) : Job = lifecycleCoroutineScope.launch {
    repeatOnLifecycle(state) { block.invoke(this) }
}

/**
 * Observe for the [flow] events.
 *
 * @author Arturo Segura
 * @since 1.0
 */
fun <T> CoroutineScope.observeFor(
    flow: Flow<T>,
    collector: (T) -> Unit = {},
) = launch {
    flow.collectLatest { collector.invoke(it) }
}