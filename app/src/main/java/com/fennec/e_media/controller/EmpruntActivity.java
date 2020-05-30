package com.fennec.e_media.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fennec.e_media.R;
import com.fennec.e_media.adapter.empruntAdapter;
import com.fennec.e_media.adapter.mediaAdminAdapter;
import com.fennec.e_media.adapter.mediaEmpruntAdapter;
import com.fennec.e_media.adapter.memberAdapter;
import com.fennec.e_media.apiComm.AllEmpruntJson;
import com.fennec.e_media.apiComm.AllMediaJson;
import com.fennec.e_media.apiComm.AllUserJson;
import com.fennec.e_media.apiComm.UrlComm;
import com.fennec.e_media.apiComm.deleteUser;
import com.fennec.e_media.repository.empruntsRepository;
import com.fennec.e_media.repository.mediaRepository;
import com.fennec.e_media.repository.userRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EmpruntActivity extends AppCompatActivity {

    public static EmpruntActivity main;
    public static ProgressDialog dialog;

    public static AllMediaJson MymediaJson;
    public static AllEmpruntJson MyEmpruntJson;
    public static AllUserJson MyUserJson;

    public static RecyclerView recyclerView;

    public static empruntAdapter myEmpruntAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emprunt);

        main = this;

        //////////// clear all data exesting
        empruntsRepository.list_emprunts.clear();
        mediaRepository.list_media.clear();
        userRepository.list_user.clear();


        String url_informations = UrlComm.url_host+"emprunt";
        MyEmpruntJson = new AllEmpruntJson(url_informations, main, 1);
        dialog = ProgressDialog.show(main, "", "Traitement de données. Veulliez attendre ...", true);

        Log.e("TAG_EMPRUNT", "onCreate: "+url_informations );

        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(main, AddEmpruntActivity.class);
                main.startActivity(intent);
            }
        });
    }

    public static void EmpruntOnSucces()
    {
        String url_informations = UrlComm.url_host+"media";
        MymediaJson = new AllMediaJson(url_informations, main, 2);
        Log.e("TAG_EMPRUNT", "onCreate: "+url_informations );
    }

    public static void MediaOnSucces()
    {
        String url_informations = UrlComm.url_host+"user";
        MyUserJson = new AllUserJson(url_informations, main, 2);
        Log.e("TAG_EMPRUNT", "onCreate: "+url_informations );
    }

    public static void OnSucces()
    {
        Log.e("TAG_EMPRUNT", "onCreate: IN onSucces emprunt" );

        /** adapter for test we have to improve our self for this app  **/
        recyclerView = (RecyclerView) main.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        myEmpruntAdapter = new empruntAdapter(empruntsRepository.list_emprunts);
        recyclerView.setAdapter(myEmpruntAdapter);
        /** adapter for test we have to improve our self for this end  **/

        dialog.dismiss();
    }

    public static void updateRecycle()
    {
        /** adapter for test we have to improve our self for this app  **/
        recyclerView = (RecyclerView) main.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        myEmpruntAdapter = new empruntAdapter(empruntsRepository.list_emprunts);
        recyclerView.setAdapter(myEmpruntAdapter);
        /** adapter for test we have to improve our self for this end  **/
    }

    public static void DeleteEmprunt(int id)
    {
        String url_informations = UrlComm.url_host+"emprunt/"+id;
        Log.e("TAG_DELETE", "onClick: SEND URL" + url_informations);
        deleteUser deleteCurrentUser = new deleteUser(url_informations,"delete", main);
        Toast.makeText(main, "l'emprunt est suprimer avec success", Toast.LENGTH_SHORT).show();
    }

    public static void to_newIntent(int id)
    {
        Intent intent = new Intent(main, EditEmpruntActivity.class);
        intent.putExtra("id",id);
        main.startActivity(intent);
    }
}
