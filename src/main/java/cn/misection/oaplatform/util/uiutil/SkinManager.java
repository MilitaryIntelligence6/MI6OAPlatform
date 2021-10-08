package cn.misection.oaplatform.util.uiutil;

import cn.misection.oaplatform.config.ResourceBundle;
import cn.misection.oaplatform.util.proputil.PropertiesProxy;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName SkinUtil
 * @Description TODO
 * @CreateTime 2021年06月12日 00:13:00
 */
public final class SkinManager {

    private static final boolean beenDark = Boolean.parseBoolean(
            String.valueOf(PropertiesProxy
                    .instanceWithBundle(ResourceBundle.CONFIG)
                    .getProperty("dark")));

    private SkinManager() {
        throw new UnsupportedOperationException(String.format("here are no %s instance for you", getClass().getName()));
    }

    public static void chooseSetSkinMod() {
        if (beenDark) {
            setDarkSkin();
        } else {
            setLightSkin();
        }
    }

    public static void changeDarkMod() {
        PropertiesProxy
                .instanceWithBundle(ResourceBundle.CONFIG)
                .putAndSave("dark", String.valueOf(!beenDark));
    }

    public static void setDarkSkin() {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void setLightSkin() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static boolean isBeenDark() {
        return beenDark;
    }
}
