package cn.misection.oaplatform.common.constant;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName StringPool
 * @Description TODO
 * @CreateTime 2021年06月11日 20:10:00
 */
public enum StringPool {
    /**
     * 常用 String;
     */
    EMPTY(""),

    SUFFIX_XLSX(".xlsx"),

    SUFFIX_XLS(".xls"),
    ;

    private final String value;

    StringPool(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
