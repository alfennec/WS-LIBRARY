package com.fennec.e_media.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fennec.e_media.R;
import com.fennec.e_media.apiComm.UrlComm;
import com.fennec.e_media.apiComm.deleteUser;
import com.fennec.e_media.entity.emprunts;
import com.fennec.e_media.repository.empruntsRepository;
import com.fennec.e_media.repository.informationRepository;
import com.fennec.e_media.repository.mediaRepository;
import com.fennec.e_media.repository.userRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URLEncoder;

public class EditEmpruntActivity extends AppCompatActivity {

    public static  EditEmpruntActivity main;

    public TextView tv_titre,tv_user,tv_debut, tv_fin, tv_rendu;

    public static Spinner spinner_user,spinner_media;

    public ImageButton btn_edit;

    public RadioButton radioButton_emprunt, radioButton_n_emprunt;

    public static EditText input_date_debut, input_date_fin;

    public static String value_user,value_media;

    public FloatingActionButton floatingButton;

    public static int idEmprunt;

    public emprunts current_emprunt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_emprunt);

        main = this;

        Bundle extras = getIntent().getExtras();
        idEmprunt = extras.getInt("id");

        current_emprunt = empruntsRepository.findRenduById(idEmprunt);

        spinner_user = (Spinner) findViewById(R.id.spinner_user);
        spinner_media= (Spinner) findViewById(R.id.spinner_media);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userRepository.getName());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_user.setAdapter(arrayAdapter);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mediaRepository.getTitreWithNotEmprunt());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_media.setAdapter(arrayAdapter);

        tv_titre    = (TextView) findViewById(R.id.tv_titre) ;
        tv_user     = (TextView) findViewById(R.id.tv_user) ;
        tv_debut    = (TextView) findViewById(R.id.tv_debut) ;
        tv_fin      = (TextView) findViewById(R.id.tv_fin) ;
        tv_rendu    = (TextView) findViewById(R.id.tv_rendu) ;

        input_date_debut    = (EditText) findViewById(R.id.input_date_debut) ;
        input_date_fin      = (EditText) findViewById(R.id.input_date_fin) ;

        btn_edit = (ImageButton) findViewById(R.id.btn_edit);

        floatingButton = (FloatingActionButton) findViewById(R.id.floatButton);

        //radioButton_livre,radioButton_video, radioButton_audio, radioButton_reportage
        radioButton_emprunt       = (RadioButton) findViewById(R.id.radioButton_emprunt);
        radioButton_n_emprunt       = (RadioButton) findViewById(R.id.radioButton_n_emprunt);

        tv_titre.setText(mediaRepository.getMediabyId(current_emprunt.id_element).titre);
        tv_user.setText(userRepository.getUserById(current_emprunt.id_user).nom+" "+userRepository.getUserById(current_emprunt.id_user).prenom);

        tv_debut.setText(current_emprunt.date_debut);
        tv_fin.setText(current_emprunt.date_fin);

        if(current_emprunt.rendu == 1)
        {
            tv_rendu.setText("Non Emprunter");
            tv_rendu.setTextColor(Color.rgb(0,100,0));
            radioButton_n_emprunt.isChecked();
        }else
        {
            tv_rendu.setText("Emprunter");
            tv_rendu.setTextColor(Color.RED);
            radioButton_emprunt.isChecked();
        }

        tv_fin.setText(current_emprunt.date_fin);


        input_date_debut.setText(current_emprunt.date_debut);
        input_date_fin.setText(current_emprunt.date_fin);


        input_date_debut.setVisibility(View.GONE);
        input_date_fin.setVisibility(View.GONE);


        spinner_user.setVisibility(View.GONE);
        spinner_media.setVisibility(View.GONE);

        radioButton_emprunt.setVisibility(View.GONE);
        radioButton_n_emprunt.setVisibility(View.GONE);


        btn_edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tv_titre.setVisibility(View.GONE);
                tv_user.setVisibility(View.GONE);
                tv_debut.setVisibility(View.GONE);
                tv_fin.setVisibility(View.GONE);
                tv_rendu.setVisibility(View.GONE);

                input_date_debut.setVisibility(View.VISIBLE);
                input_date_fin.setVisibility(View.VISIBLE);


                spinner_user.setVisibility(View.VISIBLE);
                spinner_media.setVisibility(View.VISIBLE);

                radioButton_emprunt.setVisibility(View.VISIBLE);
                radioButton_n_emprunt.setVisibility(View.VISIBLE);
            }
        });

        floatingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                value_media = spinner_media.getSelectedItem().toString();
                value_user = spinner_user.getSelectedItem().toString();

                current_emprunt.id_element = mediaRepository.getIdByTitre(value_media);
                current_emprunt.id_user = userRepository.getIdByNom(value_user);

                current_emprunt.date_debut = input_date_debut.getText().toString();
                current_emprunt.date_fin = input_date_fin.getText().toString();



                if(radioButton_emprunt.isChecked())
                {
                    current_emprunt.rendu = 0;
                }else
                {
                    current_emprunt.rendu = 1;
                }


                String url_informations = "emprunt?";
                String id = "id="+current_emprunt.id;
                String id_user = "&id_user="+current_emprunt.id_user;
                String id_element = "&id_element="+current_emprunt.id_element;
                String date_debut = "&date_debut="+current_emprunt.date_debut;
                String date_fin = "&date_fin="+current_emprunt.date_fin;
                String rendu = "&rendu="+current_emprunt.rendu;


                url_informations = url_informations+id+id_user+id_element+date_debut+date_fin+rendu;

                Log.e("TAG_where", "onClick: "+ UrlComm.url_host+url_informations);

                deleteUser jsonRegister = new deleteUser(UrlComm.url_host+url_informations, "PUT", main);

                Toast.makeText(main, "Media ajouter avec succer", Toast.LENGTH_SHORT);

                empruntsRepository.updateEmprunt(current_emprunt);

                EmpruntActivity.updateRecycle();


                /** past data in right place to do **/
                input_date_debut.setText(current_emprunt.date_debut);
                input_date_fin.setText(current_emprunt.date_fin);

                tv_titre.setText(mediaRepository.getMediabyId(current_emprunt.id_element).titre);
                tv_user.setText(userRepository.getUserById(current_emprunt.id_user).nom+" "+userRepository.getUserById(current_emprunt.id_user).prenom);
                tv_debut.setText(""+current_emprunt.date_debut);
                tv_fin.setText(""+current_emprunt.date_fin);



                tv_titre.setVisibility(View.VISIBLE);
                tv_user.setVisibility(View.VISIBLE);
                tv_debut.setVisibility(View.VISIBLE);
                tv_fin.setVisibility(View.VISIBLE);
                tv_rendu.setVisibility(View.VISIBLE);

                input_date_debut.setVisibility(View.GONE);
                input_date_fin.setVisibility(View.GONE);


                spinner_user.setVisibility(View.GONE);
                spinner_media.setVisibility(View.GONE);

                radioButton_emprunt.setVisibility(View.GONE);
                radioButton_n_emprunt.setVisibility(View.GONE);

            }
        });


    }
}
