package edu.uoc.ac1_android_firebase.model;

import java.io.Serializable;

import edu.uoc.ac1_android_firebase.utils.Provider;

public class User  implements Serializable {
    private String email;
    private Provider provider;
    private String name;

    public User(String email, Provider provider, String name) {
        this.email = email;
        this.provider = provider;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Provider getProvider() {
        return provider;
    }

    public String getName() {
        return name;
    }
}
