package pl.maniak.wikidiary.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setFragmentCallback(context);
    }

    protected abstract void setFragmentCallback(Context context);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDaggerComponent();
    }

    protected void initDaggerComponent() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewId(), container, false);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    protected abstract void init();

    @LayoutRes
    protected abstract int getContentViewId();

    @Override
    public void onDestroyView() {
        clear();
        super.onDestroyView();
    }

    protected void clear() {

    }

    public interface FragmentCallback {
        void onFragmentStarted();
        void onFragmentStopped();
    }
}
