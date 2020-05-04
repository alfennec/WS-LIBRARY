package com.fennec.e_media.apiComm;

import android.content.Context;
import android.util.Log;

import com.fennec.e_media.controller.EmpruntActivity;
import com.fennec.e_media.controller.MediaActivity;
import com.fennec.e_media.entity.emprunts;
import com.fennec.e_media.entity.information;
import com.fennec.e_media.myInterface.IonHandler;

import com.fennec.e_media.repository.informationRepository;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AllInformation implements IonHandler {

    public int acty;

    public AllInformation(String link , final Context ctx, int acty)
    {
        this.acty = acty;

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
        MediaActivity.OnSucces();
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
                information json_infos = new information();

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
                onSucces(result);
            }
        }
        catch (Exception e)
        {

        }

    }

}