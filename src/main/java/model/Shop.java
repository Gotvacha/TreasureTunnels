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
        if(checkForBalance(300)){
            Miner newMiner = new Miner(mine);
            miners.add(newMiner);
            new Thread(newMiner).start();
            treasury.removeMoney(300);
        } else{
            throw new NotEnoughBalanceExpection();
        }
    }

    public void upgradeMinerTool(Miner miner) throws NotEnoughBalanceExpection {
        if(checkForBalance(100)){
            miner.upgradeTool();
            treasury.removeMoney(100);
        } else{
            throw new NotEnoughBalanceExpection();
        }

    }

    public void upgradeMinerWeightThreshold(Miner miner) throws NotEnoughBalanceExpection {
        if(checkForBalance(100)) {
            miner.increaseWeightThreshold();
            treasury.removeMoney(100);
        } else{
            throw new NotEnoughBalanceExpection();
        }
    }

    public void buyMoreSpace() throws NotEnoughBalanceExpection {
        if(checkForBalance(1500)) {
            mine.addNumberOfActiveMiners(5);
            treasury.removeMoney(1500);
        } else{
            throw new NotEnoughBalanceExpection();
        }
    }

    public void hydration() throws NotEnoughBalanceExpection {
        if(checkForBalance(1000)) {
            for (Miner miner : miners) {
                miner.hydrateForDay();
            }
            treasury.removeMoney(1000);
        }else{
            throw new NotEnoughBalanceExpection();
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
