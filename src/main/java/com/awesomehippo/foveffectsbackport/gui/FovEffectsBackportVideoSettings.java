package com.awesomehippo.foveffectsbackport.gui;

import com.awesomehippo.foveffectsbackport.config.Config;

import cpw.mods.fml.client.config.GuiSlider;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionsRowList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.StatCollector;

public class FovEffectsBackportVideoSettings extends GuiVideoSettings {
    private static final int SLIDER_ID = 0xF0EFEC75; // basically a random ID

    private GuiSlider slider;
    private int lastPercent = -1;

    public FovEffectsBackportVideoSettings(GuiScreen parent, GameSettings settings) {
        super(parent, settings);
    }

    @Override
    public void initGui() {
        super.initGui();
        lastPercent = Config.getSliderPercent();

        slider = new GuiSlider(
                SLIDER_ID,
                width / 2 - 155,
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

        GuiOptionsRowList rows = (GuiOptionsRowList) optionsRowList;
        rows.field_148184_k.add(new GuiOptionsRowList.Row(slider, null));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled && button.id == 200) {
            Config.save();
        }

        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        updateFromSlider();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void updateFromSlider() {
        if (slider == null) {
            return;
        }

        int current = slider.getValueInt();
        if (current != lastPercent) {
            lastPercent = current;
            Config.setSliderPercent(current);
        }
    }
}