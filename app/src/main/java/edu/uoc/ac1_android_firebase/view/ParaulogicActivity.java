package edu.uoc.ac1_android_firebase.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.controller.Controller;

public class ParaulogicActivity extends AppCompatActivity implements ViewActivity{

    TextView paraulogicWrods;
    EditText paraulogicInput;
    ImageView paraulogic;
    ProgressBar progressBar;
    Button paraulogicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.uoc.ac1_android_firebase.R.layout.activity_paraulogic);
        ((ProgressBar) findViewById(R.id.idParaulogicPB)).setVisibility(View.VISIBLE);
        callControllerWithThisActivityAsParameter();
    }

    @Override
    public void callControllerWithThisActivityAsParameter() {
        Controller.getInstance().paraulogicActivity(this);
    }

    @Override
    public void createAllItemsAsGlobalWithGetters() {
        this.paraulogicWrods = (TextView) findViewById(R.id.idParaulesParaulogic);
        this.paraulogicInput = (EditText) findViewById(R.id.idPraulaParaulogic);
        this.paraulogic = (ImageView) findViewById(R.id.idParaulogicImg);
        this.progressBar = (ProgressBar) findViewById(R.id.idParaulogicPB);
        this.paraulogicButton = (Button) findViewById(R.id.idParaulogicButton);
    }

    public TextView getParaulogicWrods() {
        return paraulogicWrods;
    }

    public String getParaulogicInput() {
        return paraulogicInput.getText().toString();
    }

    public void cleanParaulogicInput() {
        paraulogicInput.setText("");
    }

    public ImageView getParaulogic() {
        return paraulogic;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Button getParaulogicButton() {
        return paraulogicButton;
    }
}