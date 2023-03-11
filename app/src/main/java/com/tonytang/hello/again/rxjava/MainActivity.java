package com.tonytang.hello.again.rxjava;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import learning.parallel.LearningParallelFragment;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.container, LearningParallelFragment.newInstance())
        .commitNow();
  }
}
