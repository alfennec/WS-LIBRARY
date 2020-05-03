package com.fennec.e_media.apiComm;

import android.content.Context;
import android.util.Log;


import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class deleteUser {

    public deleteUser(String link , String method, final Context ctx)
    {
        Ion.with(ctx)
                .load(method,link)
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        if(result != null)
                        {
                            Log.e("COMPILE EX", "onClick: SEND URL" + result);
                        }else
                        {
                            Log.e("TAG_GET", "onClick: ONFQILED");
                        }
                    }
                });
    }

}