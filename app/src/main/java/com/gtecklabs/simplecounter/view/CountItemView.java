package com.gtecklabs.simplecounter.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.gtecklabs.simplecounter.R;
import com.gtecklabs.simplecounter.model.Count;

import java.util.Locale;

public class CountItemView extends CardView {

  public interface Listener {

    void onIncrementClicked(CountItemView view);

    void onDecrementClicked(CountItemView view);
  }

  @BindView(R.id.rounded_background)
  CountListItemRoundedBackgroundView mRoundedBackgroundView;

  @BindView(R.id.count_value)
  TextView mCountValue;

  @BindView(R.id.name)
  TextView mName;

  @BindView(R.id.description)
  TextView mDescription;

  private @Nullable Listener mListener;

  public static CountItemView inflate(ViewGroup parent) {
    return (CountItemView) LayoutInflater.from(parent.getContext())
        .inflate(R.layout.li_count, parent, false);
  }

  public CountItemView(Context context) {
    this(context, null);
  }

  public CountItemView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CountItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

  public void setListener(@Nullable Listener listener) {
    mListener = listener;
  }

  @OnClick(R.id.increment)
  void onIncrementClicked() {
    if (mListener != null) {
      mListener.onIncrementClicked(this);
    }
  }

  @OnClick(R.id.decrement)
  void onDecrementClicked() {
    mListener.onDecrementClicked(this);
  }

  public void bind(Count count) {
    final float value = count.value();
    if (isWholeNumber(value)) {
      mCountValue.setText(String.format(Locale.getDefault(), "%.0f", value));
    } else {
      mCountValue.setText(String.format(Locale.getDefault(), "%.2f", value));
    }

    mRoundedBackgroundView.setColor(count.color());
    mName.setText(count.title());
    if (TextUtils.isEmpty(count.description())) {
      mDescription.setVisibility(GONE);
    } else {
      mDescription.setVisibility(VISIBLE);
      mDescription.setText(count.description());
    }
  }

  static boolean isWholeNumber(float value) {
    return value % 1f == 0;
  }
}
