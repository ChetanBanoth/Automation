package com.automation.ui.connected.elements.fandom.components;

import com.automation.ui.base.pageobjectsfactory.pageobject.BasePageObject;
import com.automation.ui.connected.common.core.helpers.FandomUser;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WPLoginBox extends BasePageObject {

    @FindBy(css = "#user_login")
    private WebElement username;

    @FindBy(css = "#user_pass")
    private WebElement password;

    @FindBy(css = "#wp-submit")
    private WebElement submit;

    public WPLoginBox login(FandomUser user) {
        username.sendKeys(user.getUsername());
        password.sendKeys(user.getPassword());
        submit.click();

        return this;
    }
}
