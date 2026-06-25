package com.awesomehippo.foveffectsbackport;

import com.awesomehippo.foveffectsbackport.config.Config;
import com.awesomehippo.foveffectsbackport.gui.VideoSettingsHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = FovEffectsBackport.MOD_ID, name = "FOV Effects Backport", version = "1.0.0", acceptedMinecraftVersions = "[1.7.10]")
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
