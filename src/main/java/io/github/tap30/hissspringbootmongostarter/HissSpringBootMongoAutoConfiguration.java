package io.github.tap30.hissspringbootmongostarter;

import io.github.tap30.hiss.Hiss;
import io.github.tap30.hiss.HissFactory;
import io.github.tap30.hiss.HissPropertiesFromEnvProvider;
import io.github.tap30.hiss.HissPropertiesProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;

import java.util.Optional;

@AutoConfiguration
@ConditionalOnClass(Hiss.class)
public class HissSpringBootMongoAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Hiss hiss(Optional<HissPropertiesProvider> optionalHissPropertiesProvider) {
        var hissPropertiesProvider = optionalHissPropertiesProvider
                .orElse(new HissPropertiesFromEnvProvider());
        return HissFactory.createHiss(hissPropertiesProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(AbstractMongoEventListener.class)
    public HissInterceptorMongoIntegration hissInterceptorMongoIntegration(Hiss hiss) {
        return new HissInterceptorMongoIntegration(hiss);
    }

}
