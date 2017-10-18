package com.lsp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


import java.math.BigDecimal;

/**
 * Created by lsp on 2017/10/13 10 47
 * Email:6161391073@qq.com
 */

public class RulerView extends View {
    private static final String TAG = "RulerView";
    /**
     * 尺子高度
     */
    private int rulerHeight = 50;
    /**
     * 尺子和屏幕顶部以及结果之间的高度
     */
    private int rulerToResultgap = rulerHeight / 4;
    /**
     * 刻度平分多少份
     */
    private int scaleCount = 10;  //刻度评分多少份
    /**
     * 刻度间距
     */
    private int scaleGap = 20;
    /**
     * 刻度最小值
     */
    private int minScale = 0;
    /**
     * 第一次显示的刻度
     */
    private float firstScale = 50f;
    /**
     * 刻度最大值
     */
    private int maxScale = 100;

    /**
     * 背景颜色
     */
    private int bgColor = 0xfffcfffc;
    /**
     * 小刻度的颜色
     */
    private int smallScaleColor = 0xff999999;
    /**
     * 中刻度的颜色
     */
    private int midScaleColor = 0xff666666;
    /**
     * 大刻度的颜色
     */
    private int largeScaleColor = 0xff50b586;
    /**
     * 刻度颜色
     */
    private int scaleNumColor = 0xff333333;
    /**
     * 结果值颜色
     */
    private int resultNumColor = 0xff50b586;
    /**
     * kg颜色
     */
    private String unit = "kg";
    /**
     * kg颜色
     */
    private int unitColor = 0xff50b586;
    /**
     * 小刻度粗细大小
     */
    private int smallScaleStroke = 1;
    /**
     * 中刻度粗细大小
     */
    private int midScaleStroke = 2;
    /**
     * 大刻度粗细大小
     */
    private int largeScaleStroke = 3;
    /**
     * 结果字体大小
     */
    private int resultNumTextSize = 20;
    /**
     * 刻度字体大小
     */
    private int scaleNumTextSize = 16;
    /**
     * 单位字体大小
     */
    private int unitTextSize = 13;
    /**
     * 是否显示刻度结果
     */
    private boolean showScaleResult = true;
    /**
     * 是否背景显示圆角
     */
    private boolean isBgRoundRect = true;

