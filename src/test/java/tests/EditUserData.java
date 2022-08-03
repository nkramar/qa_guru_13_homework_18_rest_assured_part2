package tests;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class EditUserData {
  String
          authorizationCookieName = "NOPCOMMERCE.AUTH",
          requestVerificationTokenName = "__RequestVerificationToken",
          requestVerificationTokenValue = "ruwO6cbPsCZ17AixcpW3h1i4TqF_dTwFGqc1_" +
                  "-EccP5zRSibl9Fdz8-3ShlysgmimUr9e5ypdkUt" +
                  "muFYTAV8IO7VzB3O_-scmg-ywBtGIeo1;",
          requestVerificationTokenFormParamValue = "zuvoT4TvaDdQ0jKATgH4_" +
                  "b2kLTW976AV8CcVbw0HCZ9VEXz6gUm7AxbCP0fx9TrYmIuYnci1j52" +
                  "obk9NfxRN7ESHj9v9pNsXIQFN4jMD2lV6cnhdwNdrz5uebLMDOoyL0";

  SelenideElement firstName = $("#FirstName");
  SelenideElement lastName = $("#LastName");


}
