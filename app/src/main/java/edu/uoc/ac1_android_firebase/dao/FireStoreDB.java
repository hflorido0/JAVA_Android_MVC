package edu.uoc.ac1_android_firebase.dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uoc.ac1_android_firebase.model.Ahorcado;
import edu.uoc.ac1_android_firebase.model.Paraulogic;
import edu.uoc.ac1_android_firebase.model.Stadistics;
import edu.uoc.ac1_android_firebase.utils.Constants;

public class FireStoreDB {
    private FirebaseFirestore db;
    private String email;

    public FireStoreDB(FirebaseFirestore db, String email) {
        this.db = db;
        this.email = email;
    }

    public void save(String collectionPath, Map<?,?> map) {
        db.collection(collectionPath).document(email)
                .set(map);
    }

    public Task<DocumentSnapshot> get(String collectionPath) {
        return db.collection(collectionPath).document(email).get();
    }

    public void update(String collectionPath,  Map<?,?> map) {
        db.collection(collectionPath).document(email).set(map, SetOptions.merge());
    }

    public void delete(String collectionPath) {
        db.collection(collectionPath).document(email)
                .delete();
    }

    public void incrementByOne(String collectionPath, String valueToIncrement) {

        HashMap<String, Object> values = new HashMap<>();
        values.put(valueToIncrement, FieldValue.increment(1));

        update(collectionPath, values);
    }

    public void setTime(String collectionPath, String valueToSetTime) {

        HashMap<String, Object> values = new HashMap<>();
        values.put(valueToSetTime,  FieldValue.serverTimestamp());

        update(collectionPath, values);
    }

    public void pushElement(String collectionPath, ArrayList<String> current, String valueToPush, String arrayName) {
        current.add(valueToPush);

        HashMap<String, Object> values = new HashMap<>();
        values.put(arrayName, current);

        update(collectionPath, values);
    }
}
