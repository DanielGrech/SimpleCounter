package com.gtecklabs.simplecounter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class ColorPickerItemView extends View {

  private final Paint mPaint;

  public ColorPickerItemView(Context context, int color) {
    super(context);

    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    mPaint.setColor(color);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    final int w = canvas.getWidth();
    final int h = canvas.getHeight();

    final int rad = Math.max(w, h) / 2;
    canvas.drawCircle(w / 2, h / 2, rad, mPaint);
  }
}
