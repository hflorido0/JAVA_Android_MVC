package edu.uoc.ac1_android_firebase.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import edu.uoc.ac1_android_firebase.dao.Persistencia;
import edu.uoc.ac1_android_firebase.model.Paraulogic;
import edu.uoc.ac1_android_firebase.model.User;
import edu.uoc.ac1_android_firebase.utils.Provider;
import edu.uoc.ac1_android_firebase.utils.Constants;
import edu.uoc.ac1_android_firebase.view.AhorcadoActivity;
import edu.uoc.ac1_android_firebase.view.HomeActivity;
import edu.uoc.ac1_android_firebase.view.MainActivity;
import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.view.LoginActivity;
import edu.uoc.ac1_android_firebase.view.ParaulogicActivity;
import edu.uoc.ac1_android_firebase.view.ProfileActivity;
import edu.uoc.ac1_android_firebase.view.StadisticsActivity;

public class Controller {
    //Todas las activities como variables globales
    private MainActivity mainActivity;
    private LoginActivity loginActivity;
    private HomeActivity homeActivity;
    private ProfileActivity profileActivity;
    private AhorcadoActivity ahorcadoActivity;
    private ParaulogicActivity paraulogicActivity;
    private StadisticsActivity stadisticsActivity;

    private Persistencia persistencia;

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
        this.persistencia = new Persistencia(FirebaseFirestore.getInstance());
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

    public void profileActivity(ProfileActivity profileActivity) {
        this.profileActivity = profileActivity;
        this.profileActivity.createAllItemsAsGlobalWithGetters();
        setProfileActivityButtons();
    }

    public void ahorcadoActivity(AhorcadoActivity ahorcadoActivity) {
        this.ahorcadoActivity = ahorcadoActivity;
        this.ahorcadoActivity.createAllItemsAsGlobalWithGetters();
        setAhorcadoActivityButtons();
    }

    public void paraulogicActivity(ParaulogicActivity paraulogicActivity) {
        this.paraulogicActivity = paraulogicActivity;
        this.paraulogicActivity.createAllItemsAsGlobalWithGetters();
        setParaulogicActivityButtons();
    }

    public void stadisticsActivity(StadisticsActivity stadisticsActivity) {
        this.stadisticsActivity = stadisticsActivity;
        this.stadisticsActivity.createAllItemsAsGlobalWithGetters();
        setStadisticsActivityButtons();
    }

    private void setStadisticsActivityButtons() {
        //TODO:
    }

    private void setParaulogicActivityButtons() {
        //TODO:
    }

    private Paraulogic getParaulogicFromDocumentSnapshot(DocumentSnapshot documentSnapshot) {
        return new Paraulogic((List<String>) documentSnapshot.get(Constants.RESPUESTAS),
                (List<String>) documentSnapshot.get(Constants.SOLUTIONS),
                (int)((long) documentSnapshot.get(Constants.PARAULOGIC)),
                (int)((long) documentSnapshot.get(Constants.P_GANADAS)));
    }

    private void setAhorcadoActivityButtons() {
        //TODO:
    }

