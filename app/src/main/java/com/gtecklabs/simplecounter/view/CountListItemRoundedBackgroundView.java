package com.gtecklabs.simplecounter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class CountListItemRoundedBackgroundView extends FrameLayout {

  private final Paint mPaint;
  private final Path mPath;

  public CountListItemRoundedBackgroundView(Context context) {
    this(context, null);
  }

  public CountListItemRoundedBackgroundView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CountListItemRoundedBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setWillNotDraw(false);

    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    mPath = new Path();
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    final int radius = w / 5;

    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
      mPath.reset();
      mPath.moveTo(w, 0);
      mPath.lineTo(w, h);
      mPath.lineTo(radius, h);
      mPath.cubicTo(
          radius, h,
          0, h / 2,
          radius, 0);
      mPath.lineTo(w, 0);
    } else {
      mPath.reset();
      mPath.moveTo(0, 0);
      mPath.lineTo(0, h);
      mPath.lineTo(w - radius, h);
      mPath.cubicTo(
          w - radius, h,
          w, h / 2,
          w - radius, 0);
      mPath.lineTo(0, 0);
    }
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    canvas.drawPath(mPath, mPaint);
    super.dispatchDraw(canvas);
  }

  public void setColor(@ColorInt int color) {
    if (mPaint.getColor() != color) {
      mPaint.setColor(color);
      invalidate();
    }
  }
}
