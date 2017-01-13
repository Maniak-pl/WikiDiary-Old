package pl.maniak.wikidiary.ui;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.utils.di.AppComponent;

public abstract class BaseActivity extends AppCompatActivity implements BaseContract.Router, BaseContract.View {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        initDaggerComponent();
        init();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initDaggerComponent();

    protected abstract void init();

    @Override
    protected void onDestroy() {
        clear();
        super.onDestroy();
    }

    protected abstract void clear();

    protected AppComponent getAppComponent() {
        try {
            return ((App) getApplication()).getAppComponent();
        } catch (ClassCastException e) {
            throw new ClassCastException("Application has to extends " + App.class.getSimpleName());
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /*
   _____ _                     _____
  / ____| |                   |  __ \
 | (___ | |__   _____      __ | |__) | __ ___   __ _ _ __ ___  ___ ___
  \___ \| '_ \ / _ \ \ /\ / / |  ___/ '__/ _ \ / _` | '__/ _ \/ __/ __|
  ____) | | | | (_) \ V  V /  | |   | | | (_) | (_| | | |  __/\__ \__ \
 |_____/|_| |_|\___/ \_/\_/   |_|   |_|  \___/ \__, |_|  \___||___/___/
                                                __/ |
                                               |___/
     */

    public void showProgress() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mProgressDialog.setContentView(R.layout.progres_bar);
    }

    public void stopProgress() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
        mProgressDialog = null;

    }
}
