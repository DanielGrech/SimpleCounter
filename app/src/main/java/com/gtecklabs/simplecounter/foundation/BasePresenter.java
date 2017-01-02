package com.gtecklabs.simplecounter.foundation;

import android.view.View;
import com.gtecklabs.simplecounter.ScApp;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.util.Preconditions;
import rx.Observable;
import rx.Subscription;

public abstract class BasePresenter<V extends View, D> {

  protected abstract void inject(D component);
  protected abstract D getDiComponent(DiComponent component);

  private final V mView;
  private final BaseActivityPresenter<?> mActivityPresenter;

  public BasePresenter(V view, BaseActivityPresenter<?> activityPresenter) {
    mView = Preconditions.checkNotNull(view);
    mActivityPresenter = Preconditions.checkNotNull(activityPresenter);

    D diComponent = getDiComponent(ScApp.getDi(view.getContext()));
    inject(diComponent);
  }

  protected V getView() {
    return mView;
  }

  protected <S> Subscription subscribe(Observable<S> observable, BaseSubscriber<S> subscriber) {
    return mActivityPresenter.subscribe(observable, subscriber);
  }
}
