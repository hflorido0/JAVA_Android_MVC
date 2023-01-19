package edu.uoc.ac1_android_firebase.controller;

import android.content.Intent;
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

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.dao.FireStoreDB;
import edu.uoc.ac1_android_firebase.dao.SharedPreferences;
import edu.uoc.ac1_android_firebase.utils.Constants;
import edu.uoc.ac1_android_firebase.utils.Provider;
import edu.uoc.ac1_android_firebase.view.HomeActivity;
import edu.uoc.ac1_android_firebase.view.LoginActivity;

public class LoginController implements ControllerInterface {

    private LoginActivity loginActivity;
    private FireStoreDB persistencia;
    private SharedPreferences sharedPreferences;
    private HomeActivity homeActivity;

    public LoginController(LoginActivity loginActivity, FireStoreDB persistencia, SharedPreferences sharedPreferences,
                           HomeActivity homeActivity) {
        this.loginActivity = loginActivity;
        this.persistencia = persistencia;
        this.sharedPreferences = sharedPreferences;
        this.homeActivity = homeActivity;
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
                            loginActivity.setEmail(account.getEmail());
                            if (persistencia == null)
                                persistencia = new FireStoreDB(FirebaseFirestore.getInstance(), account.getEmail());
                            sharedPreferences.saveSession(Provider.GOOGLE.toString(), loginActivity, account.getEmail());
                            switchActivity(loginActivity, homeActivity);
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

    public void createActivityButtons() {
        this.loginActivity.getGoogleButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creamos las configuraciones de google
                GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(loginActivity.getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                //Creamos el agente de inicio de signin
                GoogleSignInClient googleCliet = GoogleSignIn.getClient(loginActivity, googleConf);

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
                                sharedPreferences.saveSession(Provider.LOGIN.toString(), loginActivity, email);
                                switchActivity(loginActivity, homeActivity);
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
                                sharedPreferences.saveSession(Provider.LOGIN.toString(), loginActivity, email);
                                switchActivity(loginActivity, homeActivity);
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
}
