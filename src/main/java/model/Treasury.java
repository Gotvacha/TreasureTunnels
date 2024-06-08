package org.example.models;

public class Treasury {
    private static Treasury instance;
    private static int money = 0;

    public static Treasury getInstance(){
        if(instance == null){
            instance = new Treasury();
        }

        return instance;
    }

    public void addMoney(int minerMoney){
        money += minerMoney;
    }


}
