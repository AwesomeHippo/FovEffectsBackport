package com.awesomehippo.foveffectsbackport;

import com.awesomehippo.foveffectsbackport.config.Config;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;

public class FovEffectsBackportHandler {

    // main logic
    @SubscribeEvent
    public void onFovUpdate(FOVUpdateEvent event) {
        float scale = Config.getFovEffectScale();
        if (scale >= 1.0F) {
            return;
        }

        event.newfov = 1.0F + (event.fov - 1.0F) * scale;
    }
}