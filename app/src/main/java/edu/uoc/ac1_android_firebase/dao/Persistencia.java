package edu.uoc.ac1_android_firebase.dao;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import edu.uoc.ac1_android_firebase.controller.Controller;
import edu.uoc.ac1_android_firebase.model.Ahorcado;
import edu.uoc.ac1_android_firebase.model.Paraulogic;
import edu.uoc.ac1_android_firebase.model.Stadistics;
import edu.uoc.ac1_android_firebase.model.User;
import edu.uoc.ac1_android_firebase.utils.Provider;

public class Persistencia {

    public void save(FirebaseFirestore db, User user, String collectionPath, HashMap<?,?> map) {
        db.collection(collectionPath).document(user.getEmail())
                .set(map);
    }

    public void getUser(FirebaseFirestore db, String email) {
        db.collection("users").document(email)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = new User(email, Provider.valueOf(documentSnapshot.get("provider").toString()),
                            documentSnapshot.get("name").toString());
                        Controller.getInstance().returnUser(user);
                    }
                });
    }

    public void getAhorcado(FirebaseFirestore db, String email) {
        db.collection("ahorcado").document(email)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Ahorcado ahorcado = new Ahorcado();
                        Controller.getInstance().returnAhorcado(ahorcado);
                    }
                });
    }

    public void getParaulogic(FirebaseFirestore db, String email) {
        db.collection("paraulogic").document(email)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Paraulogic paraulogic = new Paraulogic();
                        Controller.getInstance().returnParaulogic(paraulogic);
                    }
                });
    }

    public void getStadistics(FirebaseFirestore db, String email) {
        db.collection("stadistics").document(email)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Stadistics stadistics = new Stadistics();
                        Controller.getInstance().returnEstadistica(stadistics);
                    }
                });
    }

    public void delete (FirebaseFirestore db, String email, String collectionPath) {
        db.collection(collectionPath).document(email)
                .delete();
    }

}
