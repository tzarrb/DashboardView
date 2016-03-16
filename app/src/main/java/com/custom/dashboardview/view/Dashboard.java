package com.custom.dashboardview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.custom.dashboardview.R;
import com.custom.dashboardview.utils.Util;

/**
 * Created by dev on 15/12/24.
 */
public class Dashboard extends View{
    private Context mContext;

    private final Paint mPaint;

    private int mWidth;
    private int mHeight;
    private int mInnerWidth;
    private int mInnerRadius;
    private int mOuterWidth;
    private int mOuterRadius;
    private int mPointWidth;
    private int mPointRadius;
    private int mInnerOuterOffset;

    private int mOuterColor;
    private int mInnerColor;

    private int mStartAngle;
    private int mSweepAngle;

    private int mDashCount;

    private float mPercent;
    private float mStartPercent;
    private float mEndPercent;
    private float mAngle;

    private float mCenterX = 0.0f;
    private float mCenterY = 0.0f;

    private RectF mInnerOval = new RectF();
    private RectF mOuterOval = new RectF();
    private RectF mPointOval = new RectF();

    private Shader shader;
    private SweepGradient mSweepGradient;
    private int[] mInnerCircleColors;

    public Dashboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        mOuterWidth = Util.dip2px(context, 6);
        mInnerWidth = Util.dip2px(context, 2);
        mPointWidth = Util.dip2px(context, 1);
        mPointRadius = Util.dip2px(context, 4);
        mInnerOuterOffset = Util.dip2px(context,20);

        mStartAngle = 270;
        mSweepAngle = 360;

        mDashCount = 60;

