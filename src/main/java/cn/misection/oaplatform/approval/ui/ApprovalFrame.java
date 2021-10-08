package cn.misection.oaplatform.approval.ui;

import cn.misection.oaplatform.common.ui.component.DarkModToggleButton;
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

    private final FuncPanel funcPanel = new FuncPanel();

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
        this.setSize(1200, 800);
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
        this.add(funcPanel, BorderLayout.WEST);
    }

    public Browser getBrowser() {
        return browser;
    }

    public BrowserView getBrowserView() {
        return browserView;
    }

    /**
     * @author Military Intelligence 6 root
     * @version 1.0.0
     * @ClassName FuncPanel
     * @Description TODO
     * @CreateTime 2021年10月08日 19:41:00
     */
    private static class FuncPanel extends JPanel {

        private final JPanel vpnPanel = new JPanel();

        private final JLabel vpnLabel = new JLabel("WebVpn 模式");

        private final JToggleButton vpnModeSwitch = new JToggleButton();

        private final JPanel darkModPanel = new JPanel();

        private final JLabel darkModLabel = new JLabel("夜间模式");

        private final DarkModToggleButton darkModToggleButton = new DarkModToggleButton();

        public FuncPanel() {
            this.setLayout(new GridLayout(5, 1));
            this.setPreferredSize(new Dimension(300, 0));

            vpnModeSwitch.setPreferredSize(new Dimension(50, 30));
            darkModToggleButton.setPreferredSize(new Dimension(50, 30));

            vpnPanel.add(vpnLabel);
            vpnPanel.add(vpnModeSwitch);
            darkModPanel.add(darkModLabel);
            darkModPanel.add(darkModToggleButton);

            this.add(vpnPanel);
            this.add(darkModPanel);
        }
    }
}
