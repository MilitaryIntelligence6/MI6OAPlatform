package cn.misection.oaplatform.approval.ui;

import cn.misection.oaplatform.common.ui.component.MyToggleButton;
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

    private final FuncPanel funcPanel = new FuncPanel();

    public ApprovalFrame() {
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        setTitle("欢迎您使用 MI6 自动化请假管理办公系统");
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
        this.add(new BrowserView(browser), BorderLayout.CENTER);
        this.add(funcPanel, BorderLayout.WEST);
    }

    public Browser getBrowser() {
        return browser;
    }

    public FuncPanel getFuncPanel() {
        return funcPanel;
    }

    /**
     * @author Military Intelligence 6 root
     * @version 1.0.0
     * @ClassName FuncPanel
     * @Description TODO
     * @CreateTime 2021年10月08日 19:41:00
     */
    public static class FuncPanel extends JPanel {

        private static final Dimension switchDimen = new Dimension(50, 30);

        private static final GridLayout splitLayout = new GridLayout(1, 2);

        private static final GridLayout panelSplitLayout = new GridLayout(3, 1, 0, 20);

        private final JTextField usernameField = new JTextField();

        private final JPasswordField passwordField = new JPasswordField();

        private final JButton clearUserButton = new JButton("清除");

        private final JButton savaUserButton = new JButton("保存");

        private final JTextField intervalField = new JTextField();

        private final JButton saveIntervalButton = new JButton("保存并重置时间间隔");

        private final JRadioButton fdyModButton = new JRadioButton("辅导员模式");

        private final JRadioButton fsjModButton = new JRadioButton("副书记模式");

        private final JButton reloadButton = new JButton("刷新页面");

        private final MyToggleButton vpnModeSwitch = new MyToggleButton() {
            {
                setPreferredSize(switchDimen);
            }
        };

        private final MyToggleButton darkModSwitch = new MyToggleButton() {
            {
                setPreferredSize(switchDimen);
            }
        };

        private final JPanel titlePanel = new JPanel() {
            {
                setLayout(panelSplitLayout);
                add(new JPanel());
                add(new JPanel() {
                    {
                        add(new JLabel("控制面板"));
                    }
                });
                add(new JPanel() {
                    {
                        add(new JLabel("MI6 控制台"));
                    }
                });
            }
        };

        private final JPanel switchPanel = new JPanel() {
            {
                setLayout(panelSplitLayout);
                final JPanel vpnPanel = new JPanel() {
                    {
                        setLayout(splitLayout);
                        add(new JLabel("WebVpn 模式"));
                        add(vpnModeSwitch);
                    }
                };

                final JPanel darkModPanel = new JPanel() {
                    {
                        setLayout(splitLayout);
                        add(new JLabel("夜间模式"));
                        add(darkModSwitch);
                    }
                };
                final JPanel rolePanel = new JPanel() {
                    {
                        setLayout(splitLayout);
                        ButtonGroup buttonGroup = new ButtonGroup();
                        buttonGroup.add(fdyModButton);
                        buttonGroup.add(fsjModButton);
                        add(fdyModButton);
                        add(fsjModButton);
                    }
                };
                add(vpnPanel);
                add(darkModPanel);
                add(rolePanel);
            }
        };

        private final JPanel userPanel = new JPanel() {
            {
                setLayout(panelSplitLayout);
                final JPanel usernamePanel = new JPanel() {
                    {
                        setLayout(splitLayout);
                        add(new JLabel("用户名"));
                        add(usernameField);
                    }
                };

                final JPanel passwordPanel = new JPanel() {
                    {
                        setLayout(splitLayout);
                        add(new JLabel("密码"));
                        add(passwordField);
                    }
                };

                final JPanel buttonPanel = new JPanel() {
                    {
                        setLayout(splitLayout);
                        add(clearUserButton);
                        add(savaUserButton);
                    }
                };

                add(usernamePanel);
                add(passwordPanel);
                add(buttonPanel);
            }
        };

        private final JPanel controlPanel = new JPanel() {
            {
                setLayout(panelSplitLayout);
                final JPanel internalPanel = new JPanel() {
                    {
                        setLayout(splitLayout);
                        add(new JLabel("时间间隔(秒)"));
                        add(intervalField);
                    }
                };
                add(internalPanel);
                add(saveIntervalButton);
                add(reloadButton);
            }
        };

        private final JPanel copyRightPanel = new JPanel() {
            {
                setLayout(panelSplitLayout);
                add(new JPanel() {
                    {
                        add(new JLabel("版权所有: MI6"));
                    }
                });
                add(new JPanel() {
                    {
                        add(new JLabel("Copyright (c) 2021 MI6 "));
                    }
                });
            }
        };

        public FuncPanel() {
            this.setLayout(new GridLayout(5, 1, 10, 20));
            this.setPreferredSize(new Dimension(200, 0));

            this.add(titlePanel);
            this.add(userPanel);
            this.add(switchPanel);
            this.add(controlPanel);
            this.add(copyRightPanel);
        }

        public JTextField getUsernameField() {
            return usernameField;
        }

        public JPasswordField getPasswordField() {
            return passwordField;
        }

        public JButton getClearUserButton() {
            return clearUserButton;
        }

        public JButton getSavaUserButton() {
            return savaUserButton;
        }

        public JTextField getIntervalField() {
            return intervalField;
        }

        public JButton getSaveIntervalButton() {
            return saveIntervalButton;
        }

        public JRadioButton getFdyModButton() {
            return fdyModButton;
        }

        public JRadioButton getFsjModButton() {
            return fsjModButton;
        }

        public JButton getReloadButton() {
            return reloadButton;
        }

        public MyToggleButton getVpnModeSwitch() {
            return vpnModeSwitch;
        }

        public MyToggleButton getDarkModSwitch() {
            return darkModSwitch;
        }
    }
}
