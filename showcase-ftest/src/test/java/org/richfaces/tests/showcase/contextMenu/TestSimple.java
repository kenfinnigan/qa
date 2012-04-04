/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2012, Red Hat, Inc. and individual contributors
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
package org.richfaces.tests.showcase.contextMenu;

import static org.jboss.arquillian.ajocado.Graphene.elementVisible;
import static org.jboss.arquillian.ajocado.Graphene.waitGui;

import static org.jboss.arquillian.ajocado.locator.LocatorFactory.jq;

import static org.testng.Assert.assertTrue;

import org.jboss.arquillian.ajocado.geometry.Point;
import org.jboss.arquillian.ajocado.locator.JQueryLocator;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jhuska@redhat.com">Juraj Huska</a>
 * @version $Revision$
 */
public class TestSimple extends AbstractContextMenuTest {

    /* *****************************************************
     * Locators*****************************************************
     */
    private JQueryLocator picture = jq("img[id$=pic]");

    private JQueryLocator contextMenuZoomIn = jq("div[id$=zin]");
    private JQueryLocator contextMenuZoomOut = jq("div[id$=zout]");

    /* ******************************************************
     * Tests ****************************************************
     */
    @Test
    public void testZoomIn() {
        zoom(true);
    }

    @Test
    public void testZoomOut() {
        zoom(false);
    }

    @Test
    public void testContextMenuRenderedAtCorrectPosition() {

        checkContextMenuRenderedAtCorrectPosition(picture, new Point(20, 20), false);
    }

    /* ********************************************************
     * Help methods********************************************************
     */
    private void zoom(boolean in) {

        int widthBeforeZoom = selenium.getElementWidth(picture);

        selenium.focus(picture);
        selenium.clickAt(picture, new Point(20, 20));

        waitGui.failWith(new RuntimeException("The context menu should be visible")).timeout(2000)
            .until(elementVisible.locator(contextMenu));

        if (in) {
            selenium.click(contextMenuZoomIn);
        } else {
            selenium.click(contextMenuZoomOut);
        }

        int widthAfterZoom = selenium.getElementWidth(picture);

        if (in) {
            assertTrue(widthAfterZoom > widthBeforeZoom,
                "The width of the picture should be bigger after zoom in triggered from context menu!");
        } else {
            assertTrue(widthAfterZoom < widthBeforeZoom,
                "The width of the picture should be smaller after zoom out triggered from context menu");
        }

    }
}
