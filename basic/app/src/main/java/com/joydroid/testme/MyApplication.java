package com.joydroid.testme;

import android.app.Application;
import timber.log.Timber;

/**
 * @author epeng on 5/2/16
 */
public class MyApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }
}
