package com.fennec.e_media.repository;

import android.util.Log;

import com.fennec.e_media.entity.emprunts;

import java.util.ArrayList;

public class empruntsRepository {

    public static ArrayList<emprunts> list_emprunts = new ArrayList<>();

    public static emprunts findRenduById(int id)
    {
        emprunts currentEmprunt = new emprunts();

        for (int i = 0; i < list_emprunts.size(); i++)
        {
            if(list_emprunts.get(i).id_element == id)
            {
                currentEmprunt = list_emprunts.get(i);
                Log.e("TAG RENDU", "FIND IT: ");
            }
        }

        return currentEmprunt;
    }

    public static void updateEmprunt(emprunts current_emprunt)
    {
        for (int i = 0; i < list_emprunts.size(); i++)
        {
           if(current_emprunt.id == list_emprunts.get(i).id)
           {
               list_emprunts.get(i).id_element = current_emprunt.id_element;
               list_emprunts.get(i).id_user = current_emprunt.id_user;
               list_emprunts.get(i).date_debut = current_emprunt.date_debut;
               list_emprunts.get(i).date_fin = current_emprunt.date_fin;
               list_emprunts.get(i).rendu = current_emprunt.rendu;
           }
        }
    }

}
