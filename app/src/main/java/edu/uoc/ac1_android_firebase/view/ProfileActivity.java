package edu.uoc.ac1_android_firebase.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.controller.Controller;
import edu.uoc.ac1_android_firebase.model.User;
import edu.uoc.ac1_android_firebase.utils.Constants;

public class ProfileActivity extends AppCompatActivity implements ViewActivity{

    Button guardar;
    EditText name;
    TextView provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.uoc.ac1_android_firebase.R.layout.activity_profile);

        if(getIntent().getExtras() != null) {
            User user = (User) getIntent().getSerializableExtra(Constants.USER_COLLECTION);
            ((EditText) findViewById(R.id.idName)).setText(user.getName());
        }

        callControllerWithThisActivityAsParameter();
    }

    @Override
    public void callControllerWithThisActivityAsParameter() {
        Controller.getInstance().profileActivity(this);
    }

    @Override
    public void createAllItemsAsGlobalWithGetters() {
        this.guardar = (Button) findViewById(R.id.idGuardar);
        this.name = (EditText) findViewById(R.id.idName);
        this.provider = (TextView) findViewById(R.id.idProvider);
    }

    public Button getGuardar() {
        return guardar;
    }

    public String getName() {
        return name.getText().toString();
    }

    public String getProvider() {
        return this.provider.getText().toString();
    }
}