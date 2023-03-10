package learning.parallel;

import static com.uber.autodispose.AutoDispose.autoDisposable;
import static com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider.from;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.jakewharton.rxbinding3.view.RxView;
import com.tonytang.hello.again.rxjava.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.Locale;

public class LearningParallelFragment extends Fragment {


  private TextView tv_cost_time_in_sequence;
  private Button btn_resolve_in_sequence;

  private TextView tv_cost_time_parallel;
  private Button btn_resolve_parallel;

  public static LearningParallelFragment newInstance() {
    return new LearningParallelFragment();
  }

  public LearningParallelFragment() {
    super(R.layout.fragment_learning_parallel);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView(view);
    bindAction();
  }

  private void bindAction() {
    bindResolveAction();

  }

  private void bindResolveAction() {
    resolveActionStream().flatMapSingle(PersonStreaming::resolve)
        .observeOn(AndroidSchedulers.mainThread())
        .as(autoDisposable(from(getViewLifecycleOwner())))
        .subscribe(this::onActionResolved);
  }

  private void onActionResolved(ResolveResult result) {
    switch (result.resolveType) {
      case PARALLEL:
        tv_cost_time_parallel.setText(formattedDuration(result));
        enableButton(btn_resolve_parallel);
        break;
      case SEQUENCE:
        tv_cost_time_in_sequence.setText(formattedDuration(result));
        enableButton(btn_resolve_in_sequence);
        break;
    }
  }

  private static String formattedDuration(ResolveResult result) {
    return String.format(Locale.US, "duration:%s ms", result.span.duration());
  }


  private void enableButton(Button button) {
    button.setEnabled(true);
    button.setText(originalTextId(button));
  }

  private int originalTextId(Button button) {
    return button.getId() == R.id.btn_resolve_in_sequence ? R.string.resolve_in_sequence
        : R.string.resolve_parallel;
  }

  private Observable<ResolveAction> resolveActionStream() {
    return Observable.merge(
        resolve(btn_resolve_in_sequence, ResolveType.SEQUENCE),
        resolve(btn_resolve_parallel, ResolveType.PARALLEL));
  }

  private Observable<ResolveAction> resolve(Button button, ResolveType type) {
    return RxView.clicks(button).map(unit -> ResolveAction.create(type))
        .doOnNext(action -> disableButton(button));
  }

  private void disableButton(Button button) {
    button.setEnabled(false);
    button.setText(R.string.resolving);
  }

  private void initView(@NonNull View view) {
    tv_cost_time_parallel = view.findViewById(R.id.tv_cost_time_parallel);
    btn_resolve_in_sequence = view.findViewById(R.id.btn_resolve_in_sequence);

    tv_cost_time_in_sequence = view.findViewById(R.id.tv_cost_time_in_sequence);
    btn_resolve_parallel = view.findViewById(R.id.btn_resolve_parallel);
  }
}