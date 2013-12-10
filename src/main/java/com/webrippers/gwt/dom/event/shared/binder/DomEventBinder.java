package com.webrippers.gwt.dom.event.shared.binder;

import com.google.web.bindery.event.shared.HandlerRegistration;

public interface DomEventBinder<T> {
    public HandlerRegistration bindEventHandlers(T target);
}