package com.gtecklabs.simplecounter.foundation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.gtecklabs.simplecounter.Navigator;
import com.gtecklabs.simplecounter.ScApp;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.util.Preconditions;
import com.gtecklabs.simplecounter.util.RxUtils;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import rx.Observable;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

import java.util.LinkedList;
import java.util.List;

import static com.trello.rxlifecycle.android.ActivityEvent.*;

public abstract class BaseActivityPresenter<T extends Activity> {

  private T mActivity;
  private Navigator mNavigator;
  private final BehaviorSubject<ActivityEvent> mLifecycleObservable;

  private final List<Subscription> mSubscriptions = new LinkedList<>();

  protected abstract void inject(DiComponent component);

  public BaseActivityPresenter(T activity) {
    mActivity = Preconditions.checkNotNull(activity);
    mLifecycleObservable = BehaviorSubject.create();

    mNavigator = new Navigator(mActivity);
  }

  public void onCreate(@Nullable Bundle bundle) {
    final DiComponent diComponent = ScApp.getDi(mActivity);
    inject(diComponent);

    mLifecycleObservable.onNext(CREATE);
  }

  public void onSaveInstanceState(Bundle outState) {
    // No-op. Up to subclasses to implement
  }

  public void onRestoreInstanceState(Bundle state) {
    // No-op. Up to subclasses to implement
  }

  public void onStart() {
    mLifecycleObservable.onNext(START);
  }

  public void onResume() {
    mLifecycleObservable.onNext(RESUME);
  }

  public void onPause() {
    mLifecycleObservable.onNext(PAUSE);
  }

  public void onDestroy() {
    mLifecycleObservable.onNext(DESTROY);
    for (Subscription subscription : mSubscriptions) {
      if (!subscription.isUnsubscribed()) {
        subscription.unsubscribe();
      }
    }

    mActivity = null;
    mNavigator = null;
  }

  public void onStop() {
    mLifecycleObservable.onNext(STOP);
  }

  public boolean onBackPressed() {
    return false;
  }

  protected T getActivity() {
    return mActivity;
  }

  protected Navigator getNavigator() {
    return mNavigator;
  }

  protected <S> Subscription subscribe(Observable<S> observable, BaseSubscriber<S> subscriber) {
    final Subscription subscription =
        observable.compose(RxLifecycleAndroid.<S>bindActivity(mLifecycleObservable))
            .compose(RxUtils.<S>executeOnIoThread())
            .subscribe(subscriber);
    mSubscriptions.add(subscriber);
    return subscription;
  }
}
