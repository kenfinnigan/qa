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

import static org.jboss.arquillian.ajocado.Ajocado.elementVisible;
import static org.jboss.arquillian.ajocado.Ajocado.guardXhr;
import static org.jboss.arquillian.ajocado.Ajocado.waitGui;

import static org.jboss.arquillian.ajocado.locator.LocatorFactory.jq;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Iterator;

import org.jboss.arquillian.ajocado.geometry.Point;
import org.jboss.arquillian.ajocado.locator.JQueryLocator;
import org.richfaces.tests.showcase.tree.AbstractTreeTest;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jhuska@redhat.com">Juraj Huska</a>
 * @version $Revision$
 */
public class TestTree extends AbstractTreeTest {

    /* *****************************************
     * Locators*****************************************
     */
    private JQueryLocator nodeExpander = jq(".rf-trn-hnd.rf-trn-hnd-colps");
    private JQueryLocator node = jq(".rf-trn-lbl");

    private JQueryLocator contextMenu = jq(".rf-ctx-lst");
    private JQueryLocator contextMenuItem = jq(contextMenu.getRawLocator() + " .rf-ctx-itm-lbl");

    private JQueryLocator popupContent = jq("div[id=popup_content]");
    private JQueryLocator closeButtonPopup = jq("input[type=button]");

    /* *********************************************
     * Tests*********************************************
     */
    @Test
    public void testSelectNodesByCtxMenu() {

        collapseOrExpandAllNodes(nodeExpander);

        for (Iterator<JQueryLocator> i = node.iterator(); i.hasNext();) {
            JQueryLocator currentNode = i.next();
            String text = selenium.getText(currentNode);
            if (text.contains("-")) {
                text = text.substring(0, text.indexOf('-'));
            }

            guardXhr(selenium).clickAt(currentNode, new Point(0, 0));
            selenium.contextMenuAt(currentNode, new Point(2, 5));

            waitGui.failWith(new RuntimeException("The context menu should be visible, when right clicking on node"))
                .timeout(3000).until(elementVisible.locator(contextMenu));

            selenium.click(contextMenuItem);

            waitGui.failWith(new RuntimeException("The popup should be visible when triggering it from context menu!"))
                .timeout(3000).until(elementVisible.locator(popupContent));

            String actualText = selenium.getText(popupContent);
            assertTrue(actualText.contains(text),
                "The text in the popup triggered from context menu, does not contain selected text");

            selenium.click(closeButtonPopup);
        }
    }

    @Test
    public void testContextMenuPosition() {
        Point offset = new Point(2,5);
        JQueryLocator target = jq(node.getRawLocator() + ":eq(0)");
        guardXhr(selenium).clickAt(node, new Point(0, 0));
        selenium.contextMenuAt( target , offset );

        waitGui.failWith(new RuntimeException("The context menu should be visible")).timeout(2000)
            .until(elementVisible.locator(contextMenu));

        Point actualContextMenuPosition = selenium.getElementPosition(contextMenu);
        Point targetPosition = selenium.getElementPosition(target);
        Point expectedContextMenuPosition = targetPosition.add(offset);

        assertEquals(actualContextMenuPosition.getX(), expectedContextMenuPosition.getX());
        assertEquals(actualContextMenuPosition.getY(), expectedContextMenuPosition.getY());
    }
}