package cn.misection.oaplatform.util.urlutil;

import cn.misection.oaplatform.common.constant.RoleMode;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName ApprovalUrlUtil
 * @Description TODO
 * @CreateTime 2021年10月10日 23:35:00
 */
public class ApprovalUrlUtil {

    public static final String UNITE_INFIX = "QJ/";

    public static final String NON_VPN_PREFIX = "https://qxj.iswufe.info/";

    public static final String VPN_PREFIX = "https://webvpn.swufe.edu.cn/" +
            "https/77726476706e69737468656265737421e1ef4bd22e237f45780dc7a596532c/";

    private ApprovalUrlUtil() {
        throw new UnsupportedOperationException(String.format("here are no %s instance for you", getClass().getName()));
    }

    public static String fetch(boolean vpn) {
        return String.format(
                "%s%s",
                vpn
                        ? VPN_PREFIX
                        : NON_VPN_PREFIX,
                UNITE_INFIX
        );
    }

    public static String fetch(boolean vpn, RoleMode roleMode) {
        return String.format(
                "%s%s%s",
                vpn
                        ? VPN_PREFIX
                        : NON_VPN_PREFIX,
                UNITE_INFIX,
                roleMode.getSuffix()
        );
    }
}
