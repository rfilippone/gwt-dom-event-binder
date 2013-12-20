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

import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Interface for generated code that connects an DOM Events to some event handlers. Use
 * GWT.create() to get instances of this interface. The generated class will
 * search the target's methods for any annotated with {@link DomEventHandler} and
 * register a handler on the DOM that calls that method. The first and
 * only parameter of the annotated methods must specify the type of event to
 * handle; the method's name is ignored.
 * <p>
 * Users of this interface should define an interface which extends
 * DomEventBinder, and invoke bindEventHandlers on an instance of that interface in
 * the class's constructor:
 *
 * <pre>
 * interface MyDomEventBinder extends DomEventBinder&lt;MyClass&gt; {}
 * private static MyDomEventBinder domEventBinder = GWT.create(MyDomEventBinder.class);
 *
 * public MyClass() {
 *   domEventBinder.bindEventHandlers(this);
 * }
 * 
 * {@literal @}DomEventHandler
 * void onSomeEvent(ClickEvent event) {
 *   // Interesting stuff goes here...
 * }
 * </pre>
 *
 * @param <T> type of object being bound, which should be the same as the type
 *        enclosing this interface
 * 
 * @author rfilippone@gmail.com (Roberto Filippone)        
 */
public interface DomEventBinder<T> {
	/**
	 * Connects DOM events to each event handler method on a target object.
	 * After this method returns, whenever the DOM triggers an event, it
	 * will call the handler with the same event type on the given target.
	 * Is equivalent to bindEventHandlers(target, (IsWidget) target);
	 * 
	 * @param target
	 *            class to search for {@link DomEventHandler}-annotated methods
	 *            
	 * @return a registration that can be used to unbind all handlers registered
	 *         via this call
	 */
    public HandlerRegistration bindEventHandlers(T target);

    /**
	 * Connects DOM events to each event handler method on a target object.
	 * After this method returns, whenever the DOM triggers an event, it
	 * will call the handler with the same event type on the given target.
     * 
     * @param target class to search for {@link DomEventHandler}-annotated methods
     * 
     * @param widget on which the event handlers will be attached
     *  
     * @return a registration that can be used to unbind all handlers registered
	 *         via this call
     */
    public HandlerRegistration bindEventHandlers(T target, IsWidget widget);
}