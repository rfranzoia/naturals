package com.naturals.orders.util;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;

@Singleton
public class ConfigHelper {

    private Config config = ConfigProvider.getConfig();

    public String getString(final String key) {
        return config.getOptionalValue(key, String.class)
                .orElse(key);
    }

    public Integer getInteger(final String key) {
        return config.getOptionalValue(key, Integer.class)
                .orElse(Integer.valueOf(-1));
    }

}
