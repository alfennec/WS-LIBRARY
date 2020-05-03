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
import com.fennec.e_media.apiComm.AllUserJson;
import com.fennec.e_media.apiComm.UrlComm;
import com.fennec.e_media.apiComm.deleteUser;
import com.fennec.e_media.entity.user;
import com.fennec.e_media.repository.userRepository;
import com.google.android.material.textfield.TextInputLayout;

public class AddUserctivity extends AppCompatActivity {

    public Button button_valide_form;

    public static AddUserctivity main;

    public static Boolean all_Right ;

    public user new_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_userctivity);

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

                TextInputLayout editText_pass1 = (TextInputLayout) findViewById(R.id.input_pass);
                TextInputLayout editText_pass2 = (TextInputLayout) findViewById(R.id.input_pass2);

                String pass1 = editText_pass1.getEditText().getText().toString();
                String pass2 = editText_pass2.getEditText().getText().toString();

                TextInputLayout editText_nom = (TextInputLayout) findViewById(R.id.input_nom);
                TextInputLayout editText_prenom = (TextInputLayout) findViewById(R.id.input_prenom);
                TextInputLayout editText_tel = (TextInputLayout) findViewById(R.id.input_tel);
                TextInputLayout editText_email = (TextInputLayout) findViewById(R.id.input_email);

                RadioButton radioButton_homme = (RadioButton) findViewById(R.id.radioButton_homme);

                if(verifyIfBlank(editText_nom)
                        && verifyIfBlank(editText_prenom)
                        && verifyIfBlank(editText_tel)
                        && verifyIfBlank(editText_email)
                        && verifyIfBlank(editText_pass1)
                        && verifyIfBlank(editText_pass2))
                {
                    all_Right = true;
                }else
                {
                    all_Right = false;
                }

                if(all_Right)
                {
                    if(pass1.equals(pass2))
                    {
                        int RadioGroupe;

                        if(radioButton_homme.isChecked())
                        {
                            RadioGroupe = 200;
                        }else
                        {
                            RadioGroupe = 100;
                        }

                        new_user = new user(
                                editText_nom.getEditText().getText().toString(),
                                editText_prenom.getEditText().getText().toString(),
                                editText_tel.getEditText().getText().toString(),
                                editText_email.getEditText().getText().toString(),
                                editText_pass1.getEditText().getText().toString(),
                                RadioGroupe
                        );

                        userRepository.list_user.add(new_user);

                        ///livraison/json/setClient.php?email=bermod@gmail.com&pass=123456&nom=med&prenom=ber&tel=0611336605&adresse=haylaymoune&ville=oujda&sexe=1

                        String url_informations = "user?";
                        String nom              = "nom="+new_user.nom;
                        String prenom           = "&prenom="+new_user.prenom;
                        String tel              = "&tel="+new_user.tel;
                        String email            = "&email="+new_user.email;
                        String pass             = "&password="+new_user.passord;
                        String type             = "&type="+new_user.type;

                        url_informations = url_informations+nom+prenom+tel+email+pass+type;

                        Log.e("COMPILE E3", "onClick: "+url_informations);

                        deleteUser jsonRegister = new deleteUser(UrlComm.url_host+url_informations, "post", main);

                        Toast.makeText(main, "Membre ajouter avec succer", Toast.LENGTH_SHORT);

                        MembreActivity.updateRecycle();
                        main.finish();

                    }else
                    {
                        //textView_msg.setText("Retapez votre mot de passe");
                        Toast.makeText(main, "Retapez votre mot de passe", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(main, "Veuillez saisir tout les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void OnSuccesRegistre()
    {
       //dialog.dismiss();

        //Costum_toast("Inscription faite avec succes");

    }

    public static void OnFailedRegistre()
    {
        //dialog.dismiss();

        //Costum_toast("Erreur veuillez resaisir vos données");
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
