package com.automation.ui.connected.pageobjectsfactory.pageobject.special.block;

import com.automation.ui.base.common.logging.Log;
import com.automation.ui.connected.pageobjectsfactory.pageobject.SiteBasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SpecialBlockListPage extends SiteBasePageObject {

    private static final String SPECIAL_BLOCKLIST = "Special:BlockList";

    @FindBy(css = "#mw-input-wpTarget")
    private WebElement userNameField;
    @FindBy(css = "input.mw-htmlform-submit")
    private WebElement searchButton;
    @FindBy(xpath = "//p[contains(text(), 'The requested IP address or username is not blocked.')]")
    private WebElement userUnblockedMessage;
    @FindBy(css = ".mw-blocklist td:nth-child(3)")
    private WebElement expirationDateElement;

    public SpecialBlockListPage open() {
        getUrl(urlBuilder.getUrlForPage(SPECIAL_BLOCKLIST));
        Log.log("Open Special Block List Page", "blocked users list page opened", true);

        return this;
    }

    private void typeInUserName(String userName) {
        //NEEDTOCHECK
        wait.forElementVisibleW(userNameField);
        userNameField.sendKeys(userName);
        Log.log("Special:BlockList typeInUserName", userName + " typed in username field",
                true);
    }

    private void clickSearchButton() {
        //NEEDTOCHECK
        wait.forElementVisibleW(searchButton);
        scrollAndClick(searchButton);
        Log.log("Special:BlockList clickSearchButton", "search button clicked", true);
    }

    public void searchForUser(String userName) {
        typeInUserName(userName);
        clickSearchButton();
    }

    public void verifyUserUnblocked() {
        //NEEDTOCHECK
        wait.forElementVisibleW(userUnblockedMessage);
        Log.log("Special:BlockList verifyUSerUnblocked",
                "verified that user is not on blocked users list", true, driver);
    }

    public void verifyUserBlocked(String userName) {
        wait.forElementVisible(
                By.cssSelector("table td.TablePager_col_ipb_target a[href='/wiki/User:" + userName + "']"));
        Log.log("Special:BlockList verifyUSerUnblocked",
                "verified that user is on blocked users list", true, driver);
    }

    /**
     * this method checks if specified user is currently blocked
     *
     * @param username user to be checked
     * @return boolean value with the answer for the question
     */
    public boolean isUserBlocked(String username) {
        boolean isBlocked = false;
        searchForUser(username);
        if (!isElementOnPage(expirationDateElement)) {
            return isBlocked;
        }
        SimpleDateFormat blockListDateFormat = new SimpleDateFormat("HH:mm, MMMM dd, yyyy", Locale.US);
        String expirationDateText = expirationDateElement.getText();
        try {
            Date expirationDate = blockListDateFormat.parse(expirationDateText);
            Date currentDate = new Date();
            isBlocked = currentDate.before(expirationDate);
        } catch (ParseException ex) {
            throw new WebDriverException("Can't parse expirationDateText: " + expirationDateText);
        }
        Log.log("isUserBlocked", "user is" + (isBlocked ? " blocked" : "n't blocked"),
                true);
        return isBlocked;
    }
}
