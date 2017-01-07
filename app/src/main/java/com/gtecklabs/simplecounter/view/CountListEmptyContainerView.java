package com.gtecklabs.simplecounter.view;

import android.content.Context;
import android.graphics.*;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.gtecklabs.simplecounter.R;
import com.gtecklabs.simplecounter.ScApp;
import com.gtecklabs.simplecounter.ui.SpringFactory;
import com.gtecklabs.simplecounter.util.ViewUtils;

import java.util.LinkedList;
import java.util.List;

public class CountListEmptyContainerView extends FrameLayout {

  private static final float ARC_DEGREES = 90;
  private static final float ARC_RADIANS = (float) Math.toRadians(ARC_DEGREES);

  @BindView(R.id.message)
  TextView mMessage;

  private float mArcAnimValue = 0f;
  private float mArrowAnimValue = 0f;

  private Point mSourcePoint;
  private Point mTargetPoint;
  private float mArcStartAngle;

  private final Paint mArrowPaint;
  private final RectF mArcRect;
  private final int mArrowHeadLength;

  boolean mHasAnimated;

  private final List<Spring> mSprings = new LinkedList<>();

  public CountListEmptyContainerView(Context context) {
    this(context, null);
  }

  public CountListEmptyContainerView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CountListEmptyContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setWillNotDraw(false);
    LayoutInflater.from(getContext()).inflate(R.layout.count_list_empty_view, this);
    ButterKnife.bind(this);

    mArrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    mArrowPaint.setColor(ContextCompat.getColor(getContext(), R.color.accent));
    mArrowPaint.setStrokeWidth(10);
    mArrowPaint.setStyle(Paint.Style.STROKE);
    mArrowPaint.setStrokeCap(Paint.Cap.ROUND);

    mArcRect = new RectF();

    mArrowHeadLength = getResources().getDimensionPixelSize(R.dimen.count_list_empty_view_arrow_head_length);
  }

  public void animateArrow() {
    if (mHasAnimated) {
      return;
    }

    // TODO: Hax - pass in view correctly..
    final View targetView = getRootView().findViewById(R.id.fab);
    if (targetView == null) {
      return;
    }

    mHasAnimated = true;

    computeArcValues(targetView);

    final SpringFactory springFactory = ScApp.getDi(getContext()).springFactory();
    Spring spring = springFactory.createDefaultSpring()
        .setCurrentValue(0f)
        .setAtRest()
        .addListener(new SimpleSpringListener() {

          private Spring mArrowHeadSpring;

          @Override
          public void onSpringUpdate(Spring spring) {
            mArcAnimValue = (float) spring.getCurrentValue();
            invalidate();

            if (mArrowHeadSpring == null && Float.compare(0.8f, mArcAnimValue) > 0) {
              mArrowHeadSpring = springFactory.createDefaultSpring()
                  .setCurrentValue(0f)
                  .setAtRest()
                  .addListener(new SimpleSpringListener() {
                    @Override
                    public void onSpringUpdate(Spring spring) {
                      mArrowAnimValue = (float) spring.getCurrentValue();
                      invalidate();
                    }
                  })
                  .setEndValue(1f);
              mSprings.add(mArrowHeadSpring);
            }
          }
        })
        .setEndValue(1f);
    mSprings.add(spring);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    final View targetView = getRootView().findViewById(R.id.fab);
    if (targetView == null) {
      return;
    }
    computeArcValues(targetView);
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    if (mSourcePoint != null && mTargetPoint != null) {
      // Kudos: https://www.tbray.org/ongoing/When/200x/2009/01/02/Android-Draw-a-Curved-Line

      canvas.save();
      {
        canvas.clipRect(0, 0, canvas.getWidth(), canvas.getHeight() * mArcAnimValue);
        drawArc(canvas);
        drawArrowHead(canvas);
      }
      canvas.restore();
    }

    super.dispatchDraw(canvas);
  }

  @Override
  protected void onDetachedFromWindow() {
    for (Spring spring : mSprings) {
      spring.removeAllListeners();
    }
    super.onDetachedFromWindow();
  }

  private void drawArc(Canvas canvas) {
    canvas.drawArc(mArcRect, mArcStartAngle, ARC_DEGREES, false, mArrowPaint);
  }

  private void drawArrowHead(Canvas canvas) {
    double oppLength = mArrowAnimValue * Math.sin(Math.toRadians(60)) * mArrowHeadLength;
    double adjLength = mArrowAnimValue * Math.sqrt((mArrowHeadLength * mArrowHeadLength) - (oppLength * oppLength));

    canvas.rotate(mArcStartAngle, mTargetPoint.x, mTargetPoint.y);
    canvas.drawLine(
        mTargetPoint.x,
        mTargetPoint.y,
        (float) (mTargetPoint.x - adjLength),
        (float) (mTargetPoint.y + oppLength),
        mArrowPaint
    );

    canvas.drawLine(
        mTargetPoint.x,
        mTargetPoint.y,
        (float) (mTargetPoint.x + adjLength),
        (float) (mTargetPoint.y + oppLength),
        mArrowPaint
    );
  }

  private void computeArcValues(View targetView) {
    final int arrowPadding = getResources().getDimensionPixelSize(R.dimen.padding_default);

    final Rect myRect = ViewUtils.getPositionOnScreen(this);
    final Rect sourceRect = ViewUtils.getPositionOnScreen(mMessage);
    final Rect targetRect = ViewUtils.getPositionOnScreen(targetView);

    mSourcePoint = new Point(
        (int) (sourceRect.left - myRect.left + (0.1f * sourceRect.width())),
        sourceRect.bottom - myRect.top + arrowPadding
    );
    mTargetPoint = new Point(
        targetRect.left - myRect.left - arrowPadding,
        targetRect.top + (targetRect.height() / 2) - myRect.top
    );

    double dx = mSourcePoint.x - mTargetPoint.x;
    double dy = mSourcePoint.y - mTargetPoint.y;
    double l = Math.sqrt((dx * dx) + (dy * dy));
    double l1 = l / 2.0;
    // h is length of the line from the middle of the connecting line to the
    //  center of the circle.
    double h = l1 / (Math.tan(ARC_RADIANS / 2.0));

    // r is the radius of the circle
    float r = (float) (l1 / (Math.sin(ARC_RADIANS / 2.0)));

    // a2 is the angle at which L intersects the x axis
    double a2 = Math.atan2(dy, dx);

    // a3 is the angle at which H intersects the x axis
    double a3 = (Math.PI / 2.0) - a2;

    // m is the midpoint of the line from e1 to e2
    double mX = (mTargetPoint.x + mSourcePoint.x) / 2.0;
    double mY = (mTargetPoint.y + mSourcePoint.y) / 2.0;

    // c is the the center of the circle
    float cY = (float) (mY + (h * Math.sin(a3)));
    float cX = (float) (mX - (h * Math.cos(a3)));

    // rect is the square RectF that bounds the "oval"
    mArcRect.set(cX - r, cY - r, cX + r, cY + r);

    // rawStartRads is the starting sweep angle
    double rawStartRads = Math.atan2(mTargetPoint.y - cY, mTargetPoint.x - cX);
    mArcStartAngle = (float) Math.toDegrees(rawStartRads);
  }
}
