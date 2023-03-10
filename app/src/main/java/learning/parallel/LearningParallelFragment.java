package learning.parallel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.tonytang.hello.again.rxjava.R;

public class LearningParallelFragment extends Fragment {


  public static LearningParallelFragment newInstance() {
    return new LearningParallelFragment();
  }

  public LearningParallelFragment() {
    super(R.layout.fragment_learning_parallel);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_learning_parallel, container, false);
  }

}