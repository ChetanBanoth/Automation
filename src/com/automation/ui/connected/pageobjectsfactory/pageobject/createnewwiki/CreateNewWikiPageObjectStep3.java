package com.automation.ui.connected.pageobjectsfactory.pageobject.createnewwiki;

import com.automation.ui.base.common.logging.Log;
import com.automation.ui.base.pageobjectsfactory.pageobject.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateNewWikiPageObjectStep3 extends BasePageObject {

    private static final String DATA_THEME_LIST = "li[data-theme]";
    private By loadingIndicatorBy = By.cssSelector(".wikiaThrobber");
    private String themeLocator = "li[data-theme='%name%']";

    @FindBy(css = "li[id='ThemeWiki'] input.next.enabled")
    private WebElement submitButton;

    public CreateNewWikiPageObjectStep3(WebDriver driver) {
        super();
        PageFactory.initElements(driver, this);
    }

    public void selectThemeByName(String name) {
        wait.forElementVisible(By.cssSelector(DATA_THEME_LIST));
        String themeName = themeLocator.replace("%name%", name);
        scrollAndClick(driver.findElement(By.cssSelector(themeName)));
        Log.log("selectTheme", "skin " + name + " selected", true, driver);
    }
/*
  public ArticlePageObject submit() {

    changeImplicitWait(250, TimeUnit.MILLISECONDS);
    try {
      new WebDriverWait(driver, 180).until(CommonExpectedConditions
          .elementNotPresent(loadingIndicatorBy));
    } finally {
      restoreDefaultImplicitWait();
    }

    scrollAndClick(submitButton);
    Log.log("submit", "Submit button clicked", true, driver);
    return new ArticlePageObject();
  }
  */
}
