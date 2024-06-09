package model;

import lombok.Getter;
import model.errors.NotEnoughBalanceExpection;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private final Mine mine;
    @Getter
    private final List<Miner> miners;
    private final Treasury treasury;

    public Shop(int maxActiveMiners) {
        this.mine = new Mine(maxActiveMiners);
        this.treasury = Treasury.getInstance();
        this.miners = new ArrayList<>();

        for (int i = 0; i < maxActiveMiners; i++) {
            Miner newMiner = new Miner(mine);
            miners.add(newMiner);
            new Thread(newMiner).start();
        }
    }

    private boolean checkForBalance(int moneyRequired){
        return moneyRequired <= treasury.getBalance();

    }

    public void addMiners() throws NotEnoughBalanceExpection {
        if(checkForBalance(30)){
            Miner newMiner = new Miner(mine);
            miners.add(newMiner);
            new Thread(newMiner).start();
        } else{
            throw new NotEnoughBalanceExpection();
        }
    }

    public void upgradeMinerTool(Miner miner) throws NotEnoughBalanceExpection {
        if(checkForBalance(10)){
            miner.upgradeTool();
        } else{
            throw new NotEnoughBalanceExpection();
        }

    }

    public void upgradeMinerWeightThreshold(Miner miner) {
        miner.increaseWeightThreshold();
    }

    public void buyMoreSpace() {
        mine.addNumberOfActiveMiners(5);
    }

    public void hydration() {
        for (Miner miner : miners) {
            miner.hydrateForDay();
        }
    }

    public String printMiners() {
        int i = 1;
        StringBuilder sb = new StringBuilder();
        for (Miner miner : miners) {
            sb.append(i++).append(" | ").append(miner.toString());
        }
        return sb.toString();
    }

}
