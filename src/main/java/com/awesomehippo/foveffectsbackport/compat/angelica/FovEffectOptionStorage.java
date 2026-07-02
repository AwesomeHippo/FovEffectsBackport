package com.awesomehippo.foveffectsbackport.compat.angelica;

import com.awesomehippo.foveffectsbackport.config.Config;

import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;

public final class FovEffectOptionStorage implements OptionStorage<FovEffectOptionStorage> {
    public static final FovEffectOptionStorage INSTANCE = new FovEffectOptionStorage();

    private FovEffectOptionStorage() {
    }

    @Override
    public FovEffectOptionStorage getData() {
        return this;
    }

    @Override
    public void save() {
        Config.save();
    }
}