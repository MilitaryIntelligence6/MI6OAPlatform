package cn.misection.oaplatform.util.uiutil;

import javax.swing.*;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName MsgDialogProxy
 * @Description TODO
 * @CreateTime 2021年06月14日 21:07:00
 */
public final class DialogPopper {

    private DialogPopper() {
        throw new UnsupportedOperationException(String.format("here are no %s instance for you", getClass().getName()));
    }

    public static void unknownError() {
        JOptionPane.showMessageDialog(
                null,
                "未知异常, 请联系开发者debug",
                "Unknown ERROR",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void error(String msg) {
        JOptionPane.showMessageDialog(
                null,
                msg,
                "ERROR",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void question(String msg) {
        JOptionPane.showMessageDialog(
                null,
                msg,
                "Question",
                JOptionPane.QUESTION_MESSAGE
        );
    }

    public static void warning(String msg) {
        JOptionPane.showMessageDialog(
                null,
                msg,
                "Warning",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static int confirm(String msg) {
        return JOptionPane.showConfirmDialog(
                null,
                msg,
                "确认",
                JOptionPane.YES_NO_OPTION
        );
    }

    public static void error(String title, String msg) {
        JOptionPane.showMessageDialog(
                null,
                msg,
                title,
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void question(String title, String msg) {
        JOptionPane.showMessageDialog(
                null,
                msg,
                title,
                JOptionPane.QUESTION_MESSAGE
        );
    }

    public static void warning(String title, String msg) {
        JOptionPane.showMessageDialog(
                null,
                msg,
                title,
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static int confirm(String title, String msg) {
        return JOptionPane.showConfirmDialog(
                null,
                msg,
                title,
                JOptionPane.YES_NO_OPTION
        );
    }
}
