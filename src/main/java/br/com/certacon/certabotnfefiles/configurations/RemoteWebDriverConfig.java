package br.com.certacon.certabotnfefiles.configurations;

import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;

@Configuration
public class RemoteWebDriverConfig {
    @Bean
    public ChromeOptions chromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("browserVersion", "110.0");
        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            /* How to add test badge */
            put("name", "Test badge...");

            /* How to set session timeout */
            put("sessionTimeout", "60m");

            /* How to set timezone */
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
            }});

            /* How to add "trash" button */
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
            }});

            /* How to enable video recording */
            put("enableVNC", true);
        }});
        options.setExperimentalOption("prefs", new HashMap<String, Object>() {
            {
                put("profile.default_content_settings.popups", 0);
                put("download.default_directory", "/home/selenium/Downloads");
                put("download.prompt_for_download", false);
                put("download.directory_upgrade", true);
            }
        });
        return options;
    }
}
