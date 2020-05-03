package com.fennec.e_media.repository;

import com.fennec.e_media.entity.user;

import java.util.ArrayList;

public class userRepository {

    public static user main_user = new user();

    public static ArrayList<user> list_user = new ArrayList<>();

    public static user getUserById(int id)
    {
        user currect_user = new user();

        for (int i = 0; i < list_user.size(); i++)
        {
            if(list_user.get(i).id == id)
            {
                currect_user = list_user.get(i);
            }
        }

        return currect_user;
    }

    public static ArrayList<String> getName()
    {
        ArrayList<String> current_list = new ArrayList<>();

        for (int i = 0; i < list_user.size(); i++)
        {
            current_list.add(list_user.get(i).nom);
        }

        return current_list;
    }

    public static int getIdByNom(String nom)
    {
        int id = 0;

        for (int i = 0; i < list_user.size(); i++)
        {
            if(list_user.get(i).nom.equals(nom))
            {
                id = list_user.get(i).id;
            }
        }

        return id;

    }

}
