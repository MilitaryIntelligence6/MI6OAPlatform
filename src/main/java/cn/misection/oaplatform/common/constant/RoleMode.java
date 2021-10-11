package cn.misection.oaplatform.common.constant;

import cn.hutool.core.util.StrUtil;
import cn.misection.oaplatform.config.BuildConfig;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName RoleMode
 * @Description TODO
 * @CreateTime 2021年10月10日 23:26:00
 */
public enum RoleMode {

    /**
     * @important! 不要修改名字!, name() 会用到!;
     * 辅导员模式;
     */
    FDY("FDYSTUList"),

    /**
     * 副书记模式;
     */
    FSJ("FSJCheckQJLists"),
    ;

    private final String suffix;

    RoleMode(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    private static final Map<String, RoleMode> lookup = new HashMap<>();

    static {
        for (RoleMode roleMode : EnumSet.allOf(RoleMode.class)) {
            lookup.put(roleMode.name().toLowerCase(Locale.ROOT).trim(), roleMode);
        }
    }

    public static RoleMode requireOrDefaultByLiteral(String key) {
        RoleMode roleMode = lookup.get(StrUtil.nullToEmpty(key).toLowerCase(Locale.ROOT).trim());
        return roleMode == null
                ? RoleMode.FSJ
                : roleMode;
    }
}
