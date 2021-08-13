package org.ivankobzarev.signalgiphy.repository;

public interface RepositoryCallback<T> {
  void onComplete(Result<T> result);
}
