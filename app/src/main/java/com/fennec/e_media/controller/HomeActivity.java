package com.fennec.e_media.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.fennec.e_media.R;
import com.fennec.e_media.entity.user;
import com.fennec.e_media.repository.userRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static String MY_PREFS_NAME = "first_log";

    public static HomeActivity main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        main = this;

        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                quitter();
                onDestroy();
                /*Snackbar.make(view, "Developer par : KHLOUF WALID", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        String scanContent="";
        String scanFormat="";

        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                scanContent = scanningResult.getContents().toString();
                scanFormat = scanningResult.getFormatName().toString();
            }

            Toast.makeText(this,scanContent+"   type:"+scanFormat,Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,"Nothing scanned",Toast.LENGTH_SHORT).show();
        }
    }

    public static void to_newIntent(int id_app)
    {
        Intent intent = new Intent(main, InfosActivity.class);
        intent.putExtra("id_app",id_app);
        main.startActivity(intent);
    }

    public static void quitter()
    {
        //clear var client
        userRepository.main_user = new user();

        SharedPreferences prefs = main.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit= prefs.edit();

        edit.putInt("id", 0);
        edit.putString("nom", "vide");
        edit.putString("prenom", "vide");
        edit.putString("email", "vide");
        edit.putString("tel", "vide");
        edit.putInt("type", 0);

        edit.commit();
        main.finish();
        System.exit(0);
    }

    @Override
    protected void onDestroy()
    {
        quitter();
        super.onDestroy();
    }
}
