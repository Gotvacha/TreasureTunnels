package model;

import org.example.models.Tool;
import org.example.models.Treasury;
import org.example.models.enums.PickaxeRarity;

import java.time.Duration;

public class Miner implements Runnable{
    private Tool pickaxe;
    private int miningSpeed;
    private int weight;
    private final Mine mine;
    private final Treasury treasury;
    public Miner(Mine mine){
        this.treasury = Treasury.getInstance();
        this.pickaxe = new Tool(PickaxeRarity.IRON_PICKAXE);
        this.miningSpeed = this.pickaxe.getMiningSpeed();
        this.mine = mine;
    }

    @Override
    public void run() {
        while(true) {
            this.mine();

            if (this.weight >= 120 && this.weight <= 150) {
                this.addMoney();
            }
        }
    }

    private void mine(){
        switch (this.mine.getOreType()){
            case IRON:
                if(this.pickaxe.getRarity() == PickaxeRarity.IRON_PICKAXE){
                    this.mine.mineOre(this.weight);
                    //add thread sleep based on mining speed
                }
                break;
            case GOLD:
            case DIAMOND:
        }
    }

    private void addMoney(){
        try{
            synchronized (this.treasury){
                int moneyToAdd = this.weight * 5;
                this.treasury.addMoney(moneyToAdd);
                this.weight = 0;
            }
            Thread.sleep(Duration.ofSeconds(5).toMillis());
        }catch (InterruptedException e){
            throw new RuntimeException("Race condition during adding money");
        }
    }

    public void upgradeTool(){
        //this.pickaxe.upgradeRarity();
    }

}
