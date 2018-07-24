package com.automation.ui.connected.elements.oasis.components.video;

import com.automation.ui.base.pageobjectsfactory.pageobject.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class VideoTile extends BasePageObject {

    WebElement parentElement;
    private By titleBy = By.cssSelector(".title");
    private By addedByBy = By.cssSelector(".by-views a");

    public VideoTile(WebElement parentElement) {
        super();
        this.parentElement = parentElement;
    }

    public String getTitle() {
        return parentElement.findElement(titleBy).getText();
    }

}
