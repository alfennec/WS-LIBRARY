package com.fennec.e_media.apiComm;

import android.content.Context;
import android.util.Log;

import com.fennec.e_media.controller.ui.home.HomeFragment;
import com.fennec.e_media.entity.emprunts;
import com.fennec.e_media.entity.information;
import com.fennec.e_media.myInterface.IonHandler;
import com.fennec.e_media.repository.empruntsRepository;
import com.fennec.e_media.repository.informationRepository;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class empruntsJson implements IonHandler {


    public empruntsJson(String link , final Context ctx)
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
                            Log.d("REP-EMPRUNT", "onCompleted: "+result);
                            ParseData(result);

                        }else
                        {
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
        HomeFragment.onSucces();
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

            }
            Log.e("LISTE-EMPRUNT", "size : "+ empruntsRepository.list_emprunts.size());
            onSucces(result);
        }
        catch (Exception e)
        {

        }

    }

}