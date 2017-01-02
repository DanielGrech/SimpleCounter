package com.gtecklabs.simplecounter.ui;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SpringFactory {

  private static final float SPRING_VALUE_START = 0f;
  private static final float SPRING_VALUE_END = 1f;

  @Inject
  SpringSystem mSpringSystem;

  @Inject
  SpringFactory() {

  }

  public Spring createDefaultSpring() {
    return mSpringSystem.createSpring()
        .setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(40, 7))
        .setCurrentValue(SPRING_VALUE_START)
        .setEndValue(SPRING_VALUE_END)
        .setAtRest();
  }

  public static void toEndValue(Spring spring) {
    spring.setEndValue(SPRING_VALUE_END);
  }
  public static void toStartValue(Spring spring) {
    spring.setEndValue(SPRING_VALUE_START);
  }

}
