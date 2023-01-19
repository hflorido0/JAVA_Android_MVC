package edu.uoc.ac1_android_firebase.controller;

import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import edu.uoc.ac1_android_firebase.R;
import edu.uoc.ac1_android_firebase.dao.FireStoreDB;
import edu.uoc.ac1_android_firebase.model.Ahorcado;
import edu.uoc.ac1_android_firebase.utils.Constants;
import edu.uoc.ac1_android_firebase.view.AhorcadoActivity;

public class AhorcadoController implements ControllerInterface {

    private AhorcadoActivity ahorcadoActivity;
    private FireStoreDB persistencia;
    private Ahorcado ahorcado;

    public AhorcadoController(AhorcadoActivity ahorcadoActivity, FireStoreDB persistencia) {
        this.ahorcadoActivity = ahorcadoActivity;
        this.persistencia = persistencia;
    }

    @Override
    public void createActivityButtons() {
        this.ahorcadoActivity.getAhorcadoButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ahorcadoActivity.getProgressBar().setVisibility(View.VISIBLE);
                persistencia.get(Constants.AHORCADO_COLLECTION).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Ahorcado ahorcado = getAhorcadoFromDocumentSnapshot(documentSnapshot);

                        boolean nextImage = false;
                        if (ahorcadoActivity.getAhorcadoInput().length() > 1) {
                            if (ahorcadoActivity.getAhorcadoInput().equals(ahorcado.getSolutions())) {
                                //TODO: partida ganada
                            } else {
                                persistencia.pushElement(Constants.AHORCADO_COLLECTION, (ArrayList<String>) ahorcado.getRespuestas(),
                                        ahorcadoActivity.getAhorcadoInput(), Constants.RESPUESTAS);
                                nextImage = true;
                            }
                        } else {
                            if (((ArrayList<String>) ahorcado.getRespuestas()).contains(ahorcadoActivity.getAhorcadoInput())) {
                                //TODO: mostrar letra en la solución
                            } else {
                                nextImage = true;
                            }
                            persistencia.pushElement(Constants.AHORCADO_COLLECTION, (ArrayList<String>) ahorcado.getRespuestas(),
                                    ahorcadoActivity.getAhorcadoInput(), Constants.RESPUESTAS);
                        }
                        if (nextImage) {
                            ahorcado.sumPosImg();
                            ahorcadoActivity.getAhoracado().setImageDrawable(
                                    ahorcadoActivity.getResources().getDrawable(Constants.drawablesAhorcado[ahorcado.getPosImg()]));
                            persistencia.incrementByOne(Constants.AHORCADO_COLLECTION, Constants.POS_IMG);
                        }
                        ahorcadoActivity.getAhorcadoWrods().setText(((ArrayList<String>) ahorcado.getRespuestas()).toString());
                        ahorcadoActivity.getProgressBar().setVisibility(View.GONE);
                    }
                });
            }
        });
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

        Ahorcado ahorcado = new Ahorcado(Arrays.asList(), ahorcadoP, R.drawable.h1, 0);
        this.ahorcado = ahorcado;

        this.ahorcadoActivity.getAhorcadoSecretWord().setText(getSecredWord(ahorcadoP));

        setAhorcadopartida(ahorcado);
    }

    protected String getSecredWord(String solucio) {
        String[] letras = solucio.split("");
        String secretWord = "";
        for (String l : letras) {
            secretWord += "_ ";
        }
        return secretWord;
    }

    protected void setAhorcadopartida(Ahorcado ahorcado) {
        this.ahorcado = ahorcado;
        ahorcadoActivity.getAhoracado()
                .setImageDrawable(ahorcadoActivity.getResources().getDrawable(ahorcado.getImg()));
        ahorcadoActivity.getAhorcadoWrods().setText(ahorcado.getRespuestas().toString());
        ahorcadoActivity.getAhorcadoSecretWord().setText(getSecredWord(ahorcado.getSolutions()));
    }

    protected Ahorcado getAhorcadoFromDocumentSnapshot(DocumentSnapshot documentSnapshot) {
        return new Ahorcado((List<String>) documentSnapshot.get(Constants.RESPUESTAS),
                (String) documentSnapshot.get(Constants.SOLUTIONS),
                (int) ((long) documentSnapshot.get(Constants.AHORCADO)),
                (int) ((long) documentSnapshot.get(Constants.POS_IMG)));
    }

}

