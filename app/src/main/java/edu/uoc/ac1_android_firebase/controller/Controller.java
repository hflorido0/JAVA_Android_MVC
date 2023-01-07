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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.uoc.ac1_android_firebase.model.User;
import edu.uoc.ac1_android_firebase.utils.Provider;
import edu.uoc.ac1_android_firebase.utils.Constants;
import edu.uoc.ac1_android_firebase.view.HomeActivity;
import edu.uoc.ac1_android_firebase.view.MainActivity;
import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.view.LoginActivity;

public class Controller {
    //Todas las activities como variables globales
    private MainActivity mainActivity;
    private LoginActivity loginActivity;
    private HomeActivity homeActivity;

    private FirebaseFirestore firebase;

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
        this.firebase = FirebaseFirestore.getInstance();
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

    public void returnCollectedData(User user) {
        //TODO:
    }

    private void setHomeActivityButtons() {
        this.homeActivity.getLogoutButton().setOnClickListener(new View.OnClickListener() {
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

                String email = loginActivity.getEmail().getText().toString();
                String password = loginActivity.getPassword().getText().toString();

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

                String email = loginActivity.getEmail().getText().toString();
                String password = loginActivity.getPassword().getText().toString();

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
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle("ERROR")
                .setMessage(message)
                .setPositiveButton("Aceptar", null)
                .create();
        dialog.show();
    }

    private void saveSession(String provider) {

        // Guardar datos de sesión
        SharedPreferences.Editor prefs = this.loginActivity.getSharedPreferences(
                this.loginActivity.getString(R.string.prefs_files), Context.MODE_PRIVATE).edit();
        prefs.putString("email",this.loginActivity.getEmail().getText().toString());
        prefs.putString("provider", provider);
        prefs.apply();
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

    public void switchActivity(Activity from, Activity to) {
        Intent intent = new Intent(from, to.getClass());
        from.startActivity(intent);
    }
}
