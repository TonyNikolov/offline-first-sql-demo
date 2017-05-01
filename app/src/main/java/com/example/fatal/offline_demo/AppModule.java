package com.example.fatal.offline_demo;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fatal on 4/30/2017.
 */
@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application mApplication){
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Application providesApplication(){
        return mApplication;
    }

}
