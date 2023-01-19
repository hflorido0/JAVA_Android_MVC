package edu.uoc.ac1_android_firebase.dao;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.utils.Constants;
import edu.uoc.ac1_android_firebase.view.LoginActivity;

public class SharedPreferences {

    public FireStoreDB checkSession (LoginActivity activity) {
        android.content.SharedPreferences prefs = activity.getSharedPreferences(
                activity.getString(R.string.prefs_files), Context.MODE_PRIVATE);
        String email = prefs.getString(Constants.EMAIL, null);
        String provider = prefs.getString(Constants.PROVIDER, null);

        if (email != null && provider != null) {
            FireStoreDB persistencia = new FireStoreDB(FirebaseFirestore.getInstance(), email);

            persistencia.incrementByOne(Constants.STADISTICS_COLLECTION,
                    Constants.INICIS_SESSIO);

            persistencia.setTime(Constants.STADISTICS_COLLECTION,
                    Constants.ULTIM_INICI);

            return persistencia;
        }
        return null;
    }

    public FireStoreDB saveSession(String provider, LoginActivity loginActivity, String email) {

        // Guardar datos de sesión
        android.content.SharedPreferences.Editor prefs = loginActivity.getSharedPreferences(
                loginActivity.getString(R.string.prefs_files), Context.MODE_PRIVATE).edit();
        prefs.putString(Constants.EMAIL,email);
        prefs.putString(Constants.PROVIDER, provider);
        prefs.apply();

        HashMap<String, Object> values = new HashMap<>();
        values.put(Constants.PROVIDER, provider);
        FireStoreDB persistencia = new FireStoreDB(FirebaseFirestore.getInstance(), email);
        persistencia.update(Constants.USER_COLLECTION, values);

        return persistencia;

    }

    public void clearSession (Activity loginActivity) {

        // Borrar datos de sesión
        android.content.SharedPreferences.Editor prefs = loginActivity.getSharedPreferences(
                loginActivity.getString(R.string.prefs_files), Context.MODE_PRIVATE).edit();
        prefs.clear();
        prefs.apply();

        FirebaseAuth.getInstance().signOut();
    }
}
