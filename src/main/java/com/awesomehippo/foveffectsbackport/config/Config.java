package com.awesomehippo.foveffectsbackport.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {
    private static final String CATEGORY = "client";

    private static Configuration configuration;
    private static float fovEffectScale = 1.0F;

    public static void load(File configFile) {
        configuration = new Configuration(configFile);
        configuration.load();

        fovEffectScale = (float) configuration
                .get(CATEGORY, "fovEffectScale", 1.0, null, 0.0, 1.0)
                .getDouble();

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static float getFovEffectScale() {
        return fovEffectScale;
    }

    public static int getSliderPercent() {
        return Math.round(fovEffectScale * fovEffectScale * 100.0F);
    }

    // just like vanilla 1.20.1 : percent->sqrt
    public static void setSliderPercent(int percent) {
        int clamped = Math.max(0, Math.min(100, percent));
        fovEffectScale = (float) Math.sqrt(clamped / 100.0D);
    }

    public static void save() {
        if (configuration == null) {
            return;
        }

        configuration.get(CATEGORY, "fovEffectScale", 1.0, null, 0.0, 1.0)
                .set(fovEffectScale);
        configuration.save();
    }
}