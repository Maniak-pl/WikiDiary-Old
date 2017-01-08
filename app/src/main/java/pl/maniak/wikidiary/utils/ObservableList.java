package pl.maniak.wikidiary.utils;

import java.util.List;

import rx.subjects.PublishSubject;

public interface ObservableList<T> {

    List<T> getList();
    PublishSubject<List<T>> getObservable();
    void clear();
    void add(T value);
    void addAll(List<T> list);
    void onComplete();
    void replaceAll(List<T> list);
}
