package edu.uoc.ac1_android_firebase.dao;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import edu.uoc.ac1_android_firebase.controller.Controller;
import edu.uoc.ac1_android_firebase.model.User;
import edu.uoc.ac1_android_firebase.utils.Provider;

public class Persistencia {

    public void save (FirebaseFirestore db, User user) {

        HashMap<String, String> collection = new HashMap<String, String>();
        collection.put("provider",user.getProvider().toString());
        collection.put("name",user.getName());

        db.collection("users").document(user.getEmail())
                .set(collection);
    }

    public void get(FirebaseFirestore db, String email) {
        db.collection("users").document(email)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = new User(email, Provider.valueOf(documentSnapshot.get("provider").toString()),
                            documentSnapshot.get("name").toString());
                        Controller.getInstance().returnCollectedData(user);
                    }
                });
    }

    public void delete (FirebaseFirestore db, User user) {
        db.collection("users").document(user.getEmail())
                .delete();
    }

}
