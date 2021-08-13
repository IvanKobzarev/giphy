package org.ivankobzarev.signalgiphy;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

public class AppExecutors {
  public final Executor networkIO;
  public final Executor mainThread;

  public AppExecutors(Executor networkIO, Executor mainThread) {
    this.networkIO = networkIO;
    this.mainThread = mainThread;
  }

  public static class MainThreadExecutor implements Executor {
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable command) {
      mainThreadHandler.post(command);
    }
  }
}
