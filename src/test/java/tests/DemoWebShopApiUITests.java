package tests;


import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static helpers.RestAssuredListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;


public class DemoWebShopApiUITests extends TestBase {

  public  void setCookies() {
    Cookie ck = new Cookie(authCookieName, getCookie());
    getWebDriver().manage().addCookie(ck);
  }


  @Test
  @DisplayName("New user registration using (API+UI)")
  void userRegistrationTest() {

    step("Create user using API", () -> {
      Response response = given()
              .log().all()
              .when()
              .get("/register")
              .then()
              .log().all()
              .statusCode(200)
              .extract()
              .response();

      String value = response.htmlPath().getString("**.find{it.@name == '__RequestVerificationToken'}.@value");

      Map<String, Object> requestData = new LinkedHashMap<>();
      requestData.put("__RequestVerificationToken", value);
      requestData.put("Gender", "M");
      requestData.put("FirstName", "Maria");
      requestData.put("LastName", "Ivanova");
      requestData.put("Email", "MariaIvanova@gmail.com");
      requestData.put("Password", "123321");
      requestData.put("ConfirmPassword", "123321");
      requestData.put("register-button", "Register");


      given()
              .contentType(ContentType.JSON)
              .body(requestData)
              .filter(withCustomTemplates())
              .log().all()
              .when()
              .post("/registerresult/1")
              .then()
              .log().all()
              .statusCode(200);
    });

    step("Check that registration is successful in UI", () -> {
      open("/registerresult/1");
      $(".result").shouldHave(text("Your registration completed"));
    });
  }

  @Test
  @DisplayName("Change user data using (API+UI)")
  void changeUserdataTest() {


    step("Open minimal page", () ->
            open(minPageUrl));

    step("Set cookie", () -> {
      setCookies();

      Response response = given()
              .log().all()
              .when()
              .get("/register")
              .then()
              .log().all()
              .statusCode(200)
              .extract()
              .response();

      String value = response.htmlPath().getString("**.find{it.@name == '__RequestVerificationToken'}.@value");


    String body = "__RequestVerificationToken:" + value +
            "Gender:M\n" +
            "FirstName:Petr\n" +
            "LastName:Ivanov\n" +
            "Email:petr.petrov@gmail.com\n" +
            "save-info-button:Save";

    given().cookie(authCookieName, getCookie())
            .filter(withCustomTemplates())
            .contentType(reqContentType)
            .body(body)
            .log().all()
            .when()
            .post("http://demowebshop.tricentis.com/customer/info")
            .then()
            .log().all()
            .statusCode(302);  });


    step("Check that the LastName was changed in UI", () -> {
      open("http://demowebshop.tricentis.com/customer/info");
      $("#LastName").shouldHave(text("Ivanov"));
    });

  }
}



