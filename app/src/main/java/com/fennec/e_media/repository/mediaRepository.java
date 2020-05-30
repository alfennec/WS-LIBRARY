package com.fennec.e_media.repository;

import android.util.Log;

import com.fennec.e_media.entity.media;

import java.util.ArrayList;

public class mediaRepository {

    public static ArrayList<media> list_media = new ArrayList<>();

    public static media getMediabyId(int id)
    {
        media current_media = new media();

        for (int i = 0; i < list_media.size(); i++)
        {
            if(list_media.get(i).id == id)
            {
                current_media = list_media.get(i);
            }
        }

        return current_media;
    }

    public static ArrayList<String> getTitreWithNotEmprunt()
    {
        ArrayList<String> current_list = new ArrayList<>();

        for (int i = 0; i < list_media.size(); i++)
        {
            if(empruntsRepository.ifExist(list_media.get(i).id))
            {
                if(empruntsRepository.findRenduById(list_media.get(i).id).rendu == 1)
                {
                    current_list.add(list_media.get(i).titre);
                    Log.e("RETURNS-MEDIA", "MEDIA SANS EMPRUNT if : "+list_media.get(i).titre);
                }
            }else
                {
                    current_list.add(list_media.get(i).titre);
                    Log.e("RETURNS-MEDIA", "MEDIA SANS EMPRUNT else : "+list_media.get(i).titre);
                }
        }

        return current_list;
    }

    public static int getIdByTitre(String titre)
    {
        int id = 0;

        for (int i = 0; i < list_media.size(); i++)
        {
            if(list_media.get(i).titre.equals(titre))
            {
                id = list_media.get(i).id;
            }
        }

        return id;

    }
}
