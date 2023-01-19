package edu.uoc.ac1_android_firebase.controller;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.dao.FireStoreDB;
import edu.uoc.ac1_android_firebase.model.Ahorcado;
import edu.uoc.ac1_android_firebase.utils.Constants;
import edu.uoc.ac1_android_firebase.view.AhorcadoActivity;
import edu.uoc.ac1_android_firebase.view.LoginActivity;

public class AhorcadoController implements ControllerInterface {

    private AhorcadoActivity ahorcadoActivity;
    private FireStoreDB persistencia;

    public AhorcadoController(AhorcadoActivity ahorcadoActivity, FireStoreDB persistencia) {
        this.ahorcadoActivity = ahorcadoActivity;
        this.persistencia = persistencia;

    }

    @Override
    public void createActivityButtons() {
        //TODO:
    }

    protected void createAhorcado() {
        Random random = new Random();
        int rand = random.nextInt(Constants.ahorcado.length - 1);
        String ahorcadoP = Constants.ahorcado[rand];

        HashMap<String, Object> values = new HashMap<>();
        values.put(Constants.SOLUTIONS, ahorcadoP);
        values.put(Constants.RESPUESTAS, Arrays.asList());
        values.put(Constants.AHORCADO, R.drawable.h1);

        persistencia.update(Constants.AHORCADO_COLLECTION, values);

        Ahorcado ahorcado = new Ahorcado(Arrays.asList(), ahorcadoP, R.drawable.h1);

        setAhorcadopartida(ahorcado);
    }

    protected void setAhorcadopartida(Ahorcado ahorcado) {
        ahorcadoActivity.getAhoracado()
                .setImageDrawable(ahorcadoActivity.getResources().getDrawable(ahorcado.getImg()));
        ahorcadoActivity.getAhorcadoWrods().setText(ahorcado.getRespuestas().toString());
    }

    protected Ahorcado getAhorcadoFromDocumentSnapshot(DocumentSnapshot documentSnapshot) {
        return new Ahorcado((List<String>) documentSnapshot.get(Constants.RESPUESTAS),
                (String) documentSnapshot.get(Constants.SOLUTIONS),
                (int) ((long) documentSnapshot.get(Constants.AHORCADO)));
    }

}

