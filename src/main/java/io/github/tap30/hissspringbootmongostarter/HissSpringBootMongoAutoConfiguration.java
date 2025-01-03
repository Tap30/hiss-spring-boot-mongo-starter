package io.github.tap30.hissspringbootmongostarter;

import io.github.tap30.hiss.Hiss;
import io.github.tap30.hiss.HissFactory;
import io.github.tap30.hiss.encryptor.Encryptor;
import io.github.tap30.hiss.hasher.Hasher;
import io.github.tap30.hiss.properties.HissProperties;
import io.github.tap30.hiss.properties.HissPropertiesProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;

import java.util.Optional;
import java.util.Set;

@AutoConfiguration
@ConditionalOnClass(Hiss.class)
public class HissSpringBootMongoAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Hiss hiss(Optional<HissPropertiesProvider> optionalHissPropertiesProvider,
                     Set<Encryptor> encryptors,
                     Set<Hasher> hashers) {
        var hissProperties = optionalHissPropertiesProvider
                .map(HissProperties::withProvider)
                .orElse(HissProperties.fromEnv());
        return HissFactory.createHiss(hissProperties, encryptors, hashers);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(AbstractMongoEventListener.class)
    public HissInterceptorMongoIntegration hissInterceptorMongoIntegration(Hiss hiss) {
        return new HissInterceptorMongoIntegration(hiss);
    }

}
