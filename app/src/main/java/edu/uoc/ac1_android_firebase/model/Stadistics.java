package edu.uoc.ac1_android_firebase.model;

import java.io.Serializable;

public class Stadistics  implements Serializable {
    int paraulogic;
    int ahorcado;
    int inicis;
    String ultimInici;

    public Stadistics(int paraulogic, int ahorcado, int inicis, String ultimInici) {
        this.paraulogic = paraulogic;
        this.ahorcado = ahorcado;
        this.inicis = inicis;
        this.ultimInici = ultimInici;
    }

    public int getParaulogic() {
        return paraulogic;
    }

    public int getAhorcado() {
        return ahorcado;
    }

    public int getInicis() {
        return inicis;
    }

    public String getUltimInici() {
        return ultimInici;
    }
}
