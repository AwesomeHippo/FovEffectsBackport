package com.awesomehippo.foveffectsbackport;

import com.awesomehippo.foveffectsbackport.config.Config;
import com.awesomehippo.foveffectsbackport.gui.VideoSettingsHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = FovEffectsBackport.MOD_ID, name = "FOV Effects Backport", version = "1.0.2", acceptedMinecraftVersions = "[1.7.10]")
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

        if (Loader.isModLoaded("angelica")) {
            registerAngelicaCompat();
        } else {
            MinecraftForge.EVENT_BUS.register(new VideoSettingsHandler());
        }
    }

    private static void registerAngelicaCompat() {
        try {
            Class<?> handler = Class.forName("com.awesomehippo.foveffectsbackport.compat.angelica.AngelicaSettingsHandler");
            MinecraftForge.EVENT_BUS.register(handler.newInstance());
        } catch (Throwable ignored) {
        }
    }
}
