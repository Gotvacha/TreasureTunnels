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
    private static final int MAX_WEIGHT = 150;

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
                boolean enteredMine = false;
                try {
                    enteredMine = mine.tryEnterMine();
                    if (enteredMine) {
                        mine();
                        if (this.weight >= WEIGHT_THRESHOLD) {
                            if(this.weight > MAX_WEIGHT){
                                this.weight = MAX_WEIGHT;
                            }
                            addMoney();
                        }
                    } else {
                        Thread.sleep(Duration.ofSeconds(5).toMillis());
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } finally {
                    if (enteredMine) {
                        mine.leaveMine();
                    }
                }
            } else {
                try {
                    Thread.sleep(Duration.ofMinutes(1).toMillis());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Miner thread interrupted");
                }
            }
        }
    }

    private boolean isWorkingHours(){
        return this.clock.getHours() >= 9 && this.clock.getHours() < 17;
    }

    private void mine(){
        try {
            int adjustedMiningSpeed = getAdjustedMiningSpeed();

            this.weight += this.mine.tryMineOre(this.pickaxe.getRarity());

            Thread.sleep(Duration.ofMillis(adjustedMiningSpeed).toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int getAdjustedMiningSpeed() {
        int baseSpeed = this.pickaxe.getMiningSpeed();
        Season currentSeason = this.clock.getSeasons();

        if (currentSeason == Season.WINTER || currentSeason == Season.SUMMER) {
            baseSpeed *= 1.3; // multiplied so that when thread sleeps, it will be for longer duration
        }

        return baseSpeed;
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
