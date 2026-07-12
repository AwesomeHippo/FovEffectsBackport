package com.awesomehippo.foveffectsbackport;

import com.awesomehippo.foveffectsbackport.config.Config;

import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FovEffectsBackportHandler {

    // main logic
    @SubscribeEvent
    public void onFovUpdate(FOVUpdateEvent event) {
        float scale = Config.getFovEffectScale();
        if (scale >= 1.0F) {
            return;
        }

        event.setNewfov(1.0F + (event.getFov() - 1.0F) * scale);
    }
}