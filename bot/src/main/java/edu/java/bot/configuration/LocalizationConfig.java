package edu.java.bot.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Configuration
public class LocalizationConfig {
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
    public Locale defaultLocale() {
        return Locale.of(DEFAULT_LOCALE_CODE);
    }

    @Bean
    public Locale[] availableLocales(Locale defaultLocale) {
        return new Locale[] {
            defaultLocale,
            Locale.ENGLISH
        };
    }
}
