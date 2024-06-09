package model;

import model.enums.OreType;
import model.enums.PickaxeRarity;
import java.time.Duration;

public class Miner implements Runnable{
    private Tool pickaxe;
    private int miningSpeed;
    private int weight;
    private final Mine mine;
    private final Treasury treasury;
    private final ThreadClock clock;

    public Miner(Mine mine, ThreadClock clock){
        this.clock = clock;
        this.treasury = Treasury.getInstance();
        this.pickaxe = new Tool(PickaxeRarity.IRON_PICKAXE);
        this.miningSpeed = this.pickaxe.getMiningSpeed();
        this.mine = mine;
    }

    @Override
    public void run() {
        while (true) {
            if (isWorkingHours()) {
                mine();

                if (this.weight >= 120) {
                    addMoney();
                }
            }
        }
    }

    private boolean isWorkingHours(){
        if(this.clock.getHours() >= 8 && this.clock.getHours() < 20){
            return true;
        }
        return false;
    }

    private void mine(){
        try {
            OreType oreType = this.mine.getOreType();

            if (this.pickaxe.canMine(oreType)) {
                this.mine.mineOre(this.weight);

                Thread.sleep(Duration.ofMillis(miningSpeed).toMillis());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
        this.pickaxe.upgradeRarity();
        this.miningSpeed = this.pickaxe.getMiningSpeed();
    }

}
