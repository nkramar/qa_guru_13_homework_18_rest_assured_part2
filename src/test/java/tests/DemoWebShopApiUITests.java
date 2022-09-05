package tests;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import static com.codeborne.selenide.Condition.attribute;
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

    step("Open minimal content page", () ->
            open(minPageUrl));

    step("Create user using API", () ->

            given()
                    .filter(withCustomTemplates())
                    .cookie(newUserData.requestVerificationTokenName,
                            newUserData.requestVerificationTokenValue)
                    .formParam(newUserData.requestVerificationTokenName,
                            newUserData.requestVerificationTokenFormParamValue)
                    .formParam("Gender", testData.gender)
                    .formParam("FirstName", testData.firstName)
                    .formParam("LastName", testData.lastName)
                    .formParam("Email", testData.email)
                    .formParam("Password", testData.password)
                    .formParam("ConfirmPassword", testData.confirmPassword)
                    .formParam("register-button", "Register")
                    .log().all()
                    .when()
                    .post(registerUrl)
                    .then()
                    .log().all()
                    .statusCode(302));

    step("Open login URL", () ->
            open(loginUrl));
    step("Fill out login form", () ->
            $("#Email").setValue(testData.email));
    $("#Password").setValue(testData.password).pressEnter();

    step("Check that new registered user can login via UI", () ->
            $(".account").shouldHave(text(testData.email)));

  }

  @Test
  @DisplayName("Change user data using API(UI)")
  void changeUserFirstName() {
    step("Authorization with already existing user",
            () -> {
              String authorizationCookieValue = given()
                      .filter(withCustomTemplates())
                      .formParam("Email", login)
                      .formParam("Password", password)
                      .when()
                      .post(loginUrl)
                      .then()
                      .statusCode(302)
                      .extract().cookie(editUserData.authorizationCookieName);

              step("Changing user name via API",
                      () -> {
                        String newLocationUrl = given()
                                .filter(withCustomTemplates())
                                .cookie(
                                        editUserData.authorizationCookieName,
                                        authorizationCookieValue
                                )
                                .cookie(
                                        editUserData.requestVerificationTokenName,
                                        editUserData.requestVerificationTokenValue
                                )
                                .formParam(
                                        editUserData.requestVerificationTokenName,
                                        editUserData.requestVerificationTokenFormParamValue
                                )
                                .formParam("FirstName", testData.firstName)
                                .formParam("LastName", testData.lastName)
                                .formParam("Email", login)
                                .when()
                                .post(userInfoUrl)
                                .then()
                                .statusCode(302)
                                .extract().header("Location");

                        open(minPageUrl);
                        WebDriverRunner.getWebDriver().manage().addCookie(new Cookie(
                                editUserData.authorizationCookieName,
                                authorizationCookieValue
                        ));

                        step("...Open user profile page",
                                () -> open(newLocationUrl));
                      });

              step("Checking changed user data",
                      () -> editUserData.firstName.shouldHave(attribute("value", testData.firstName)));
                            editUserData.lastName.shouldHave(attribute("value", testData.lastName));
            });
  }
}





