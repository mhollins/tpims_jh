package com.ngc.ts.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.ngc.ts.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.ngc.ts.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.State.class.getName(), jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.District.class.getName(), jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.County.class.getName(), jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.Location.class.getName(), jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.Site.class.getName(), jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.Site.class.getName() + ".devices", jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.Site.class.getName() + ".amenities", jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.Site.class.getName() + ".images", jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.Site.class.getName() + ".logos", jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.Amenities.class.getName(), jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.Images.class.getName(), jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.Logos.class.getName(), jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.SiteStatus.class.getName(), jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.HistoricSiteData.class.getName(), jcacheConfiguration);
            cm.createCache(com.ngc.ts.domain.Device.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
