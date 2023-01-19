package edu.uoc.ac1_android_firebase.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.controller.Controller;
import edu.uoc.ac1_android_firebase.model.Stadistics;
import edu.uoc.ac1_android_firebase.model.User;
import edu.uoc.ac1_android_firebase.utils.Constants;

public class StadisticsActivity extends AppCompatActivity implements ViewActivity{
    TextView ahorcado;
    TextView paraulogic;
    TextView inicios;
    TextView sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.uoc.ac1_android_firebase.R.layout.activity_stadistics);

        if(getIntent().getExtras() != null) {
            Stadistics stadistics = (Stadistics) getIntent().getSerializableExtra(Constants.STADISTICS_COLLECTION);
            ((TextView) findViewById(R.id.idStadisticsPartidasAhorcado)).setText(stadistics.getAhorcado() + "");
            ((TextView) findViewById(R.id.idStadisticsPartidasParaulogic)).setText(stadistics.getParaulogic() + "");
            ((TextView) findViewById(R.id.idStadisticsIniciosSesion)).setText(stadistics.getInicis() + "");
            ((TextView) findViewById(R.id.idUltimaSession)).setText(stadistics.getUltimInici());
        }
        callControllerWithThisActivityAsParameter();
    }

    @Override
    public void callControllerWithThisActivityAsParameter() {
        Controller.getInstance().stadisticsActivity(this);
    }

    @Override
    public void createAllItemsAsGlobalWithGetters() {
        this.ahorcado = (TextView) findViewById(R.id.idStadisticsPartidasAhorcado);
        this.paraulogic = (TextView) findViewById(R.id.idStadisticsPartidasParaulogic);
        this.inicios = (TextView) findViewById(R.id.idStadisticsIniciosSesion);
        this.sesion = (TextView) findViewById(R.id.idUltimaSession);
    }

    public TextView getAhorcado() {
        return ahorcado;
    }

    public TextView getParaulogic() {
        return paraulogic;
    }

    public TextView getInicios() {
        return inicios;
    }

    public TextView getSesion() {
        return sesion;
    }
}