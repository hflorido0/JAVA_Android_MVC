package edu.uoc.ac1_android_firebase.model;

import java.io.Serializable;
import java.util.List;

public class Paraulogic implements Serializable {
    List<String> respuestas;
    List<String> solutions;
    int img;
    int p_ganadas;

    public Paraulogic(List<String> respuestas, List<String> solutions, int img, int p_ganadas) {
        this.respuestas = respuestas;
        this.solutions = solutions;
        this.img = img;
        this.p_ganadas = p_ganadas;
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

    public int getP_ganadas() {
        return p_ganadas;
    }
}
