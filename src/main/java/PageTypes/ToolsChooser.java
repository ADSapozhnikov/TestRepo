package PageTypes;

import TestTools.ToolBox;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;


public class ToolsChooser {

    public static RemoteWebDriver driver;
    Logger log;
    public static ToolBox tools;
    //private org.testng.ITestResult ITestResult;

    public ToolsChooser(RemoteWebDriver driver) {
        this.driver = driver;
        log = Logger.getLogger (getClass ());
        tools = new ToolBox (driver);
    }

      public static void sendEmail() {
        ToolBox.sendEmailTool (new String[]{});
    }


    //перевірка URL сторінки на яку ми потрапили
    public String whatUrlWeGotToChooser(String currentURL) {
        return tools.whatUrlWeGotToTool (currentURL);
    }

}