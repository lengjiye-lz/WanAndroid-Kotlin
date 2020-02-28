package com.lengjiye.code.widgets.glide;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;

public class RoundedCornersTransformation extends BitmapTransformation {

    private static final int VERSION = 1;
    //这个ID值 随意
    private static final String ID = "com.lengjiye.code.widgets.glide.RoundedCornersTransformation." + VERSION;

    public static final int CORNER_NONE = 0;
    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 1 << 1;
    public static final int CORNER_BOTTOM_LEFT = 1 << 2;
    public static final int CORNER_BOTTOM_RIGHT = 1 << 3;
    public static final int CORNER_ALL = CORNER_TOP_LEFT | CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;
    public static final int CORNER_TOP = CORNER_TOP_LEFT | CORNER_TOP_RIGHT;
    public static final int CORNER_BOTTOM = CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;
    public static final int CORNER_LEFT = CORNER_TOP_LEFT | CORNER_BOTTOM_LEFT;
    public static final int CORNER_RIGHT = CORNER_TOP_RIGHT | CORNER_BOTTOM_RIGHT;

    public static final int FIT_CENTER = 1;
    public static final int CENTER_CROP = 2;
    public static final int CENTER_INSIDE = 3;


    @IntDef({FIT_CENTER, CENTER_CROP, CENTER_INSIDE})
    public @interface ScaleType {
    }

    private float radius;
    private float diameter;
    private int cornerType;
    private Paint paint;

    // 是否显示边框
    private boolean isShowStroke;
    // 边框颜色和宽度
    private int strokeWidth;
    private int strokeColor;

    private @ScaleType
    int scaleType;

    public RoundedCornersTransformation(int dpRadius) {
        this(dpRadius, CORNER_ALL, FIT_CENTER);
    }

    public RoundedCornersTransformation(int dpRadius, int cornerType, @ScaleType int scaleType) {
        this(dpRadius, cornerType, scaleType, false, 0, 0);
    }

    public RoundedCornersTransformation(int dpRadius, int cornerType, @ScaleType int scaleType, boolean isShowStroke, int strokeWidth, int strokeColor) {
        this.radius = Resources.getSystem().getDisplayMetrics().density * dpRadius;
        this.diameter = this.radius * 2;
        this.cornerType = cornerType;
        this.scaleType = scaleType;
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
        this.isShowStroke = isShowStroke;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap;
        switch (scaleType) {
            case CENTER_CROP:
                bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
                break;
            case CENTER_INSIDE:
                bitmap = TransformationUtils.centerInside(pool, toTransform, outWidth, outHeight);
                break;
            case FIT_CENTER:
            default:
                bitmap = TransformationUtils.fitCenter(pool, toTransform, outWidth, outHeight);
                break;
        }
        return roundCrop(pool, bitmap);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

        result.setHasAlpha(true);

        Canvas canvas = new Canvas(result);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        paint = new Paint();
        paint.setAntiAlias(true);

        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        drawRoundRect(canvas, paint, source.getWidth(), source.getHeight());

        if (isShowStroke) {
            drawRoundRect(canvas, source.getWidth(), source.getHeight());
        }

        canvas.setBitmap(null);
        return result;
    }

    /**
     * 绘制圆角
     *
     * @param canvas
     * @param paint
     * @param width
     * @param height
     */
    private void drawRoundRect(Canvas canvas, Paint paint, float width, float height) {
        if (cornerType == CORNER_NONE) {
            canvas.drawRect(new RectF(0, 0, width, height), paint);
        } else {
            canvas.drawRoundRect(new RectF(0, 0, width, height), radius, radius, paint);

            //把不需要的圆角去掉
            int notRoundedCorners = cornerType ^ CORNER_ALL;
            if ((notRoundedCorners & CORNER_TOP_LEFT) != 0) {
                clipTopLeft(canvas, paint, radius);
            }
            if ((notRoundedCorners & CORNER_TOP_RIGHT) != 0) {
                clipTopRight(canvas, paint, radius, width);
            }
            if ((notRoundedCorners & CORNER_BOTTOM_LEFT) != 0) {
                clipBottomLeft(canvas, paint, radius, height);
            }
            if ((notRoundedCorners & CORNER_BOTTOM_RIGHT) != 0) {
                clipBottomRight(canvas, paint, radius, width, height);
            }
        }
    }

    /**
     * 绘制圆角边框
     * <p>
     * 目前只能显示全圆角
     *
     * @param canvas
     * @param width
     * @param height
     */
    private void drawRoundRect(Canvas canvas, float width, float height) {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(strokeColor);
        paint.setStrokeWidth(strokeWidth);
        Path mPath = new Path();
        // 设置起点
        mPath.moveTo(strokeWidth / 2, height - radius - strokeWidth / 2);
        mPath.lineTo(strokeWidth / 2, radius + strokeWidth / 2);
        mPath.quadTo(strokeWidth / 2, strokeWidth / 2, radius + strokeWidth / 2, strokeWidth / 2);
        mPath.lineTo(width - radius - strokeWidth / 2, strokeWidth / 2);
        mPath.quadTo(width - strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2, radius + strokeWidth / 2);
        mPath.lineTo(width - strokeWidth / 2, height - radius - strokeWidth / 2);
        mPath.quadTo(width - strokeWidth / 2, height - strokeWidth / 2, width - radius - strokeWidth / 2, height - strokeWidth / 2);
        mPath.lineTo(radius + strokeWidth / 2, height - strokeWidth / 2);
        mPath.quadTo(strokeWidth / 2, height - strokeWidth / 2, strokeWidth / 2, height - radius - strokeWidth / 2);

        canvas.drawPath(mPath, paint);
    }

    private void clipTopLeft(final Canvas canvas, final Paint paint, float offset) {
        final RectF block = new RectF(0, 0, offset, offset);
        canvas.drawRect(block, paint);
    }

    private void clipTopRight(final Canvas canvas, final Paint paint, float offset, float width) {
        final RectF block = new RectF(width - offset, 0, width, offset);
        canvas.drawRect(block, paint);
    }

    private void clipBottomLeft(final Canvas canvas, final Paint paint, float offset, float height) {
        final RectF block = new RectF(0, height - offset, offset, height);
        canvas.drawRect(block, paint);
    }

    private void clipBottomRight(final Canvas canvas, final Paint paint, float offset, float width, float height) {
        final RectF block = new RectF(width - offset, height - offset, width, height);
        canvas.drawRect(block, paint);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof RoundedCornersTransformation &&
                ((RoundedCornersTransformation) o).radius == radius &&
                ((RoundedCornersTransformation) o).diameter == diameter &&
                ((RoundedCornersTransformation) o).cornerType == cornerType;
    }


    @Override
    public int hashCode() {
        return (int) (ID.hashCode() + radius * 10000 + diameter * 1000 + cornerType * 10);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID + radius + diameter + cornerType).getBytes(CHARSET));
    }
}
