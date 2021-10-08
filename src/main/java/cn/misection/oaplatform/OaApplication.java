package cn.misection.oaplatform;

import cn.misection.oaplatform.approval.controller.ApprovalController;
import cn.misection.oaplatform.util.crackutil.CrackUtil;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

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
    }

    public static void main(String[] args) {
        new ApprovalController();
    }
}
