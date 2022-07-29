package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:properties/${host}.properties"
})

public interface LaunchConfig extends Config {

    @Key("remoteUrl")
    String getRemoteUrl();

    @Key ("browser")
    @DefaultValue("chrome")
    String getBrowser();

    @Key("browserSize")
    @DefaultValue("1920x1080")
    String getBrowserSize();

    @Key("browserVersion")
    @DefaultValue("100.0")
    String getBrowserVersion();
}
