/**
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and individual contributors
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
package org.richfaces.tests.metamer.ftest.richCalendar;

import static org.jboss.arquillian.ajocado.utils.URLUtils.buildUrl;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.joda.time.DateTime;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.richfaces.tests.metamer.model.CalendarModel;
import org.richfaces.tests.page.fragments.impl.calendar.common.dayPicker.CalendarDay;
import org.richfaces.tests.page.fragments.impl.calendar.common.dayPicker.CalendarDays;
import org.richfaces.tests.page.fragments.impl.calendar.common.dayPicker.DayPicker;
import org.testng.annotations.Test;

/**
 * Test case for @preloadDateRangeBegin and @preloadDateRangeEnd
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 * @since 4.3.0.CR2
 */
public class TestPreloadedDates extends AbstractCalendarTest {

    private static final DateTime secondOfJanuary2012 = new DateTime(2012, 1, 2, 12, 0);
    private static final DateTime firstOfFebruary2012 = new DateTime(2012, 2, 1, 12, 0);
    @Inject
    @Use(empty = false)
    private DateTime date;
    private DateTime[] inBoundsDates = { secondOfJanuary2012, firstOfFebruary2012, secondOfJanuary2012.plusYears(1) };
    private DateTime[] outOfBounds = { secondOfJanuary2012.minusMonths(1), firstOfFebruary2012.plusYears(1) };

    private void checkClasses(boolean inBoundaries) {
        int sundays = 1;
        int tuesdays = 3;
        int thursdays = 5;
        int saturdays = 7;

        DayPicker proxiedDayPicker = calendar.getPopup().getProxiedDayPicker();
        CalendarDays weekends = proxiedDayPicker.getSpecificDays(sundays);
        weekends.addAll(proxiedDayPicker.getSpecificDays(saturdays));
        weekends.removeSpecificDays(CalendarDay.DayType.boundaryDay);
        for (CalendarDay day : weekends) {
            if (inBoundaries) {
                assertTrue(day.containsStyleClass(CalendarModel.WEEKEND_DAY_CLASS), "Weekends are not marked correctly (yellow color).");
                assertFalse(day.is(CalendarDay.DayType.clickable), "Weekends should not be selectable.");
            } else {
                assertFalse(day.containsStyleClass(CalendarModel.WEEKEND_DAY_CLASS), "Weekends should not be marked (yellow color) after/before preloaded dates.");
                assertTrue(day.is(CalendarDay.DayType.clickable), "Weekends should be selectable after/before preloaded dates..");
            }
        }

        CalendarDays busyDays = proxiedDayPicker.getSpecificDays(tuesdays);
        busyDays.addAll(proxiedDayPicker.getSpecificDays(thursdays));
        busyDays.removeSpecificDays(CalendarDay.DayType.boundaryDay);
        for (CalendarDay day : busyDays) {
            if (inBoundaries) {
                assertTrue(day.containsStyleClass(CalendarModel.BUSY_DAY_CLASS), "Tuesdays and thursdays should be busy (aqua color).");
                assertFalse(day.is(CalendarDay.DayType.clickable), "Busy days should not be selectable.");
            } else {
                assertFalse(day.containsStyleClass(CalendarModel.BUSY_DAY_CLASS), "Tuesdays and thursdays should not be busy (aqua color) after/before preloaded dates.");
                assertTrue(day.is(CalendarDay.DayType.clickable), "Busy days should be selectable after/before preloaded dates.");
            }

        }
    }

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richCalendar/preloadedDates.xhtml");
    }

    @Test
    @Use(field = "date", value = "inBoundsDates")
    public void testClassesInPreloadedBoundaries() {
        calendar.openPopup().setDateTime(date);
        checkClasses(Boolean.TRUE);
    }

    @Test
    @Use(field = "date", value = "outOfBounds")
    public void testClassesOutOfPreloadedBoundaries() {
        calendar.openPopup().setDateTime(date);
        checkClasses(Boolean.FALSE);
    }
}
