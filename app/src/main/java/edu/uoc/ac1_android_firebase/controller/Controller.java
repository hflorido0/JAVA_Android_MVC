package edu.uoc.ac1_android_firebase.controller;

import edu.uoc.ac1_android_firebase.view.HomeActivity;
import edu.uoc.ac1_android_firebase.view.MainActivity;

public class Controller implements ControllerInterface {

    //Todas las activities como variables globales
    private MainActivity mainActivity;
    private HomeActivity homeActivity;
    //SIngleton
    private static Controller controller;


    public static Controller getInstance() {
        if (controller == null) controller = new Controller();
        return controller;
    }

    //instanciamos todos los activities para prevenir nullPointers
    public Controller () {
        this.homeActivity = new HomeActivity();
    }

    public void mainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.mainActivity.createAllItemsAsGlobalWithGetters();
        //TODO: switch to loginActivity
        //switchActivity(this.mainActivity, this.loginActivity);
    }

    public void homeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        this.homeActivity.createAllItemsAsGlobalWithGetters();
        createActivityButtons();
    }

    @Override
    public void createActivityButtons() {
        //TODO: HomeActivity : get Buttons and add onClickListener
    }
}
