package edu.uoc.ac1_android_firebase.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.controller.Controller;

public class AhorcadoActivity extends AppCompatActivity implements ViewActivity{

    TextView ahorcadoWrods;
    EditText ahorcadoInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.uoc.ac1_android_firebase.R.layout.activity_ahorcado);
    }

    @Override
    public void callControllerWithThisActivityAsParameter() {
        Controller.getInstance().ahorcadoActivity(this);
    }

    @Override
    public void createAllItemsAsGlobalWithGetters() {
        this.ahorcadoWrods = (TextView) findViewById(R.id.idParaulesParaulogic);
        this.ahorcadoInput = (EditText) findViewById(R.id.idPraulaParaulogic);
    }

    public TextView getAhorcadoWrods() {
        return ahorcadoWrods;
    }

    public EditText getAhorcadoInput() {
        return ahorcadoInput;
    }
}