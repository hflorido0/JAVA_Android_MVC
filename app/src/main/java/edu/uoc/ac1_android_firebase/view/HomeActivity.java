package edu.uoc.ac1_android_firebase.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.controller.Controller;

public class HomeActivity extends AppCompatActivity  implements ViewActivity{

    private Button logoutButon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        callControllerWithThisActivityAsParameter();
    }

    @Override
    public void callControllerWithThisActivityAsParameter() {
        Controller.getInstance().homeActivity(this);
    }

    @Override
    public void createAllItemsAsGlobalWithGetters() {
        this.logoutButon = (Button) findViewById(R.id.idLogout);
    }

    public Button getLogoutButon() {
        return logoutButon;
    }
}