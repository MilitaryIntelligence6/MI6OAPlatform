package cn.misection.oaplatform.util.uiutil;

import cn.misection.oaplatform.config.PropertiesBundle;
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
public class SkinManager {

    private static final boolean beenDark = Boolean.parseBoolean(
            String.valueOf(
                    PropertiesProxy.getProperty(
                            PropertiesBundle.DARK.getLiteral())));

    private SkinManager() {
        throw new RuntimeException("here are no skinProxy instance for you");
    }

    public static void chooseSetSkinMod() {
        if (beenDark) {
            setDarkSkin();
        } else {
            setLightSkin();
        }
    }

    public static void changeDarkMod() {
        PropertiesProxy.putAndSave(
                PropertiesBundle.DARK.getLiteral(),
                String.valueOf(!beenDark));
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
