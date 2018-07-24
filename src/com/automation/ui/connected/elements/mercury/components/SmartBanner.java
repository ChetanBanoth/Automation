package com.automation.ui.connected.elements.mercury.components;

import com.automation.ui.base.common.core.Assertion;
import com.automation.ui.base.common.core.element.JavascriptActions;
import com.automation.ui.base.common.core.element.Wait;
import com.automation.ui.base.common.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SmartBanner {

    private static final int SMART_BANNER_POS_Y = 50;
    @FindBy(css = ".fandom-app-smart-banner__close")
    private WebElement closeButton;
    private By smartBannerComponent = By.cssSelector(".fandom-app-smart-banner");
    private Wait wait;
    private JavascriptActions jsActions;
    private WebDriver driver;

    public SmartBanner(WebDriver driver) {
        this.wait = new Wait(driver);
        this.jsActions = new JavascriptActions(driver);
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public SmartBanner close() {
        Log.info("Close smart banner");
        wait.forElementClickable(closeButton);
        closeButton.click();

        Log.info("Smart banner is closed");
        wait.forElementNotVisible(smartBannerComponent);

        return this;
    }

    public SmartBanner scrollDown() {
        Log.info("Smart banner is visible");
        wait.forElementVisible(smartBannerComponent);

        Log.info("Scroll down");
        jsActions.scrollBy(0, 100);

        Log.info("Smart banner position is not fixed at the top");
        wait.forElementPresent(smartBannerComponent);
        Assertion.assertFalse(
                driver.findElement(smartBannerComponent).getLocation().getY() == SMART_BANNER_POS_Y,
                "Smart banner position is not fixed at the top"
        );

        return this;
    }

    public SmartBanner scrollUp() {
        Log.info("Scroll up");
        jsActions.scrollBy(0, -100);

        Log.info("Smart banner is visible");
        wait.forElementVisible(smartBannerComponent);

        return this;
    }
}
