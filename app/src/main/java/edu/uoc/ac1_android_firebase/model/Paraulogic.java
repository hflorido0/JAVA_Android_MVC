package edu.uoc.ac1_android_firebase.model;

import java.io.Serializable;
import java.util.List;

public class Paraulogic implements Serializable {
    List<String> respuestas;
    List<String> solutions;
    int img;

    public Paraulogic(List<String> respuestas, List<String> solutions, int img) {
        this.respuestas = respuestas;
        this.solutions = solutions;
        this.img = img;
    }

    public List<String> getRespuestas() {
        return respuestas;
    }

    public List<String> getSolutions() {
        return solutions;
    }

    public int getImg() {
        return img;
    }
}
