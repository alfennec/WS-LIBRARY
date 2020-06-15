package com.fennec.e_media.repository;

import android.util.Log;

import com.fennec.e_media.entity.information;

import java.util.ArrayList;

public class informationRepository {

    public static ArrayList<information> list_information = new ArrayList<>();

    public static information getInfoid(int id)
    {
        information current_infos = new information();

        for (int i = 0; i < list_information.size(); i++)
        {
            if(list_information.get(i).id_element == id)
            {
                current_infos = list_information.get(i);
                Log.d("IF-GET-INFOS", "getInfoid: "+list_information.get(i).titre);
            }
        }

        return current_infos;
    }

    public static information getInfoScann(String scanne)
    {
        information current_infos = new information();

        for (int i = 0; i < list_information.size(); i++)
        {
            if(list_information.get(i).qrcode.equals(scanne))
            {
                current_infos = list_information.get(i);
            }
        }

        return current_infos;
    }
}
