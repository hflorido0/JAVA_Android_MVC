package edu.uoc.ac1_android_firebase.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.controller.Controller;

public class LoginActivity extends AppCompatActivity implements ViewActivity{

    private Button googleButton;
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
        this.email = (EditText) findViewById(R.id.idEmail);
        this.password = (EditText) findViewById(R.id.idPass);
    }
}