        mOuterColor = Color.WHITE;
        mInnerColor = Color.WHITE;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Dashboard, 0, 0);
        mInnerWidth = a.getDimensionPixelSize(R.styleable.Dashboard_innerWidth, mInnerWidth);
        mInnerRadius = a.getDimensionPixelSize(R.styleable.Dashboard_innerRadius, mInnerRadius);
        mOuterWidth = a.getDimensionPixelSize(R.styleable.Dashboard_outerWidth, mOuterWidth);
        mOuterRadius = a.getDimensionPixelSize(R.styleable.Dashboard_outerRadius, mOuterRadius);
        mPointRadius = a.getDimensionPixelSize(R.styleable.Dashboard_pointRadius, mPointRadius);
        mStartAngle = a.getInteger(R.styleable.Dashboard_startAngle, mStartAngle);
        mSweepAngle = a.getInteger(R.styleable.Dashboard_sweepAngle, mSweepAngle);
        mPercent = a.getFloat(R.styleable.Dashboard_percent, 0.0f);

        mDashCount = a.getInteger(R.styleable.Dashboard_dashCount, mDashCount);
        a.recycle();

        this.mPaint = new Paint();
        this.shader = this.mPaint.getShader();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mInnerCircleColors = new int[]{0xa0ffffff, 0xc0ffffff, 0xe0ffffff, 0xffffffff, 0x00ffffff, 0x20ffffff, 0x40ffffff, 0x60ffffff, 0x80ffffff};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOutDashedCircle(canvas);
        drawOuterCircle(canvas);
        drawInnerCircle(canvas);
    }

    private void drawOutDashedCircle(Canvas canvas)
    {
        float percent = 0f;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xffffff);
        for (int i = 0 ; i < mDashCount; i++)
        {
            if (i % 2 ==0){
                paint.setAlpha(255);
            }else{
                paint.setAlpha(120);
            }
            percent = i/(float)mDashCount;
            float angle = (float) (Math.PI * (2 * percent - 1));
            float pointCenterX = (float) (mCenterX + Math.sin(angle) * (mOuterRadius));
            float pointCenterY = (float) (mCenterY + Math.cos(angle) * (mOuterRadius));

            canvas.drawCircle(pointCenterX, pointCenterY, (mOuterWidth-2)/2, paint);
        }
    }


    private void drawOuterCircle(Canvas canvas)
    {
        //this.mPaint.setShader(shader);
        this.mPaint.setColor(mOuterColor);
        this.mPaint.setStrokeWidth(mOuterWidth);
        canvas.drawArc(mOuterOval, mStartAngle, mSweepAngle * mPercent, false, this.mPaint);

        //尾部小圆点
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mOuterColor);
        float pointCenterX = (float) (mCenterX - Math.sin(mAngle) * mOuterRadius);
        float pointCenterY = (float) (mCenterY + Math.cos(mAngle) * mOuterRadius);
        canvas.drawCircle(pointCenterX, pointCenterY, mOuterWidth / 2, paint);
    }

    private void drawInnerCircle(Canvas canvas){
        this.mPaint.setColor(mInnerColor);
        this.mPaint.setStrokeWidth(mInnerWidth);
        //this.mPaint.setShader(mSweepGradient);
        this.mPaint.setAlpha((int)(255 * mPercent));
        canvas.drawArc(mInnerOval, mStartAngle, mSweepAngle * mPercent, false, this.mPaint);

        //尾部小圆心
        //this.mPaint.setStrokeWidth(mPointWidth);
        //this.mPaint.setAlpha(255);
        //canvas.drawArc(mPointOval, 0, 360, false, this.mPaint);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mInnerColor);
        paint.setAlpha((int)(255 * mPercent));
        float pointCenterX = (float) (mCenterX - Math.sin(mAngle) * mInnerRadius);
        float pointCenterY = (float) (mCenterY + Math.cos(mAngle) * mInnerRadius);
        canvas.drawCircle(pointCenterX, pointCenterY, mPointRadius / 2, paint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2.0f;
        mCenterY = h / 2.0f;
        mWidth = Math.min(w, h);
        mHeight = mWidth;
        mOuterRadius = (mWidth-mOuterWidth)/2;
        mInnerRadius = mOuterRadius - mInnerOuterOffset;
        mInnerOval.left = mCenterX - mInnerRadius;
        mInnerOval.right = mCenterX + mInnerRadius;
        mInnerOval.top = mCenterY - mInnerRadius;
        mInnerOval.bottom = mCenterY + mInnerRadius;
        mOuterOval.left = mCenterX - mOuterRadius;
        mOuterOval.right = mCenterX + mOuterRadius;
        mOuterOval.top = mCenterY - mOuterRadius;
        mOuterOval.bottom = mCenterY + mOuterRadius;
        float angle = (float) (Math.PI * (2 * mPercent - 1));
        float pointCenterX = (float) (mCenterX + Math.sin(angle) * mInnerRadius);
        float pointCenterY = (float) (mCenterY + Math.cos(angle) * mInnerRadius);
        mPointOval.left = pointCenterX - mPointRadius;
        mPointOval.right = pointCenterX + mPointRadius;
        mPointOval.top = pointCenterY - mPointRadius;
        mPointOval.bottom = pointCenterY + mPointRadius;
        mSweepGradient = new SweepGradient(mCenterX, mCenterY, mInnerCircleColors, null);
    }

    public void setPercent(float percent) {
        if (mEndPercent == percent)
            return;

        if (Math.abs(percent - mEndPercent) <= 0.03) {
            mStartPercent = percent;
            mEndPercent = percent;
            mPercent = percent;

            mAngle = (float) (Math.PI * (2 * mPercent - 1));
            float pointCenterX = (float) (mCenterX - Math.sin(mAngle) * mInnerRadius);
            float pointCenterY = (float) (mCenterY + Math.cos(mAngle) * mInnerRadius);
            //pointCenterX = pointCenterX - (float)(Math.sin(angle) * mPointRadius);
            //pointCenterY = pointCenterY + (float)(Math.cos(angle) * mPointRadius);
            mPointOval.left = pointCenterX - mPointRadius;
            mPointOval.right = pointCenterX + mPointRadius;
            mPointOval.top = pointCenterY - mPointRadius;
            mPointOval.bottom = pointCenterY + mPointRadius;

            invalidate();
            return;
        }

        mStartPercent = this.mPercent;
        mEndPercent = percent;
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1f);
        animator.setDuration((int) (Math.abs(mEndPercent - mStartPercent) * 1000));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                //value = Util.curve(value);
                mPercent = mStartPercent + (mEndPercent - mStartPercent) * value;

                mAngle = (float) (Math.PI * (2 * mPercent - 1));
                float pointCenterX = (float) (mCenterX - Math.sin(mAngle) * mInnerRadius);
                float pointCenterY = (float) (mCenterY + Math.cos(mAngle) * mInnerRadius);
                //pointCenterX = pointCenterX - (float)(Math.sin(angle) * mPointRadius);
                //pointCenterY = pointCenterY + (float)(Math.cos(angle) * mPointRadius);
                mPointOval.left = pointCenterX - mPointRadius;
                mPointOval.right = pointCenterX + mPointRadius;
                mPointOval.top = pointCenterY - mPointRadius;
                mPointOval.bottom = pointCenterY + mPointRadius;

                invalidate();

            }
        });

        animator.start();
    }
}
