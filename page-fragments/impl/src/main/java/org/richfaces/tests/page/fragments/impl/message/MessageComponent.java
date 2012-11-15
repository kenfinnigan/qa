/**
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and individual contributors
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
 */
package org.richfaces.tests.page.fragments.impl.message;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public interface MessageComponent {

    public enum MessageType {

        fatal("rf-msg-ftl"),
        error("rf-msg-err"),
        information("rf-msg-inf"),
        warning("rf-msg-wrn"),
        ok("rf-msg-ok");
        //
        private final String value;

        private MessageType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    String getDetail();

    WebElement getMessageDetailElement();

    WebElement getMessageSummaryElement();

    WebElement getRoot();

    String getSummary();

    ExpectedCondition<Boolean> isDetailNotVisibleCondition();

    boolean isDetailVisible();

    ExpectedCondition<Boolean> isDetailVisibleCondition();

    ExpectedCondition<Boolean> isNotVisibleCondition();

    ExpectedCondition<Boolean> isSummaryNotVisibleCondition();

    boolean isSummaryVisible();

    ExpectedCondition<Boolean> isSummaryVisibleCondition();

    boolean isType(MessageType type);

    boolean isVisible();

    ExpectedCondition<Boolean> isVisibleCondition();
}
