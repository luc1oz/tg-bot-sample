package ru.luc1o.tgbot.infrastructure.configuration.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.luc1o.tgbot.infrastructure.configuration.Environment;

@Configuration
public class BotPropertiesConfiguration {

    @Bean
    @ConfigurationProperties(prefix = Environment.BOT_CONFIGURATION_PROPERTIES_PREFIX)
    public BotProperties getBotProperties() {
        return new BotProperties();
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class BotProperties {

        private String name;

        private String token;

    }

}
