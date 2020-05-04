package com.fennec.e_media.apiComm;

import android.content.Context;
import android.util.Log;

import com.fennec.e_media.controller.MainActivity;
import com.fennec.e_media.entity.user;
import com.fennec.e_media.myInterface.IonHandler;
import com.fennec.e_media.repository.userRepository;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class userJson implements IonHandler {


    public userJson(String link , final Context ctx)
    {
        Ion.with(ctx)
                .load("post",link)
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        if(result != null)
                        {
                            Log.e("TAG_USER", "onClick: SEND URL" + result);
                            Main_Function(result);
                        }else
                        {
                            Log.e("TAG_USER", "onClick: ONFQILED");
                            onFailed(-1);
                        }
                    }
                });
    }

    public void Main_Function(String result)
    {
        String result_check = result;

        int sizeResult = result_check.length();
        result_check = result.substring(8,result_check.length()-1);

        Log.e("TAG_USER", "ParseData: "+result_check+" "+result_check.length());

        if(result_check.length() < 3)
        {
            onFailed(result);
        }else
            {
                ParseData(result);
            }
    }

    @Override
    public void onSucces(Object obj)
    {
        MainActivity.OnSuccesLogin();
    }

    @Override
    public void onFailed(Object obj)
    {
        MainActivity.OnFailedLogin();
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
                user json_user = new user();

                try
                {
                    JSONObject oneObject = jArray.getJSONObject(i);

                    json_user.id        = Integer.parseInt(oneObject.getString("id"));
                    json_user.nom       = oneObject.getString("nom");
                    json_user.prenom    = oneObject.getString("prenom");
                    json_user.tel       = oneObject.getString("tel");
                    json_user.email     = oneObject.getString("email");
                    json_user.type      = Integer.parseInt(oneObject.getString("type"));
                }
                catch (JSONException e)
                {
                    Log.e("tag_json", ""+e);
                }


                userRepository.main_user = json_user;

                Log.e("tag_json", ""+json_user.prenom );

                onSucces(result);
            }
        }
        catch (Exception e)
        {

        }
    }
}
