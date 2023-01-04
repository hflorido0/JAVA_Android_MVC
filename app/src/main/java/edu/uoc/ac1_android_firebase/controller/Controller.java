package edu.uoc.ac1_android_firebase.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import edu.uoc.ac1_android_firebase.model.Providers;
import edu.uoc.ac1_android_firebase.view.HomeActivity;
import edu.uoc.ac1_android_firebase.view.MainActivity;
import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.view.LoginActivity;

public class Controller {

    private MainActivity mainActivity;
    private LoginActivity loginActivity;
    private HomeActivity homeActivity;
    private static Controller controller;

    public static Controller getInstance() {
        if (controller == null) controller = new Controller();
        return controller;
    }

    //instanciamos todos los activities para prevenir nullPointers
    public Controller () {
        this.loginActivity = new LoginActivity();
        this.homeActivity = new HomeActivity();
    }

    public void mainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.mainActivity.createAllItemsAsGlobalWithGetters();
        switchActivity(this.mainActivity, this.loginActivity);
    }

    public void loginActivity(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        this.loginActivity.createAllItemsAsGlobalWithGetters();
        if (!checkSession())
            setLoginActivityButtons();
    }

    public void homeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        this.homeActivity.createAllItemsAsGlobalWithGetters();
        setHomeActivityButtons();
    }

    private void setHomeActivityButtons() {
        this.homeActivity.getLogoutButon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSession();
            }
        });
    }

    private boolean checkSession () {
        SharedPreferences prefs = this.loginActivity.getSharedPreferences(
                this.loginActivity.getString(R.string.prefs_files), Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);
        String provider = prefs.getString("provider", null);
        if (email != null && provider != null) {
            switchActivity(this.loginActivity, this.homeActivity);
            return true;
        }
        return false;
    }

    private void setLoginActivityButtons() {
        this.loginActivity.getGoogleButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSession();
            }
        });
    }

    private void saveSession() {

        // Guardar datos de sesión
        SharedPreferences.Editor prefs = this.loginActivity.getSharedPreferences(
                this.loginActivity.getString(R.string.prefs_files), Context.MODE_PRIVATE).edit();
        prefs.putString("email",this.loginActivity.getEmail().getText().toString());
        prefs.putString("provider", Providers.GOOGLE.toString());
        prefs.apply();
        switchActivity(this.loginActivity, this.homeActivity);
    }

    private void clearSession () {

        // Borrar datos de sesión
        SharedPreferences.Editor prefs = this.loginActivity.getSharedPreferences(
                this.loginActivity.getString(R.string.prefs_files), Context.MODE_PRIVATE).edit();
        prefs.clear();
        prefs.apply();
        switchActivity(this.homeActivity, this.mainActivity);
    }

    private void switchActivity(Activity from, Activity to) {
        Intent intent = new Intent(from, to.getClass());
        from.startActivity(intent);
    }
}
