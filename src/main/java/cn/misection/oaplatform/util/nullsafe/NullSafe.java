package cn.misection.oaplatform.util.nullsafe;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName SafeStringUtil
 * @Description TODO
 * @CreateTime 2021年06月11日 21:02:00
 */
public class NullSafe {

    private NullSafe() {
        throw new UnsupportedOperationException(String.format("here are no %s instance for you", getClass().getName()));
    }

    public static String safeString(String string) {
        return string == null
                ? ""
                : string.trim();
    }

    public static String safeString(Object obj) {
        return obj == null
                ? ""
                : String.valueOf(obj).trim();
    }

    public static String safeString(char[] data) {
        return data == null
                ? ""
                : String.valueOf(data).trim();
    }

    public static String safeDoubleString(String string) {
        if (string == null || string.isEmpty()) {
            return "0";
        }
        return string;
    }
}
