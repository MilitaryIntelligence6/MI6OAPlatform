package cn.misection.oaplatform.approval.ui;

import cn.misection.oaplatform.util.uiutil.CenterUtil;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName ApprovalView
 * @Description TODO
 * @CreateTime 2021年10月08日 19:13:00
 */
public class ApprovalFrame extends JFrame {

    private final Browser browser = new Browser();

    private final BrowserView browserView = new BrowserView(browser);

    public ApprovalFrame() {
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        measureView();
        layoutView();
        drawView();
    }

    private void measureView() {
        this.setSize(800, 600);
    }

    private void layoutView() {
        this.setLayout(new BorderLayout());
        layoutChildren();
    }

    private void drawView() {
        CenterUtil.keepCenter(this);
        this.setVisible(true);
    }

    private void layoutChildren() {
        this.add(browserView, BorderLayout.CENTER);
    }

    public Browser getBrowser() {
        return browser;
    }

    public BrowserView getBrowserView() {
        return browserView;
    }
}
