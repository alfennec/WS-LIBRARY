package com.fennec.e_media.apiComm;

import android.content.Context;
import android.util.Log;

import com.fennec.e_media.controller.ui.home.HomeFragment;
import com.fennec.e_media.entity.information;
import com.fennec.e_media.myInterface.IonHandler;
import com.fennec.e_media.repository.informationRepository;
import com.fennec.e_media.repository.mediaRepository;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class informationJson implements IonHandler {


    public informationJson(String link , final Context ctx)
    {
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
                            Log.e("TAG_GET", "onClick: SEND URL" + result);
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
        HomeFragment.onSuccesInfos();
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
                information  json_infos = new information();

                try
                {
                    JSONObject oneObject = jArray.getJSONObject(i);

                    json_infos.id           = Integer.parseInt(oneObject.getString("id"));
                    json_infos.id_element   = Integer.parseInt(oneObject.getString("id_element"));
                    json_infos.annee        = oneObject.getString("annee");
                    json_infos.titre        = oneObject.getString("titre");
                    json_infos.isbn         = oneObject.getString("isbn");
                    json_infos.nbr_page     = Integer.parseInt(oneObject.getString("nbr_page"));
                    json_infos.date_achat   = oneObject.getString("date_achat");
                    json_infos.qrcode       = oneObject.getString("qrcode");

                }
                catch (JSONException e)
                {
                    Log.e("tag_json", ""+e);
                }

                informationRepository.list_information.add(json_infos);

                Log.e("IF-GET-INFOS", "marche bien "+json_infos.titre);

            }

            onSucces(result);

            Log.e("IF-GET-INFOS", "marche bien "+informationRepository.list_information.size());
        }
        catch (Exception e)
        {

        }
    }
}