    /**
     * 结果回调
     */
    private OnChooseResulterListener onChooseResulterListener;
    private ValueAnimator valueAnimator;
    private VelocityTracker velocityTracker = VelocityTracker.obtain();
    private String resultText = String.valueOf(firstScale);
    private Paint bgPaint;
    private Paint smallScalePaint;
    private Paint midScalePaint;
    private Paint lagScalePaint;
    private Paint scaleNumPaint;
    private Paint resultNumPaint;
    private Paint kgPaint;
    private Rect scaleNumRect;
    private Rect resultNumRect;
    private Rect kgRect;
    private RectF bgRect;
    private int height, width;
    private int smallScaleHeight;
    private int midScaleHeight;
    private int lagScaleHeight;
    private int rulerRight = 0;
    private int resultNumRight;
    private float downX;
    private float moveX = 0;
    private float currentX;
    private float lastMoveX = 0;
    private boolean isUp = false;
    private int leftScroll;
    private int rightScroll;
    private int xVelocity;

    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttr(attrs, defStyleAttr);
        init();
    }

    public void setOnChooseResulterListener(OnChooseResulterListener onChooseResulterListener) {
        this.onChooseResulterListener = onChooseResulterListener;
    }

    private void setAttr(AttributeSet attrs, int defStyleAttr) {

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.RulerView, defStyleAttr, 0);

        rulerHeight = a.getDimensionPixelSize(R.styleable.RulerView_rulerHeight, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, rulerHeight, getResources().getDisplayMetrics()));

        rulerToResultgap = a.getDimensionPixelSize(R.styleable.RulerView_rulerToResultgap, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, rulerToResultgap, getResources().getDisplayMetrics()));

        scaleCount = a.getInt(R.styleable.RulerView_scaleCount, scaleCount);

        scaleGap = a.getDimensionPixelSize(R.styleable.RulerView_scaleGap, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, scaleGap, getResources().getDisplayMetrics()));

        minScale = a.getInt(R.styleable.RulerView_minScale, minScale);

        firstScale = a.getFloat(R.styleable.RulerView_firstScale, firstScale);

        maxScale = a.getInt(R.styleable.RulerView_maxScale, maxScale);

        bgColor = a.getColor(R.styleable.RulerView_bgColor, bgColor);

        smallScaleColor = a.getColor(R.styleable.RulerView_smallScaleColor, smallScaleColor);

        midScaleColor = a.getColor(R.styleable.RulerView_midScaleColor, midScaleColor);

        largeScaleColor = a.getColor(R.styleable.RulerView_largeScaleColor, largeScaleColor);

        scaleNumColor = a.getColor(R.styleable.RulerView_scaleNumColor, scaleNumColor);

        resultNumColor = a.getColor(R.styleable.RulerView_resultNumColor, resultNumColor);

        unitColor = a.getColor(R.styleable.RulerView_unitColor, unitColor);

        String tempUnit = unit;
        unit = a.getString(R.styleable.RulerView_unit);
        if (TextUtils.isEmpty(unit)) {
            unit = tempUnit;
        }

        smallScaleStroke = a.getDimensionPixelSize(R.styleable.RulerView_smallScaleStroke, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, smallScaleStroke, getResources().getDisplayMetrics()));

        midScaleStroke = a.getDimensionPixelSize(R.styleable.RulerView_midScaleStroke, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, midScaleStroke, getResources().getDisplayMetrics()));
        largeScaleStroke = a.getDimensionPixelSize(R.styleable.RulerView_largeScaleStroke, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, largeScaleStroke, getResources().getDisplayMetrics()));
        resultNumTextSize = a.getDimensionPixelSize(R.styleable.RulerView_resultNumTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, resultNumTextSize, getResources().getDisplayMetrics()));

        scaleNumTextSize = a.getDimensionPixelSize(R.styleable.RulerView_scaleNumTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, scaleNumTextSize, getResources().getDisplayMetrics()));

        unitTextSize = a.getDimensionPixelSize(R.styleable.RulerView_unitTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, unitTextSize, getResources().getDisplayMetrics()));


        showScaleResult = a.getBoolean(R.styleable.RulerView_showScaleResult, showScaleResult);
        isBgRoundRect = a.getBoolean(R.styleable.RulerView_isBgRoundRect, isBgRoundRect);

        a.recycle();
    }


    private void init() {
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        smallScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        midScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lagScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scaleNumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        resultNumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        kgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        bgPaint.setColor(bgColor);
        smallScalePaint.setColor(smallScaleColor);
        midScalePaint.setColor(midScaleColor);
        lagScalePaint.setColor(largeScaleColor);
        scaleNumPaint.setColor(scaleNumColor);
        resultNumPaint.setColor(resultNumColor);
        kgPaint.setColor(unitColor);

        resultNumPaint.setStyle(Paint.Style.FILL);
        kgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setStyle(Paint.Style.FILL);
        smallScalePaint.setStyle(Paint.Style.FILL);
        midScalePaint.setStyle(Paint.Style.FILL);
        lagScalePaint.setStyle(Paint.Style.FILL);

        lagScalePaint.setStrokeCap(Paint.Cap.ROUND);
        midScalePaint.setStrokeCap(Paint.Cap.ROUND);
        smallScalePaint.setStrokeCap(Paint.Cap.ROUND);

        smallScalePaint.setStrokeWidth(smallScaleStroke);
        midScalePaint.setStrokeWidth(midScaleStroke);
        lagScalePaint.setStrokeWidth(largeScaleStroke);

        resultNumPaint.setTextSize(resultNumTextSize);
        kgPaint.setTextSize(unitTextSize);
        scaleNumPaint.setTextSize(scaleNumTextSize);

        bgRect = new RectF();
        resultNumRect = new Rect();
        scaleNumRect = new Rect();
        kgRect = new Rect();

        resultNumPaint.getTextBounds(resultText, 0, resultText.length(), resultNumRect);
        kgPaint.getTextBounds(resultText, 0, 1, kgRect);

        smallScaleHeight = rulerHeight / 4;
        midScaleHeight = rulerHeight / 2;
        lagScaleHeight = rulerHeight / 2 + 5;
        valueAnimator = new ValueAnimator();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightModule = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        switch (heightModule) {
            case MeasureSpec.AT_MOST:
                height = rulerHeight + (showScaleResult ? resultNumRect.height() : 0) + rulerToResultgap * 2 + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.EXACTLY:
                height = heightSize + getPaddingTop() + getPaddingBottom();
                break;
        }

        width = widthSize + getPaddingLeft() + getPaddingRight();

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBg(canvas);
        drawScaleAndNum(canvas);
        drawResultText(canvas, resultText);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = event.getX();
        isUp = false;
        velocityTracker.computeCurrentVelocity(500);
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    valueAnimator.end();
                    valueAnimator.cancel();
                }
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = currentX - downX + lastMoveX;
                if (moveX >= width / 2) {
                    moveX = width / 2;
                } else if (moveX <= getWhichScalMovex(maxScale)) {
                    moveX = getWhichScalMovex(maxScale);
                }
                break;
            case MotionEvent.ACTION_UP:
                lastMoveX = moveX;
                xVelocity = (int) velocityTracker.getXVelocity();
                autoVelocityScroll(xVelocity);
                velocityTracker.clear();
                break;
        }
        invalidate();
        return true;
    }

    private void autoVelocityScroll(int xVelocity) {
        if (Math.abs(xVelocity) < 50) {
            isUp = true;
            return;
        }
        if (valueAnimator.isRunning()) {
            return;
        }
        valueAnimator = ValueAnimator.ofInt(0, xVelocity / 20).setDuration(Math.abs(xVelocity / 10));
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                moveX += (int) animation.getAnimatedValue();
                if (moveX >= width / 2) {
                    moveX = width / 2;
                } else if (moveX <= getWhichScalMovex(maxScale)) {
                    moveX = getWhichScalMovex(maxScale);
                }
                lastMoveX = moveX;
                invalidate();
            }

        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isUp = true;
                invalidate();
            }
        });

        valueAnimator.start();
    }

    private float getWhichScalMovex(float scale) {

        return width / 2 - scaleGap * scaleCount * (scale - minScale);
    }

    private void drawScaleAndNum(Canvas canvas) {
        canvas.translate(0, (showScaleResult ? resultNumRect.height() : 0) + rulerToResultgap);

        int num1;  //确定刻度位置
        float num2;

        if (firstScale != -1) {
            moveX = getWhichScalMovex(firstScale);
            lastMoveX = moveX;
            firstScale = -1;
        }

        num1 = (int) -(moveX / scaleGap);
        num2 = (moveX % scaleGap);

        canvas.save();

        rulerRight = 0;

        if (isUp) {
            num2 = ((moveX - width / 2 % scaleGap) % scaleGap);
            if (num2 <= 0) {
                num2 = scaleGap - Math.abs(num2);
            }
            leftScroll = (int) Math.abs(num2);
            rightScroll = (int) (scaleGap - Math.abs(num2));

            float moveX2 = num2 <= scaleGap / 2 ? moveX - leftScroll : moveX + rightScroll;

            if (valueAnimator != null && !valueAnimator.isRunning()) {
                valueAnimator = ValueAnimator.ofFloat(moveX, moveX2);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        moveX = (float) animation.getAnimatedValue();
                        lastMoveX = moveX;
                        invalidate();
                    }
                });
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (onChooseResulterListener != null) {
                            onChooseResulterListener.onEndResult(resultText);
                        }
                    }
                });
                valueAnimator.setDuration(300);
                valueAnimator.start();
                isUp = false;
            }

            num1 = (int) -(moveX / scaleGap);
            num2 = (moveX % scaleGap);
        }

        canvas.translate(num2, 0);
        BigDecimal b = new BigDecimal((width / 2 - moveX) / (scaleGap * scaleCount));
        resultText = String.valueOf(b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue() + minScale);
        if (onChooseResulterListener != null) {
            onChooseResulterListener.onScrollResult(resultText);
        }
        while (rulerRight < width) {
            if (num1 % scaleCount == 0) {
                if ((moveX >= 0 && rulerRight < moveX - scaleGap) || width / 2 - rulerRight <= getWhichScalMovex(maxScale + 1) - moveX) {   //去除左右边界

                } else {
                    canvas.drawLine(0, 0, 0, midScaleHeight, midScalePaint);
                    scaleNumPaint.getTextBounds(num1 / scaleGap + minScale + "", 0, (num1 / scaleGap + minScale + "").length(), scaleNumRect);
                    canvas.drawText(num1 / scaleCount + minScale + "", -scaleNumRect.width() / 2, lagScaleHeight +
                            (rulerHeight - lagScaleHeight) / 2 + scaleNumRect.height(), scaleNumPaint);
                }

            } else {
                if ((moveX >= 0 && rulerRight < moveX) || width / 2 - rulerRight < getWhichScalMovex(maxScale) - moveX) {   //去除左右边界

                } else {
                    canvas.drawLine(0, 0, 0, smallScaleHeight, smallScalePaint);
                }
            }
            ++num1;
            rulerRight += scaleGap;
            canvas.translate(scaleGap, 0);
        }

        canvas.restore();
        canvas.drawLine(width / 2, 0, width / 2, lagScaleHeight, lagScalePaint);

    }

    private void drawResultText(Canvas canvas, String resultText) {
        if (!showScaleResult) {
            return;
        }
        canvas.translate(0, -resultNumRect.height() - rulerToResultgap / 2);
        resultNumPaint.getTextBounds(resultText, 0, resultText.length(), resultNumRect);
        canvas.drawText(resultText, width / 2 - resultNumRect.width() / 2, resultNumRect.height(),
                resultNumPaint);
        resultNumRight = width / 2 + resultNumRect.width() / 2 + 10;
        canvas.drawText(unit, resultNumRight, kgRect.height() + 2, kgPaint);
    }

    private void drawBg(Canvas canvas) {
        bgRect.set(0, 0, width, height);
        if (isBgRoundRect) {
            canvas.drawRoundRect(bgRect, 20, 20, bgPaint);
        } else {
            canvas.drawRect(bgRect, bgPaint);
        }
    }

    public interface OnChooseResulterListener {
        void onEndResult(String result);
        void onScrollResult(String result);
    }
}
