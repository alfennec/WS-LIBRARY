package com.fennec.e_media.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fennec.e_media.R;
import com.fennec.e_media.apiComm.UrlComm;
import com.fennec.e_media.apiComm.deleteUser;
import com.fennec.e_media.entity.media;
import com.fennec.e_media.repository.mediaRepository;

import com.google.android.material.textfield.TextInputLayout;

import java.net.URLEncoder;

public class AddMediaActivity extends AppCompatActivity {

    public Button button_valide_form;

    public static AddMediaActivity main;

    public static Boolean all_Right ;

    public media new_media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_media);

        main = this;

        inRegistreForm();
    }

    public void inRegistreForm()
    {
        button_valide_form = (Button) findViewById(R.id.button_valide_form);

        button_valide_form.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("TAG_FORM", "onClick: in form");

                TextInputLayout input_titre = (TextInputLayout) findViewById(R.id.input_titre);

                RadioButton radioButton_livre = (RadioButton) findViewById(R.id.radioButton_livre);
                RadioButton radioButton_video = (RadioButton) findViewById(R.id.radioButton_video);
                RadioButton radioButton_audio = (RadioButton) findViewById(R.id.radioButton_audio);
                RadioButton radioButton_Reportage = (RadioButton) findViewById(R.id.radioButton_Reportage);

                if(verifyIfBlank(input_titre))
                {
                    all_Right = true;
                }else
                {
                    all_Right = false;
                }

                if(all_Right)
                {
                    String RadioGroupe="";

                    if(radioButton_livre.isChecked())
                    {
                        RadioGroupe = "livre";
                    }

                    if(radioButton_video.isChecked())
                    {
                        RadioGroupe = "video";
                    }

                    if(radioButton_audio.isChecked())
                    {
                        RadioGroupe = "audio";
                    }

                    if(radioButton_Reportage.isChecked())
                    {
                        RadioGroupe = "reportage";
                    }


                    new_media = new media(input_titre.getEditText().getText().toString(), RadioGroupe);
                    mediaRepository.list_media.add(new_media);

                        ///livraison/json/setClient.php?email=bermod@gmail.com&pass=123456&nom=med&prenom=ber&tel=0611336605&adresse=haylaymoune&ville=oujda&sexe=1

                        String url_informations = "media?";
                        String titre            = "titre="+new_media.titre;
                        String des              = "&des="+new_media.des;

                    try {
                        titre  = "titre="+URLEncoder.encode(new_media.titre, "UTF-8");
                    }catch (Exception e) {}


                        url_informations = url_informations+titre+des;

                        Log.e("TAG_where", "onClick: "+UrlComm.url_host+url_informations);

                        deleteUser jsonRegister = new deleteUser(UrlComm.url_host+url_informations, "POST", main);

                        Toast.makeText(main, "Media ajouter avec succer", Toast.LENGTH_SHORT);

                        MediaActivity.updateRecycle();
                        main.finish();

                }else{
                    Toast.makeText(main, "Veuillez saisir tout les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean verifyIfBlank(TextInputLayout input)
    {
        if (TextUtils.isEmpty(input.getEditText().getText().toString()))
        {
            input.setError("Champs vide");
            return false;
        }else {
            input.setErrorEnabled(false);
            return true;
        }
    }
}
