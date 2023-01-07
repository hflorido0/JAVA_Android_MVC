package edu.uoc.ac1_android_firebase.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.controller.Controller;

public class MainActivity extends AppCompatActivity implements ViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setTheme(R.style.Theme_AC1AndroidFirebase);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callControllerWithThisActivityAsParameter();
    }

    @Override
    public void callControllerWithThisActivityAsParameter() {
        Controller.getInstance().mainActivity(this);
    }

    @Override
    public void createAllItemsAsGlobalWithGetters() {
    }
}