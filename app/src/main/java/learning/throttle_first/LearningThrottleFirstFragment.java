package learning.throttle_first;

import static com.uber.autodispose.AutoDispose.autoDisposable;
import static com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider.from;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.tonytang.hello.again.rxjava.R;
import com.tonytang.hello.again.rxjava.ThreadInfo;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class LearningThrottleFirstFragment extends Fragment {

  private TextView tv_throttle_first;
  private TextView tv_throttle_last;

  public LearningThrottleFirstFragment() {
    super(R.layout.fragment_learning_throttle_first);
  }

  public static LearningThrottleFirstFragment newInstance() {
    return new LearningThrottleFirstFragment();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView(view);
    bindingStream();
  }

  private void initView(@NonNull View view) {
    tv_throttle_first = view.findViewById(R.id.tv_throttle_first);
    tv_throttle_last = view.findViewById(R.id.tv_throttle_last);
  }

  private void bindingStream() {
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
