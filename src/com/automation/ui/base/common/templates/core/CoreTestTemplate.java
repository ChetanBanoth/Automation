package com.automation.ui.base.common.templates.core;

import com.automation.ui.base.common.utils.CommonUtils;
import com.automation.ui.base.common.core.TestContext;
import com.automation.ui.base.common.core.UIWebDriver;
import com.automation.ui.base.common.core.annotations.Execute;
import com.automation.ui.base.common.core.annotations.InBrowser;
import com.automation.ui.base.common.core.annotations.NetworkTrafficDump;
import com.automation.ui.base.common.core.annotations.UnsafePageLoad;
import com.automation.ui.base.common.core.configuration.Configuration;
import com.automation.ui.base.common.core.networktrafficinterceptor.NetworkTrafficInterceptor;
import com.automation.ui.base.common.core.url.UrlBuilder;
import com.automation.ui.base.common.driverprovider.DriverProvider;
import com.automation.ui.base.common.driverprovider.UseUnstablePageLoadStrategy;
import com.automation.ui.base.common.logging.Log;
import com.automation.ui.base.common.testnglisteners.BrowserAndTestEventListener;
import com.automation.ui.connected.common.prpreader.AssertDataReader;
import com.automation.ui.connected.common.prpreader.PropertyReader;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.Dimension;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;

@Listeners({BrowserAndTestEventListener.class,
        com.automation.ui.base.common.testnglisteners.InvokeMethodAdapter.class})
public abstract class CoreTestTemplate {

    private static Logger logger = Logger.getLogger(CoreTestTemplate.class);

    protected UIWebDriver driver;
    protected NetworkTrafficInterceptor networkTrafficInterceptor;
    protected AssertDataReader assertData;

    protected UrlBuilder urlBuilder;
     protected String siteURL;
    protected String siteCorporateURL;
    protected String siteCorpSetupURL;

    protected PropertyReader prpr;

    private void refreshDriver() {
        driver = DriverProvider.getActiveDriver();
    }

    @BeforeSuite(alwaysRun = true, groups = {"base"})
    public void beforeSuite() {
        // initialize a browser driver, connect to servers
        logger.info("beforeSuite ::CoreTestTemplate");
        Reporter.log("beforeSuite::CoreTestTemplate");
        initLogs();
        initProperty();
        initAssertData();
        prepareDirectories();
        //DISABLED NOW
        // Helios.updateTokenCache();
    }


    /**
     * Initialize Property.
     */
    private void initAssertData() {
        logger.info("initAssertData readProperty ");
        assertData = AssertDataReader.readProperty();

    }


    /**
     * Initialize Logger.
     */
    private void initLogs() {
        if (logger == null) {
            // Initialize Log4j logs
            DOMConfigurator.configure(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "log4j.xml");
            logger = Logger.getLogger(this.getClass());
            logger.info("Logger is initialized..");
        }
    }

    /**
     * Initialize Property.
     */
    private void initProperty() {
        logger.info("initProperty readProperty ");
        prpr = PropertyReader.readProperty();

    }

    @BeforeClass(alwaysRun = true)
    public void initTestClass() {
        logger.info("beforeClass initTestClass ");
    }

    @BeforeMethod(alwaysRun = true)
    public void initTestContext(Method method) {
        TestContext.writeMethodName(method);
        Log.startTest(method);
        logger.info("beforeMethod initTestContext ");

        com.automation.ui.base.common.core.configuration.Configuration.clearCustomTestProperties();

        String browser = com.automation.ui.base.common.core.configuration.Configuration.getBrowser();
        setPropertiesFromAnnotationsOnDeclaringClass(method.getDeclaringClass());
        setPropertiesFromAnnotationsOnMethod(method);
        String currentBrowser = com.automation.ui.base.common.core.configuration.Configuration.getBrowser();

        logger.info(" beforeMethod initTestContext Browser parameter changed by annotation"
                + ", old value: " + browser + ", new value: " + currentBrowser);
        if (!browser.equals(currentBrowser)) {
            Log.info("Parameter override", "Browser parameter changed by annotation"
                    + ", old value: " + browser + ", new value: " + currentBrowser);
        }

        prepareURLs();

        driver = DriverProvider.getActiveDriver();
        networkTrafficInterceptor = driver.getProxy();
        setWindowSize();

        loadFirstPage();
    }

