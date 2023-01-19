package edu.uoc.ac1_android_firebase.controller;

import android.view.View;

import java.util.HashMap;

import edu.uoc.ac1_android_firebase.dao.FireStoreDB;
import edu.uoc.ac1_android_firebase.utils.Constants;
import edu.uoc.ac1_android_firebase.view.ProfileActivity;

public class ProfileController implements ControllerInterface {

    private ProfileActivity profileActivity;
    private FireStoreDB persistencia;

    public ProfileController(ProfileActivity profileActivity, FireStoreDB persistencia) {
        this.profileActivity = profileActivity;
        this.persistencia = persistencia;
    }

    public void createActivityButtons() {
        this.profileActivity.getGuardar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> values = new HashMap<>();
                values.put(Constants.NAME, profileActivity.getName());

                persistencia.update(Constants.USER_COLLECTION, values);

                showAlert(profileActivity, "GUARDADO", "BBDD");
            }
        });
    }
}
