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