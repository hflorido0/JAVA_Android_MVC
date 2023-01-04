package edu.uoc.ac1_android_firebase.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.controller.Controller;

public class MainActivity extends AppCompatActivity implements ViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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