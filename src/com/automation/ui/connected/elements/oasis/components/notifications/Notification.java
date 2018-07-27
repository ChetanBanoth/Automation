package com.automation.ui.connected.elements.oasis.components.notifications;

import com.automation.ui.base.pageobjectsfactory.pageobject.BasePageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class Notification extends BasePageObject {

    private WebElement parentElement;
    private WebElement messageTextBox;

    private By messageBy = By.className("wds-banner-notification__text");
    private By undeleteLinkBy = By.cssSelector(".wds-banner-notification__text a");

    public Notification(WebDriver driver, WebElement parentElement) {
        super();
        this.parentElement = parentElement;
        this.messageTextBox = parentElement.findElement(messageBy);
    }

    public String getMessage() {
        return messageTextBox.getText();
    }

    public String getType() {
        String classString = parentElement.getAttribute("class");
        if (classString == null) {
            throw new NullPointerException("Couldn't get class attribute from notification");
        }
        String[] classArray = classString.split(" ", 2);
        return classArray[classArray.length - 1];
    }



    public boolean isVisible() {
        return parentElement.isDisplayed();
    }
}
