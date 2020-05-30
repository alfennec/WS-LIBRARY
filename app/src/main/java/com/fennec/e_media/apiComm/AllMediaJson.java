package com.fennec.e_media.apiComm;

import android.content.Context;
import android.util.Log;

import com.fennec.e_media.controller.EmpruntActivity;
import com.fennec.e_media.controller.MediaActivity;
import com.fennec.e_media.controller.MembreActivity;
import com.fennec.e_media.entity.media;
import com.fennec.e_media.entity.user;
import com.fennec.e_media.myInterface.IonHandler;
import com.fennec.e_media.repository.mediaRepository;
import com.fennec.e_media.repository.userRepository;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AllMediaJson implements IonHandler {


    public static int acty;


    public AllMediaJson(String link , final Context ctx , int acty)
    {
        this.acty = acty;

        Ion.with(ctx)
                .load("get",link)
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        if(result != null)
                        {
                            Log.e("TAG_USER", "onClick: SEND URL" + result);
                            ParseData(result);
                        }else
                        {
                            Log.e("TAG_GET", "onClick: ONFQILED");
                            onFailed(-1);
                        }
                    }
                });
    }



    @Override
    public void onSucces(Object obj)
    {
        if(this.acty == 1)
        {
            MediaActivity.OnSucces();
        }else if(this.acty == 3)
        {
            MediaActivity.OnMediaSucces();
        }
        else
        {
            EmpruntActivity.MediaOnSucces();
        }
    }

    @Override
    public void onFailed(Object obj)
    {

    }

    public void ParseData(String result)
    {
        try
        {
            int sizeResult = result.length();
            result = result.substring(8,result.length()-1);
            Log.e("TAG_DATA", "ParseData: "+result);

            JSONArray jArray = new JSONArray(result);

            for (int i=0; i < jArray.length(); i++)
            {
                media json_media = new media();

                try
                {
                    JSONObject oneObject = jArray.getJSONObject(i);

                    json_media.id        = Integer.parseInt(oneObject.getString("id"));
                    json_media.titre       = oneObject.getString("titre");
                    json_media.des    = oneObject.getString("des");
                }
                catch (JSONException e)
                {
                    Log.e("tag_json", ""+e);
                }

                mediaRepository.list_media.add(json_media);

                Log.e("tag_json_media", "marche bien");
            }

            onSucces(result);
        }
        catch (Exception e)
        {

        }
    }
}
