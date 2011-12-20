/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
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
package org.richfaces.tests.archetypes.simpleapp.ftest;

import org.jboss.test.selenium.support.ui.TextEquals;
import org.jboss.test.selenium.support.ui.WebDriverWait;
import org.richfaces.tests.archetypes.simpleapp.AbstractWebDriverTest;
import org.testng.annotations.Test;

/**
 * <p>
 * Tests that input reacts to keyup events by sending XHR request and rerendering output as greeting to given name.
 * </p>
 * 
 * <p>
 * If input has empty value, output is also empty.
 * </p>
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public class TestInput extends AbstractWebDriverTest<WithInputPage> {

    private final String inputName = "RichFaces Fan";

    @Test
    public void testTypeName() {
        getPage().getInput().click();
        getPage().getInput().clear();
        getPage().getInput().sendKeys(inputName);
        new WebDriverWait(getWebDriver())
            .failWith("The output text doesn't match.")
            .until(TextEquals.getInstance().element(getPage().getOutput()).text("Hello " + inputName + "!"));
        
    }

    @Override
    protected WithInputPage createPage() {
        return new WithInputPage();
    }

}