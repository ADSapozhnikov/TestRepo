package TestTools;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static TestTools.Config.ui;

/**
        * Created by Eugene.Korogodsky on 9/11/2016.
        * УВАГА!!! бібліотеку javax.mail maven самостійно не підтягує - треба її додавати вручну
        * звідси: http://www.java2s.com/Code/Jar/j/Downloadjavaxmailjar.htm
        * і класти до папки з jar-файлами, а потім прописати у Project Structure / Libraries
        * або прописати залежність у POM.xml - файлі
        */

public class ToolBox {

    RemoteWebDriver driver;
    Logger log;
    WebDriverWait wait;

    public ToolBox(RemoteWebDriver driver){
        this.driver = driver;
        log = Logger.getLogger(getClass());
       wait = new WebDriverWait(driver, 20);

    }

//	Цей метод просто пише в лог-файл те, що отримує на вході
    public void protocolirenTool(String somethingToBeProtocoliren)
    {
        log.info(somethingToBeProtocoliren);
    }

// Цей метод надсилає емаіл з Укр.нет

    public static void sendEmailTool(String[] args) {


        String to = "a.d.sapozhnikov@gmail.com"; // адреса отримувача повідомлення
        String from = "dorado2@ukr.net"; // адреса відправника повідомлення
        String host = "smtp.ukr.net"; // SMTP сервер, що використовується для відправлення повідомлення
        int port = 2525; // порт SMTP серверу

        // Создание свойств, получение сессии
        Properties props = new Properties();

        // При использовании статического метода Transport.send()
        // необходимо указать через какой хост будет передано сообщение
        props.put("mail.smtp.host", host);
        // Если почтовый сервер использует SSL
        props.put("mail.smtp.ssl.enable", "true");
        // Указываем порт SMTP сервера.
        props.put("mail.smtp.port", port);
        // Большинство SMTP серверов, используют авторизацию.
        props.put("mail.smtp.auth", "true");
        // Включение debug-режима
        props.put("mail.debug", "true");
        // Авторизируемся.
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            // Указываем логин пароль, от почты, с которой будем отправлять сообщение.
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("dorado2@ukr.net", "barbatsutsa");
            }
        });

        try {
            // Создание объекта сообщения
            Message msg = new MimeMessage (session);

            // Установка атрибутов сообщения
            msg.setFrom(new InternetAddress (from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setRecipients(Message.RecipientType.CC, address2);
            msg.setSubject("URGENT - some significant problems with BU");
            msg.setSentDate(new Date ());

            // Установка тіла повідомлення

            BodyPart emailAttachment = new MimeBodyPart ();
            String myfile = "..\\BUcontrol\\log\\BU_Accessibility_Monitoring.log";
            DataSource source = new FileDataSource (myfile);
            emailAttachment.setDataHandler (new DataHandler (source));
            emailAttachment.setFileName (myfile);
            // текст
            BodyPart emailText = new MimeBodyPart ();
            emailText.setText (new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss").format (new Date ()) + " - Can't access the BU\n"
                    + "Attached please find the log ");
            // складаємо лист з обох частин
            Multipart multipart = new MimeMultipart ();
            multipart.addBodyPart (emailText);
            multipart.addBodyPart (emailAttachment);
            msg.setContent (multipart);


            // Отправка сообщения
            Transport.send(msg);
        }
        catch (MessagingException mex) {
            // Печать информации об исключении в случае его возникновения
            mex.printStackTrace();
        }
    }



// ================= перевіряємо сторінку, на яку ми потрапили ===========
    public String whatUrlWeGotToTool(String currentURL){
        currentURL = driver.getCurrentUrl();
        currentURL.equals ("https://www.binaryuno.com");
        log.info("BU is available");
        return currentURL;
        }



    /** Перевіряє чи доступний на сторінці якийсь елемент
   * Method check that element is Displayed and is Enable
   * @param elementLocator
   * @return
   */
    public boolean isElementPresentOnPageTool(String elementLocator) {
        WebElement tempElement;
        try {
//            wait.until(ExpectedConditions.elementToBeSelected(driver.findElement(ui(elementLocator))));
            tempElement = driver.findElement(ui(elementLocator));
            if
                    (tempElement.isDisplayed() && tempElement.isEnabled())
            {
                log.info("Element " + elementLocator + " is present");
                return true;
            } else {
                log.info("Element \"" + elementLocator + "\" is not present");
                return false;
            }

        } catch (Exception e) {
//            log.error("Catch" + e);
            log.info("Element \"" + elementLocator + "\" is not present");
            return false;
        }

    }

    //Прибираємо кукі
    public void deleteAllCookiesTool(String url)
    {
        driver.manage ().deleteAllCookies ();
    }

}

