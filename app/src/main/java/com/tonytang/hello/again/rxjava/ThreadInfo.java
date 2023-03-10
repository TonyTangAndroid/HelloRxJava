package com.tonytang.hello.again.rxjava;

import androidx.annotation.NonNull;
import java.util.Locale;

public class ThreadInfo {

  public final long iteration;
  public final Source source;
  public final String threadName;

  public ThreadInfo(long iteration, Source source, String threadName) {
    this.iteration = iteration;
    this.source = source;
    this.threadName = threadName;
  }

  @NonNull
  @Override
  public String toString() {
    return String.format(Locale.US, "%s:%s:%s", iteration, source, threadName);
  }
}
