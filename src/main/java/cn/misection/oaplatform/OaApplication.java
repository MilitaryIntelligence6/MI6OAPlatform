package cn.misection.oaplatform;

import cn.misection.oaplatform.approval.controller.ApprovalController;
import cn.misection.oaplatform.util.crackutil.CrackUtil;
import cn.misection.oaplatform.util.uiutil.SkinManager;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName Application
 * @Description TODO
 * @CreateTime 2021年10月08日 19:09:00
 */
public class OaApplication {
    static {
        CrackUtil.crackJxBrowser();
        SkinManager.setDarkSkin();
    }

    public static void main(String[] args) {
        new ApprovalController();
    }
}
