package com.fennec.e_media.apiComm;

import android.content.Context;
import android.util.Log;

import com.fennec.e_media.controller.EmpruntActivity;
import com.fennec.e_media.controller.MediaActivity;
import com.fennec.e_media.controller.ui.home.HomeFragment;
import com.fennec.e_media.entity.emprunts;
import com.fennec.e_media.entity.media;
import com.fennec.e_media.myInterface.IonHandler;
import com.fennec.e_media.repository.empruntsRepository;
import com.fennec.e_media.repository.mediaRepository;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AllEmpruntJson implements IonHandler {


    public AllEmpruntJson(String link , final Context ctx)
    {
        Ion.with(ctx)
                .load(link)
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        if(result != null)
                        {
                            ParseData(result);
                        }else
                        {
                            onFailed(-1);
                        }
                    }
                });
    }

    @Override
    public void onSucces(Object obj)
    {
        EmpruntActivity.EmpruntOnSucces();
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
                emprunts json_coursier = new emprunts();

                try
                {
                    JSONObject oneObject = jArray.getJSONObject(i);

                    json_coursier.id      = Integer.parseInt(oneObject.getString("id"));
                    json_coursier.id_user      = Integer.parseInt(oneObject.getString("id_user"));
                    json_coursier.id_element   = Integer.parseInt(oneObject.getString("id_element"));
                    json_coursier.date_debut   = oneObject.getString("date_debut");
                    json_coursier.date_fin   = oneObject.getString("date_fin");
                    json_coursier.rendu   = Integer.parseInt(oneObject.getString("rendu"));

                }
                catch (JSONException e)
                {
                    Log.e("tag_json", ""+e);
                }

                empruntsRepository.list_emprunts.add(json_coursier);
                onSucces(result);
            }
        }
        catch (Exception e)
        {

        }

    }

}