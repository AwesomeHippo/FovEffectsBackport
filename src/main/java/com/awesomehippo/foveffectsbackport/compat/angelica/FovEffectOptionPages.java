package com.awesomehippo.foveffectsbackport.compat.angelica;

import com.awesomehippo.foveffectsbackport.config.Config;

import me.jellysquid.mods.sodium.client.gui.options.Option;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;
import me.jellysquid.mods.sodium.client.gui.options.control.SliderControl;
import net.minecraft.util.StatCollector;

public final class FovEffectOptionPages {
    private static final String NAME_KEY = "options.fovEffectScale";
    private static final String TOOLTIP_KEY = "options.fovEffectScale.tooltip"; // exactly like 1.20.1!

    private FovEffectOptionPages() {}

    public static Option<Integer> fovEffectOption() {
        return OptionImpl.createBuilder(Integer.class, FovEffectOptionStorage.INSTANCE)
                .setName(translate(NAME_KEY))
                .setTooltip(translate(TOOLTIP_KEY))
                .setControl(option -> new SliderControl(option, 0, 100, 1, ControlValueFormatter.percentage()))
                .setBinding((storage, value) -> Config.setSliderPercent(value), storage -> Config.getSliderPercent())
                .build(); //... and no performance impact
    }

    public static OptionGroup fovEffectGroup() {
        return OptionGroup.createBuilder()
                .add(fovEffectOption())
                .build();
    }

    private static String translate(String key) {
        String text = StatCollector.translateToLocal(key);
        if (!text.equals(key)) {
            return text;
        }
        if (key.equals(TOOLTIP_KEY)) {
            return translate(NAME_KEY);
        }
        return key;
    }
}