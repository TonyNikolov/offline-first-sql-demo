package com.example.fatal.offline_demo;

import com.example.fatal.offline_demo.data.remote.AppRemoteRepository;
import com.example.fatal.offline_demo.presenters.PresenterModule;
import com.example.fatal.offline_demo.views.DetailsActivity;
import com.example.fatal.offline_demo.views.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by fatal on 4/30/2017.
 */

@Singleton
@Component(modules = {AppModule.class,PresenterModule.class})
public interface AppComponent {
    void inject(App app);

    void inject(AppRemoteRepository appRemoteRepository);

    void inject(MainActivity activity);

    void inject(DetailsActivity activity);
}
