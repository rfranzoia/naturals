package com.critical.example.sample_bitofcode.util;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;

@Singleton
public class MessageHelper {

    public String getMessage(final String key) {
        Config config = ConfigProvider.getConfig();
        return config.getOptionalValue(key, String.class)
                .orElse(key);
    }

}
