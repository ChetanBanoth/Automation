package com.automation.ui.connected.pageobjectsfactory.pageobject.special;

import com.automation.ui.connected.pageobjectsfactory.pageobject.SiteBasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ArticleHistoryPage extends SiteBasePageObject {

    private static String ARTICLE_HISTORY_FORMAT = "%s?action=history";
    private static String SPECIFIC_ARTICLE_FORMAT = "AnonymizationTest?oldid=%s";
    @FindBy(css = ".mw-userlink")
    private List<WebElement> activityList;
    @FindBy(css = ".mw-userlink")
    private WebElement anonName;
    @FindBy(css = "#pagehistory li")
    private List<WebElement> history;

    public ArticleHistoryPage open(String articleName) {
        getUrl(urlBuilder.getUrlForPage(String.format(ARTICLE_HISTORY_FORMAT, articleName)));

        this.refreshPageAddingCacheBuster();

        return this;
    }

    public ArticleHistoryPage openArticleId(String articleId) {
        getUrl(urlBuilder.getUrlForPage(String.format(SPECIFIC_ARTICLE_FORMAT, articleId)));

        this.refreshPageAddingCacheBuster();

        return this;
    }

    public boolean isUserInHistory(String username) {
        for (WebElement element : activityList) {
            if (element.getText().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public String getHistoryID(String username) {

        for (WebElement element : history) {
            if (element.findElement(By.cssSelector(".mw-userlink")).getText().equals(username)) {
                return element.findElement(By.cssSelector("input")).getAttribute("value");
            }
        }
        return null;
    }

    public String getAnonByArticleID() {
        //NEEDTOCHECK
        wait.forElementVisibleW(anonName);
        return anonName.getText();
    }
}
