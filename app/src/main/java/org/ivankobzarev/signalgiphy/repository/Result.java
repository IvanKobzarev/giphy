package org.ivankobzarev.signalgiphy.repository;

import androidx.annotation.Nullable;

public abstract class Result<T> {
  private Result() {}

  public abstract boolean isOk();
  public abstract @Nullable T getData();

  public static final class Success<T> extends Result<T> {
    public T data;

    public Success(T data) {
      this.data = data;
    }

    @Override
    public boolean isOk() {
      return true;
    }

    @Nullable
    @Override
    public T getData() {
      return data;
    }
  }

  public static final class Error<T> extends Result<T> {
    public Throwable throwable;

    public Error(Throwable throwable) {
      this.throwable = throwable;
    }

    @Override
    public boolean isOk() {
      return false;
    }

    @Nullable
    @Override
    public T getData() {
      return null;
    }
  }
}