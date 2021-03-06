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
package org.richfaces.tests.metamer.ftest.richList;

import static java.lang.Math.max;
import static java.lang.Math.min;

import static org.jboss.arquillian.ajocado.dom.Event.CLICK;
import static org.jboss.arquillian.ajocado.dom.Event.DBLCLICK;
import static org.jboss.arquillian.ajocado.dom.Event.KEYDOWN;
import static org.jboss.arquillian.ajocado.dom.Event.KEYPRESS;
import static org.jboss.arquillian.ajocado.dom.Event.KEYUP;
import static org.jboss.arquillian.ajocado.dom.Event.MOUSEDOWN;
import static org.jboss.arquillian.ajocado.dom.Event.MOUSEMOVE;
import static org.jboss.arquillian.ajocado.dom.Event.MOUSEOUT;
import static org.jboss.arquillian.ajocado.dom.Event.MOUSEOVER;
import static org.jboss.arquillian.ajocado.dom.Event.MOUSEUP;

import static org.jboss.test.selenium.locator.utils.LocatorEscaping.jq;
import static org.richfaces.tests.metamer.ftest.attributes.AttributeList.listAttributes;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.jboss.arquillian.ajocado.dom.Event;
import org.richfaces.component.ListType;
import org.richfaces.tests.metamer.bean.Model;
import org.richfaces.tests.metamer.ftest.AbstractGrapheneTest;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.richfaces.tests.metamer.model.Employee;
import org.testng.annotations.BeforeMethod;

public abstract class AbstractListTest extends AbstractGrapheneTest {
    protected static List<Employee> employees = Model.unmarshallEmployees();
    protected static final int ELEMENTS_TOTAL = employees.size();
    protected static final Event[] EVENTS = { CLICK, DBLCLICK, KEYDOWN, KEYPRESS, KEYUP, MOUSEDOWN, MOUSEMOVE,
        MOUSEOUT, MOUSEOVER, MOUSEUP };
    protected static final Integer[] INTS = { -1, 0, 1, ELEMENTS_TOTAL / 2, ELEMENTS_TOTAL - 1, ELEMENTS_TOTAL,
        ELEMENTS_TOTAL + 1 };

    ListModel list;

    @Inject
    @Use(empty = true)
    Integer first;

    @Inject
    @Use(empty = true)
    Integer rows;

    @Inject
    @Use(empty = true)
    Event event;

    ListType type = ListType.ordered;

    int expectedBegin;
    int displayedRows;
    int expectedEnd;
    List<Employee> expectedEmployees;

    @BeforeMethod(alwaysRun = true)
    public void prepareAttributes() {
        list = new ListModel(jq("*[id$=richList]"));
        listAttributes.set(ListAttributes.type, type);
        list.setType(type);

        if (rows == null) {
            rows = 20;
        }

        if (first != null) {
            listAttributes.set(ListAttributes.first, first);
        }
        if (rows != null) {
            listAttributes.set(ListAttributes.rows, rows);
        }
    }

    protected void verifyList() {
        countExpectedValues();
        verifyCounts();
        verifyRows();
    }

    private void verifyCounts() {
        assertEquals(list.getTotalRowCount(), displayedRows);
    }

    private void verifyRows() {
        int rowCount = list.getTotalRowCount();
        for (int position = 1; position <= rowCount; position++) {
            Employee employee = expectedEmployees.get(position - 1);
            assertEquals(list.getRowText(position), employee.getName());
        }
    }

    private void countExpectedValues() {

        // expected begin

        if (first == null || first < 0) {
            expectedBegin = 0;
        } else {
            expectedBegin = first;
        }

        expectedBegin = minMax(0, expectedBegin, ELEMENTS_TOTAL);

        // expected displayed rows

        if (rows == null || rows < 1 || rows > ELEMENTS_TOTAL) {
            displayedRows = ELEMENTS_TOTAL;
        } else {
            displayedRows = rows;
        }

        if (first != null && first < 0) {
            displayedRows = 0;
        }

        displayedRows = min(displayedRows, ELEMENTS_TOTAL - expectedBegin);

        // expected end

        if (rows == null || rows < 1) {
            expectedEnd = ELEMENTS_TOTAL - 1;
        } else {
            expectedEnd = rows - 1;
        }

        expectedEmployees = employees.subList(expectedBegin, expectedBegin + displayedRows);
    }

    private int minMax(int min, int value, int max) {
        return max(0, min(max, value));
    }
}
