package model;

import lombok.Getter;

@Getter
public class Treasury {
    private static volatile Treasury instance;
    private static int money = 0;

    private Treasury(){
        this(0);
    }

    private Treasury(int money){
        Treasury.money = money;
    }

    public static Treasury getInstance(){
        if(instance == null){
            synchronized (Treasury.class){
                if(instance == null){
                    instance = new Treasury();
                }
            }
        }
        return instance;
    }

    public synchronized void addMoney(int minerMoney){
        money += minerMoney;
    }

    public int getBalance(){
        return money;
    }

    public void removeMoney(int moneyToRemove){
        money -= moneyToRemove;
    }
}
