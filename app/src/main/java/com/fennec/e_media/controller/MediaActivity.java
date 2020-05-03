package com.fennec.e_media.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fennec.e_media.R;
import com.fennec.e_media.adapter.mediaAdminAdapter;
import com.fennec.e_media.adapter.memberAdapter;
import com.fennec.e_media.apiComm.AllMediaJson;
import com.fennec.e_media.apiComm.AllUserJson;
import com.fennec.e_media.apiComm.UrlComm;
import com.fennec.e_media.apiComm.deleteUser;
import com.fennec.e_media.apiComm.mediaJson;
import com.fennec.e_media.repository.mediaRepository;
import com.fennec.e_media.repository.userRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MediaActivity extends AppCompatActivity {

    public static MediaActivity main;
    public static ProgressDialog dialog;

    public static AllMediaJson MymediaJson;

    public static RecyclerView recyclerView;

    public static mediaAdminAdapter myMediaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        main = this;

        //////////// clear all data exesting
        mediaRepository.list_media.clear();


        String url_informations = UrlComm.url_host+"media";
        MymediaJson = new AllMediaJson(url_informations, main, 1);
        dialog = ProgressDialog.show(main, "", "Traitement de données. Veulliez attendre ...", true);

        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(main, AddMediaActivity.class);
                main.startActivity(intent);
            }
        });
    }

    public static void OnSucces()
    {
        /** adapter for test we have to improve our self for this app  **/
        recyclerView = (RecyclerView) main.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        myMediaAdapter = new mediaAdminAdapter(mediaRepository.list_media);
        recyclerView.setAdapter(myMediaAdapter);
        /** adapter for test we have to improve our self for this end  **/

        dialog.dismiss();
    }

    public static void updateRecycle()
    {
        /** adapter for test we have to improve our self for this app  **/
        recyclerView = (RecyclerView) main.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        myMediaAdapter = new mediaAdminAdapter(mediaRepository.list_media);
        recyclerView.setAdapter(myMediaAdapter);
        /** adapter for test we have to improve our self for this end  **/
    }

    public static void DeleteUser(int id)
    {
        String url_informations = UrlComm.url_host+"media/"+id;
        deleteUser deleteCurrentUser = new deleteUser(url_informations,"delete", main);
    }

    public static void to_newIntent(int id)
    {
        Intent intent = new Intent(main, EditMediaActivity.class);
        intent.putExtra("id",id);
        main.startActivity(intent);
    }
}
