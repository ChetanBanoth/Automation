package com.automation.ui.connected.pageobjectsfactory.pageobject.special;

import com.automation.ui.base.common.contentpatterns.URLsContent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SpecialWhatLinksHerePageObject extends SpecialPageObject {
    @FindBy(css = "input[name=target]")
    private WebElement pageInputField;
    @FindBy(css = ".namespaceselector + input[type=submit]")
    private WebElement showButton;
    @FindBy(css = "#mw-whatlinkshere-list")
    private List<WebElement> whatLinksList;
    @FindBy(css = "#mw-content-text > fieldset > legend")
    private WebElement filtersSection;

    public SpecialWhatLinksHerePageObject open() {
        getUrl(urlBuilder.getUrlForPage(URLsContent.SPECIAL_WHAT_LINKS_HERE));

        return this;
    }

    public SpecialWhatLinksHerePageObject clickShowButton() {
        //NEEDTOCHECK
        wait.forElementVisibleW(showButton);
        showButton.click();
        return this;
    }

    public SpecialWhatLinksHerePageObject clickPageInputField() {
        //NEEDTOCHECK
        wait.forElementVisibleW(pageInputField);
        pageInputField.click();
        return this;
    }

    public SpecialWhatLinksHerePageObject typeInfoboxImageName(String imageFileName) {
        pageInputField.sendKeys(imageFileName);
        return this;
    }

    public boolean whatLinksHereContainsArticleName(String articleName) {
        boolean containsArticleName = false;
        for (int i = 0; i < whatLinksList.size(); i++) {
            //NEEDTOCHECK
            wait.forElementVisibleW(whatLinksList.get(i));
            if (whatLinksList.get(i).getText().contains(articleName)) {
                containsArticleName = true;
            }
        }

        return containsArticleName;
    }
}
