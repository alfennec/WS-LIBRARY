package com.fennec.e_media.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fennec.e_media.R;
import com.fennec.e_media.apiComm.UrlComm;
import com.fennec.e_media.apiComm.userJson;
import com.fennec.e_media.repository.userRepository;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    public static MainActivity main;
    public static String MY_PREFS_NAME = "first_log";

    public ProgressBar progressBar2;
    public int progress = 0;

    TextInputLayout editText_email;
    TextInputLayout editText_pass;

    public static ProgressDialog dialog;

    public Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main = this;

        if(isNetworkConnected())
        {
            if(isSharedPreferences())
            {
                if(userRepository.main_user.type == 100)
                {
                    Intent intent = new Intent(main, HomeActivity.class);
                    main.startActivity(intent);
                }else
                {
                    Intent intent = new Intent(main, AdminActivity.class);
                    main.startActivity(intent);
                }

                main.finish();
            }
            else{
                setContentView(R.layout.login_form);

                editText_email = (TextInputLayout) findViewById(R.id.editText_email);
                editText_pass = (TextInputLayout) findViewById(R.id.editText_pass);

                Button Button_valider = (Button) findViewById(R.id.Button_valider);

                Button Button_registre = (Button) findViewById(R.id.Button_registre);

                Button_valider.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(verifyIfBlank(editText_email) && verifyIfBlank(editText_pass))
                        {
                            String url_informations = "lguser?";

                            String email = "email="+editText_email.getEditText().getText().toString();
                            String pass  = "&password="+editText_pass.getEditText().getText().toString();

                            url_informations = UrlComm.url_host+url_informations+email+pass;

                            Log.d("TAG_DEPLOY", " app : "+url_informations);

                            userJson jsonUser = new userJson(url_informations, main);

                            dialog = ProgressDialog.show(main, "", "Traitement de données. Veulliez attendre ...", true);
                        }
                    }
                });

                Button_registre.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Toast.makeText(main, "Veuillez contactez l'administrateur", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }else {
            setContentView(R.layout.not_connected);

            Button btn_rafraichir = (Button) findViewById(R.id.btn_rafraichir);

            btn_rafraichir.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    main.finish();
                    startActivity(main.getIntent());
                }
            });
        }

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

    public static void OnSuccesLogin()
    {
        dialog.dismiss();

        Toast.makeText(main, "Connexion faite avec succés !! ", Toast.LENGTH_SHORT).show();

        if(userRepository.main_user.type == 100){
        Intent intent = new Intent(main, HomeActivity.class);
        main.startActivity(intent);
        }else
            {
                Intent intent = new Intent(main, AdminActivity.class);
                main.startActivity(intent);
            }

        SharedPreferences prefs = main.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit= prefs.edit();

        edit.putInt("id", userRepository.main_user.id);
        edit.putString("nom", userRepository.main_user.nom);
        edit.putString("prenom", userRepository.main_user.prenom);
        edit.putString("tel", userRepository.main_user.tel);
        edit.putString("email", userRepository.main_user.email);
        edit.putInt("type", userRepository.main_user.type);


        edit.commit();
        main.finish();

    }

    public static void OnFailedLogin()
    {
        dialog.dismiss();
        Toast.makeText(main, " Email ou mot de pass incorrect !", Toast.LENGTH_SHORT).show();
    }

    public boolean isSharedPreferences()
    {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int id = prefs.getInt("id", 0);
        String email = prefs.getString("email", "vide");
        String nom = prefs.getString("nom", "vide");
        String prenom = prefs.getString("prenom", "vide");
        String tel = prefs.getString("tel", "vide");
        int type = prefs.getInt("type", 0);

        userRepository.main_user.id = id;
        userRepository.main_user.nom = nom;
        userRepository.main_user.prenom = prenom;
        userRepository.main_user.email = email;
        userRepository.main_user.tel = tel;
        userRepository.main_user.type = type;

        if(id == 0 && email.equals("vide"))
        {
            return false;
        }else {
            return true;
        }
    }

    private boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static void quitter()
    {
        SharedPreferences prefs = main.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit= prefs.edit();

        edit.putInt("id", 0);
        edit.putString("nom", "vide");
        edit.putString("prenom", "vide");
        edit.putString("email", "vide");
        edit.putString("tel", "vide");
        edit.putInt("type", 0);

        edit.commit();
    }
}
