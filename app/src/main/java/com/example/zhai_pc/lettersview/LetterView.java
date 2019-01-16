package com.example.zhai_pc.lettersview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @Created by xww.
 * @Creation time 2018/8/18.
 * @Email 767412271@qq.com
 * @Blog https://blog.csdn.net/smile_running
 */

public class LetterView extends View {
    //控件实际的宽高
    private int mRealWidth;
    private int mRealHeight;
    private int mHeight;
    //每一个字母所占高度
    private int mEachHeight;
    //点击区域的下标
    private int mTouchIndex = 0;
    //画笔
    private Paint mPaint;
    private Rect mRect;
    private onShowLetterListener onShowLetterListener = null;

    private int colorBg;
    private int colorNormal;
    private int colorChecked;
    private int colorCheckedBg;
    private int bgRadius;
    private int changedW;
    private String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    public LetterView(Context context) {
        super(context);
        init();
    }

    public LetterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public LetterView(Context context, @Nullable AttributeSet attrs, int mRealWidth) {
        super(context, attrs);
        this.mRealWidth = mRealWidth;
        init();
    }

    public interface onShowLetterListener {
        void showLatter(int letter);
    }

    public void setOnShowLetter(onShowLetterListener showLetterListener) {
        this.onShowLetterListener = showLetterListener;
    }

    private void init() {
        colorBg = getContext().getResources().getColor(R.color.color_ffffff);//背景颜色
        colorCheckedBg = getContext().getResources().getColor(R.color.color_e24745);//选中（圆圈）颜色
        colorNormal = getContext().getResources().getColor(R.color.color_333333);//未选中字体颜色
        colorChecked = getContext().getResources().getColor(R.color.color_ffffff);//选中字体颜色

        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStrokeWidth(3f);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(ScreenUtils.sp2px(getContext(), 12));
        //选中圆圈半径
        bgRadius = ScreenUtils.dp2px(getContext(), 8);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mRealWidth = widthSize;
                break;
        }
        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                mRealHeight = ScreenUtils.dp2px(getContext(), 16) * letters.length;
                break;
            case MeasureSpec.EXACTLY:
                mRealHeight = heightSize;
                break;
        }
        setMeasuredDimension(mRealWidth, mRealHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(colorBg);
        //每一个存放字母的宽，也就是画布的宽
        // 画布高度
        mHeight = getHeight();
        //每一个存放字母的高
        mEachHeight = mHeight / letters.length;

        //绘制26个字母
        for (int i = 0; i < letters.length; i++) {
            final String latter = letters[i];
            //获得每一个字母所占的宽、高
            mPaint.getTextBounds(latter, 0, 1, mRect);
            final int letterWidth = mRect.width();
            final int letterHeight = mRect.height();

            //绘制点击高亮的字母
            if (mTouchIndex == i) {
                mPaint.setColor(colorCheckedBg);
                canvas.drawCircle(changedW / 2, (i + 1) * mEachHeight - letterHeight / 2 - bgRadius / 2, bgRadius, mPaint);
                mPaint.setColor(colorChecked);
                canvas.drawText(latter, changedW / 2 - letterWidth / 2, (i + 1) * mEachHeight - letterHeight / 2, mPaint);
            } else {
                mPaint.setColor(colorBg);
                canvas.drawCircle(changedW / 2, (i + 1) * mEachHeight - letterHeight / 2 - bgRadius / 2, bgRadius, mPaint);
                mPaint.setColor(colorNormal);
                canvas.drawText(latter, changedW / 2 - letterWidth / 2, (i + 1) * mEachHeight - letterHeight / 2, mPaint);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        changedW = w;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                refreshLetterIndex(y);
                break;
            case MotionEvent.ACTION_MOVE:
                refreshLetterIndex(y);
                break;
            case MotionEvent.ACTION_UP:
                actionUp();
                break;
        }
        return true;
    }

    /**
     * 手抬起回调
     */
    private void actionUp() {
        onShowLetterListener.showLatter(-1);
    }


    /**
     * 刷新被选中的字母的下标，得到下标交给onDraw()
     */
    private void refreshLetterIndex(int y) {
        //y坐标 / 每个字母高度 = 当前字母下标
        int index = y / mEachHeight;
        if (index != mTouchIndex) {
            mTouchIndex = index;
            //回调选中的字母
            if (onShowLetterListener != null) {
                if (mTouchIndex >= 0 && mTouchIndex < letters.length)
                    onShowLetterListener.showLatter(mTouchIndex);
            }

            invalidate();
        }
    }

    /**
     * 根据显示的第一条文字定位选中的索引
     */
    public void setFirstVisibleItemIndex(int firstItemIndex) {
        mTouchIndex = firstItemIndex;
        invalidate();
    }
}
