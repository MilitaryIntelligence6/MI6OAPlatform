package cn.misection.oaplatform.approval.controller;

import cn.misection.oaplatform.approval.ui.ApprovalFrame;

import javax.swing.*;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName ApprovalController
 * @Description TODO
 * @CreateTime 2021年10月08日 19:21:00
 */
public class ApprovalController {

    private final ApprovalFrame frame = new ApprovalFrame();

    public ApprovalController() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getBrowser().loadURL("https://www.baidu.com/");
//        frame.getBrowser().executeJavaScript("alert(\"hello, jxbrwser\")");
    }
}
