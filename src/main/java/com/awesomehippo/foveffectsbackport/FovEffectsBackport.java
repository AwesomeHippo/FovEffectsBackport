package com.awesomehippo.foveffectsbackport;

import com.awesomehippo.foveffectsbackport.config.Config;
import com.awesomehippo.foveffectsbackport.gui.VideoSettingsHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = FovEffectsBackport.MOD_ID, name = "FOV Effects Backport", version = "1.0.0", acceptedMinecraftVersions = "[1.12.2]")
public class FovEffectsBackport {
    public static final String MOD_ID = "foveffectsbackport";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (FMLCommonHandler.instance().getSide().isClient()) {
            Config.load(event.getSuggestedConfigurationFile());
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (!FMLCommonHandler.instance().getSide().isClient()) {
            return;
        }

        MinecraftForge.EVENT_BUS.register(new FovEffectsBackportHandler());
        MinecraftForge.EVENT_BUS.register(new VideoSettingsHandler());
    }
}