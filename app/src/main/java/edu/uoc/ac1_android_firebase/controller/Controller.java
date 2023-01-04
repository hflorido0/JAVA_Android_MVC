package edu.uoc.ac1_android_firebase.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.uoc.ac1_android_firebase.view.MainActivity;
import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.view.LoginActivity;

public class Controller {
    private MainActivity mainActivity;
    private LoginActivity loginActivity;

    public Controller (MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void init () {
        setMainActivityButtons();
        saveSession();
    }

    private void setMainActivityButtons() {
        this.mainActivity.getGoogleButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mainActivity,"Google button clicked", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveSession() {
        Bundle bundle = this.mainActivity.getIntent().getExtras();

        // Guardar datos de sesión
        SharedPreferences.Editor prefs = this.mainActivity.getSharedPreferences(
                this.mainActivity.getString(R.string.prefs_files), Context.MODE_PRIVATE).edit();
        prefs.putString("email",bundle.getString("email"));
        prefs.putString("provider",bundle.getString("provider"));
        prefs.apply();
    }

}
