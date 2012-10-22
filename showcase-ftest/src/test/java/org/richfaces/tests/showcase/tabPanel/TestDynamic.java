package org.richfaces.tests.showcase.tabPanel;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.tests.showcase.AbstractWebDriverTest;
import org.testng.annotations.Test;

public class TestDynamic extends AbstractWebDriverTest {

    @FindBy(css = ".example-cnt td.rf-tab-hdr-inact")
    private List<WebElement> tabsSwitchers;

    @FindBy(css = ".rf-tab")
    private List<WebElement> tabsContent;

    @Test
    public void testSwitchingBetweenTabs() {
        clickOnAllTabs(tabsSwitchers);

        for (WebElement i : tabsSwitchers) {
            i.click();

            String header = i.getText();

            // first default tab
            if (header.contains("Static")) {
                continue;
            } else {
                String foo = tabsContent.get(0).getText();
            }

        }
    }

    private void clickOnAllTabs(List<WebElement> tabs) {
        for (WebElement i : tabs) {
            try {
                i.click();
            } catch (org.openqa.selenium.ElementNotVisibleException ex) {
                // it is ok for the first tab
            }
        }
    }
}
