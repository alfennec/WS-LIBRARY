package com.fennec.e_media.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.fennec.e_media.R;
import com.koushikdutta.ion.Ion;

import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SplashActivity extends AppCompatActivity {


    public static int time_splash = 3000;

    public static SplashActivity main;

    //public JsonGetSetting jsonGetSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        main=this;

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                SplashActivity.this.finish();
            }
        }, time_splash);


        Ion.getDefault(main).getHttpClient().getSSLSocketMiddleware().setTrustManagers(new TrustManager[] {new X509TrustManager() {

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }});

        String url_informations = "json/getSetting.php";

        //url_informations = constant.url_host+url_informations;

        Log.d("TAG_DEPLOY", " app : "+ url_informations);

        //jsonGetSetting = new JsonGetSetting(url_informations, main);



    }

    public static void onSucces()
    {

    }
}