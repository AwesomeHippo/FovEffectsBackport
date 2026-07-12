package com.awesomehippo.foveffectsbackport.gui;

import com.awesomehippo.foveffectsbackport.config.Config;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionsRowList;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class VideoSettingsHandler {
    private static final int SLIDER_ID = 0xF0EFEC75; // basically a random ID

    private GuiSlider slider;
    private int lastPercent = -1;

    @SubscribeEvent
    public void onInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
        if (!(event.getGui() instanceof GuiVideoSettings)) {
            slider = null;
            lastPercent = -1;
            return;
        }

        GuiVideoSettings screen = (GuiVideoSettings) event.getGui();
        lastPercent = Config.getSliderPercent();

        slider = new GuiSlider(
                SLIDER_ID,
                screen.width / 2 - 155,
                0,
                150,
                20,
                I18n.format("options.fovEffectScale") + " ",
                "%",
                0,
                100,
                lastPercent,
                false,
                true
        );

        GuiOptionsRowList rows = (GuiOptionsRowList) screen.optionsRowList;
        rows.options.add(new GuiOptionsRowList.Row(slider, null));
    }

    @SubscribeEvent
    public void onDrawScreen(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (!(event.getGui() instanceof GuiVideoSettings) || slider == null) {
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
        if (!(event.getGui() instanceof GuiVideoSettings)) {
            return;
        }

        GuiButton button = event.getButton();
        if (button != null && button.enabled && button.id == 200) {
            Config.save();
        }
    }
}