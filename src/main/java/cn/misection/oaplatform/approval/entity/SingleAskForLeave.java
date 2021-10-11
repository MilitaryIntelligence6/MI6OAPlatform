package cn.misection.oaplatform.approval.entity;

import cn.hutool.core.util.StrUtil;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName SingleSome
 * @Description TODO
 * @CreateTime 2021年10月11日 19:03:00
 */
public class SingleAskForLeave {

    private String type;

    private DOMElement passButton;

    public SingleAskForLeave(String type, DOMElement passButton) {
        this.type = type;
        this.passButton = passButton;
    }

    public boolean canAutoPass() {
        return StrUtil.isNotBlank(type)
                && !(type.contains("十天以上"));// || type.contains("成都市范围外"));
    }

    public void pass() {
        passButton.click();
    }

    public String getType() {
        return type;
    }

    public DOMElement getPassButton() {
        return passButton;
    }
}
