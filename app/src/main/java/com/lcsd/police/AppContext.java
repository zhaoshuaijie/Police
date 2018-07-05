package com.lcsd.police;

import android.app.Application;
import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.tencent.smtt.sdk.QbSdk;
import com.tsy.sdk.myokhttp.MyOkHttp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/1/3.
 */
public class AppContext extends Application {
    private static AppContext appContext = null;
    private static Context context;
    private static MyOkHttp mMyOkHttp;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        context = this.getApplicationContext();
        QbSdk.initX5Environment(context, null);
    }

    public static AppContext getInstance() {
        if (appContext == null) {
            return new AppContext();
        }
        return appContext;
    }

    public static MyOkHttp getmMyOkHttp() {
        if (mMyOkHttp == null) {
            //持久化存储cookie
            ClearableCookieJar cookieJar =
                    new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
            //自定义OkHttp
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                    .readTimeout(10000L, TimeUnit.MILLISECONDS)
                    .writeTimeout(10000L, TimeUnit.MILLISECONDS)
                    .cookieJar(cookieJar)       //设置开启cookie
                    .build();
            mMyOkHttp = new MyOkHttp(okHttpClient);
        }
        return mMyOkHttp;
    }

}
