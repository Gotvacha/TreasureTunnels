package model;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private final Mine mine;
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

    public synchronized void addMiners() {
        Miner newMiner = new Miner(mine);
        miners.add(newMiner);

        new Thread(newMiner).start();
    }

    public synchronized void upgradeMinerTool(Miner miner) {
        miner.upgradeTool();
    }

    public synchronized void upgradeMinerWeightThreshold(Miner miner) {
        miner.increaseWeightThreshold();
    }

    public synchronized void buyMoreSpace() {
        mine.addNumberOfActiveMiners(5);
    }

    public synchronized void hydration() {
        for (Miner miner : miners) {
            miner.hydrateForDay();
        }
    }

    public void printMiners() {
        int i = 1;
        for (Miner miner : miners) {
            System.out.println(i++ + " | " + miner.toString());
        }
    }

    public List<Miner> getMiners() {
        return miners;
    }
}
