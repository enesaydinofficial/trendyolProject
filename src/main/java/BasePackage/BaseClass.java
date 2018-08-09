package BasePackage;

import SetPropertiesFile.SetProperties;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static KeywordLayer.Command.ImplicitlyWait;
import static KeywordLayer.Command.PageLoad;

public class BaseClass {

    public static WebDriver driver;
    public static String browserType = "Chrome";
    protected static Properties config = null;
    private static String OS = System.getProperty("os.name").toUpperCase();

    String sFileName = System.getProperty("user.dir") + "//src//main//resources//Config//trendyolImage.har";
    protected BrowserMobProxy proxy;


    public static void LoadConfigProperty() throws IOException {
        config = new Properties();
        FileInputStream ConfigFile = new FileInputStream(System.getProperty("user.dir") + "//src//main//resources//Config//config.properties");
        config.load(ConfigFile);
    }

    @BeforeTest
    public void Init() throws Exception {

        SetProperties.SetValueProperties(); // Set value config.properties file

        LoadConfigProperty();

        // start the proxy
        proxy = new BrowserMobProxyServer();
        proxy.start(0);

        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        // configure it as a desired capability
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

        if (config.getProperty("browserType").equals("Chrome")) {

            if (OS.contains("MAC OS X")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + config.getProperty("MacOSChromeDriverPath"));
            } else if (OS.contains("WİNDOWS 10") || OS.contains("WINDOWS 10")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + config.getProperty("WindowsChromeDriverPath"));
            }
            driver = new ChromeDriver(capabilities);

        }

        driver.manage().window().maximize();
        ImplicitlyWait();
        PageLoad();

    }

    @AfterTest
    public void Teardown() {

        try {
            // get the HAR data
            Har har = proxy.getHar();

            // Write HAR Data in a File
            File harFile = new File(sFileName);
            try {
                har.writeTo(harFile);
            } catch (IOException ex) {
                System.out.println(ex.toString());
                System.out.println("Could not find file " + sFileName);
            }
        } catch (Exception ex) {
        }

        if (driver != null) {
            driver.quit();
        }
    }


}

