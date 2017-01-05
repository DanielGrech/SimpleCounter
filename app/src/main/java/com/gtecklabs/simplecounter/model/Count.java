package com.gtecklabs.simplecounter.model;

import android.support.annotation.ColorInt;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

@AutoValue
public abstract class Count {

  public static final long NO_ID = -1;

  public abstract long id();

  public abstract String title();

  public abstract @Nullable String description();

  public abstract @ColorInt int color();

  public abstract float value();

  public static Builder builder() {
    return new AutoValue_Count.Builder()
        .id(NO_ID)
        .value(0f);
  }

  public Count increment() {
    return toBuilder()
        .value(value() + 1)
        .build();
  }

  public Count decrement() {
    return toBuilder()
        .value(value() - 1)
        .build();
  }

  public Builder toBuilder() {
    return new AutoValue_Count.Builder(this);
  }

  @AutoValue.Builder
  public static abstract class Builder {

    public abstract Builder id(long id);

    public abstract Builder title(String title);

    public abstract Builder description(String description);

    public abstract Builder color(@ColorInt int color);

    public abstract Builder value(float value);

    public abstract Count build();
  }
}
