package cn.misection.oaplatform.util.crackutil;

import com.teamdev.jxbrowser.chromium.ba;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName CrackUtil
 * @Description TODO
 * @CreateTime 2021年10月08日 19:06:00
 */
public final class CrackUtil {

    private CrackUtil() {
        throw new UnsupportedOperationException(String.format("here are no %s instance for you", getClass().getName()));
    }

    public static void crackJxBrowser() {
        try {
            Field e = ba.class.getDeclaredField("e");
            e.setAccessible(true);
            Field f = ba.class.getDeclaredField("f");
            f.setAccessible(true);
            Field modifersField = Field.class.getDeclaredField("modifiers");
            modifersField.setAccessible(true);
            modifersField.setInt(e, e.getModifiers() & ~Modifier.FINAL);
            modifersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
            e.set(null, new BigInteger("1"));
            f.set(null, new BigInteger("1"));
            modifersField.setAccessible(false);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
