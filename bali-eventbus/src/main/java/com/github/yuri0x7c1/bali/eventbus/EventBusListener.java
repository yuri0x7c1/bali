/*
 * Copyright 2015 The original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yuri0x7c1.bali.eventbus;

import java.io.Serializable;

/**
 * Interface to be implemented by listeners that want to subscribe to an {@link com.github.yuri0x7c1.bali.eventbus.EventBus}.
 *
 * @param <T> type of event payloads that the listener is interested in receiving.
 * @author Petter Holmström (petter@vaadin.com)
 */
public interface EventBusListener<T> extends Serializable {

    /**
     * Called when an event has been received.
     *
     * @param event the event, never {@code null}.
     */
    void onEvent(Event<T> event);

}
