package edu.uoc.ac1_android_firebase.model;

import java.io.Serializable;
import java.util.List;

public class Ahorcado implements Serializable {
    String solutions;
    List<String> respuestas;
    int img;

    public Ahorcado(List<String> respuestas, String solutions, int img) {
        this.respuestas = respuestas;
        this.solutions = solutions;
        this.img = img;
    }

    public List<String> getRespuestas() {
        return respuestas;
    }

    public String getSolutions() {
        return solutions;
    }

    public int getImg() {
        return img;
    }
}
