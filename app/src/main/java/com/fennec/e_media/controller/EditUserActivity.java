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
import com.fennec.e_media.repository.userRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.fennec.e_media.entity.user;

import java.net.URLEncoder;

public class EditUserActivity extends AppCompatActivity {

    public static EditUserActivity main;

    public TextView tv_nameUser, tv_total, tv_nom, tv_prenom, tv_type, tv_email, tv_tel;

    public EditText edt_nom, edt_prenom, edt_email, edt_tel, edt_pass1, edt_pass2;

    public ImageButton btn_edit;

    public RadioButton radioButton_admin ,radioButton_user;

    public FloatingActionButton floatingButton;

    public static int idUser;

    public user currentUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        main = this;

        Bundle extras = getIntent().getExtras();
        idUser = extras.getInt("id");

        currentUser = userRepository.getUserById(idUser);

        tv_nameUser = (TextView) findViewById(R.id.tv_nameUser);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_nom = (TextView) findViewById(R.id.tv_nom);
        tv_prenom= (TextView) findViewById(R.id.tv_prenom);
        tv_type = (TextView) findViewById(R.id.tv_sexe);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_tel = (TextView) findViewById(R.id.tv_tel);

        edt_nom = (EditText) findViewById(R.id.edt_nom);
        edt_prenom = (EditText) findViewById(R.id.edt_prenom);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_tel = (EditText) findViewById(R.id.edt_tel);
        edt_pass1 = (EditText) findViewById(R.id.edt_pass1);
        edt_pass2 = (EditText) findViewById(R.id.edt_pass2);

        btn_edit = (ImageButton) findViewById(R.id.btn_edit);

        floatingButton = (FloatingActionButton) findViewById(R.id.floatButton);

        radioButton_admin  = (RadioButton) findViewById(R.id.radioButton_admin);
        radioButton_user  = (RadioButton) findViewById(R.id.radioButton_user);

        tv_nameUser.setText(currentUser.prenom+" "+currentUser.nom);

        tv_nom.setText(currentUser.nom);
        tv_prenom.setText(currentUser.prenom);
        tv_email.setText(""+currentUser.email);
        tv_tel.setText(""+currentUser.tel);

        edt_nom.setText(currentUser.nom);
        edt_prenom.setText(currentUser.prenom);
        edt_email.setText(""+currentUser.email);
        edt_tel.setText(""+currentUser.tel);

        edt_pass1.setText(""+currentUser.passord);
        edt_pass2.setText(""+currentUser.passord);

        if(currentUser.type == 100)
        {
            tv_type.setText("Utilisateur");
            radioButton_user.setChecked(true);
        }else
        {
            tv_type.setText("Administrateur");
            radioButton_admin.setChecked(true);
        }


        edt_nom.setVisibility(View.GONE);
        edt_prenom.setVisibility(View.GONE);
        edt_email.setVisibility(View.GONE);
        edt_tel.setVisibility(View.GONE);
        edt_pass1.setVisibility(View.GONE);
        edt_pass2.setVisibility(View.GONE);

        //radioButton_admin.setVisibility(View.GONE);
        //radioButton_user.setVisibility(View.GONE);

        btn_edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tv_nameUser.setVisibility(View.GONE);
                tv_total.setVisibility(View.GONE);
                tv_nom.setVisibility(View.GONE);
                tv_prenom.setVisibility(View.GONE);
                tv_type.setVisibility(View.GONE);
                tv_email.setVisibility(View.GONE);
                tv_tel.setVisibility(View.GONE);

                edt_nom.setVisibility(View.VISIBLE);
                edt_prenom.setVisibility(View.VISIBLE);
                edt_email.setVisibility(View.VISIBLE);
                edt_tel.setVisibility(View.VISIBLE);
                edt_pass1.setVisibility(View.VISIBLE);
                edt_pass2.setVisibility(View.VISIBLE);

                radioButton_admin.setVisibility(View.VISIBLE);
                radioButton_user.setVisibility(View.VISIBLE);
            }
        });

        floatingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(edt_pass1.getText().toString().equals(edt_pass2.getText().toString()))
                {
                    currentUser.passord = edt_pass1.getText().toString();

                    currentUser.email = edt_email.getText().toString();
                    currentUser.nom = edt_nom.getText().toString();
                    currentUser.prenom = edt_prenom.getText().toString();
                    currentUser.tel = edt_tel.getText().toString();

                    if(radioButton_admin.isChecked())
                    {
                        currentUser.type = 200;
                    }else
                    {
                        currentUser.type = 100;
                    }



                    String url_informations = "user?";
                    String id               = "id="+currentUser.id;
                    String nom              = "&nom="+currentUser.nom;
                    String prenom           = "&prenom="+currentUser.prenom;
                    String tel              = "&tel="+currentUser.tel;
                    String email            = "&email="+currentUser.email;
                    String pass             = "&password="+currentUser.passord;
                    String type             = "&type="+currentUser.type;

                    try {
                        url_informations = "user?";
                        id               = "id="+currentUser.id;
                        nom              = "&nom="+URLEncoder.encode(currentUser.nom, "UTF-8");
                        prenom           = "&prenom="+URLEncoder.encode(currentUser.prenom, "UTF-8");
                        tel              = "&tel="+URLEncoder.encode(currentUser.tel, "UTF-8");
                        email            = "&email="+URLEncoder.encode(currentUser.email, "UTF-8");
                        pass             = "&password="+currentUser.passord;
                        type             = "&type="+currentUser.type;
                    }catch (Exception e) {}

                    url_informations = url_informations+id+nom+prenom+tel+email+pass+type;

                    Log.e("TAG", "onClick: "+url_informations);

                    deleteUser jsonRegister = new deleteUser(UrlComm.url_host+url_informations, "PUT", main);

                    Toast.makeText(main, "Membre modifier avec succer", Toast.LENGTH_SHORT);

                    MembreActivity.updateRecycle();

                    /** past data in right place to do **/
                    tv_nameUser.setText(currentUser.prenom+" "+currentUser.nom);

                    tv_nom.setText(currentUser.nom);
                    tv_prenom.setText(currentUser.prenom);
                    tv_email.setText(" "+currentUser.email);
                    tv_tel.setText(" "+currentUser.tel);

                    tv_nameUser.setVisibility(View.VISIBLE);
                    tv_total.setVisibility(View.VISIBLE);
                    tv_nom.setVisibility(View.VISIBLE);
                    tv_prenom.setVisibility(View.VISIBLE);
                    tv_type.setVisibility(View.VISIBLE);
                    tv_email.setVisibility(View.VISIBLE);
                    tv_tel.setVisibility(View.VISIBLE);

                    edt_nom.setVisibility(View.GONE);
                    edt_prenom.setVisibility(View.GONE);
                    edt_email.setVisibility(View.GONE);
                    edt_tel.setVisibility(View.GONE);
                    edt_pass1.setVisibility(View.GONE);
                    edt_pass2.setVisibility(View.GONE);

                    //radioButton_admin.setVisibility(View.GONE);
                    //radioButton_user.setVisibility(View.GONE);
                }else
                {
                    Toast.makeText(main, "les mots de passe ne sont pas identique ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
