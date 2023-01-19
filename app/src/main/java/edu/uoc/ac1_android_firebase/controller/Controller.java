package edu.uoc.ac1_android_firebase.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import edu.uoc.ac1_android_firebase.dao.FireStoreDB;
import edu.uoc.ac1_android_firebase.model.Stadistics;
import edu.uoc.ac1_android_firebase.model.User;
import edu.uoc.ac1_android_firebase.utils.Constants;
import edu.uoc.ac1_android_firebase.utils.Provider;
import edu.uoc.ac1_android_firebase.view.AhorcadoActivity;
import edu.uoc.ac1_android_firebase.view.HomeActivity;
import edu.uoc.ac1_android_firebase.view.MainActivity;
import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.view.LoginActivity;
import edu.uoc.ac1_android_firebase.view.ParaulogicActivity;
import edu.uoc.ac1_android_firebase.view.ProfileActivity;
import edu.uoc.ac1_android_firebase.view.StadisticsActivity;
import edu.uoc.ac1_android_firebase.view.ViewActivity;

public class Controller implements ControllerInterface {

    //Todas las activities como variables globales
    private MainActivity mainActivity;
    private LoginActivity loginActivity;
    private HomeActivity homeActivity;
    private ProfileActivity profileActivity;
    private AhorcadoActivity ahorcadoActivity;
    private ParaulogicActivity paraulogicActivity;
    private StadisticsActivity stadisticsActivity;

    private FireStoreDB persistencia;
    private edu.uoc.ac1_android_firebase.dao.SharedPreferences sharedPreferences;

    private AhorcadoController ahorcadoController;
    private ParaulogicController paraulogicController;
    private ProfileController profileController;
    private StadisticsController stadisticsController;
    private LoginController loginController;

    private String email;

    //SIngleton
    private static Controller controller;
    public static Controller getInstance() {
        if (controller == null) controller = new Controller();
        return controller;
    }

    //instanciamos todos los activities para prevenir nullPointers
    public Controller () {
        this.loginActivity = new LoginActivity();
        this.homeActivity = new HomeActivity();
        this.profileActivity = new ProfileActivity();
        this.ahorcadoActivity = new AhorcadoActivity();
        this.paraulogicActivity = new ParaulogicActivity();
        this.stadisticsActivity = new StadisticsActivity();

        this.sharedPreferences = new edu.uoc.ac1_android_firebase.dao.SharedPreferences();
    }

    public void mainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.mainActivity.createAllItemsAsGlobalWithGetters();
        switchActivity(this.mainActivity, this.loginActivity);
    }

    public void loginActivity(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        this.loginActivity.createAllItemsAsGlobalWithGetters();
        if ((this.persistencia = this.sharedPreferences.checkSession(this.loginActivity)) != null)
            switchActivity(this.loginActivity, this.homeActivity);
        this.loginController = new LoginController(this.loginActivity, this.persistencia, this.sharedPreferences, this.homeActivity);
        this.loginController.createActivityButtons();
    }

    public void homeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        this.homeActivity.createAllItemsAsGlobalWithGetters();
        createActivityButtons();
    }

    public void profileActivity(ProfileActivity profileActivity) {
        this.profileActivity = profileActivity;
        this.profileActivity.createAllItemsAsGlobalWithGetters();
        this.profileController = new ProfileController(this.profileActivity, this.persistencia);
        this.profileController.createActivityButtons();
    }

    public void ahorcadoActivity(AhorcadoActivity ahorcadoActivity) {
        this.ahorcadoActivity = ahorcadoActivity;
        this.ahorcadoActivity.createAllItemsAsGlobalWithGetters();
        this.ahorcadoController = new AhorcadoController(this.ahorcadoActivity, this.persistencia);
        this.ahorcadoController.createActivityButtons();
    }

    public void paraulogicActivity(ParaulogicActivity paraulogicActivity) {
        this.paraulogicActivity = paraulogicActivity;
        this.paraulogicActivity.createAllItemsAsGlobalWithGetters();
        this.paraulogicController = new ParaulogicController(this.paraulogicActivity, this.persistencia);
        this.paraulogicController.createActivityButtons();
    }

    public void stadisticsActivity(StadisticsActivity stadisticsActivity) {
        this.stadisticsActivity = stadisticsActivity;
        this.stadisticsActivity.createAllItemsAsGlobalWithGetters();
        this.stadisticsController = new StadisticsController(this.stadisticsActivity, this.persistencia);
        this.stadisticsController.createActivityButtons();
    }

    public void logginWithGoogle(Intent data) {
        this.loginController.logginWithGoogle(data);
    }

    @Override
    public void createActivityButtons() {
        this.homeActivity.getLogoutButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.clearSession(homeActivity);
                switchActivity(homeActivity, mainActivity);
            }
        });

        this.homeActivity.getAhorcadoButton().setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivity(homeActivity, ahorcadoActivity);
                if (ahorcadoActivity.getProgressBar() != null)
                    ahorcadoActivity.getProgressBar().setVisibility(View.VISIBLE);
                persistencia.get(Constants.AHORCADO_COLLECTION)
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (!documentSnapshot.exists()) {
                                    ahorcadoController.createAhorcado();
                                } else {
                                    ahorcadoController.setAhorcadopartida(ahorcadoController.getAhorcadoFromDocumentSnapshot(documentSnapshot));
                                }
                                ahorcadoActivity.getProgressBar().setVisibility(View.GONE);
                            }
                        });
            }
        }));

        this.homeActivity.getParaulogicButton().setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivity(homeActivity, paraulogicActivity);
                if (paraulogicActivity.getProgressBar() != null)
                    paraulogicActivity.getProgressBar().setVisibility(View.VISIBLE);
                persistencia.get(Constants.PARAULOGIC_COLLECTION)
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (!documentSnapshot.exists()) {
                                    paraulogicController.createNewParaulogic();
                                } else {
                                    paraulogicController.setParaulogicPartida(paraulogicController.getParaulogicFromDocumentSnapshot(documentSnapshot));
                                }
                                paraulogicActivity.getProgressBar().setVisibility(View.GONE);
                            }
                        });
            }
        }));

        this.homeActivity.getProfileButton().setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task<DocumentSnapshot> user = persistencia.get(Constants.USER_COLLECTION);
                user.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = new User(
                                loginActivity.getEmail(),
                                Provider.valueOf(documentSnapshot.get(Constants.PROVIDER).toString()),
                                documentSnapshot.get(Constants.NAME) != null ?
                                        documentSnapshot.get(Constants.NAME).toString() : ""
                        );
                        switchActivity(homeActivity, profileActivity, Constants.USER_COLLECTION, user);
                    }
                });

            }
        }));

        this.homeActivity.getEstadisticasButton().setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task<DocumentSnapshot> user = persistencia.get(Constants.STADISTICS_COLLECTION);
                user.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Stadistics stadistics = new Stadistics(
                                ifNullInt(documentSnapshot.get(Constants.PAR_PARAULOGIC)),
                                ifNullInt(documentSnapshot.get(Constants.PAR_AHORCADO)),
                                ifNullInt(documentSnapshot.get(Constants.INICIS_SESSIO)),
                                ifNullString(documentSnapshot.get(Constants.ULTIM_INICI))
                        );
                        switchActivity(homeActivity, stadisticsActivity, Constants.STADISTICS_COLLECTION, stadistics);
                    }
                });

            }
        }));
    }
}
