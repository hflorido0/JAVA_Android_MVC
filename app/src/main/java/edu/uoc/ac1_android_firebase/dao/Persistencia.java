package edu.uoc.ac1_android_firebase.dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uoc.ac1_android_firebase.model.Ahorcado;
import edu.uoc.ac1_android_firebase.model.Paraulogic;
import edu.uoc.ac1_android_firebase.model.Stadistics;
import edu.uoc.ac1_android_firebase.utils.Constants;

public class Persistencia {
    private FirebaseFirestore db;

    public Persistencia(FirebaseFirestore db) {
        this.db = db;
    }

    public void save(String email, String collectionPath, Map<?,?> map) {
        db.collection(collectionPath).document(email)
                .set(map);
    }

    public Task<DocumentSnapshot> get(String email, String collectionPath) {
        return db.collection(collectionPath).document(email).get();
    }

    public void update(String email, String collectionPath,  Map<?,?> map) {
        db.collection(collectionPath).document(email).set(map, SetOptions.merge());
    }

    public void delete(String email, String collectionPath) {
        db.collection(collectionPath).document(email)
                .delete();
    }

}
