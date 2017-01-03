package com.gtecklabs.simplecounter.model;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

@AutoValue
public abstract class Count {

  public abstract String title();

  public abstract @Nullable String description();

  public abstract float value();

  public static Builder builder() {
    return new AutoValue_Count.Builder();
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

    public abstract Builder title(String title);

    public abstract Builder description(String description);

    public abstract Builder value(float value);

    public abstract Count build();
  }
}
