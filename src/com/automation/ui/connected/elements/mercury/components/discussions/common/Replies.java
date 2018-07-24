package com.automation.ui.connected.elements.mercury.components.discussions.common;

import com.automation.ui.base.common.core.UIWebDriver;
import com.automation.ui.base.pageobjectsfactory.pageobject.BasePageObject;
import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.concurrent.TimeUnit;

public class Replies extends BasePageObject {

    @FindBy(className = "replies-list")
    private WebElement webElement;

    @FindBy(css = ".discussion-reply .discussion-content")
    private WebElement replyContent;

    @FindBy(css = ".discussion-reply .og-container")
    private WebElement replyOpenGraph;

    public boolean hasNoRepliesIcon() {
        return null != webElement.findElement(By.cssSelector(".icon.no-replies"));
    }

    public boolean isEmpty() {
        WebElement element = webElement.findElement(By.cssSelector("div:first-of-type"));
        return element.getAttribute("class").contains("discussion-no-replies");
    }

    public String getNoRepliesMessage() {
        return webElement.findElement(By.className("discussion-no-replies")).getText();
    }

    public Replies waitForReplyToAppearWithText(final String text) {
        //NEEDTOCHECK
        new FluentWait<>(driver)
                .withTimeout(DiscussionsConstants.TIMEOUT, TimeUnit.SECONDS)
                .until((Function<UIWebDriver, Boolean>) input -> wait.forElementVisibleW(replyContent)
                        .getText()
                        .contains(text));

        return this;
    }

    public Replies waitForReplyToAppearWithOpenGraph(final String url) {
        //NEEDTOCHECK
        new FluentWait<>(driver)
                .withTimeout(DiscussionsConstants.TIMEOUT, TimeUnit.SECONDS)
                .until((Function<UIWebDriver, Boolean>) input -> wait.forElementVisibleW(replyOpenGraph)
                        .getAttribute("href")
                        .contains(url));
        return this;
    }

    public Reply getNewestReply() {
        return new Reply(webElement.findElement(By.className("post-reply")));
    }
}
