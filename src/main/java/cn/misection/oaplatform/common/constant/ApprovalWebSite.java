package cn.misection.oaplatform.common.constant;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName ApprivalUrl
 * @Description TODO
 * @CreateTime 2021年10月10日 23:18:00
 */
public enum ApprovalWebSite {

    /**
     * 枚举站点;
     */
    FSJ("https://qxj.iswufe.info/QJ/FSJCheckQJLists"),

    FSJ_VPN("https://webvpn.swufe.edu.cn/https/77726476706e69737468656265737421e1ef4bd22e237f45780dc7a596532c/QJ/FSJCheckQJLists"),

    FDY("https://qxj.iswufe.info/QJ/FDYSTUList"),

    FDY_VPN("https://webvpn.swufe.edu.cn/https/77726476706e69737468656265737421e1ef4bd22e237f45780dc7a596532c/QJ/FDYSTUList"),
    ;



    private final String url;

    ApprovalWebSite(String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }
}
