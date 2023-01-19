package edu.uoc.ac1_android_firebase.controller;

import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import edu.uoc.ac1_android_firebase.dao.FireStoreDB;
import edu.uoc.ac1_android_firebase.model.Paraulogic;
import edu.uoc.ac1_android_firebase.utils.Constants;
import edu.uoc.ac1_android_firebase.view.ParaulogicActivity;

public class ParaulogicController implements ControllerInterface {

    private ParaulogicActivity paraulogicActivity;
    private FireStoreDB persistencia;

    public ParaulogicController(ParaulogicActivity paraulogicActivity, FireStoreDB persistencia) {
        this.paraulogicActivity = paraulogicActivity;
        this.persistencia = persistencia;
    }

    public void createActivityButtons() {
        this.paraulogicActivity.getParaulogicButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paraulogicActivity.getProgressBar().setVisibility(View.VISIBLE);
                persistencia.get(Constants.PARAULOGIC_COLLECTION).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ArrayList<String> list = (ArrayList<String>) documentSnapshot.get(Constants.SOLUTIONS);
                        ArrayList<String> current = (ArrayList<String>) documentSnapshot.get(Constants.RESPUESTAS);
                        if (list.contains(paraulogicActivity.getParaulogicInput())) {
                            persistencia.pushElement(Constants.PARAULOGIC_COLLECTION, current,
                                    paraulogicActivity.getParaulogicInput(), Constants.RESPUESTAS);
                        }
                        paraulogicActivity.getParaulogicWrods().setText(current.toString());
                        paraulogicActivity.cleanParaulogicInput();
                        paraulogicActivity.getProgressBar().setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    protected void createNewParaulogic() {
        Random random = new Random();
        int rand = random.nextInt(Constants.drawables.length - 1);
        int randomDrawable = Constants.drawables[rand];
        String[] paraulogic = Constants.paraulogics[rand];

        HashMap<String, Object> values = new HashMap<>();
        values.put(Constants.SOLUTIONS, Arrays.asList(paraulogic));
        values.put(Constants.RESPUESTAS, Arrays.asList());
        values.put(Constants.PARAULOGIC, randomDrawable);

        persistencia.update(Constants.PARAULOGIC_COLLECTION, values);

        Paraulogic para = new Paraulogic(Arrays.asList(), Arrays.asList(paraulogic), randomDrawable);

        setParaulogicPartida(para);
    }

    protected void setParaulogicPartida(Paraulogic para) {
        paraulogicActivity.getParaulogic()
                .setImageDrawable(paraulogicActivity.getResources().getDrawable(para.getImg()));
        paraulogicActivity.getParaulogicWrods().setText(para.getRespuestas().toString());
    }

    protected Paraulogic getParaulogicFromDocumentSnapshot(DocumentSnapshot documentSnapshot) {
        return new Paraulogic((List<String>) documentSnapshot.get(Constants.RESPUESTAS),
                (List<String>) documentSnapshot.get(Constants.SOLUTIONS),
                (int) ((long) documentSnapshot.get(Constants.PARAULOGIC)));
    }

}
