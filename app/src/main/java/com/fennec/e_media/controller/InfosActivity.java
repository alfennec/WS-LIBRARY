package com.fennec.e_media.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.fennec.e_media.R;
import com.fennec.e_media.entity.information;
import com.fennec.e_media.entity.media;
import com.fennec.e_media.repository.empruntsRepository;
import com.fennec.e_media.repository.informationRepository;
import com.fennec.e_media.repository.mediaRepository;

public class InfosActivity extends AppCompatActivity {

    TextView tv_nom_media,tv_titre,tv_isbn,tv_nbr_page,tv_date_achat,tv_qrcode,tv_autre,tv_emprunter;

    public int id_app;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);


        tv_nom_media    = (TextView) findViewById(R.id.tv_nom_media);
        tv_titre        = (TextView) findViewById(R.id.tv_titre);
        tv_isbn         = (TextView) findViewById(R.id.tv_isbn);
        tv_nbr_page     = (TextView) findViewById(R.id.tv_nbr_page);
        tv_date_achat   = (TextView) findViewById(R.id.tv_date_achat);
        tv_qrcode       = (TextView) findViewById(R.id.tv_qrcode);
        tv_autre        = (TextView) findViewById(R.id.tv_autre);

        tv_emprunter    = (TextView) findViewById(R.id.tv_emprunter);

        Bundle extras = getIntent().getExtras();
        id_app = extras.getInt("id_app");

        setAll(id_app);
    }

    public void setAll(int idMedia)
    {
        media currentMedia = mediaRepository.getMediabyId(idMedia);
        information currentInfo = informationRepository.getInfoid(idMedia);

            tv_nom_media.setText(""+currentMedia.titre);
            tv_titre.setText(""+currentMedia.titre);
            tv_isbn.setText(""+currentInfo.isbn);
            tv_nbr_page.setText(""+currentInfo.nbr_page);
            tv_date_achat.setText(""+currentInfo.date_achat);
            tv_qrcode.setText(""+currentInfo.qrcode);
            tv_autre.setText("--");

            if(empruntsRepository.findRenduById(currentMedia.id).rendu == 1)
            {
                tv_emprunter.setText("Non Emprunter");
                tv_emprunter.setTextColor(Color.GREEN);
            }else
            {
                tv_emprunter.setText("Emprunter");
                tv_emprunter.setTextColor(Color.RED);
            }
    }
}
