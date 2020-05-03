package com.fennec.e_media.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fennec.e_media.R;
import com.fennec.e_media.adapter.mediaAdapter;
import com.fennec.e_media.adapter.memberAdapter;
import com.fennec.e_media.apiComm.AllUserJson;
import com.fennec.e_media.apiComm.UrlComm;
import com.fennec.e_media.apiComm.deleteUser;
import com.fennec.e_media.apiComm.mediaJson;
import com.fennec.e_media.repository.empruntsRepository;
import com.fennec.e_media.repository.informationRepository;
import com.fennec.e_media.repository.mediaRepository;
import com.fennec.e_media.repository.userRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MembreActivity extends AppCompatActivity {

    public static MembreActivity main;
    public static ProgressDialog dialog;

    public static AllUserJson usersJson;

    public static RecyclerView recyclerView;

    public static memberAdapter myMemberAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membre);

        main = this;

        //////////// clear all data exesting
        userRepository.list_user.clear();


        String url_informations = UrlComm.url_host+"user";

        Log.d("COMPILE E1", "onClick: SEND URL" + url_informations);

        usersJson = new AllUserJson(url_informations, main, 1);
        dialog = ProgressDialog.show(main, "", "Traitement de données. Veulliez attendre ...", true);

        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(main, AddUserctivity.class);
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

        myMemberAdapter = new memberAdapter(userRepository.list_user);
        recyclerView.setAdapter(myMemberAdapter);
        /** adapter for test we have to improve our self for this end  **/

        dialog.dismiss();
    }

    public static void updateRecycle()
    {
        /** adapter for test we have to improve our self for this app  **/
        recyclerView = (RecyclerView) main.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        myMemberAdapter = new memberAdapter(userRepository.list_user);
        recyclerView.setAdapter(myMemberAdapter);
        /** adapter for test we have to improve our self for this end  **/
    }

    public static void DeleteUser(int id)
    {
        String url_informations = UrlComm.url_host+"user/"+id;
        deleteUser deleteCurrentUser = new deleteUser(url_informations,"delete", main);
    }

    public static void to_newIntent(int id)
    {
        Intent intent = new Intent(main, EditUserActivity.class);
        intent.putExtra("id",id);
        main.startActivity(intent);
    }

}
