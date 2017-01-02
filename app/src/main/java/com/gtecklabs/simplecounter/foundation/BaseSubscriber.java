package com.gtecklabs.simplecounter.foundation;

import rx.Subscriber;
import timber.log.Timber;

public class BaseSubscriber<S> extends Subscriber<S> {

  @Override
  public void onCompleted() {

  }

  @Override
  public void onError(Throwable e) {
    Timber.e(e, "Error executing observable");
  }

  @Override
  public void onNext(S s) {

  }
}