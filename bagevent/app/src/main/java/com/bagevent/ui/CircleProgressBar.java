package com.bagevent.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.bagevent.R;
import com.bagevent.utils.DxPxUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/26
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class CircleProgressBar extends ProgressBar {
    private static final int LINE = 0;
    private static final int SOLID = 1;
    private static final int SOLID_LINE = 2;

    private static final int LINEAR = 0;
    private static final int RADIAL = 1;
    private static final int SWEEP = 2;

    private static final float DEFAULT_START_DEGREE = -90.0f;

    private static final int DEFAULT_LINE_COUNT = 45;

    private static final float DEFAULT_LINE_WIDTH = 4.0f;
    private static final float DEFAULT_PROGRESS_TEXT_SIZE = 11.0f;
    private static final float DEFAULT_PROGRESS_STROKE_WIDTH = 1.0f;

    private static final String COLOR_FFF2A670 = "#fff2a670";
    private static final String COLOR_FFD3D3D5 = "#ffe3e3e5";
    private static final String DEFAULT_PATTERN = "%d%%";
    private static final String DEFAULT_PATTERNSS = "";

    private final RectF mProgressRectF = new RectF();
    private final Rect mProgressTextRect = new Rect();

    private final Paint mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mProgressBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Paint mProgressTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float mRadius;
    private float mCenterX;
    private float mCenterY;

    private int progress;
    private int max;

    //Background Color of the progress bar
    private int mBackgroundColor;

    //Only work well in the Line Style, represents the line count of the rings included
    private int mLineCount;
    //Only work well in the Line Style, Height of the line of the progress bar
    private float mLineWidth;

    //Stroke width of the progress of the progress bar
    private float mProgressStrokeWidth;
    //Text size of the progress of the progress bar
    private float mProgressTextSize;

    //Start color of the progress of the progress bar
    private int mProgressStartColor;
    //End color of the progress of the progress bar
    private int mProgressEndColor;
    //Color of the progress value of the progress bar
    private int mProgressTextColor;
    //Background color of the progress of the progress bar
    private int mProgressBackgroundColor;

    //If mDrawProgressText is true, will draw the progress text. otherwise, will not draw the progress text.
    private boolean mDrawProgressText;
    //Format the current progress value to the specified format
    private String mProgressTextFormatPattern;
    private String mProgressTExtMark;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LINE, SOLID, SOLID_LINE})
    private @interface Style {
    }

    //The style of the progress color
    @Style
    private int mStyle;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LINEAR, RADIAL, SWEEP})
    private @interface ShaderMode {
    }

    //The Shader of mProgressPaint
    @ShaderMode
    private int mShader;
    //The Stroke Cap of mProgressPaint and mProgressBackgroundPaint
    private Paint.Cap mCap;

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Basic data initialization
     */
    @SuppressWarnings("ResourceType")
    private void initFromAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        mBackgroundColor = a.getColor(R.styleable.CircleProgressBar_background_color, Color.TRANSPARENT);

        mDrawProgressText = a.getBoolean(R.styleable.CircleProgressBar_draw_progress_text, true);

        mLineCount = a.getInt(R.styleable.CircleProgressBar_line_count, DEFAULT_LINE_COUNT);
        mProgressTextFormatPattern = a.hasValue(R.styleable.CircleProgressBar_progress_text_format_pattern) ?
                a.getString(R.styleable.CircleProgressBar_progress_text_format_pattern) : DEFAULT_PATTERN;

        mProgressTExtMark = a.hasValue(R.styleable.CircleProgressBar_progress_text_format_mark) ?
                a.getString(R.styleable.CircleProgressBar_progress_text_format_mark) : DEFAULT_PATTERNSS;

        mStyle = a.getInt(R.styleable.CircleProgressBar_style, LINE);
        mShader = a.getInt(R.styleable.CircleProgressBar_progress_shader, LINEAR);
        mCap = a.hasValue(R.styleable.CircleProgressBar_progress_stroke_cap) ?
                Paint.Cap.values()[a.getInt(R.styleable.CircleProgressBar_progress_stroke_cap, 0)] : Paint.Cap.BUTT;

        mLineWidth = a.getDimensionPixelSize(R.styleable.CircleProgressBar_line_width, DxPxUtils.dip2px(DEFAULT_LINE_WIDTH));
        mProgressTextSize = a.getDimensionPixelSize(R.styleable.CircleProgressBar_progress_text_size, DxPxUtils.dip2px(DEFAULT_PROGRESS_TEXT_SIZE));
        mProgressStrokeWidth = a.getDimensionPixelSize(R.styleable.CircleProgressBar_progress_stroke_width, DxPxUtils.dip2px(DEFAULT_PROGRESS_STROKE_WIDTH));

        mProgressStartColor = a.getColor(R.styleable.CircleProgressBar_progress_start_color, Color.parseColor(COLOR_FFF2A670));
        mProgressEndColor = a.getColor(R.styleable.CircleProgressBar_progress_end_color, Color.parseColor(COLOR_FFF2A670));
        mProgressTextColor = a.getColor(R.styleable.CircleProgressBar_progress_text_color, Color.parseColor(COLOR_FFF2A670));
        mProgressBackgroundColor = a.getColor(R.styleable.CircleProgressBar_progress_background_color, Color.parseColor(COLOR_FFD3D3D5));

        a.recycle();
    }

    /**
     * Paint initialization
     */
    private void initPaint() {
        mProgressTextPaint.setTextAlign(Paint.Align.CENTER);
        mProgressTextPaint.setTextSize(mProgressTextSize);

        mProgressPaint.setStyle(mStyle == SOLID ? Paint.Style.FILL : Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mProgressStrokeWidth);
        mProgressPaint.setColor(mProgressStartColor);
        mProgressPaint.setStrokeCap(mCap);

        mProgressBackgroundPaint.setStyle(mStyle == SOLID ? Paint.Style.FILL : Paint.Style.STROKE);
        mProgressBackgroundPaint.setStrokeWidth(mProgressStrokeWidth);
        mProgressBackgroundPaint.setColor(mProgressBackgroundColor);
        mProgressBackgroundPaint.setStrokeCap(mCap);

        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(mBackgroundColor);
    }

    /**
     * The progress bar color gradient,
     * need to be invoked in the {@link #onSizeChanged(int, int, int, int)}
     */
    private void updateProgressShader() {
        if (mProgressStartColor != mProgressEndColor) {
            Shader shader = null;
            switch (mShader) {
                case LINEAR:
                    shader = new LinearGradient(mProgressRectF.left, mProgressRectF.top,
                            mProgressRectF.left, mProgressRectF.bottom,
                            mProgressStartColor, mProgressEndColor, Shader.TileMode.CLAMP);
                    break;
                case RADIAL:
                    shader = new RadialGradient(mCenterX, mCenterY, mRadius,
                            mProgressStartColor, mProgressEndColor, Shader.TileMode.CLAMP);
                    break;
                case SWEEP:
                    //arc = radian * radius
                    float radian = (float) (mProgressStrokeWidth / Math.PI * 2.0f / mRadius);
                    float rotateDegrees = (float) (DEFAULT_START_DEGREE
                            - (mCap == Paint.Cap.BUTT && mStyle == SOLID_LINE ? 0 : Math.toDegrees(radian)));

                    shader = new SweepGradient(mCenterX, mCenterY, new int[]{mProgressStartColor, mProgressEndColor},
                            new float[]{0.0f, 1.0f});
                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotateDegrees, mCenterX, mCenterY);
                    shader.setLocalMatrix(matrix);
                    break;
            }

            mProgressPaint.setShader(shader);
        } else {
            mProgressPaint.setShader(null);
            mProgressPaint.setColor(mProgressStartColor);
        }
    }

    /**
     * In order to work well, need to modify some of the following fields through reflection.
     * Another available way: write the following attributes to the xml
     * <p>
     * android:indeterminateOnly="false"
     * android:indeterminate="false"
     */
    private void adjustIndeterminate() {
        try {
            Field mOnlyIndeterminateField = ProgressBar.class.getDeclaredField("mOnlyIndeterminate");
            mOnlyIndeterminateField.setAccessible(true);
            mOnlyIndeterminateField.set(this, false);

            Field mIndeterminateField = ProgressBar.class.getDeclaredField("mIndeterminate");
            mIndeterminateField.setAccessible(true);
            mIndeterminateField.set(this, false);

            Field mCurrentDrawableField = ProgressBar.class.getDeclaredField("mCurrentDrawable");
            mCurrentDrawableField.setAccessible(true);
            mCurrentDrawableField.set(this, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawProgress(canvas);
        drawProgressText(canvas);
    }

    private void drawBackground(Canvas canvas) {
        if (mBackgroundColor != Color.TRANSPARENT) {
            canvas.drawCircle(mCenterX, mCenterX, mRadius, mBackgroundPaint);
        }
    }

    private void setCurrentProgress(int progress) {
        this.progress = progress;
    }

    private void setCurrentMax(int max) {
        this.max = max;
    }

    private void drawProgressText(Canvas canvas) {
        if (!mDrawProgressText) {
            return;
        }

        String progressText = String.format(mProgressTextFormatPattern, getProgress());
        // String markText = String.format(mProgressTExtMark,getProgress());
        Point point = new Point((int) mCenterX, (int) mCenterY);
        String[] strings = {progressText};
        textCenter(strings, canvas, point, Paint.Align.CENTER);
    }

    private void textCenter(String[] chars, Canvas canvas, Point point, Paint.Align align) {
        mProgressTextPaint.setTextAlign(align);
        Paint.FontMetrics fontMetrics = mProgressTextPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int length = chars.length;
        float total = (length - 1) * (-top + bottom) + (-fontMetrics.ascent + fontMetrics.descent);
        float offset = total / 2 - bottom;
        for (int i = 0; i < length; i++) {
            if (i == 1) {
                mProgressTextPaint.setTextSize(mProgressTextSize);
                mProgressTextPaint.setColor(mProgressTextColor);
            } else {
                mProgressTextPaint.setTextSize(mProgressTextSize);
                mProgressTextPaint.setColor(mProgressTextColor);
            }
            float yAxis = -(length - i - 1) * (-top + bottom) + offset;
            canvas.drawText(chars[i] + "", point.x, point.y + yAxis, mProgressTextPaint);
        }
    }

    private void drawProgress(Canvas canvas) {
        switch (mStyle) {
            case SOLID:
                drawSolidProgress(canvas);
                break;
            case SOLID_LINE:
                drawSolidLineProgress(canvas);
                break;
            case LINE:
            default:
                drawLineProgress(canvas);
                break;
        }
    }

    /**
     * In the center of the drawing area as a reference point , rotate the canvas
     */
    private void drawLineProgress(Canvas canvas) {
        float unitDegrees = (float) (2.0f * Math.PI / mLineCount);
        float outerCircleRadius = mRadius;
        float interCircleRadius = mRadius - mLineWidth;

        int progressLineCount = (int) ((float) getProgress() / (float) getMax() * mLineCount);

        for (int i = 0; i < mLineCount; i++) {
            float rotateDegrees = i * unitDegrees;

            float startX = mCenterX + (float) Math.sin(rotateDegrees) * interCircleRadius;
            float startY = mCenterX - (float) Math.cos(rotateDegrees) * interCircleRadius;

            float stopX = mCenterX + (float) Math.sin(rotateDegrees) * outerCircleRadius;
            float stopY = mCenterX - (float) Math.cos(rotateDegrees) * outerCircleRadius;

            if (i < progressLineCount) {
                canvas.drawLine(startX, startY, stopX, stopY, mProgressPaint);
            } else {
                canvas.drawLine(startX, startY, stopX, stopY, mProgressBackgroundPaint);
            }
        }
    }

    /**
     * Just draw arc
     */
    private void drawSolidProgress(Canvas canvas) {
        canvas.drawArc(mProgressRectF, DEFAULT_START_DEGREE, 360.0f, false, mProgressBackgroundPaint);
        canvas.drawArc(mProgressRectF, DEFAULT_START_DEGREE, 360.0f * getProgress() / getMax(), true, mProgressPaint);
    }

    /**
     * Just draw arc
     */
    private void drawSolidLineProgress(Canvas canvas) {

        canvas.drawArc(mProgressRectF, DEFAULT_START_DEGREE, 360.0f, false, mProgressBackgroundPaint);
        canvas.drawArc(mProgressRectF, DEFAULT_START_DEGREE, 360.0f * getProgress() / getMax(), false, mProgressPaint);
    }

    /**
     * When the size of CircleProgressBar changed, need to re-adjust the drawing area
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;

        mRadius = Math.min(mCenterX, mCenterY);
        mProgressRectF.top = mCenterY - mRadius;
        mProgressRectF.bottom = mCenterY + mRadius;
        mProgressRectF.left = mCenterX - mRadius;
        mProgressRectF.right = mCenterX + mRadius;

        updateProgressShader();

        //Prevent the progress from clipping
        mProgressRectF.inset(mProgressStrokeWidth / 2, mProgressStrokeWidth / 2);
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.mBackgroundColor = backgroundColor;
        mBackgroundPaint.setColor(backgroundColor);
        invalidate();
    }

    public void setProgressTextFormatPattern(String progressTextformatPattern) {
        this.mProgressTextFormatPattern = progressTextformatPattern;
        invalidate();
    }

    public String getProgressTextFormatPattern() {
        return mProgressTextFormatPattern;
    }

    public void setProgressTextFormatMark(String textFormatMark) {
        this.mProgressTExtMark = textFormatMark;
        invalidate();
    }

    public String getProgressTextFormatMark() {
        return mProgressTExtMark;
    }

    public void setProgressStrokeWidth(float progressStrokeWidth) {
        this.mProgressStrokeWidth = progressStrokeWidth;
        mProgressRectF.inset(mProgressStrokeWidth / 2, mProgressStrokeWidth / 2);
        invalidate();
    }

    public float getProgressStrokeWidth() {
        return mProgressStrokeWidth;
    }

    public void setProgressTextSize(float progressTextSize) {
        this.mProgressTextSize = progressTextSize;
        invalidate();
    }

    public float getProgressTextSize() {
        return mProgressTextSize;
    }

    public void setProgressStartColor(int progressStartColor) {
        this.mProgressStartColor = progressStartColor;
        updateProgressShader();
        invalidate();
    }

    public int getProgressStartColor() {
        return mProgressStartColor;
    }

    public void setProgressEndColor(int progressEndColor) {
        this.mProgressEndColor = progressEndColor;
        updateProgressShader();
        invalidate();
    }

    public int getProgressEndColor() {
        return mProgressEndColor;
    }

    public void setProgressTextColor(int progressTextColor) {
        this.mProgressTextColor = progressTextColor;
        invalidate();
    }

    public int getProgressTextColor() {
        return mProgressTextColor;
    }

    public void setProgressBackgroundColor(int progressBackgroundColor) {
        this.mProgressBackgroundColor = progressBackgroundColor;
        mProgressBackgroundPaint.setColor(mProgressBackgroundColor);
        invalidate();
    }

    public int getProgressBackgroundColor() {
        return mProgressBackgroundColor;
    }

    public int getLineCount() {
        return mLineCount;
    }

    public void setLineCount(int lineCount) {
        this.mLineCount = lineCount;
        invalidate();
    }

    public float getLineWidth() {
        return mLineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.mLineWidth = lineWidth;
        invalidate();
    }

    public int getStyle() {
        return mStyle;
    }

    public void setStyle(@Style int style) {
        this.mStyle = style;
        mProgressPaint.setStyle(mStyle == SOLID ? Paint.Style.FILL : Paint.Style.STROKE);
        mProgressBackgroundPaint.setStyle(mStyle == SOLID ? Paint.Style.FILL : Paint.Style.STROKE);
        invalidate();
    }

    public int getShader() {
        return mShader;
    }

    public void setShader(@ShaderMode int shader) {
        mShader = shader;
        updateProgressShader();
        invalidate();
    }

    public Paint.Cap getCap() {
        return mCap;
    }

    public void setCap(Paint.Cap cap) {
        mCap = cap;
        mProgressPaint.setStrokeCap(cap);
        mProgressBackgroundPaint.setStrokeCap(cap);
        invalidate();
    }
}
