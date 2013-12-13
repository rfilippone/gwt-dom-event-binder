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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks a method to be automatically bound as an event handler for DOM events. 
 * The annotation values must be declared in the "ui:field" template attribute of a DOM element.
 * 
 * The method will start receiving events after it is bound to the DOM using a {@link DomEventBinder}. 
 * Only the parameter of the method is relevant; the name is ignored. 
 * For example, the given method will be invoked whenever a user clicks on the DOM element with ui:field="MyElement":
 * 
 * <pre>
 * {@literal @}DomEventHandler("myElement")
 * void onUserAction(ClickEvent event) {
 *   Window.alert("Hello World!");
 * }
 * </pre>
 *
 * Note that an {@link DomEventBinder} MUST be used to register these annotations,
 * otherwise they will have no effect.
 *
 * @see DomEventBinder
 * 
 * @author rfilippone@gmail.com (Roberto Filippone)
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DomEventHandler {
	/**
	 * The name of the DOM element to which the handler method will be bound
	 */
    String[] value();
}