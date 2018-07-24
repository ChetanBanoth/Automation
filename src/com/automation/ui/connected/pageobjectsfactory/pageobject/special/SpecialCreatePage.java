package com.automation.ui.connected.pageobjectsfactory.pageobject.special;

import com.automation.ui.base.common.contentpatterns.URLsContent;
import com.automation.ui.connected.pageobjectsfactory.pageobject.article.editmode.VisualEditModePageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SpecialCreatePage extends SpecialPageObject {

    @FindBy(css = "#HiddenFieldsDialog input[name='wpTitle']")
    private WebElement titleInput;
    @FindBy(css = "#HiddenFieldsDialog #ok")
    private WebElement submitTitleInput;

    public SpecialCreatePage() {
        super();
    }


    public SpecialCreatePage open(String wikiURL) {
        getUrl(wikiURL + URLsContent.SITE_DIR + URLsContent.SPECIAL_CREATE_PAGE);
        return this;
    }

    public SpecialCreatePage open() {
        return open(urlBuilder.getUrl());
    }

    public VisualEditModePageObject populateTitleField(String title) {
        //NEEDTOCHECK
        wait.forElementVisibleW(titleInput);
        titleInput.sendKeys(title);
        wait.forElementClickable(submitTitleInput);
        submitTitleInput.click();
        waitForElementNotVisibleByElement(submitTitleInput);
        return new VisualEditModePageObject();
    }
}
