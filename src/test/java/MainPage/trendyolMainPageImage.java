package MainPage;

import BasePackage.BaseClass;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static KeywordLayer.Command.*;

public class trendyolMainPageImage extends BaseClass {

    public static final By ClosePopup = By.cssSelector(".fancybox-skin [title='Kapat']");

    @Test
    public void mainPageImageLoadTime() throws Exception {
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_BINARY_CONTENT, CaptureType.REQUEST_BINARY_CONTENT);
        proxy.newHar("Main Page Image Load Time");

        GetURL("https://www.trendyol.com/");
        WaitElementToBeClickable(ClosePopup);
        Click(ClosePopup);

        for (int c = 100; c <= 50000; c += 100) {
            PageScroll(0, c);
        }

        // Please "http://www.softwareishard.com/har/viewer/" site open and report viewed.
    }


}
