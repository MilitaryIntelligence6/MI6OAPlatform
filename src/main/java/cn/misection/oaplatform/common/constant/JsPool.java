package cn.misection.oaplatform.common.constant;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName JsPool
 * @Description TODO
 * @CreateTime 2021年10月08日 20:34:00
 */
public enum JsPool {

    AUTO_LOGIN("document.getElementById(\"username\").value = \"%s\";\n" +
            "document.getElementById(\"password\").value = \"%s\";\n" +
            "document.getElementsByClassName(\"iCheck-helper\")[0].click();\n" +
            "document.getElementsByClassName(\"auth_login_btn primary full_width\")[0].click();"),
    ;
    private final String value;

    JsPool(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
