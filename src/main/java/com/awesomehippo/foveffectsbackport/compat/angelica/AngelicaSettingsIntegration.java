package com.awesomehippo.foveffectsbackport.compat.angelica;

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.collect.ImmutableList;

import me.jellysquid.mods.sodium.client.gui.options.Option;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StatCollector;

public final class AngelicaSettingsIntegration {
    private static final String GENERAL_PAGE_KEY = "stat.generalButton";
    private static final String NOTFINE_GENERAL_PAGE_KEY = "options.video";
    private static final String FOV_OPTION_NAME = I18n.format("options.fovEffectScale");

    private AngelicaSettingsIntegration() {}

    // we patch their gui lists when the screen opens since there's no way to register options
    public static void injectIntoScreen(GuiScreen screen) {
        if (screen == null) {
            return;
        }

        try {
            if (isNotFineMenu(screen)) {
                injectIntoNotFineMenu(screen);
                return;
            }

            if (isSodiumOptionsScreen(screen)) {
                injectIntoSodiumPages(screen);
            }
        } catch (Throwable ignored) {
            // may not work depending on the version, so we'll just skip the slider..
        }
    }

    private static boolean isNotFineMenu(GuiScreen screen) {
        return "jss.notfine.gui.GuiCustomMenu".equals(screen.getClass().getName());
    }

    private static boolean isSodiumOptionsScreen(GuiScreen screen) {
        Class<?> type = screen.getClass();
        while (type != null && type != GuiScreen.class) {
            if ("me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI".equals(type.getName())) {
                return true;
            }
            type = type.getSuperclass();
        }
        return false;
    }

    private static void injectIntoNotFineMenu(GuiScreen screen) throws ReflectiveOperationException {
        Field optionPageField = screen.getClass().getDeclaredField("optionPage");
        optionPageField.setAccessible(true);
        OptionPage currentPage = (OptionPage) optionPageField.get(screen);
        OptionPage updatedPage = appendFovOption(currentPage);
        if (updatedPage != currentPage) {
            optionPageField.set(screen, updatedPage);
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectIntoSodiumPages(GuiScreen screen) throws ReflectiveOperationException {
        Field pagesField = findPagesField(screen.getClass());
        if (pagesField == null) {
            return;
        }

        List<OptionPage> pages = (List<OptionPage>) pagesField.get(screen);
        if (pages == null || pages.isEmpty()) {
            return;
        }

        int targetIndex = findGeneralPageIndex(pages);
        if (targetIndex < 0) {
            return;
        }

        OptionPage page = pages.get(targetIndex);
        OptionPage updatedPage = appendFovOption(page);
        if (updatedPage != page) {
            pages.set(targetIndex, updatedPage);
        }
    }

    private static Field findPagesField(Class<?> type) {
        while (type != null && type != GuiScreen.class) {
            try {
                Field field = type.getDeclaredField("pages");
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException ignored) {
                type = type.getSuperclass();
            }
        }
        return null;
    }

    private static int findGeneralPageIndex(List<OptionPage> pages) {
        String generalName = StatCollector.translateToLocal(GENERAL_PAGE_KEY);
        String notFineName = StatCollector.translateToLocal(NOTFINE_GENERAL_PAGE_KEY);

        for (int i = 0; i < pages.size(); i++) {
            String pageName = pages.get(i).getName();
            if (generalName.equals(pageName) || notFineName.equals(pageName)) {
                return i;
            }
        }

        return 0;
    }

    private static OptionPage appendFovOption(OptionPage page) {
        for (Option<?> option : page.getOptions()) {
            if (FOV_OPTION_NAME.equals(option.getName())) {
                return page;
            }
        }

        ImmutableList.Builder<OptionGroup> groups = ImmutableList.builder();
        groups.addAll(page.getGroups());
        groups.add(FovEffectOptionPages.fovEffectGroup());
        return new OptionPage(page.getName(), groups.build());
    }
}