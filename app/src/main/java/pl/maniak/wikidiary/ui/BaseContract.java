package pl.maniak.wikidiary.ui;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;

import pl.maniak.wikidiary.R;

public interface BaseContract {

    interface View {
        void showToast(String message);
        void showProgress();
        void stopProgress();
    }

    interface Router {

    }

    interface Presenter<V, R> {
        void attachView(V view);
        void attachRouter(R router);
        void detachRouter();
        void detachView();
    }
}
