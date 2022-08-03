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

import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;


public class TestBase {
  TestData testData = new TestData();
  EditUserData editUserData = new EditUserData();
  NewUserData newUserData = new NewUserData();

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
          userInfoUrl = webConfig.getUserInfoUrl(),
          login = aConfig.emailValue(),
          password = aConfig.passwordValue();



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



