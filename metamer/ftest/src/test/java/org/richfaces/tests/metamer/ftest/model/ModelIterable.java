/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2013, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *******************************************************************************/
package org.richfaces.tests.metamer.ftest.model;

import java.lang.reflect.Constructor;
import java.util.Iterator;

import org.jboss.arquillian.ajocado.locator.element.ExtendedLocator;

public class ModelIterable<E extends ExtendedLocator<E>, T extends AbstractModel<E>> implements Iterable<T> {

    Iterator<E> iterable;
    Class<T> classT;
    Class[] constructorParamTypes = new Class[] {};
    Object[] constructorParams = new Object[] {};

    public ModelIterable(Iterator<E> iterable, Class<T> classT) {
        this.iterable = iterable;
        this.classT = classT;
    }

    public ModelIterable(Iterator<E> iterable, Class<T> classT, Class[] constructorParamTypes, Object[] constructorParams) {
        this.iterable = iterable;
        this.classT = classT;
        this.constructorParamTypes = constructorParamTypes;
        this.constructorParams = constructorParams;
    }

    @Override
    public Iterator<T> iterator() {
        return new ModelIterator();
    }

    public class ModelIterator implements Iterator<T> {

        Iterator<E> iterator = iterable;

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            ExtendedLocator<E> locator = iterator.next();

            try {
            constructorLoop:
                for (Constructor<?> constructor : classT.getConstructors()) {
                    if (ExtendedLocator.class.isAssignableFrom(constructor.getParameterTypes()[0])) {
                        if (constructorParamTypes.length == constructor.getParameterTypes().length - 1) {
                            Object[] params = new Object[constructorParamTypes.length + 1];
                            for (int i = 0; i < constructorParamTypes.length; i++) {
                                if (constructorParamTypes[i] != constructor.getParameterTypes()[i+1]) {
                                    continue constructorLoop;
                                }
                                params[i+1] = constructorParams[i];
                            }
                            params[0] = locator;
                            T newInstance = (T) constructor.newInstance(params);
                            return newInstance;
                        }
                    }
                }
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            throw new NoSuchMethodError("There is no constructor for ExtendedLocator");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("unsupported operation");
        }
    }

}
