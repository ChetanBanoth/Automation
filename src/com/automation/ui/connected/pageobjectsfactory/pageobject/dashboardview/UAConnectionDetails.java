package com.automation.ui.connected.pageobjectsfactory.pageobject.dashboardview;

import com.automation.ui.base.common.constants.BASEConstants;
import com.automation.ui.connected.pageobjectsfactory.pageobject.base.SiteBasePageObject;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UAConnectionDetails extends SiteBasePageObject {


    private static Logger logger = Logger.getLogger(UAConnectionDetails.class);

    @FindBy(xpath = UAConnectionDetailsCONSTANTS.DATACONNECTIONDROPDOWN)
    private WebElement dcDropdownButton;
    @FindBy(xpath = UAConnectionDetailsCONSTANTS.DATACONNECTIONPAUSE)
    private WebElement dcPause;
    @FindBy(xpath = UAConnectionDetailsCONSTANTS.DATACONNECTIONEDIT)
    private WebElement dcEdit;
    @FindBy(xpath = UAConnectionDetailsCONSTANTS.DATACONNECTIONREMOVE)
    private WebElement dcRemove;
    @FindBy(xpath = UAConnectionDetailsCONSTANTS.DATACONNECTIONDETAILS)
    private WebElement dcDetails;

    public UAConnectionDetails open() {


        return new UAConnectionDetails();
    }


    public void dcDropdown() {
        try {
            logger.info("Click on DataConnection drop down");
            wait.forElementVisible(dcDropdownButton, BASEConstants.WAITTIME10000MILLISEC);

            dcDropdownButton.click();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dcPause() {
        try {
            logger.info("Click on DataConnection Pause");
            wait.forElementVisible(dcPause, BASEConstants.WAITTIME10000MILLISEC);
            scrollAndClick(dcPause, 0);


        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }

    public void dcEdit() {
        try {
            logger.info("Click on DataConnection Edit");
            wait.forElementVisible(dcEdit, BASEConstants.WAITTIME10000MILLISEC);
            scrollAndClick(dcEdit, 1);


        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }


}
