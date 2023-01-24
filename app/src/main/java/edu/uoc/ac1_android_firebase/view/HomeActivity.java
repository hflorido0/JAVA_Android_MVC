package edu.uoc.ac1_android_firebase.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.controller.Controller;

public class HomeActivity extends AppCompatActivity  implements ViewActivity{

    private RelativeLayout logoutButton;
    private RelativeLayout profileButton;
    private RelativeLayout ahorcadoButton;
    private RelativeLayout paraulogicButton;
    private RelativeLayout estadisticasButton;
    private RelativeLayout otrosButton;

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
        //TODO: fill all buttons into global variables
    }

    public RelativeLayout getLogoutButton() {
        return logoutButton;
    }

    public RelativeLayout getAhorcadoButton() {
        return ahorcadoButton;
    }

    public RelativeLayout getEstadisticasButton() {
        return estadisticasButton;
    }

    public RelativeLayout getProfileButton() {
        return profileButton;
    }

    public RelativeLayout getParaulogicButton() {
        return paraulogicButton;
    }

    public RelativeLayout getOtrosButton() {
        return otrosButton;
    }
}