    private void setTestProperty(String key, String value) {
        if (!"".equals(value)) {
            com.automation.ui.base.common.core.configuration.Configuration.setTestValue(key, value);
        }
    }

    private void setPropertiesFromAnnotationsOnDeclaringClass(Class<?> declaringClass) {
        if (declaringClass.isAnnotationPresent(Execute.class)) {
            setTestProperty("siteName", declaringClass.getAnnotation(Execute.class).onSite());
            setTestProperty("language", declaringClass.getAnnotation(Execute.class).language());
            setTestProperty("disableFlash", declaringClass.getAnnotation(Execute.class).disableFlash());
            setTestProperty("mockAds", declaringClass.getAnnotation(Execute.class).mockAds());
            setTestProperty("disableCommunityPageSalesPitchDialog",
                    declaringClass.getAnnotation(Execute.class).disableCommunityPageSalesPitchDialog());
        }

        if (declaringClass.isAnnotationPresent(InBrowser.class)) {
            setTestProperty("browser", declaringClass.getAnnotation(InBrowser.class).browser().getName());
            setTestProperty("browserSize", declaringClass.getAnnotation(InBrowser.class).browserSize());
        }
    }

    private void setPropertiesFromAnnotationsOnMethod(Method method) {
        if (method.isAnnotationPresent(Execute.class)) {
            setTestProperty("siteName", method.getAnnotation(Execute.class).onSite());
            setTestProperty("language", method.getAnnotation(Execute.class).language());
            setTestProperty("disableFlash", method.getAnnotation(Execute.class).disableFlash());
            setTestProperty("mockAds", method.getAnnotation(Execute.class).mockAds());
            setTestProperty("disableCommunityPageSalesPitchDialog",
                    method.getAnnotation(Execute.class).disableCommunityPageSalesPitchDialog());
        }

        if (method.isAnnotationPresent(InBrowser.class)) {
            setTestProperty("browser", method.getAnnotation(InBrowser.class).browser().getName());
            setTestProperty("browserSize", method.getAnnotation(InBrowser.class).browserSize());
        }

        if (method.isAnnotationPresent(UseUnstablePageLoadStrategy.class)) {
            setTestProperty("unstablePageLoadStrategy", "true");
        }

        if (method.isAnnotationPresent(NetworkTrafficDump.class)) {
            setTestProperty("dumpNetworkTraffic",
                    String.valueOf(method.getAnnotation(NetworkTrafficDump.class).networkTrafficDump()));
            setTestProperty("useProxy", "true");
            setTestProperty("useMITM",
                    String.valueOf(method.getAnnotation(NetworkTrafficDump.class).useMITM()));
        }

        if (method.isAnnotationPresent(UnsafePageLoad.class) || method.getDeclaringClass()
                .isAnnotationPresent(UnsafePageLoad.class)) {
            setTestProperty("unsafePageLoad", "true");
        }
    }

    private void prepareDirectories() {
        CommonUtils.deleteDirectory("." + File.separator + "logs");
        CommonUtils.createDirectory("." + File.separator + "logs");
    }

    protected void setWindowSize() {
        Dimension browserSize = Configuration.getBrowserSize();

        if (!driver.isAndroid()) {
            if (browserSize != null) {
                driver.manage().window().setSize(browserSize);
            } else {
                driver.manage().window().maximize();
            }
        }
    }

    //@AfterMethod(alwaysRun = true)
    @AfterClass(alwaysRun = true)
    public void stop() {

        logger.info("Stop  AfterClass or AfterMethod ::CoreTestTemplate ");


        DriverProvider.close();
    }


    @AfterSuite
    protected void tearDownAfterTestSuite() {
        logger.info("Quiting the tearDownAfterTestSuite ::CoeTestTemplate ");

    }

    protected void switchToWindow(int index) {
        DriverProvider.switchActiveWindow(index);
        refreshDriver();

        String driverName =
                DriverProvider.getActiveDriver().equals(driver) ? "primary window" : "secondary window";
        Log.log("switchToWindow", "================ " + driverName + " ================",
                true);
    }

    protected abstract void prepareURLs();

    protected abstract void loadFirstPage();

}
