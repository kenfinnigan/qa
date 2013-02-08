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
package org.richfaces.tests.metamer.ftest.richPanelMenu;

import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.openqa.selenium.WebElement;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage;
import org.richfaces.tests.page.fragments.impl.panelMenu.RichPanelMenu;

/**
 * @author <a href="jjamrich@redhat.com">Jan Jamrich</a>
 *
 */
public class PanelMenuPage extends MetamerPage {

    @FindBy( css = "div.rf-pm[id$=panelMenu]")
    public RichPanelMenu panelMenu;

    @FindBy(css = "input[id$=expandAll]")
    public WebElement expandAll;

    @FindBy(css = "input[id$=expandAllBtn1]")
    public WebElement expandAllBtn1;

    @FindBy(css = "input[id$=collapseAll]")
    public WebElement collapseAll;

    @FindBy(css = "input[id$=collapseAllBtn1]")
    public WebElement collapseAllBtn1;

    @FindBy(css = "input[id$=selectItem]")
    public WebElement selecItem;

    @FindBy(css = "input[id$=selectItemBtn1]")
    public WebElement selectItemBtn1;

    @FindBy(css = "span[id$=current]")
    public WebElement selectedItem;
}