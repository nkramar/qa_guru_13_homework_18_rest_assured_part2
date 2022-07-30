package tests;

import config.AuthConfig;
import config.LaunchConfig;
import config.WebConfig;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.AllureAttachments;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static helpers.RestAssuredListener.withCustomTemplates;
import static io.restassured.RestAssured.given;

public class TestBase {
  static AuthConfig aConfig = ConfigFactory
          .create(AuthConfig.class, System.getProperties());

  static LaunchConfig launchConfig = ConfigFactory
          .create(LaunchConfig.class, System.getProperties());

  static WebConfig webConfig = ConfigFactory
          .create(WebConfig.class, System.getProperties());

  static String
          baseUrl = webConfig.getBaseUrl(),
          loginUrl = webConfig.getLoginUrl(),
          minPageUrl = webConfig.getMinPageUrl(),
          registerUrl = webConfig.getRegisterUrl(),
          someUrl = webConfig.getSomeUrl(),
          login = aConfig.emailValue(),
          password = aConfig.passwordValue(),
          authCookieName = "NOPCOMMERCE.AUTH",
          reqContentType = "application/x-www-form-urlencoded", /*; charset=UTF-8*/
          product = "camera";


  String getCookies() {
    return given()
            .filter(withCustomTemplates())
            .contentType(reqContentType)
            .formParam("Email", login)
            .formParam("Password", password)
            .log().all()
            .when()
            .post(loginUrl)
            .then()
            .log().all()
            .statusCode(302)
            .extract().cookie(authCookieName);
  }

  public void setCookies() {
    Cookie ck = new Cookie(authCookieName, getCookies());
    getWebDriver().manage().addCookie(ck);
  }

  @BeforeAll
  static void configure() {
    System.setProperty("launch", "remote");
    Configuration.remote = launchConfig.getRemoteUrl();
    Configuration.browser = launchConfig.getBrowser();
    Configuration.browserSize = launchConfig.getBrowserSize();
    Configuration.browserVersion = launchConfig.getBrowserVersion();
    Configuration.baseUrl = baseUrl;
    RestAssured.baseURI = baseUrl;
    SelenideLogger.addListener("Allure Selenide", new AllureSelenide());

    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("enableVNC", true);
    capabilities.setCapability("enableVideo", true);
    Configuration.browserCapabilities = capabilities;
  }

  @AfterEach
  void addAttach() {
    AllureAttachments.screenshotAs("Last screenshot");
    AllureAttachments.pageSource();
    AllureAttachments.browserConsoleLogs();
    AllureAttachments.addVideo();

  }

  @AfterAll
  static void close() {
    closeWebDriver();
  }

}



