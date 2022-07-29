package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class DemoWebShopUiTests extends TestBase {

    @Test
    @Tag("UI")
    @DisplayName("Authorize to demowebshop (UI)")
    void loginTest() {

        step("Open login page", () ->
                open(loginUrl));

        step("Fill login form", () -> {
            $("#Email").setValue(login);
            $("#Password").setValue(password)
                    .pressEnter();
        });

        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(login)));
    }


}
