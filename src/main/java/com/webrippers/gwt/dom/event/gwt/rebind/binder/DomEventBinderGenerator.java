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

package com.webrippers.gwt.dom.event.gwt.rebind.binder;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JGenericType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.webrippers.gwt.dom.event.shared.binder.DomEventBinder;
import com.webrippers.gwt.dom.event.shared.binder.DomEventHandler;
import com.webrippers.gwt.dom.event.shared.binder.impl.AbstractDomEventBinder;

/**
 * @author FirstName LastName
 * 
 */
public class DomEventBinderGenerator extends Generator {

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.core.ext.Generator#generate(com.google.gwt.core.ext.TreeLogger, com.google.gwt.core.ext.GeneratorContext, java.lang.String)
     */
    @Override
    public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
        try {
            TypeOracle typeOracle = context.getTypeOracle();
            JClassType eventBinderType = typeOracle.getType(typeName);
            JClassType targetType = getTargetType(eventBinderType, typeOracle);

            SourceWriter writer = createSourceWriter(logger, context, eventBinderType, targetType);
            if (writer != null) {

                write_doBindEventHandlers(targetType, typeOracle, writer);

                writer.commit(logger);
            }
            return getFullyQualifiedGeneratedClassName(eventBinderType);
        } catch (NotFoundException e) {
            logger.log(Type.ERROR, "Error generating " + typeName, e);
            throw new UnableToCompleteException();
        }
    }

    private void write_doBindEventHandlers(JClassType target, TypeOracle typeOracle, SourceWriter writer) {
        writer.println("protected List<HandlerRegistration> doBindEventHandlers(final %s target, IsWidget isWidget) {", target.getQualifiedSourceName());
        writer.indent();
        writer.println("List<HandlerRegistration> registrations = new LinkedList<HandlerRegistration>();");

        for (JMethod method : target.getMethods()) {
            DomEventHandler annotation = method.getAnnotation(DomEventHandler.class);
            if (annotation != null) {
                if (method.getParameters().length != 1)
                    break;

                JType paramJType = method.getParameters()[0].getType();
                String paramTypeQualifiedSourceName = paramJType.getQualifiedSourceName();

                JClassType paramJClassType = typeOracle.findType(paramTypeQualifiedSourceName);

                if (!paramJClassType.isAssignableTo(typeOracle.findType(DomEvent.class.getCanonicalName())))
                    break;

                JGenericType domEventJGenericType = typeOracle.findType(DomEvent.class.getCanonicalName()).isGenericType();
                JParameterizedType paramJParameterizedType = paramJClassType.asParameterizationOf(domEventJGenericType);

                JClassType handlerJClassType = paramJParameterizedType.getTypeArgs()[0];

                writer.println("registrations.add(isWidget.asWidget().addDomHandler(new " + paramJParameterizedType.getTypeArgs()[0].getParameterizedQualifiedSourceName() + "() {");
                writer.indent();
                writer.println(handlerJClassType.getOverridableMethods()[0].getReadableDeclaration(false, false, false, false, true) + " {");
                writer.indent();
                writer.println("if (Element.as(event.getNativeEvent().getEventTarget()) == target." + annotation.value()[0] + ") {");
                writer.indent();
                writer.println("target." + method.getName() + "(event);");
                writer.outdent();
                writer.println("}");
                writer.outdent();
                writer.println("}");
                writer.outdent();
                writer.println("}, " + paramTypeQualifiedSourceName + ".getType()));");
            }
        }

        writer.println("return registrations;");
        writer.outdent();
        writer.println("}");
    }

    private JClassType getTargetType(JClassType interfaceType, TypeOracle typeOracle) {
        JClassType[] superTypes = interfaceType.getImplementedInterfaces();
        JClassType domEventBinderType = typeOracle.findType(DomEventBinder.class.getCanonicalName());
        if (superTypes.length != 1
            || !superTypes[0].isAssignableFrom(domEventBinderType)
            || superTypes[0].isParameterized() == null
        ) {
            throw new IllegalArgumentException(interfaceType + " must extend DomEventBinder with a type parameter");
        }
        return superTypes[0].isParameterized().getTypeArgs()[0];
    }

    private SourceWriter createSourceWriter(TreeLogger logger, GeneratorContext context, JClassType eventBinderType, JClassType targetType) {
        String simpleName = getSimpleGeneratedClassName(eventBinderType);
        String packageName = eventBinderType.getPackage().getName();
        ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);

        composer.setSuperclass(AbstractDomEventBinder.class.getCanonicalName() + "<" + targetType.getName() + ">");
        composer.addImplementedInterface(eventBinderType.getName());

        composer.addImport(LinkedList.class.getCanonicalName());
        composer.addImport(List.class.getCanonicalName());
        composer.addImport(Element.class.getCanonicalName());
        composer.addImport(HandlerRegistration.class.getCanonicalName());
        composer.addImport(IsWidget.class.getCanonicalName());

        PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);
        return (printWriter != null) ? composer.createSourceWriter(context, printWriter) : null;
    }

    private String getSimpleGeneratedClassName(JClassType eventBinderType) {
        return eventBinderType.getName().replace('.', '_') + "Impl";
    }

    private String getFullyQualifiedGeneratedClassName(JClassType eventBinderType) {
        return new StringBuilder().append(eventBinderType.getPackage().getName()).append('.').append(getSimpleGeneratedClassName(eventBinderType)).toString();
    }
}
