package cn.misection.oaplatform.approval.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.misection.oaplatform.approval.entity.SingleAskForLeave;
import cn.misection.oaplatform.approval.ui.ApprovalFrame;
import cn.misection.oaplatform.common.constant.RoleMode;
import cn.misection.oaplatform.config.BuildConfig;
import cn.misection.oaplatform.config.ResourceBundle;
import cn.misection.oaplatform.util.nullsafe.NullSafe;
import cn.misection.oaplatform.util.proputil.PropertiesProxy;
import cn.misection.oaplatform.util.uiutil.DialogPopper;
import cn.misection.oaplatform.util.urlutil.ApprovalUrlUtil;
import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    private static final Log logger = Log.get();

    private static final PropertiesProxy configProxy = PropertiesProxy.instanceWithBundle(ResourceBundle.CONFIG);

    private static final PropertiesProxy userProxy = PropertiesProxy.instanceWithBundle(ResourceBundle.USER);

    private final ScheduledExecutorService singleThreadPool = Executors.newScheduledThreadPool(1);

    private long interval = configProxy.getLong("interval");

    private boolean vpn = configProxy.getBoolean("vpn");

    private final ApprovalFrame frame;

    private final FuncPanelController funcPanelController;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private String url;

    private RoleMode roleMode = RoleMode.requireOrDefaultByLiteral(configProxy.getSafeString("role"));

    public ApprovalController(ApprovalFrame frame) {
        this.frame = frame;
        this.funcPanelController = new FuncPanelController(this);
        init();
    }

    private void init() {
        initController();
        initState();
        initActionListener();
        initApprovalLooper();
    }

    private void initController() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initState() {
        reGenUrl();
        reload();
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
                        if (event.isMainFrame()) {
                            if (BuildConfig.DEBUG) {
                                logger.debug("event.getValidatedURL() = %s%n", event.getValidatedURL());
                            }
                            boolean needReload = false;
                            DOMDocument document = event.getBrowser().getDocument();
                            DOMElement titleDom = document.findElement(By.tagName("title"));
                            if (titleDom != null) {
                                if (titleDom.getInnerHTML().contains("访问出错")) {
                                    needReload = true;
                                }
                            }
                            if (event.getValidatedURL().contains(";")
                                    || event.getValidatedURL().contains("jsessionid") || needReload) {
                                reload();
                            } else if (event.getValidatedURL().contains("authserver")) {
                                if (++enterLoginPageTime <= 3) {
                                    autoLogin();
                                } else {
                                    DialogPopper.error("由于三次尝试帮您自动登录失败, 可能是账户密码输入错误的原因, 请检查左边板块的账号秘钥重新登录");
                                }
                            } else if (event.getValidatedURL().contains("FSJCheckQJLists") || event.getValidatedURL
                            ().contains("FDYSTUList")) {
                                executorService.schedule(
                                        ApprovalController.this::autoApprovalSingleTask,
                                        5,
                                        TimeUnit.SECONDS);
                            }
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

        frame.getBrowser().setDialogHandler(
                new DialogHandler() {
                    @Override
                    public void onAlert(DialogParams dialogParams) {
                        if (BuildConfig.DEBUG) {
                            logger.debug("ApprovalController::onAlert");
                        }
                        logger.debug("dialogParams = " + dialogParams);
                    }

                    @Override
                    public CloseStatus onConfirmation(DialogParams dialogParams) {
                        if (BuildConfig.DEBUG) {
                            logger.debug("ApprovalController::onConfirmation");
                        }
                        logger.debug("dialogParams = " + dialogParams);
                        return CloseStatus.OK;
                    }

                    @Override
                    public CloseStatus onPrompt(PromptDialogParams promptDialogParams) {
                        if (BuildConfig.DEBUG) {
                            logger.debug("ApprovalController::onPrompt");
                        }
                        return null;
                    }

                    @Override
                    public CloseStatus onFileChooser(FileChooserParams fileChooserParams) {
                        if (BuildConfig.DEBUG) {
                            logger.debug("ApprovalController::onFileChooser");
                        }
                        return null;
                    }

                    @Override
                    public CloseStatus onBeforeUnload(UnloadDialogParams unloadDialogParams) {
                        if (BuildConfig.DEBUG) {
                            logger.debug("ApprovalController::onBeforeUnload");
                        }
                        return null;
                    }

                    @Override
                    public CloseStatus onSelectCertificate(CertificatesDialogParams certificatesDialogParams) {
                        if (BuildConfig.DEBUG) {
                            logger.debug("ApprovalController::onSelectCertificate");
                        }
                        return null;
                    }

                    @Override
                    public CloseStatus onReloadPostData(ReloadPostDataParams reloadPostDataParams) {
                        if (BuildConfig.DEBUG) {
                            logger.debug("ApprovalController::onReloadPostData");
                        }
                        return null;
                    }

                    @Override
                    public CloseStatus onColorChooser(ColorChooserParams colorChooserParams) {
                        if (BuildConfig.DEBUG) {
                            logger.debug("ApprovalController::onColorChooser");
                        }
                        return null;
                    }
                }
        );
    }

    private void autoLogin() {
        // FIXME: 2021/10/11 改成 Java;
        DOMDocument document = frame.getBrowser().getDocument();
        DOMElement usernameField = document.findElement(By.id("username"));
        DOMElement passwordField = document.findElement(By.id("password"));
        DOMElement icheckHelper = document.findElement(By.className("iCheck-helper"));
        DOMElement authLoginBtnPrimaryFullWidth = document.findElement(By.className("auth_login_btn primary " +
                "full_width"));
        if (usernameField != null) {
            usernameField.setAttribute("value", userProxy.getSafeString("username"));
        }
        if (passwordField != null) {
            passwordField.setAttribute("value", userProxy.getSafeString("password"));
        }
        if (icheckHelper != null) {
            icheckHelper.click();
        }
        if (authLoginBtnPrimaryFullWidth != null) {
            authLoginBtnPrimaryFullWidth.click();
        }
    }

    private void initApprovalLooper() {
        singleThreadPool.scheduleAtFixedRate(
                this::reload,
                1,
                interval,
                TimeUnit.SECONDS
        );
    }

    private void autoApprovalSingleTask() {
        DOMDocument document = frame.getBrowser().getDocument();
        DOMElement dataTablesEmpty = document.findElement(By.className("dataTables_empty"));
        DOMElement tbfDs = document.findElement(By.id("tbf_ds"));
        boolean canEvalScript = true;
        // empty 不存在(书记页)
        if (dataTablesEmpty != null) {
            canEvalScript = false;
        }
        // tbfds 不是暂无数据;
        if (tbfDs != null) {
            if (tbfDs.getInnerHTML().contains("暂无数据")) {
                canEvalScript = false;
            }
        }
        if (canEvalScript) {
            List<DOMElement> buttonList = document.findElements(By.className("btn btn-success"));
            List<DOMElement> tdList = document.findElements(By.tagName("td"));
            List<DOMElement> passButtonList = new ArrayList<>();
            List<DOMElement> durationList = new ArrayList<>();

            List<SingleAskForLeave> singleList = new ArrayList<>();

            for (DOMElement button : buttonList) {
                if ("请假通过".equals(button.getAttribute("value"))) {
                    passButtonList.add(button);
                }
            }
            for (DOMElement td : tdList) {
                if (td.getInnerHTML().contains("成都市范围")) {
                    durationList.add(td);
                }
            }
            if (passButtonList.size() != durationList.size()) {
                DialogPopper.error("出现了兼容性异常! 请联系开发者解决");
                return;
            }
            for (int i = 0, size = durationList.size(); i < size; i++) {
                singleList.add(new SingleAskForLeave(
                        durationList.get(i).getInnerHTML(),
                        passButtonList.get(i)));
            }
            for (SingleAskForLeave singleAskForLeave : singleList) {
                if (singleAskForLeave.canAutoPass()) {
                    singleAskForLeave.pass();
                }
            }
            if (BuildConfig.DEBUG) {
                logger.debug("TODO: loop unit");
                passButtonList.forEach(
                        ele -> {
                            logger.debug("ele.getAttribute(\"value\") = " + ele.getAttribute("value"));
                        }
                );
                durationList.forEach(
                        ele -> {
                            logger.debug("ele.getInnerHTML() = " + ele.getInnerHTML());
                        }
                );
                logger.debug("buttonList.size() = " + buttonList.size());
                logger.debug("tdList.size() = " + tdList.size());
                logger.debug("passButtonList.size() = " + passButtonList.size());
                logger.debug("durationList.size() = " + durationList.size());
            }
        }
    }

    private void reload() {
        frame.getBrowser().loadURL(url);
    }

    public void reGenUrl() {
        this.url = String.format(
                "%s%s%s",
                vpn
                        ? ApprovalUrlUtil.VPN_PREFIX
                        : ApprovalUrlUtil.NON_VPN_PREFIX,
                ApprovalUrlUtil.UNITE_INFIX,
                roleMode.getSuffix()
        );
    }

    public void saveRoleModeAndReload(RoleMode roleMode) {
        this.roleMode = roleMode;
        configProxy.putAndSave("role", roleMode.name().toLowerCase(Locale.ROOT));
        reGenUrl();
        reload();
    }

    public void saveVpnAndReload(boolean vpn) {
        this.vpn = vpn;
        configProxy.putAndSave("vpn", String.valueOf(vpn));
        reGenUrl();
        reload();
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public ApprovalFrame getFrame() {
        return frame;
    }

    public long getInterval() {
        return interval;
    }

    public RoleMode getRoleMode() {
        return roleMode;
    }

    public boolean isVpn() {
        return vpn;
    }

    /**
     * ##class FuncPanelController
     */
    private static class FuncPanelController {

        private final ApprovalController context;

        private final ApprovalFrame.FuncPanel funcPanel;

        public FuncPanelController(ApprovalController context) {
            this.context = context;
            this.funcPanel = context.getFrame().getFuncPanel();
            init();
        }

        private void init() {
            initState();
            initController();
            initActionListener();
        }

        private void initState() {
            funcPanel.getIntervalField().setText(String.valueOf(context.getInterval()));
            funcPanel.getUsernameField().setText(userProxy.getSafeString("username"));
            funcPanel.getPasswordField().setText(userProxy.getSafeString("password"));
            funcPanel.getFsjModButton().setSelected(context.getRoleMode() == RoleMode.FSJ);
            funcPanel.getFdyModButton().setSelected(context.getRoleMode() == RoleMode.FDY);
            funcPanel.getVpnModeSwitch().setSelectedAndRepaint(configProxy.getBoolean("vpn"));
            funcPanel.getDarkModSwitch().setSelectedAndRepaint(configProxy.getBoolean("dark"));
        }

        private void initController() {

        }

        private void initActionListener() {
            funcPanel.getClearUserButton().addActionListener(
                    (ActionEvent e) -> {
                        funcPanel.getPasswordField().setText("");
                        funcPanel.getUsernameField().setText("");
                    }
            );

            funcPanel.getSavaUserButton().addActionListener(
                    (ActionEvent e) -> {
                        String username =
                                NullSafe.safeString(funcPanel.getUsernameField().getText());
                        String password =
                                NullSafe.safeString(funcPanel.getPasswordField().getPassword());
                        if (username.isEmpty() || password.isEmpty()) {
                            DialogPopper.warning("账号或密码为空", "请检查确认账号密码是否都填写完整!");
                        }
                        userProxy.putAndSave("username", username);
                        userProxy.putAndSave("password", password);
                        DialogPopper.info("保存成功", "账号密码保存成功!");
                    }
            );

            funcPanel.getSaveIntervalButton().addActionListener(
                    (ActionEvent e) -> {
                        String intervalString =
                                NullSafe.safeString(funcPanel.getIntervalField().getText());
                        if (!StrUtil.isNumeric(intervalString)) {
                            DialogPopper.error("数字格式错误", "请在间隔时间框输入正确的数字");
                            return;
                        }
                        configProxy.putAndSave("interval", intervalString);
                        context.setInterval(Convert.toInt(intervalString, 60));
                        DialogPopper.info("保存成功", "时间间隔保存成功, 将在重启后生效");
                    }
            );

            funcPanel.getVpnModeSwitch().addMouseListener(
                    new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            super.mousePressed(e);
                            context.saveVpnAndReload(!configProxy.getBoolean("vpn"));
                            if (BuildConfig.DEBUG) {
                                logger.debug("onClicked vpn");
                            }
                        }
                    }
            );

            funcPanel.getDarkModSwitch().addMouseListener(
                    new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            super.mousePressed(e);
                            configProxy.putAndSave("dark", String.valueOf(!configProxy.getBoolean("dark")));
                            DialogPopper.info("切换皮肤成功, 将在重启后生效");
                            if (BuildConfig.DEBUG) {
                                logger.debug("onClicked dark");
                            }
                        }
                    }
            );

            funcPanel.getFdyModButton().addChangeListener(
                    e -> {
                        context.saveRoleModeAndReload(
                                funcPanel.getFdyModButton().isSelected()
                                        ? RoleMode.FDY
                                        : RoleMode.FSJ
                        );
                    }
            );

            funcPanel.getReloadButton().addActionListener(
                    e -> context.reload()
            );
        }
    }
}
