package com.automation.ui.connected.pageobjectsfactory.componentobject.slideshow;

import com.automation.ui.base.common.logging.Log;
import com.automation.ui.base.pageobjectsfactory.pageobject.BasePageObject;
import com.automation.ui.connected.pageobjectsfactory.componentobject.addphoto.AddPhotoComponentObject;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class SlideshowBuilderComponentObject extends BasePageObject {

    @FindBy(css = "#WikiaPhotoGallerySlideshowAddImage")
    private WebElement addPhotoButton;
    @FindBy(css = "#WikiaPhotoGalleryEditorSlideshowAlign")
    private WebElement slideshowPosition;
    @FindBy(css = "#WikiaPhotoGalleryEditorSave")
    private WebElement finishButton;

    public SlideshowBuilderComponentObject(WebDriver driver) {
        super();
    }

    public void adjustPosition(Positions position) {
        Select pos = new Select(slideshowPosition);
        pos.selectByVisibleText(position.getPosition());
        Log
                .log("adjustPosition", "slideshow position set to " + position.getPosition(), true);
    }

    public AddPhotoComponentObject clickAddPhoto() {
        //NEEDTOCHECK
        wait.forElementVisibleW(addPhotoButton);
        addPhotoButton.click();
        return new AddPhotoComponentObject(driver);
    }

    public void clickFinish() {
        //NEEDTOCHECK
        wait.forElementVisibleW(finishButton);
        finishButton.click();
        Log.log("clickFinish", "finish button clicked", true);
    }

    public enum Positions {
        LEFT, CENTER, RIGHT;

        private final String label;

        Positions() {
            this.label = StringUtils.capitalize(this.toString().toLowerCase());
        }

        public String getPosition() {
            return this.label;
        }
    }
}
