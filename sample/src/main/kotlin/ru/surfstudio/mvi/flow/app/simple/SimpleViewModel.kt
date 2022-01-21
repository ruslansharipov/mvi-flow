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
package ru.surfstudio.mvi.flow.app.simple

import ru.surfstudio.mvi.flow.FlowEventHub
import ru.surfstudio.mvi.flow.FlowState
import ru.surfstudio.mvi.flow.lifecycle.MviViewModel

class SimpleViewModel : MviViewModel<SimpleState, SimpleEvent>() {

    override val state: FlowState<SimpleState> = FlowState(SimpleState())
    override val hub: FlowEventHub<SimpleEvent> = FlowEventHub()
    override val middleware: SimpleMiddleware = SimpleMiddleware(state)
    override val reducer: SimpleReducer = SimpleReducer()

    init {
        init()
    }
}