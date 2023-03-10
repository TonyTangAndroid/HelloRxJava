package com.tonytang.hello.again.rxjava;

import static com.uber.autodispose.AutoDispose.autoDisposable;
import static com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider.from;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

  private TextView tv_throttle_first;
  private TextView tv_throttle_last;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    tv_throttle_first = findViewById(R.id.tv_throttle_first);
    tv_throttle_last = findViewById(R.id.tv_throttle_last);
    ThreadInfoStreaming.eventStreaming()
        .observeOn(AndroidSchedulers.mainThread())
        .as(autoDisposable(from(this)))
        .subscribe(this::renderUI);
  }

  private void renderUI(ThreadInfo threadInfo) {
    switch (threadInfo.source) {
      case THROTTLE_FIRST:
        tv_throttle_first.setText(threadInfo.toString());
        break;
      case THROTTLE_LAST:
        tv_throttle_last.setText(threadInfo.toString());
        break;
    }
  }


}
