package com.automation.ui.connected.pageobjectsfactory.pageobject.chatpageobject;

import com.automation.ui.base.common.contentpatterns.URLsContent;
import com.automation.ui.base.common.core.Assertion;
import com.automation.ui.base.common.logging.Log;
import com.automation.ui.connected.pageobjectsfactory.pageobject.SiteBasePageObject;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ChatPage extends SiteBasePageObject {

    private static final String USER_UNBAN_LINK = "//a[@data-type='ban-undo' and @data-user='%s']";
    private static final String USER_UNBAN_CONFIRM_MESSAGE =
            "//div[@class='Chat']//li[contains(text(), 'has ended the Chat ban for %s')]";
    private static final String USER_SELECTOR = "#user-%s";
    private static final String PRIVATE_MESSAGE_USER_SELECTOR = "#priv-user-%s";
    private static final String PATH_MESSAGE_ON_CHAT = "//span[@class='message' and contains(text(), '%1$s')] | " +
            "//span[@class='message']//*[contains(text(), '%1$s')]";
    private static final String NOTIFICATION_COUNTER =
            "//span[@class='splotch' and contains(text(), '%s')]";
    private static final String PATH_EMOTICON_ON_CHAT = "//span[@class='message']/img[contains(@src,'%s')]";
    private static final int REGULAR_USER_DROPDOWN_ELEMENTS_COUNT = 3;
    private final By newMessageTextBoxBy = By.cssSelector(".message");
    @FindBy(css = "textarea[name='message']")
    private WebElement messageWritingArea;
    @FindBy(css = "div.Rail")
    private WebElement sideBar;
    @FindBy(css = "div.User span.username")
    private WebElement userNameTextBox;
    @FindBy(css = "[id*='Chat'] .inline-alert[id*='entry']")
    private WebElement chatInlineAlert;
    @FindBy(css = "div.User img")
    private WebElement userAvatar;
    @FindBy(css = "#UserStatsMenu .private")
    private WebElement privateMassageButton;
    @FindBy(css = "#UserStatsMenu")
    private WebElement userStatsMenu;
    @FindBy(css = "li.private-allow")
    private WebElement allowPrivateMassageButton;
    @FindBy(css = "li.kick")
    private WebElement kickUserButton;
    @FindBy(css = "li.private-block")
    private WebElement blockPrivateMassageButton;
    @FindBy(css = "h1.public.wordmark.selected")
    private WebElement mainChatSelection;
    @FindBy(css = "ul.PrivateChatList span.splotch")
    private WebElement privateMessageNotification;
    @FindBy(css = ".continued.inline-alert")
    private WebElement chatInlineAlertContinued;
    @FindBy(css = "#UserStatsMenu li.ban")
    private WebElement banUserButton;
    @FindBy(css = "#ChatBanModal")
    private WebElement chatBanModal;
    @FindBy(css = "#ChatBanModal button.primary")
    private WebElement chatBanModalButton;
    @FindBy(css = "#UserStatsMenu .regular-actions li")
    private List<WebElement> userDropDownActionsElements;
    @FindBy(css = "#UserStatsMenu .admin-actions li")
    private List<WebElement> adminDropDownActionsElements;
    @FindBy(css = "#Rail h1.private")
    private WebElement privateMessagesHeader;
    @FindBy(css = "#Rail h1.wordmark")
    private WebElement chatWordmarkImage;
    @FindBy(css = "#ChatHeader div.User img")
    private WebElement chatLoadedIndicator;
    @FindBy(css = "#ChatHeader h1.private")
    private WebElement privateChatHeader;
    @FindBy(css = "div.actions .admin-actions .give-chat-mod")
    private WebElement addUserModStatusButton;
    @FindBy(css = "ul.admin-actions")
    private WebElement userModOptions;
    @FindBy(css = "li.message-wall")
    private WebElement userMessageWallButton;
    @FindBy(css = "div.speech-bubble-message")
    private WebElement userPageMessageWallTab;
    @FindBy(css = "li.contribs")
    private WebElement userContributionsButton;
    @FindBy(css = "table.mw-contributions-table")
    private WebElement userPageContributionsTab;
    @FindBy(css = "form#Write.Write.blocked")
    private WebElement userBlockedMessageField;
    @FindBy(css = "div.header-column.header-title")
    private WebElement permissionsErrorTitle;
    @FindBy(xpath = "//h1")
    private WebElement userNameTitle;
    @FindBy(css = "div.limit-reached-msg")
    private WebElement messageLengthExceeded;

    public ChatPage open() {
        getUrl(urlBuilder.getUrlForPage(URLsContent.SPECIAL_CHAT));

        return this;
    }

    private WebElement userOnChat(String userName, String message) {
        return driver.findElement(By.cssSelector(String.format(message, userName)));
    }

    private WebElement userPostedMessage(String message) {
        return driver.findElement(By.xpath(String.format(PATH_MESSAGE_ON_CHAT, message)));
    }

    private WebElement userPrivateMessageNotification(int notificationCount) {
        return driver.findElement(By.xpath(String.format(NOTIFICATION_COUNTER, notificationCount)));
    }

    private WebElement getUserUnbanMessage(String userName) {
        return driver.findElement(By.xpath(String.format(USER_UNBAN_CONFIRM_MESSAGE, userName)));
    }

    private WebElement getUserUnbanLink(String userName) {
        return driver.findElement(By.xpath(String.format(USER_UNBAN_LINK, userName)));
    }

    public String getUsername() {
        return userNameTextBox.getText();
    }

    public boolean isMessageOnChat(String message) {
        try {
            WebElement userPostedMessage = userPostedMessage(message);
            //NEEDTOCHECK
            wait.forElementVisibleW(userPostedMessage);
            return true;
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("Message on chat not displayed", ex, true);
            return false;
        }
    }

    public boolean isMessageTooLongWarningDisplayed() {
        try {
            return messageLengthExceeded.isDisplayed();
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("Warning about too long message not displayed", ex, true);
            return false;
        }
    }

    public boolean isPrivateMessageHeaderDisplayed() {
        try {
            //NEEDTOCHECK
            wait.forElementVisibleW(privateMessagesHeader);
            return true;
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("Message header not displayed", ex, true);
            return false;
        }
    }

    public boolean isPermissionsErrorTitleDisplayed() {
        try {
            wait.forTextInElementAfterRefresh(By.cssSelector("#PageHeader"), "Permissions error.");
            return true;
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("Permission error title not displayed", ex, true);
            return false;
        }
    }

    public boolean isPrivateMessageNotificationDisplayed() {
        try {
            //NEEDTOCHECK
            wait.forElementVisibleW(privateMessageNotification);
            return true;
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("Private message notification not displayed", ex, true);
            return false;
        }
    }

    public boolean isPrivateNotificationCountDisplayed(int notificationCount) {
        try {
            //NEEDTOCHECK
            wait.forElementVisibleW(userPrivateMessageNotification(notificationCount));
            return true;
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("Private messages counter not displayed", ex, true);
            return false;
        }
    }

    public boolean isPrivateMessageButtonDisplayed() {
        try {
            return privateMassageButton.isDisplayed();
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("Private message button not displayed", ex, true);
            return false;
        }
    }

    public boolean isPrivateChatOpen() {
        try {
            wait.forElementVisibleW(privateChatHeader);
            return true;
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("Private chat header not displayed", ex, true);
            return false;
        }
    }

    public boolean isRegularUserDropdownDisplayed() {
        return userDropDownActionsElements.size() == REGULAR_USER_DROPDOWN_ELEMENTS_COUNT;
    }

    public boolean isUserInPrivateSectionDisplayed(String userName) {
        try {
            return userOnChat(userName, PRIVATE_MESSAGE_USER_SELECTOR).isDisplayed();
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("User in private section not displayed", ex, true);
            return false;
        }
    }

    public boolean isChatUnbanMessageDisplayed(String userName) {
        try {
            wait.forElementVisibleW(getUserUnbanMessage(userName));
            return true;
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("Un-ban message not displayed", ex, true);
            return false;
        }
    }

    public boolean isMessageWallOpened(String userName) {
        try {
            Assertion.assertEquals(userName, userNameTitle.getText());
            return userPageMessageWallTab.isDisplayed();
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("Message Wall tab not displayed", ex, true);
            return false;
        }
    }

    public boolean isContributionsPageOpened(String userName) {
        try {
            Assertion.assertEquals(userName, userNameTitle.getText());
            return userPageContributionsTab.isDisplayed();
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("Contribution tab is not displayed", ex, true);
            return false;
        }
    }

    public boolean isUserKickedFromChat() {
        try {
            //NEEDTOCHECK
            wait.forElementVisibleW(userBlockedMessageField);
            return true;
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("User kicked message not displayed", ex, true);
            return false;
        }
    }

    public boolean isBlockPrivateMessageButtonDisplayed() {
        try {
            //NEEDTOCHECK
            wait.forElementVisibleW(blockPrivateMassageButton);
            return true;
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("Block private message button not displayed", ex, true);
            return false;
        }
    }

    public boolean areStaffOptionsDisplayed() {
        try {
            //NEEDTOCHECK
            wait.forElementVisibleW(userModOptions);
            return true;
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("Staff options are not displayed", ex, true);
            return false;
        }
    }

    public void clickOnMainChat() {
        chatWordmarkImage.click();
        Log.log("clickOnMainChat", "main chat is clicked", true);
    }

    public void clickOnUserInPrivateMessageSection(String userName) {
        //We need two clicks to see user options in privet section
        userOnChat(userName, PRIVATE_MESSAGE_USER_SELECTOR).click();
        userOnChat(userName, PRIVATE_MESSAGE_USER_SELECTOR).click();
        Log.log("clickOnUserInPrivateMessageSection", "private messages user " + userName
                + " is clicked", true);
    }

    private void clickBanUser(String userName) {
        banUserButton.click();
        //NEEDTOCHECK
        wait.forElementVisibleW(chatBanModal);
        Log.log("clickBanUser", "ban user " + userName + " is clicked", true);
    }

    public void clickOpenUserMessageWall() {
        userMessageWallButton.click();
        Log.log("clickOpenUserMessageWall", " Message wall button is clicked", true);
    }

    public void clickOpenUserContributions() {
        userContributionsButton.click();
        Log.log("clickOpenUserContributions", " Cotributions button is clicked", true);
    }

    public void clickOnUserOptionsKickButton() {
        kickUserButton.click();
        Log.log("clickOnUserOptionsKikButton", " kick user button is clicked", true);
    }

    public void clickOnDifferentUser(String userName) {
        boolean hidden = !userStatsMenu.isDisplayed();
        int i = 0;
        // we need this loop because of chat problems - sometimes we need to click more then once
        // to open user dropdown. To avoid infinite loop i (threshold) was introduced
        while (hidden) {
            userOnChat(userName, USER_SELECTOR).click();
            if (userStatsMenu.isDisplayed() || i >= 10) {
                hidden = false;
            }
            i++;
        }
        Log.log("clickOnDifferentUser", userName + " button clicked", true);
    }

    public void switchToSecondTab(String firstTab) {
        ArrayList<String> openTabs = new ArrayList(driver.getWindowHandles());
        for (String window : openTabs) {
            if (window != firstTab) {
                driver.switchTo().window(window);
            }
        }
    }

    public void unBanUser(String userName) {
        wait.forElementClickable(getUserUnbanLink(userName));
        getUserUnbanLink(userName).click();
        Log.log("unBanUser", userName + " is no longer banned", true);
    }

    public void banUser(String userName) {
        clickOnDifferentUser(userName);
        clickBanUser(userName);
        chatBanModalButton.click();
        Log.log("clickBanUser", userName + " ban modal is closed", true);
    }

    public void openUserDropDownInPrivateMessageSection(String userName) {
        boolean hidden = !userStatsMenu.isDisplayed();
        int i = 0;
        while (hidden) {
            userOnChat(userName, PRIVATE_MESSAGE_USER_SELECTOR).click();
            if (userStatsMenu.isDisplayed() || i >= 10) {
                hidden = false;
            }
            i++;
        }
        Log.log("openUserDropDownInPrivateMessageSection", userName + " button clicked",
                true);
    }

    public void blockPrivateMessageFromUser(String userName) {
        openUserDropDownInPrivateMessageSection(userName);
        blockPrivateMassageButton.click();
        waitForElementNotVisibleByElement(blockPrivateMassageButton);
    }

    public void allowPrivateMessageFromUser(String userName) {
        boolean blocked = true;
        int i = 0;
        // Open user stats dropdown and check if private messages are allowed
        // if are not allowed - allow
        // we need this loop because of chat problems - sometimes we need to click more then once
        // to open user dropdown and allow private messages.
        // To avoid infinite loop i (threshold) was introduced
        while (blocked) {
            clickOnDifferentUser(userName);
            if (isElementOnPage(allowPrivateMassageButton) || i >= 10) {
                allowPrivateMassageButton.click();
                clickOnDifferentUser(userName);
                if (!isElementOnPage(allowPrivateMassageButton)) {
                    blocked = false;
                }
            }
            i++;
        }
        Log.log("allowPrivateMessageFromUser", "private messages from " + userName
                + " are allowed now", true);
    }

    private boolean checkIfPrivateMessagesNotAllowed(String userName) {
        // check is user stats are already open
        // if not open them
        if (!userStatsMenu.isDisplayed()) {
            clickOnDifferentUser(userName);
        }
        return isElementOnPage(allowPrivateMassageButton);
    }

    public List<String> sendMultipleMessagesFromUser(String message, int messagesCount) {
        List<String> messagesSent = new ArrayList<>();
        for (int i = 0; i < messagesCount; i++) {
            writeOnChat(message);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Log.info("Sleep Interrupted", e);
            }
            messagesSent.add(message);
        }
        return messagesSent;
    }

    public void writeOnChat(String message) {
        //NEEDTOCHECK
        wait.forElementVisibleW(chatLoadedIndicator);
        messageWritingArea.sendKeys(message);
        new Actions(driver).sendKeys(messageWritingArea, Keys.ENTER).perform();
    }

    public void writeLongMessage(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= length; i++) {
            sb.append('A');
        }
        writeOnChat(sb.toString());
    }

    public void selectPrivateMessageToUser(String userName) {
        // This check is needed in case some of the previous tests failed
        // We need to do the clean up in this place - allow provided user to send private messages
        if (checkIfPrivateMessagesNotAllowed(userName)) {
            allowPrivateMessageFromUser(userName);
        }
        clickOnDifferentUser(userName);
        //NEEDTOCHECK
        wait.forElementVisibleW(privateMassageButton);
        privateMassageButton.click();
        //NEEDTOCHECK
        wait.forElementVisibleW(privateMessagesHeader);
        clickOnUserInPrivateMessageSection(userName);
        Log.log("selectPrivateMessageToUser", "private message selected from dropdown",
                true);
    }

    public Boolean isEmoticonVisible(String emoticon) {
        try {
            wait.forElementVisible(By.xpath(String.format(PATH_EMOTICON_ON_CHAT, emoticon)));
            return true;
        } catch (TimeoutException | NoSuchElementException ex) {
            Log.log("Emoticon " + emoticon + " on chat not displayed", ex, true);
            return false;
        }
    }

    public WebElement getMessage(String message) {
        By by = By.xpath(String.format(PATH_MESSAGE_ON_CHAT, message));
        return wait.forElementPresent(by);
    }
}
