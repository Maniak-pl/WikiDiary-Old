package pl.maniak.wikidiary.utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import rx.subjects.PublishSubject;

public class ObservableListImpl<T> implements ObservableList<T> {
    
    @Getter
    private final List<T> list;
    
    @Getter
    private final PublishSubject<List<T>> observable;

    public ObservableListImpl() {
        this.list = new ArrayList<>();
        this.observable = PublishSubject.create();
    }


    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public void add(T value) {
        list.add(value);
    }

    @Override
    public void onComplete() {
        observable.onNext(list);
    }

    @Override
    public void replaceAll(List<T> list) {
        clear();
        addAll(list);
    }

    @Override
    public void addAll(List<T> list) {
        this.list.addAll(list);
        onComplete();
    }
}
