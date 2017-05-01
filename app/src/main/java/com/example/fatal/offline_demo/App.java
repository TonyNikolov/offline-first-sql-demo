package com.example.fatal.offline_demo;

import android.app.Application;

import com.example.fatal.offline_demo.data.DataModule;
import com.example.fatal.offline_demo.presenters.PresenterModule;

/**
 * Created by fatal on 4/30/2017.
 */

public class App extends Application {
    private static App sInstance;

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule("http://jsonplaceholder.typicode.com/"))
                .presenterModule(new PresenterModule())
                .build();
        component.inject(this);
    }

    public static App get() {
        return sInstance;
    }

    public AppComponent component() {
        return component;
    }
}