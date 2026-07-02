package com.awesomehippo.foveffectsbackport.compat.angelica;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.GuiScreenEvent;

public class AngelicaSettingsHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onInitGui(GuiScreenEvent.InitGuiEvent.Pre event) {
        AngelicaSettingsIntegration.injectIntoScreen(event.gui);
    }
}