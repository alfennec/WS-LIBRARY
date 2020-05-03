package com.fennec.e_media.apiComm;

import android.content.Context;
import android.util.Log;

import com.fennec.e_media.controller.EmpruntActivity;
import com.fennec.e_media.controller.MainActivity;
import com.fennec.e_media.controller.MembreActivity;
import com.fennec.e_media.entity.user;
import com.fennec.e_media.myInterface.IonHandler;
import com.fennec.e_media.repository.userRepository;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AllUserJson implements IonHandler {

    public int acty;

    public AllUserJson(String link , final Context ctx, int acty)
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
                            Log.d("COMPILE E2", "onClick: SEND URL" + result);
                            ParseData(result);
                        }else
                        {
                            Log.e("TAG_GET", "onClick: ONFQILED");
                            onFailed(-1);
                        }
                    }
                });
    }

    public void Main_Function(String result)
    {
        String result_failed = result.replaceAll("\\s","");
        String result_succes = result;

        //Log.d("TAG_JSON_LOGIN", "onClick: SEND"+result+"rt");
        try
        {
            if(Integer.parseInt(result_failed) == 406 )
            {
                onFailed(result);
            }
        }catch (Exception e)
        {
            ParseData(result_succes);
            onSucces(result_succes);

        }
    }

    @Override
    public void onSucces(Object obj)
    {
        MembreActivity.OnSucces();
    }

    public void onEmpruntSucces(Object obj)
    {
        EmpruntActivity.OnSucces();
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

                userRepository.list_user.add(json_user);

                Log.e("TAG_AFFECTED", ""+json_user.nom);

                if(acty == 1){
                    onSucces(result);
                }else
                    {
                        onEmpruntSucces(result);
                    }

            }
        }
        catch (Exception e)
        {

        }
    }
}

