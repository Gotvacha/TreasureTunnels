package model;

import model.enums.OreType;
import model.enums.PickaxeRarity;
import model.enums.Season;

import java.time.Duration;

public class Miner implements Runnable{
    private final Tool pickaxe;
    private int weight;
    private int weightThreshold;
    private static int maxWeight;
    private final Mine mine;
    private final Treasury treasury;
    private final ThreadClock clock;
    private boolean hydrated;
    private static final int BASE_WEIGHT_THRESHOLD = 120;

    public Miner(Mine mine){
        this.clock = ThreadClock.getInstance();
        this.treasury = Treasury.getInstance();
        this.pickaxe = new Tool(PickaxeRarity.IRON_PICKAXE);
        this.mine = mine;
        this.weightThreshold = BASE_WEIGHT_THRESHOLD;
        maxWeight = weightThreshold + 30;
        this.hydrated = false;
    }

    @Override
    public void run() {
        while (true) {
            if (isWorkingHours()) {

                this.hydrated = false;
                boolean enteredMine = false;
                try {
                    enteredMine = mine.tryEnterMine();
                    if (enteredMine) {
                        while(isWorkingHours() && weight < weightThreshold){
                            mine();
                        }

                        if(weight > maxWeight){
                            weight = maxWeight;
                        }

                        addMoney();
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
            }
        }
    }

    private boolean isWorkingHours(){
        return clock.getHours() >= 9 && clock.getHours() < 17;
    }

    private void mine(){
        try {
            int adjustedMiningSpeed = getAdjustedMiningSpeed();

            weight += mine.tryMineOre(pickaxe);

            Thread.sleep(Duration.ofSeconds(adjustedMiningSpeed).toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int getAdjustedMiningSpeed() {
        int baseSpeed = 10 - pickaxe.getMiningSpeed();
        Season currentSeason = this.clock.getSeasons();

        if (currentSeason == Season.WINTER || currentSeason == Season.SUMMER) {
            baseSpeed *= 1.3; // multiplied so that when thread sleeps, it will be for longer duration
        }
        if (hydrated) {
            baseSpeed /= 1.5; // 50% faster if hydrated
        }

        return baseSpeed;
    }

    private void addMoney(){
        try{
            synchronized (this.treasury){
                int moneyToAdd = weight * 5; // 1 weight = 5 money
                treasury.addMoney(moneyToAdd);
                weight = 0;
            }
            Thread.sleep(Duration.ofSeconds(5).toMillis());
        }catch (InterruptedException e){
            throw new RuntimeException("Race condition during adding money");
        }
    }

    public void upgradeTool(){
        pickaxe.upgradeRarity();
    }

    public void increaseWeightThreshold() {
        weightThreshold += 20;
        maxWeight = weightThreshold + 35;
    }

    public void hydrateForDay(){
        hydrated = true;
    }

    @Override
    public String toString() {

        return  pickaxe.toString() + "\n" +
                "Weight: " + weight + "\n" +
                "Weight threshold: " + weightThreshold + "\n" +
                "Max weight: " + maxWeight + "\n";
    }

}
