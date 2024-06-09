package model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Treasury {
    private static Treasury instance;
    private static int money = 0;

    public Treasury(int money){
        Treasury.money = money;
    }

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
