package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static helpers.RestAssuredListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;


public class DemoWebShopApiUITests extends TestBase {

  @Test
  @DisplayName("New user registration using (API+UI)")
  void userRegistrationTest() {

    step("Open minimal content", () ->
            open(minPageUrl));

    step("Create user using API", () -> {

      given().contentType(reqContentType)
              .filter(withCustomTemplates())
              .formParam("Gender", "F")
              .formParam("FirstName", "Maria")
              .formParam("LastName", "Ivanova")
              .formParam("Email", "MariaIvanova@gmail.com")
              .formParam("Password", "123321")
              .formParam("ConfirmPassword", "123321")
              .formParam("register-button", "Register")
              .log().all()
              .when()
              .post(registerUrl)
              .then()
              .log().all()
              .statusCode(302).extract().response().getCookie(authCookieName);
    });
  }

  @Test
  @DisplayName("Change user data using API(UI)")
  void loginApiTest() {
    step("Get cookie by API", () -> {
      getCookies();

      step("Open minimal content to be able to set cookies", () ->
              open(minPageUrl));

      step("Set cookie to the browser", this::setCookies);
    });

    step("Open some page", () ->
            open(someUrl));

    step("Verify successful authorization", () ->
            $(".account").shouldHave(text(login)));

    step("Open user info page", () ->
            $(".account").click());

    step("Change user data", () -> {
      given()
              .cookie(authCookieName, getCookies())
              .filter(withCustomTemplates())
              .contentType(reqContentType)
              .formParam("FirstName", "Petr")
              .formParam("LastName", "Ivanov")
              .formParam("Email", "petr.petrov@gmail.com")
              .formParam("save-info-button", "Save")
              .log().all()
              .when()
              .post("/customer/info")
              .then()
              .log().all()
              .statusCode(302);
    });

  }
}


