package com.awesomehippo.foveffectsbackport.gui;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraftforge.client.event.GuiOpenEvent;

public class VideoSettingsHandler {

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        // video settings only
        if (event.gui == null || event.gui.getClass() != GuiVideoSettings.class) {
            return;
        }

        GuiVideoSettings vanilla = (GuiVideoSettings) event.gui;
        event.gui = new FovEffectsBackportVideoSettings(vanilla.parentGuiScreen, vanilla.guiGameSettings);
    }
}