package com.program.masterUI;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class TabbedPaneDef extends BasicTabbedPaneUI{
	protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        //自定义选项卡的高：比默认的高度 高20
        return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) + 20;
    }

    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
        //自定义选项卡的宽：比默认的宽度 宽30
        return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 30;
    }

	/**
	 * 自定义选项卡的背景色
	 *
	 * g 图形设置
	 * tabPlacement 标签位置
	 * tabIndex 标签下标
	 * x x轴
	 * y y轴
	 * w 宽
	 * h 高
	 * isSelected 是否被选中
	 */
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                                      int x, int y, int w, int h, boolean isSelected) {
        Color defaultColor = new Color(132,123,143);	//默认的颜色
        Color selectedColor = new Color(50, 123, 143);	//选中选项卡时的颜色
        //设置选中时和未被选中时的颜色
        g.setColor(!isSelected ? defaultColor : selectedColor);
        //填充图形，即选项卡为矩形
        g.fillRect(x + 5, y + 5, w, h);
    }

	/**
	 * 绘制标签的边框
	 * g 图形
	 * tabPlacement 标签位置
	 * tabIndex 标签下标
	 * x x轴
	 * y y轴
	 * w 宽
	 * h 高
	 * isSelected 标签是否被选中
	 */
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                                  int x, int y, int w, int h, boolean isSelected) {//自定义选项卡的边框色
        //注意：这个方法中的具体实现内容，可以写，也可以不写，根据自己实际情况决定。但这个方法定义必须有。否则会影响最终的显示效果
        Color defaultColor = new Color(154, 140, 124);
        Color selectedColor = new Color(30, 199, 61);
        //设置选中时和未被选中时的颜色
        g.setColor(!isSelected ? defaultColor : selectedColor);
        //绘制边框，即选项卡边框为矩形
        g.drawRect(x + 5, y + 5, w, h);
    }
    protected void paintFocusIndicator(Graphics g,
                                       int tabPlacement, Rectangle[] rects,
                                       int tabIndex, Rectangle iconRect, Rectangle textRect,
                                       boolean isSelected) {//这个方法定义如果没有的话，选项卡在选中时，内测会有虚线。
    }

    protected LayoutManager createLayoutManager() {// 设置Layout
        return new TabbedPaneLayout();
    }

    public class TabbedPaneLayout extends BasicTabbedPaneUI.TabbedPaneLayout {
        // 要想实现：1.选中选项卡时，选项卡突出显示 2.选项卡之间有间距。那么必须重写以下方法！！
        protected void calculateTabRects(int tabPlacement, int tabCount) {
            super.calculateTabRects(tabPlacement, tabCount);
            // 设置间距
            setRec(5);
        }

        public void setRec(int rec) {
            for (int i = 0; i < rects.length; i++) {
                rects[i].x = rects[i].x + rec * i;
            }
        }
    }
}
