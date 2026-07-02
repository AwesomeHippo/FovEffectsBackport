package com.awesomehippo.foveffectsbackport.gui;

import com.awesomehippo.foveffectsbackport.config.Config;

import cpw.mods.fml.client.config.GuiSlider;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionsRowList;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiScreenEvent;

public class VideoSettingsHandler {
    private static final int SLIDER_ID = 0xF0EFEC75; // basically a random ID

    private GuiSlider slider;
    private int lastPercent = -1;

    @SubscribeEvent
    public void onInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
        if (!(event.gui instanceof GuiVideoSettings)) {
            slider = null;
            lastPercent = -1;
            return;
        }

        GuiVideoSettings screen = (GuiVideoSettings) event.gui;
        lastPercent = Config.getSliderPercent();

        slider = new GuiSlider(
                SLIDER_ID,
                screen.width / 2 - 155,
                0,
                150,
                20,
                StatCollector.translateToLocal("options.fovEffectScale") + " ",
                "%",
                0,
                100,
                lastPercent,
                false,
                true
        );

        GuiOptionsRowList rows = (GuiOptionsRowList) screen.optionsRowList;
        rows.field_148184_k.add(new GuiOptionsRowList.Row(slider, null));
    }

    @SubscribeEvent
    public void onDrawScreen(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (!(event.gui instanceof GuiVideoSettings) || slider == null) {
            return;
        }

        int current = slider.getValueInt();
        if (current != lastPercent) {
            lastPercent = current;
            Config.setSliderPercent(current);
        }
    }

    @SubscribeEvent
    public void onActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (!(event.gui instanceof GuiVideoSettings)) {
            return;
        }

        GuiButton button = event.button;
        if (button != null && button.enabled && button.id == 200) {
            Config.save();
        }
    }
}