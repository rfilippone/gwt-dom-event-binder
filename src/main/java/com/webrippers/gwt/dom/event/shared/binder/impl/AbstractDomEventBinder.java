/*
 * Copyright 2013 webrippers.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.webrippers.gwt.dom.event.shared.binder.impl;

import java.util.List;

import com.google.web.bindery.event.shared.HandlerRegistration;
import com.webrippers.gwt.dom.event.shared.binder.DomEventBinder;

public abstract class AbstractDomEventBinder<T> implements DomEventBinder<T> {

    @Override
    public final HandlerRegistration bindEventHandlers(T target) {
        final List<HandlerRegistration> registrations = doBindEventHandlers(target);
        return new HandlerRegistration() {
            @Override
            public void removeHandler() {
                for (HandlerRegistration registration : registrations) {
                    registration.removeHandler();
                }
                registrations.clear();
            }
        };
    }

    /**
     * Implemented by DOMEventBinderGenerator to do the actual work of binding event handlers on the target.
     */
    protected abstract List<HandlerRegistration> doBindEventHandlers(T target);
}