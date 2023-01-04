package edu.uoc.ac1_android_firebase.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.controller.Controller;

public class MainActivity extends AppCompatActivity {

    private Button googleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (new Controller(this)).init();

    }

    public Button getGoogleButton() {
        return googleButton;
    }
}