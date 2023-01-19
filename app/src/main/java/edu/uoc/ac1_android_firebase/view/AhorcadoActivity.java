package edu.uoc.ac1_android_firebase.view;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.controller.Controller;

public class AhorcadoActivity extends AppCompatActivity implements ViewActivity{

    TextView ahorcadoWrods;
    EditText ahorcadoInput;
    TextView ahorcadoSecretWord;
    ImageView ahoracado;
    ProgressBar progressBar;
    Button ahorcadoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.uoc.ac1_android_firebase.R.layout.activity_ahorcado);
        ((ProgressBar) findViewById(R.id.idAhorcadoPB)).setVisibility(View.VISIBLE);
        callControllerWithThisActivityAsParameter();
    }

    @Override
    public void callControllerWithThisActivityAsParameter() {
        Controller.getInstance().ahorcadoActivity(this);
    }

    @Override
    public void createAllItemsAsGlobalWithGetters() {
        this.ahorcadoWrods = (TextView) findViewById(R.id.idParaulesAhorcado);
        this.ahorcadoSecretWord = (TextView) findViewById(R.id.idAhorcadoSecreto);
        this.ahorcadoInput = (EditText) findViewById(R.id.idPraulaAhorcado);
        this.ahoracado = (ImageView) findViewById(R.id.idAhorcadoImg);
        this.progressBar = (ProgressBar) findViewById(R.id.idAhorcadoPB);
        this.ahorcadoButton = (Button) findViewById(R.id.idAhorcadoButton);
    }

    public TextView getAhorcadoWrods() {
        return ahorcadoWrods;
    }

    public String getAhorcadoInput() {
        return ahorcadoInput.getText().toString();
    }

    public TextView getAhorcadoSecretWord() {
        return ahorcadoSecretWord;
    }

    public ImageView getAhoracado() {
        return ahoracado;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Button getAhorcadoButton() {
        return ahorcadoButton;
    }
}