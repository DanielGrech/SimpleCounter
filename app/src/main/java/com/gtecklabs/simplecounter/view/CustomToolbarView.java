package com.gtecklabs.simplecounter.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.gtecklabs.simplecounter.R;

public class CustomToolbarView extends LinearLayout {

  public interface Listener {

    void onProfileClicked(View profileButton);

    void onSettingsClicked(View settingsButton);
  }

  @BindView(R.id.title)
  TextView mTitleView;

  @Nullable private Listener mListener;

  public CustomToolbarView(Context context) {
    this(context, null);
  }

  public CustomToolbarView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomToolbarView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setOrientation(HORIZONTAL);
    setGravity(Gravity.CENTER_VERTICAL);
    LayoutInflater.from(context).inflate(R.layout.view_custom_toolbar, this);
    ButterKnife.bind(this);
  }

  public void setText(@StringRes int titleRes) {
    mTitleView.setText(titleRes);
  }

  public void setText(CharSequence title) {
    mTitleView.setText(title);
  }

  public void setListener(@Nullable Listener listener) {
    mListener = listener;
  }

  @OnClick(R.id.profile)
  void onProfileClicked() {
    if (mListener != null) {
      mListener.onProfileClicked(this);
    }
  }

  @OnClick(R.id.settings)
  void onSettingsClicked() {
    if (mListener != null) {
      mListener.onSettingsClicked(this);
    }
  }
}
