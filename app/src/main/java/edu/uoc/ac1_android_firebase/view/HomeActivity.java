package edu.uoc.ac1_android_firebase.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
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
        this.logoutButton = (RelativeLayout) findViewById(R.id.idLogout);
        this.profileButton = (RelativeLayout) findViewById(R.id.idProfile);
        this.ahorcadoButton = (RelativeLayout) findViewById(R.id.idHangOut);
        this.paraulogicButton = (RelativeLayout) findViewById(R.id.idParaulogic);
        this.estadisticasButton = (RelativeLayout) findViewById(R.id.idStadistics);
        this.otrosButton = (RelativeLayout) findViewById(R.id.idOtros);
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