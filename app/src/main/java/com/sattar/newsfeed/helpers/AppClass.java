package com.sattar.newsfeed.helpers;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created By Sattar 2/18/2019
 */
public class AppClass extends android.app.Application {


    @Override
    public void onCreate() {
        super.onCreate();
        // The default Realm file is "default.realm" in Context.getFilesDir();
        // we'll change it to "myrealm.realm"
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("nd.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}
