package cn.misection.oaplatform.approval.controller;

import cn.misection.oaplatform.approval.ui.ApprovalFrame;
import cn.misection.oaplatform.common.constant.JsPool;
import cn.misection.oaplatform.config.BuildConfig;
import cn.misection.oaplatform.config.ResourceBundle;
import cn.misection.oaplatform.util.nullsafe.NullSafe;
import cn.misection.oaplatform.util.proputil.PropertiesProxy;
import cn.misection.oaplatform.util.uiutil.DialogPopper;
import com.teamdev.jxbrowser.chromium.events.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName ApprovalController
 * @Description TODO
 * @CreateTime 2021年10月08日 19:21:00
 */
public class ApprovalController {

    private final ApprovalFrame frame;

    private final FuncPanelController funcPanelController;

    private final ScheduledExecutorService singleThreadPool = Executors.newScheduledThreadPool(1);

    private long interval = Long.parseLong(String.valueOf(
            PropertiesProxy.instanceWithBundle(
                    ResourceBundle.CONFIG).getProperty("interval")));

    public ApprovalController(ApprovalFrame frame) {
        this.frame = frame;
        this.funcPanelController = new FuncPanelController(frame.getFuncPanel());
        init();
    }

    private void init() {
        initController();
        initActionListener();
        initCallback();
        initState();
    }

    private void initController() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initActionListener() {
        frame.getBrowser().addLoadListener(
                new LoadAdapter() {

                    private int enterLoginPageTime = 0;

                    @Override
                    public void onStartLoadingFrame(StartLoadingEvent event) {
                        super.onStartLoadingFrame(event);
                    }

                    @Override
                    public void onProvisionalLoadingFrame(ProvisionalLoadingEvent event) {
                        super.onProvisionalLoadingFrame(event);
                    }

                    @Override
                    public void onFinishLoadingFrame(FinishLoadingEvent event) {
                        super.onFinishLoadingFrame(event);
                        if (BuildConfig.DEBUG) {
                            System.out.printf("event.getValidatedURL() = %s%n", event.getValidatedURL());
                        }
                        if (event.getValidatedURL().contains("authserver")) {
                            if (++enterLoginPageTime <= 3) {
                                autoLogin();
                            } else {
                                DialogPopper.error("由于三次尝试帮您自动登录失败, 可能是账户密码输入错误的原因, 请检查左边板块的账号秘钥重新登录");
                            }
                        } else if (event.getValidatedURL().contains("FSJCheckQJLists")) {
                            autoApprovalLoopAtFixedRate();
                        }
                    }

                    @Override
                    public void onFailLoadingFrame(FailLoadingEvent event) {
                        super.onFailLoadingFrame(event);
                    }

                    @Override
                    public void onDocumentLoadedInFrame(FrameLoadEvent event) {
                        super.onDocumentLoadedInFrame(event);
                    }

                    @Override
                    public void onDocumentLoadedInMainFrame(LoadEvent event) {
                        super.onDocumentLoadedInMainFrame(event);
                    }
                }
        );
    }

    private void initCallback() {

    }

    private void initState() {
        boolean vpn = Boolean.parseBoolean(
                String.valueOf(
                        PropertiesProxy.instanceWithBundle(
                                ResourceBundle.CONFIG)
                                .getProperty("vpn")));
        if (vpn) {
            frame.getBrowser()
                    .loadURL("https://webvpn.swufe.edu" +
                            ".cn/https/77726476706e69737468656265737421e1ef4bd22e237f45780dc7a596532c/QJ/FSJCheckQJLists");
        } else {
            frame.getBrowser().loadURL("https://qxj.iswufe.info/QJ/FSJCheckQJLists");
        }
    }

    private void autoLogin() {
        frame.getBrowser().executeJavaScript(String.format(
                JsPool.AUTO_LOGIN.value(),
                PropertiesProxy.instanceWithBundle(ResourceBundle.USER).getProperty("username"),
                PropertiesProxy.instanceWithBundle(ResourceBundle.USER).getProperty("password")
        ));
    }

    private void autoApprovalLoopAtFixedRate() {
        singleThreadPool.scheduleAtFixedRate(
                this::autoApprovalSingleTask,
                1,
                interval,
                TimeUnit.SECONDS
        );
    }

    private void autoApprovalSingleTask() {
        if (BuildConfig.DEBUG) {
            System.out.println("loop unit");
        }
    }

    private static class FuncPanelController {

        private final ApprovalFrame.FuncPanel funcPanel;

        private final PropertiesProxy userProxy = PropertiesProxy.instanceWithBundle(ResourceBundle.USER);

        public FuncPanelController(ApprovalFrame.FuncPanel funcPanel) {
            this.funcPanel = funcPanel;
            init();
        }

        private void init() {
            initState();
            initController();
            initActionListener();
        }

        private void initState() {
            funcPanel.getUsernameField().setText(NullSafe.safeString(userProxy.getProperty("username")));
            funcPanel.getPasswordField().setText(NullSafe.safeString(userProxy.getProperty("password")));
        }

        private void initController() {

        }

        private void initActionListener() {
            funcPanel.getClearButton().addActionListener(
                    (ActionEvent e) -> {
                        funcPanel.getPasswordField().setText("");
                        funcPanel.getUsernameField().setText("");
                    }
            );

            funcPanel.getSavaButton().addActionListener(
                    (ActionEvent e) -> {
                        String username = NullSafe.safeString(funcPanel.getUsernameField().getText());
                        String password = NullSafe.safeString(funcPanel.getPasswordField().getPassword());
                        if (username.isEmpty() || password.isEmpty()) {
                            DialogPopper.warning("账号或密码为空", "请检查确认账号密码是否都填写完整!");
                        }
                        userProxy.putAndSave("username", username);
                        userProxy.putAndSave("password", password);
                        DialogPopper.info("保存成功", "账号密码保存成功!");
                    }
            );
        }
    }

}
