package com.automation.ui.connected.elements.oasis.components.wikiabar;

import com.automation.ui.base.common.logging.Log;
import com.automation.ui.connected.elements.oasis.components.globalshortcuts.KeyboardShortcutsModal;
import com.automation.ui.connected.pageobjectsfactory.pageobject.SiteBasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WikiaBar extends SiteBasePageObject {

    @FindBy(css = ".wikia-bar .global-shortcuts-help-entry-point")
    private WebElement shortcutsLink;

    @FindBy(css = ".wikia-bar-collapse")
    private WebElement collapseButton;

    @FindBy(css = ".WikiaBarCollapseWrapper")
    private WebElement collapseButtonWrapper;

    private By keyboardShortcutsModalSelector = By.cssSelector(".global-shortcuts-help");

    public WikiaBar() {
        super();
    }

    public WikiaBar ensureBarIsNotCollapsed() {
        if (isCollapsed()) {
            toggle();
        }
        return this;
    }

    public KeyboardShortcutsModal clickOnShortcutsLink() {
        wait.forElementClickable(shortcutsLink);
        shortcutsLink.click();
        Log.info("Click on shortcuts link in Wikia bar");

        wait.forElementVisible(keyboardShortcutsModalSelector);
        Log.info("Keyboard shortcuts modal was opened");

        return new KeyboardShortcutsModal();
    }

    public boolean isCollapsed() {
        return !collapseButtonWrapper.getAttribute("class").contains("hidden");
    }

    public WikiaBar toggle() {
        wait.forElementClickable(collapseButton);
        collapseButton.click();
        Log.info("Click on collapse/uncollapse in Wikia bar");
        return this;
    }
}
