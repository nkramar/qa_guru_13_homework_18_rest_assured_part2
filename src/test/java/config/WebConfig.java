package config;

import org.aeonbits.owner.Config;

public interface WebConfig extends Config {
    @Key("baseUrl")
    @DefaultValue("http://demowebshop.tricentis.com")
    String getBaseUrl();

    @Key("minPageUrl")
    @DefaultValue("/Themes/DefaultClean/Content/images/logo.png")
    String getMinPageUrl();

    @Key("loginUrl")
    @DefaultValue("/login")
    String getLoginUrl();

    @Key("registerUrl")
    @DefaultValue("/register")
    String getRegisterUrl();

    @Key("someUrl")
    @DefaultValue("/")
    String getSomeUrl();


}
