package com.tonytang.hello.again.rxjava;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import learning.throttle_first.LearningThrottleFirstFragment;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getSupportFragmentManager().beginTransaction().replace(R.id.container,
            LearningThrottleFirstFragment.newInstance())
        .commitNow();
  }


}