    private void setProfileActivityButtons() {
        this.profileActivity.getGuardar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> values = new HashMap<>();
                values.put(Constants.NAME, profileActivity.getName());

                persistencia.update(loginActivity.getEmail(), Constants.USER_COLLECTION, values);

                showAlert(profileActivity, "GUARDADO", "BBDD");
            }
        });
    }

    public void logginWithGoogle(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account != null) {
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveSession(Provider.GOOGLE.toString());
                        } else {
                            showAlert(loginActivity, "Error en el login");
                        }
                    }
                });
            }
        } catch (ApiException e) {
            e.printStackTrace();
            showAlert(loginActivity, "Error en el login");
        }
    }

    private void createNewParaulogic() {
        Random random = new Random();
        int rand = random.nextInt(Constants.drawables.length-1);
        int randomDrawable = Constants.drawables[rand];
        String[] paraulogic = Constants.paraulogics[rand];

        HashMap<String, Object> values = new HashMap<>();
        values.put(Constants.SOLUTIONS, Arrays.asList(paraulogic));
        values.put(Constants.RESPUESTAS, Arrays.asList());
        values.put(Constants.P_GANADAS, 0);
        values.put(Constants.PARAULOGIC, randomDrawable);

        persistencia.save(loginActivity.getEmail(), Constants.PARAULOGIC_COLLECTION, values);

        Paraulogic para = new Paraulogic(Arrays.asList(),Arrays.asList(paraulogic), randomDrawable, 0);

        setParaulogicPartida(para);
    }

    private void setParaulogicPartida(Paraulogic para) {
        paraulogicActivity.getParaulogic()
                .setImageDrawable(loginActivity.getResources().getDrawable(para.getImg()));
        paraulogicActivity.getParaulogicWrods().setText(para.getRespuestas().toString());
    }

    private void setHomeActivityButtons() {
        this.homeActivity.getLogoutButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSession();
            }
        });

        this.homeActivity.getAhorcadoButton().setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivity(homeActivity, ahorcadoActivity);
            }
        }));

        this.homeActivity.getParaulogicButton().setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivity(homeActivity, paraulogicActivity);
                if (paraulogicActivity.getProgressBar() != null)
                    paraulogicActivity.getProgressBar().setVisibility(View.VISIBLE);
                persistencia.get(loginActivity.getEmail(), Constants.PARAULOGIC_COLLECTION).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.get(Constants.P_GANADAS) == null) {
                            createNewParaulogic();
                        } else {
                            setParaulogicPartida(getParaulogicFromDocumentSnapshot(documentSnapshot));
                        }
                        paraulogicActivity.getProgressBar().setVisibility(View.GONE);
                    }
                });
            }
        }));

        this.homeActivity.getProfileButton().setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task<DocumentSnapshot> user = persistencia.get(loginActivity.getEmail(), Constants.USER_COLLECTION);
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
                switchActivity(homeActivity, stadisticsActivity);
            }
        }));
    }

    private boolean checkSession () {
        SharedPreferences prefs = this.loginActivity.getSharedPreferences(
                this.loginActivity.getString(R.string.prefs_files), Context.MODE_PRIVATE);
        String email = prefs.getString(Constants.EMAIL, null);
        String provider = prefs.getString(Constants.PROVIDER, null);
        if (email != null && provider != null) {
            loginActivity.setEmail(email);
            switchActivity(this.loginActivity, this.homeActivity);
            return true;
        }
        return false;
    }

    private void setLoginActivityButtons() {
        this.loginActivity.getGoogleButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creamos las configuraciones de google
                GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(loginActivity.getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                //Creamos el agente de inicio de signin
                GoogleSignInClient googleCliet = GoogleSignIn.getClient(loginActivity,googleConf);

                //cerramos el cliente
                googleCliet.signOut();

                //identificador para coger la respuesta de la petición (el que queramos).
                loginActivity.startActivityForResult(googleCliet.getSignInIntent(), Constants.GOOGLE_SIGN_IN);
            }
        });

        this.loginActivity.getRegisterButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = loginActivity.getEmail();
                String password = loginActivity.getPassword();

                if (!email.isEmpty() && !password.isEmpty()) {

                    //creamos una instancia con usuario y contraseña
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                saveSession(Provider.LOGIN.toString());
                            } else {
                                showAlert(loginActivity, task.getException().getMessage());
                            }
                        }
                    });
                } else {
                    showAlert(loginActivity, "Error en el login");
                }
            }
        });

        this.loginActivity.getSigninButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = loginActivity.getEmail();
                String password = loginActivity.getPassword();

                if (!email.isEmpty() && !password.isEmpty()) {

                    //creamos una instancia con usuario y contraseña
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                saveSession(Provider.LOGIN.toString());
                            } else {
                                showAlert(loginActivity, "Error en el login");
                            }
                        }
                    });
                } else {
                    showAlert(loginActivity, "Error en el login");
                }
            }
        });
    }

    private void showAlert(Activity activity) {
        showAlert(activity,"Se ha porducido un error");
    }

    private void showAlert(Activity activity, String message) {
        showAlert(activity, message, "ERROR");
    }

    private void showAlert(Activity activity, String message, String title) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Aceptar", null)
                .create();
        dialog.show();
    }


    private void saveSession(String provider) {

        // Guardar datos de sesión
        SharedPreferences.Editor prefs = this.loginActivity.getSharedPreferences(
                this.loginActivity.getString(R.string.prefs_files), Context.MODE_PRIVATE).edit();
        prefs.putString(Constants.EMAIL,this.loginActivity.getEmail());
        prefs.putString(Constants.PROVIDER, provider);
        prefs.apply();

        HashMap<String, String> values = new HashMap<>();
        values.put(Constants.PROVIDER, provider);

        persistencia.save(loginActivity.getEmail(), Constants.USER_COLLECTION, values);

        switchActivity(this.loginActivity, this.homeActivity);
    }

    private void clearSession () {

        // Borrar datos de sesión
        SharedPreferences.Editor prefs = this.loginActivity.getSharedPreferences(
                this.loginActivity.getString(R.string.prefs_files), Context.MODE_PRIVATE).edit();
        prefs.clear();
        prefs.apply();

        FirebaseAuth.getInstance().signOut();
        switchActivity(this.homeActivity, this.mainActivity);
    }

    private void switchActivity(Activity from, Activity to) {
        switchActivity(from, to, "", null);
    }

    private void switchActivity(Activity from, Activity to, String extraKey, Object extra) {
        Intent intent = new Intent(from, to.getClass());
        if (extra != null) {
            intent.putExtra(extraKey, (Serializable) extra);
        }
        from.startActivity(intent);
    }
}
