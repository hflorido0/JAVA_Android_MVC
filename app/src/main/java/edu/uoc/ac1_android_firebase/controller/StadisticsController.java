package edu.uoc.ac1_android_firebase.controller;

import edu.uoc.ac1_android_firebase.dao.FireStoreDB;
import edu.uoc.ac1_android_firebase.view.StadisticsActivity;

public class StadisticsController implements ControllerInterface {

    private StadisticsActivity stadisticsActivity;
    private FireStoreDB persistencia;

    public StadisticsController(StadisticsActivity stadisticsActivity, FireStoreDB persistencia) {
        this.stadisticsActivity = stadisticsActivity;
        this.persistencia = persistencia;
    }

    @Override
    public void createActivityButtons() {

    }
}