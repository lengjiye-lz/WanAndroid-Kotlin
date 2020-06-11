package com.lengjiye.code.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import androidx.viewpager.widget.ViewPager;

import java.util.Objects;

/**
 * 可以上下滑动viewpager
 * 带回弹效果
 */
public class VerticalViewPager extends ViewPager {

    private float preY = 0f;
    private int currentPosition = 0;
    private Rect mRect = new Rect();//用来记录初始位置
    private boolean handleDefault = true;
    private static final float RATIO = 0.5f;//摩擦系数
    private static final float SCROLL_WIDTH = 10f;

    public VerticalViewPager(Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 最重要的设置，将viewpager翻转
        setPageTransformer(true, new VerticalPageTransformer());
        // 设置去掉滑到最左或最右时的滑动效果
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View view, float position) {

            if (position < -1) { // [-Infinity,-1)
                // 当前页的上一页
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                view.setAlpha(1);

                // 抵消默认幻灯片过渡
                view.setTranslationX(view.getWidth() * -position);

                //设置从上滑动到Y位置
                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);

            } else { // (1,+Infinity]
                // 当前页的下一页
                view.setAlpha(0);
            }
        }
    }

    /**
     * 交换触摸事件的X和Y坐标
     */
    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            preY = ev.getY();//记录起点
            currentPosition = getCurrentItem();
        }
        return intercepted; //为所有子视图返回触摸的原始坐标
    }

    /**
     * 处理回弹效果
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                onTouchActionUp();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Objects.requireNonNull(getAdapter()).getCount() == 1) {
                    float nowY = ev.getY();
                    float offset = nowY - preY;
                    preY = nowY;

                    if (offset > SCROLL_WIDTH) {//手指滑动的距离大于设定值
                        whetherConditionIsRight(offset);
                    } else if (offset < -SCROLL_WIDTH) {
                        whetherConditionIsRight(offset);
                    } else if (!handleDefault) {//这种情况是已经出现缓冲区域了，手指慢慢恢复的情况
                        if (getTop() + (int) (offset * RATIO) != mRect.top) {
                            layout(getLeft(), getTop() + (int) (offset * RATIO), getRight(), getBottom() + (int) (offset * RATIO));
                        }
                    }
                } else if ((currentPosition == 0 || currentPosition == getAdapter().getCount() - 1)) {
                    float nowY = ev.getY();
                    float offset = nowY - preY;
                    preY = nowY;

                    if (currentPosition == 0) {
                        if (offset > SCROLL_WIDTH) {//手指滑动的距离大于设定值
                            whetherConditionIsRight(offset);
                        } else if (!handleDefault) {//这种情况是已经出现缓冲区域了，手指慢慢恢复的情况
                            if (getTop() + (int) (offset * RATIO) >= mRect.top) {
                                layout(getLeft(), getTop() + (int) (offset * RATIO), getRight(), getBottom() + (int) (offset * RATIO));
                            }
                        }
                    } else {
                        if (offset < -SCROLL_WIDTH) {
                            whetherConditionIsRight(offset);
                        } else if (!handleDefault) {
                            if (getBottom() + (int) (offset * RATIO) <= mRect.bottom) {
                                layout(getLeft(), getTop() + (int) (offset * RATIO), getRight(), getBottom() + (int) (offset * RATIO));
                            }
                        }
                    }
                } else {
                    handleDefault = true;
                }

                if (!handleDefault) {
                    return true;
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(swapXY(ev));
    }

    private void whetherConditionIsRight(float offset) {
        if (mRect.isEmpty()) {
            mRect.set(getLeft(), getTop(), getRight(), getBottom());
        }
        handleDefault = false;
        layout(getLeft(), getTop() + (int) (offset * RATIO), getRight(), getBottom() + (int) (offset * RATIO));
    }

    private void onTouchActionUp() {
        if (!mRect.isEmpty()) {
            recoveryPosition();
        }
    }

    /**
     * 执行动画
     */
    private void recoveryPosition() {
        TranslateAnimation ta = new TranslateAnimation(getLeft(), mRect.left, 0, 0);
        ta.setDuration(300);
        startAnimation(ta);
        layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
        mRect.setEmpty();
        handleDefault = true;
    }

}
