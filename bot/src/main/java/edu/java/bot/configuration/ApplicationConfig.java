package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.core.utils.UserData;
import jakarta.validation.constraints.NotEmpty;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken
) {
    private static final String I18N_BUNDLE_PATH = "classpath:i18n/messages";
    private static final String DEFAULT_LOCALE_CODE = "ru";

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
            new ReloadableResourceBundleMessageSource();

        messageSource.setBasenames(I18N_BUNDLE_PATH);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setCacheSeconds(3600);

        return messageSource;
    }

    @Bean
    public Locale[] availableLocales() {
        return new Locale[]{
            Locale.of(DEFAULT_LOCALE_CODE),
            Locale.ENGLISH
        };
    }

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(telegramToken);
    }

    @Bean
    public Map<Long, UserData> trackHandler(){
        return new HashMap<>();
    }
}
