import PageTypes.ToolsChooser;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class AfunnelTest {

    public RemoteWebDriver driver;


   // Variables var;
    ToolsChooser toolsChooser;



    @Test
    public void testPageTitleInChrome() throws InterruptedException {

        String appURL[] = {

                "https://staging.1800option.com/30daychange/",

        };

        String appTitle[] = {

                "30DayChange",

        };

        int i = -1;
        int j = appURL.length-1;
        int m = j+1;


        while (i != j)
        {
           i++;
            int k = i+1;
            driver = new RemoteWebDriver (DesiredCapabilities.chrome ());
     //     розгорнуто
     //       driver.manage ().window ().maximize ();
     //     сховано
            driver.manage ().window ().setPosition(new Point (-2000,0));


            toolsChooser = new ToolsChooser (driver);

//Йдемо на потрібну сторінку
            driver.navigate ( ).to ( appURL[i] );
            //зачистка куків
            ToolsChooser.tools.deleteAllCookiesTool (appURL[i]);
            Thread.sleep ( 500 );
            String strPageTitle = driver.getTitle ( );
            String writeSomethingIntoLogFile = "";
            ToolsChooser.tools.protocolirenTool (writeSomethingIntoLogFile);
            if (strPageTitle.equals ( appTitle[i] )) {
                writeSomethingIntoLogFile = "";
                ToolsChooser.tools.protocolirenTool (writeSomethingIntoLogFile);
                writeSomethingIntoLogFile = "The " + appURL[i] + " is available online";
                ToolsChooser.tools.protocolirenTool (writeSomethingIntoLogFile);
                writeSomethingIntoLogFile = "(" + appTitle[i] + ")";
                ToolsChooser.tools.protocolirenTool (writeSomethingIntoLogFile);
            }
//Якщо всі 3 перевірки не пройшли - надсилаємо емаіл/смс
                    else {
                        writeSomethingIntoLogFile =                             "Can't access: " + appURL[i] +
                           "               and\n                                                               " + appURL2[i] +
                           "      and also\n                                                               " + appURL3[i];
                        ToolsChooser.tools.protocolirenTool (writeSomethingIntoLogFile);
                        //String sendEmailText = "Can't access the " + appURL[i];
                        ToolsChooser.sendEmail ();

//Чекаємо 5 хвилин і повторюємо перевірку
        //                Thread.sleep ( 300000 );

                    }
                }
            }
            writeSomethingIntoLogFile = "";
            ToolsChooser.tools.protocolirenTool (writeSomethingIntoLogFile);
            writeSomethingIntoLogFile = "-------------------------------------------";
            ToolsChooser.tools.protocolirenTool (writeSomethingIntoLogFile);


   //         Thread.sleep ( 200 );


            // Закриваємо браузер
            if(driver!=null) {
                System.out.println("Closing browser");
                driver.quit();
            }
        }

    }



}