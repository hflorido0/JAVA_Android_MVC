package edu.uoc.ac1_android_firebase.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.controller.Controller;

public class ParaulogicActivity extends AppCompatActivity implements ViewActivity{

    TextView paraulogicWrods;
    TextView paraulogicSecretWord;
    EditText paraulogicInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.uoc.ac1_android_firebase.R.layout.activity_paraulogic);
    }

    @Override
    public void callControllerWithThisActivityAsParameter() {
        Controller.getInstance().paraulogicActivity(this);
    }

    @Override
    public void createAllItemsAsGlobalWithGetters() {
        this.paraulogicWrods = (TextView) findViewById(R.id.idParaulesAhorcado);
        this.paraulogicSecretWord = (TextView) findViewById(R.id.idAhorcadoSecreto);
        this.paraulogicInput = (EditText) findViewById(R.id.idPraulaAhorcado);
    }

    public TextView getParaulogicWrods() {
        return paraulogicWrods;
    }

    public TextView getParaulogicSecretWord() {
        return paraulogicSecretWord;
    }

    public EditText getParaulogicInput() {
        return paraulogicInput;
    }
}