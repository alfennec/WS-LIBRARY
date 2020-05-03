package com.fennec.e_media.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fennec.e_media.R;
import com.fennec.e_media.apiComm.UrlComm;
import com.fennec.e_media.apiComm.deleteUser;
import com.fennec.e_media.entity.information;
import com.fennec.e_media.entity.media;
import com.fennec.e_media.repository.informationRepository;
import com.fennec.e_media.repository.mediaRepository;
import com.fennec.e_media.repository.userRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URLEncoder;

public class EditMediaActivity extends AppCompatActivity {

    public static EditMediaActivity main;

    public TextView tv_mediaTitre, tv_titre, tv_des, tv_annee, tv_isbn, tv_nbr_page, tv_date_achat;

    public EditText edt_titre, edt_annee, edt_isbn, edt_nbr_page, edt_date_achat;

    public ImageButton btn_edit;

    public RadioButton radioButton_livre,radioButton_video, radioButton_audio, radioButton_reportage;

    public FloatingActionButton floatingButton;

    public static int idMedia;

    public media currentMedia ;
    public information currentInformtion ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_media);

        main = this;

        Bundle extras = getIntent().getExtras();
        idMedia = extras.getInt("id");

        currentMedia = mediaRepository.getMediabyId(idMedia);
        currentInformtion = informationRepository.getInfoid(idMedia);

        tv_mediaTitre = (TextView) findViewById(R.id.tv_mediaTitre);
        tv_titre = (TextView) findViewById(R.id.tv_titre);
        tv_annee = (TextView) findViewById(R.id.tv_annee);
        tv_isbn= (TextView) findViewById(R.id.tv_isbn);
        tv_nbr_page = (TextView) findViewById(R.id.tv_nbr_page);
        tv_date_achat = (TextView) findViewById(R.id.tv_date_achat);
        tv_des = (TextView) findViewById(R.id.tv_des);

        edt_titre = (EditText) findViewById(R.id.edt_titre);
        edt_annee = (EditText) findViewById(R.id.edt_annee);
        edt_isbn = (EditText) findViewById(R.id.edt_isbn);
        edt_nbr_page = (EditText) findViewById(R.id.edt_nbr_page);
        edt_date_achat = (EditText) findViewById(R.id.edt_date_achat);

        btn_edit = (ImageButton) findViewById(R.id.btn_edit);

        floatingButton = (FloatingActionButton) findViewById(R.id.floatButton);

        //radioButton_livre,radioButton_video, radioButton_audio, radioButton_reportage
        radioButton_livre       = (RadioButton) findViewById(R.id.radioButton_livre);
        radioButton_video       = (RadioButton) findViewById(R.id.radioButton_video);
        radioButton_audio       = (RadioButton) findViewById(R.id.radioButton_audio);
        radioButton_reportage   = (RadioButton) findViewById(R.id.radioButton_reportage);

        tv_mediaTitre.setText(currentMedia.titre);

        tv_titre.setText(currentMedia.titre);
        tv_annee.setText(currentInformtion.annee);
        tv_isbn.setText(""+currentInformtion.isbn);
        tv_nbr_page.setText(""+currentInformtion.nbr_page);
        tv_date_achat.setText(""+currentInformtion.date_achat);
        tv_date_achat.setText(""+currentInformtion.date_achat);

        edt_titre.setText(currentMedia.titre);
        edt_annee.setText(currentInformtion.annee);
        edt_isbn.setText(""+currentInformtion.isbn);
        edt_nbr_page.setText(""+currentInformtion.nbr_page);
        tv_des.setText(""+currentMedia.des);

        // public RadioButton radioButton_livre,radioButton_video, radioButton_audio, radioButton_reportage;

        switch (currentMedia.des)
        {
            case "livre" :  tv_des.setText("livre");
                radioButton_livre.setChecked(true);
                break;

            case "video" :  tv_des.setText("video");
                radioButton_video.setChecked(true);
                break;

            case "audio" :  tv_des.setText("audio");
                radioButton_audio.setChecked(true);
                break;

            case "reportage" :  tv_des.setText("reportage");
                radioButton_reportage.setChecked(true);
                break;
        }

        edt_titre.setVisibility(View.GONE);
        edt_annee.setVisibility(View.GONE);
        edt_isbn.setVisibility(View.GONE);
        edt_nbr_page.setVisibility(View.GONE);
        edt_date_achat.setVisibility(View.GONE);

        radioButton_livre.setVisibility(View.GONE);
        radioButton_video.setVisibility(View.GONE);
        radioButton_audio.setVisibility(View.GONE);
        radioButton_reportage.setVisibility(View.GONE);

        btn_edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //tv_mediaTitre, tv_titre, tv_des, tv_annee, tv_isbn, tv_nbr_page, tv_date_achat;
                tv_titre.setVisibility(View.GONE);
                tv_des.setVisibility(View.GONE);
                tv_annee.setVisibility(View.GONE);
                tv_isbn.setVisibility(View.GONE);
                tv_nbr_page.setVisibility(View.GONE);
                tv_date_achat.setVisibility(View.GONE);

                edt_titre.setVisibility(View.VISIBLE);
                edt_annee.setVisibility(View.VISIBLE);
                edt_isbn.setVisibility(View.VISIBLE);
                edt_nbr_page.setVisibility(View.VISIBLE);
                edt_date_achat.setVisibility(View.VISIBLE);

                radioButton_livre.setVisibility(View.VISIBLE);
                radioButton_video.setVisibility(View.VISIBLE);
                radioButton_audio.setVisibility(View.VISIBLE);
                radioButton_reportage.setVisibility(View.VISIBLE);
            }
        });

        floatingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                currentMedia.titre = edt_titre.getText().toString();

                if(radioButton_livre.isChecked())
                {
                    currentMedia.des = "livre";
                }

                if(radioButton_video.isChecked())
                {
                    currentMedia.des = "video";
                }

                if(radioButton_audio.isChecked())
                {
                    currentMedia.des = "audio";
                }

                if(radioButton_reportage.isChecked())
                {
                    currentMedia.des = "reportage";
                }

                String url_informations = "media?";
                String id               = "id="+currentMedia.id;
                String titre            = "&titre="+currentMedia.titre;
                String des              = "&des="+currentMedia.des;

                try {
                    url_informations = "media?";
                    id               = "id="+currentMedia.id;
                    titre            = "&titre="+ URLEncoder.encode(currentMedia.titre, "UTF-8");
                    des              = "&des="+URLEncoder.encode(currentMedia.des, "UTF-8");
                }catch (Exception e) {}

                url_informations = url_informations+id+titre+des;

                Log.e("TAG", "onClick: "+UrlComm.url_host+url_informations);

                deleteUser jsonRegister = new deleteUser(UrlComm.url_host+url_informations, "PUT", main);

                /************** information **********************/

                currentInformtion.titre = edt_titre.getText().toString();
                currentInformtion.annee = edt_annee.getText().toString();
                currentInformtion.isbn  = edt_isbn.getText().toString();
                currentInformtion.nbr_page = Integer.parseInt(edt_nbr_page.getText().toString());
                currentInformtion.qrcode = edt_titre.getText().toString();

                url_informations        = "information?";
                id                      = "id="+currentInformtion.id;
                String id_element       = "&id_element="+currentMedia.id;
                String annee            = "&annee="+currentInformtion.annee;
                String isbn             = "&isbn="+currentInformtion.isbn;
                String nbr_page         = "&nbr_page="+currentInformtion.nbr_page;
                String date_achat       = "&date_achat="+currentInformtion.date_achat;
                String qrcode           = "&qrcode="+currentInformtion.qrcode;


                try {
                    id               = "id="+currentInformtion.id;
                    id_element       = "&id_element="+currentMedia.id;
                    annee            = "&annee="+URLEncoder.encode(currentInformtion.annee, "UTF-8");
                    titre            = "&titre="+ URLEncoder.encode(currentMedia.titre, "UTF-8");
                    isbn             = "&isbn="+URLEncoder.encode(currentInformtion.isbn, "UTF-8");
                    nbr_page         = "&nbr_page="+currentInformtion.nbr_page;
                    date_achat       = "&date_achat="+URLEncoder.encode(currentInformtion.date_achat, "UTF-8");
                    qrcode           = "&qrcode="+URLEncoder.encode(currentInformtion.qrcode, "UTF-8");
                }catch (Exception e) {}

                url_informations = url_informations+id+id_element+annee+titre+isbn+nbr_page+date_achat+qrcode;

                Log.e("TAG", "onClick: "+UrlComm.url_host+url_informations);

                jsonRegister = new deleteUser(UrlComm.url_host+url_informations, "PUT", main);

                Toast.makeText(main, "Media modifier avec succer", Toast.LENGTH_SHORT);

                MediaActivity.updateRecycle();

                /** past data in right place to do **/
                tv_mediaTitre.setText(currentMedia.titre);

                tv_mediaTitre.setText(currentMedia.des);

                tv_titre.setText(currentMedia.titre);
                tv_annee.setText(currentInformtion.annee);
                tv_isbn.setText(""+currentInformtion.isbn);
                tv_nbr_page.setText(""+currentInformtion.nbr_page);
                tv_date_achat.setText(""+currentInformtion.date_achat);
                tv_date_achat.setText(""+currentInformtion.date_achat);

                tv_titre.setVisibility(View.VISIBLE);
                tv_des.setVisibility(View.VISIBLE);
                tv_annee.setVisibility(View.VISIBLE);
                tv_isbn.setVisibility(View.VISIBLE);
                tv_nbr_page.setVisibility(View.VISIBLE);
                tv_date_achat.setVisibility(View.VISIBLE);



                edt_titre.setVisibility(View.GONE);
                edt_annee.setVisibility(View.GONE);
                edt_isbn.setVisibility(View.GONE);
                edt_nbr_page.setVisibility(View.GONE);
                edt_date_achat.setVisibility(View.GONE);

                radioButton_livre.setVisibility(View.GONE);
                radioButton_video.setVisibility(View.GONE);
                radioButton_audio.setVisibility(View.GONE);
                radioButton_reportage.setVisibility(View.GONE);
            }
        });

    }
}
