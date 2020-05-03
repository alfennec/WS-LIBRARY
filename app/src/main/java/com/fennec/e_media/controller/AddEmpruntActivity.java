package com.fennec.e_media.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.fennec.e_media.R;
import com.fennec.e_media.adapter.mediaAdminAdapter;
import com.fennec.e_media.apiComm.UrlComm;
import com.fennec.e_media.apiComm.deleteUser;
import com.fennec.e_media.entity.emprunts;
import com.fennec.e_media.entity.media;
import com.fennec.e_media.repository.empruntsRepository;
import com.fennec.e_media.repository.mediaRepository;
import com.fennec.e_media.repository.userRepository;
import com.google.android.material.textfield.TextInputLayout;

import java.net.URLEncoder;

public class AddEmpruntActivity extends AppCompatActivity {

    public static AddEmpruntActivity main;
    public Button button_valide_form;

    public static Boolean all_Right ;

    public static Spinner spinner_user,spinner_media;

    public emprunts new_emprunt;

    public static String value_user,value_media;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emprunt);

        main = this;

        spinner_user = (Spinner) findViewById(R.id.spinner_user);
        spinner_media= (Spinner) findViewById(R.id.spinner_media);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userRepository.getName());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_user.setAdapter(arrayAdapter);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mediaRepository.getTitreWithNotEmprunt());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_media.setAdapter(arrayAdapter);


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

                TextInputLayout input_date_debut = (TextInputLayout) findViewById(R.id.input_date_debut);
                TextInputLayout input_date_fin = (TextInputLayout) findViewById(R.id.input_date_fin);

                RadioButton radioButton_r_oui = (RadioButton) findViewById(R.id.radioButton_r_oui);
                RadioButton radioButton_r_non = (RadioButton) findViewById(R.id.radioButton_r_non);

                value_media = spinner_media.getSelectedItem().toString();
                value_user = spinner_user.getSelectedItem().toString();

                if(verifyIfBlank(input_date_debut) && verifyIfBlank(input_date_fin))
                {
                    all_Right = true;
                }else
                {
                    all_Right = false;
                }

                if(all_Right)
                {
                    int RadioGroupe;

                    if(radioButton_r_oui.isChecked())
                    {
                        RadioGroupe = 1;
                    }else
                        {
                            RadioGroupe = 0;
                        }




                    new_emprunt = new emprunts();
                    new_emprunt.id_element = mediaRepository.getIdByTitre(value_media);
                    new_emprunt.id_user = userRepository.getIdByNom(value_user);

                    new_emprunt.date_debut = input_date_debut.getEditText().getText().toString();
                    new_emprunt.date_fin = input_date_fin.getEditText().getText().toString();

                    new_emprunt.rendu = RadioGroupe;


                    empruntsRepository.list_emprunts.add(new_emprunt);

                    ///livraison/json/setClient.php?email=bermod@gmail.com&pass=123456&nom=med&prenom=ber&tel=0611336605&adresse=haylaymoune&ville=oujda&sexe=1

                    String url_informations = "emprunt?";
                    String id_user = "id_user="+new_emprunt.id_user;
                    String id_element = "&id_element="+new_emprunt.id_element;
                    String date_debut = "&date_debut="+new_emprunt.date_debut;
                    String date_fin = "&date_fin="+new_emprunt.date_fin;
                    String rendu = "&rendu="+new_emprunt.rendu;


                    url_informations = url_informations+id_user+id_element+date_debut+date_fin+rendu;

                    Log.e("TAG_where", "onClick: "+ UrlComm.url_host+url_informations);

                    deleteUser jsonRegister = new deleteUser(UrlComm.url_host+url_informations, "POST", main);

                    Toast.makeText(main, "Media ajouter avec succer", Toast.LENGTH_SHORT);

                    EmpruntActivity.updateRecycle();
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
