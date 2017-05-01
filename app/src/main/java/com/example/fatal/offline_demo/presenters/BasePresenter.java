package com.example.fatal.offline_demo.presenters;

/**
 * Created by fatal on 4/30/2017.
 */
public interface BasePresenter<T> {
    void subscribe();

    void unsubscribe();

    void setView(T view);
}