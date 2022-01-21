/*
 * Copyright 2022 Surf LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.surfstudio.mvi.flow.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.surfstudio.mvi.core.event.Event
import ru.surfstudio.mvi.core.reducer.Reducer
import ru.surfstudio.mvi.flow.FlowBinder
import ru.surfstudio.mvi.flow.FlowEventHub
import ru.surfstudio.mvi.flow.FlowState
import ru.surfstudio.mvi.flow.DslFlowMiddleware

/**
 * An interface of ViewModel providing implementations of observable
 * state and hub of events based on Coroutines Flow
 */
abstract class MviViewModel<S : Any, E : Event> : ViewModel(), FlowBinder {

    abstract val state: FlowState<S>
    abstract val hub: FlowEventHub<E>
    abstract val reducer: Reducer<E, S>
    abstract val middleware: DslFlowMiddleware<E>

    /** Must be called in descendant class `init` */
    fun init() {
        viewModelScope.bind(hub, middleware, state, reducer)
    }
}