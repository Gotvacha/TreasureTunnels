package model;

import model.enums.OreType;
import model.enums.PickaxeRarity;
import model.enums.Season;

import java.time.Duration;

public class Miner implements Runnable{
    private Tool pickaxe;
    private int weight;
    private final Mine mine;
    private final Treasury treasury;
    private final ThreadClock clock;
    private static final int WEIGHT_THRESHOLD = 120;

    public Miner(Mine mine){
        this.clock = ThreadClock.getInstance();
        this.treasury = Treasury.getInstance();
        this.pickaxe = new Tool(PickaxeRarity.IRON_PICKAXE);
        this.mine = mine;
    }

    @Override
    public void run() {
        while (true) {
            if (isWorkingHours()) {
                mine();

                if (this.weight >= WEIGHT_THRESHOLD) {
                    addMoney();
                }
            }
        }
    }

    private boolean isWorkingHours(){
        return this.clock.getHours() >= 9 && this.clock.getHours() < 17;
    }

    private void mine(){
        try {
            OreType oreType = this.mine.getOreType();

            if (this.pickaxe.canMine(oreType)) {
                int adjustedMiningSpeed = getAdjustedMiningSpeed();
                this.mine.mineOre(this.weight);

                Thread.sleep(Duration.ofMillis(adjustedMiningSpeed).toMillis());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int getAdjustedMiningSpeed() {
        int adjustedSpeed = this.pickaxe.getMiningSpeed();
        Season currentSeason = this.clock.getSeasons();

        // Apply a 30% slowdown during winter or summer
        if (currentSeason == Season.WINTER || currentSeason == Season.SUMMER) {
            adjustedSpeed -= adjustedSpeed * 0.3;
        }

        return adjustedSpeed;
    }

    private void addMoney(){
        try{
            synchronized (this.treasury){
                int moneyToAdd = this.weight * 5; // 1 weight = 5 money
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
    }

}
