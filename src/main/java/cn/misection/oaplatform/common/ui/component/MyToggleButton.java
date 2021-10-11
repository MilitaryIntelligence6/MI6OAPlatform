package cn.misection.oaplatform.common.ui.component;

import cn.misection.oaplatform.util.uiutil.SkinManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

/**
 * @author Administrator
 */
public class MyToggleButton extends JPanel {
    /**
     * 绘制参数设定主背景色;
     */
    public Color bgColor = new Color(0xFFFFFF);

    /**
     * 描边;
     */
    public Color lineColor = new Color(0xFFFFFF);

    /**
     * 灰色填充;
     */
    public Color darkColor = new Color(0xE1E1E1);

    /**
     * 高亮填充;
     */
    public Color lightColor = new Color(0x33B4FF);
    /**
     * 轮廓线与内部圆的距离
     */
    public int padding = 2;

    /**
     * 开关, ON/OFF;
     */
    private boolean selected = SkinManager.isBeenDark();

    /**
     * 监听属性;
     */
    private StateListener stateListener;// = source -> toggle();

    public MyToggleButton() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
//                if (stateListener != null) {
//                    stateListener.stateChanged(this);
//                }
                // FIXME: 2021/10/8 对比;
                toggle();
            }
        });
    }

    /**
     * 添加鼠标事件;
     */
    public MyToggleButton(MouseAdapter mouseAdapter) {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (stateListener != null) {
                    stateListener.stateChanged(this);
                }
                // FIXME: 2021/10/8 对比;
//                toggle();
            }
        });
        this.addMouseListener(mouseAdapter);
    }

    /**
     * 绘制;
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);

        int width = this.getWidth();
        int height = this.getHeight();
        Graphics2D g2d = (Graphics2D) g;

        //平滑绘制（反锯齿）
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 算出一个2:1的最大矩形
        // Rectangle r = new Rectangle(0,0, 100, 50);
        int w = width;
        int h = width / 2;
        if (h > height) {
            h = height;
            w = height * 2;
        }
        Rectangle r = new Rectangle((width - w) / 2, (height - h) / 2, w, h);

        //里面两个并排圆， 外层轮廓为曲线
        //左半区
        Rectangle r1 = new Rectangle(r.x, r.y, r.width / 2, r.height);
        //右半区
        Rectangle r2 = new Rectangle(r.x + r.width / 2, r.y, r.width / 2, r.height);

        //绘制外部轮廓线
        Shape arc1 = new Arc2D.Double(r1, 90, 180, Arc2D.OPEN);
        Shape arc2 = new Arc2D.Double(r2, 270, 180, Arc2D.OPEN);
        //外轮廓，使用拼装路径;
        Path2D outline = new Path2D.Double();
        outline.append(arc1.getPathIterator(null), false);
        // 右半圆弧;
        outline.append(arc2.getPathIterator(null), true);
        outline.closePath();
        g2d.setPaint(lineColor);
        g2d.draw(outline);
        g2d.setPaint(bgColor);
        g2d.fill(outline);

        //选择绘制的按钮
        if (selected) {
            drawCircleInside(g2d, r2, padding, lineColor, lightColor);
        } else {
            drawCircleInside(g2d, r1, padding, lineColor, darkColor);
        }

    }


    /**
     * 获取数据;
     * @return
     */
    public boolean isSelected() {
        return this.selected;
    }

    /**
     * 设置数据;
     * @param selected
     */
    public void setSelectedAndRepaint(boolean selected) {
        this.selected = selected;
        repaint();
    }

    /**
     * 切换开关;
     */
    public void toggle() {
        this.selected = !this.selected;
        repaint();
    }

    /**
     * 画内部的小圆;
     * @param g2d
     * @param rect
     * @param deflate
     * @param lineColor
     * @param fillColor
     */
    private void drawCircleInside(Graphics2D g2d,
                                  Rectangle rect,
                                  int deflate,
                                  Paint lineColor,
                                  Paint fillColor) {
        // 做一个备份，不会修改传入的rect;
        Rectangle r = new Rectangle(rect);
        r.x += deflate;
        r.y += deflate;
        r.width -= (deflate * 2);
        r.height -= (deflate * 2);

        Shape shape = new Ellipse2D.Double(r.x, r.y, r.width, r.height);

        // 描边
        g2d.setPaint(lineColor);
        g2d.draw(shape);
        // 填充
        g2d.setPaint(fillColor);
        g2d.fill(shape);
    }

    /**
     * 自定义接口 内部类+接口;
     */
    public interface StateListener {
        void stateChanged(Object source);
    }

    public void setStateListener(StateListener listener) {
        this.stateListener = listener;
    }
}
