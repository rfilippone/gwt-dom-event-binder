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

package com.webrippers.gwt.dom.event.shared.binder;

import com.google.web.bindery.event.shared.HandlerRegistration;

public interface DomEventBinder<T> {
    public HandlerRegistration bindEventHandlers(T target);
}