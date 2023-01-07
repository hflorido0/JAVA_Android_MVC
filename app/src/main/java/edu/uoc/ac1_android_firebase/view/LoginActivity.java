package edu.uoc.ac1_android_firebase.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.controller.Controller;
import edu.uoc.ac1_android_firebase.utils.Constants;

public class LoginActivity extends AppCompatActivity implements ViewActivity{

    private Button googleButton;
    private Button signinButton;
    private Button registerButton;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callControllerWithThisActivityAsParameter();
    }

    public Button getGoogleButton() {
        return googleButton;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public Button getSigninButton() {
        return signinButton;
    }

    public EditText getEmail() {
        return email;
    }

    public EditText getPassword() {
        return password;
    }

    @Override
    public void callControllerWithThisActivityAsParameter() {
        Controller.getInstance().loginActivity(this);
    }

    @Override
    public void createAllItemsAsGlobalWithGetters() {
        this.googleButton = (Button) findViewById(R.id.googleButton);
        this.signinButton = (Button) findViewById(R.id.idLoginButton);
        this.registerButton = (Button) findViewById(R.id.idRegistrar);
        this.email = (EditText) findViewById(R.id.idEmail);
        this.password = (EditText) findViewById(R.id.idPass);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.GOOGLE_SIGN_IN) {
            Controller.getInstance().logginWithGoogle(data);
        }
    }
}