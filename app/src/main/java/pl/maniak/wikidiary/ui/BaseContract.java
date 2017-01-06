package pl.maniak.wikidiary.ui;

public interface BaseContract {

    interface View {

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
