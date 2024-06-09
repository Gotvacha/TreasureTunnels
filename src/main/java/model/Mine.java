package model;

import lombok.Getter;
import model.enums.OreType;
import model.enums.PickaxeRarity;

import java.util.concurrent.Semaphore;

@Getter
public class Mine {
    private OreType ore; // may be array of OreType
    private final Semaphore semaphore;

    public Mine(int maxActiveMiners) {
        this.semaphore = new Semaphore(maxActiveMiners, true);
    }

    public boolean tryEnterMine() {
        try {
            semaphore.acquire();
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public void leaveMine() {
        semaphore.release();
    }

    public int tryMineOre(PickaxeRarity rarity){
        OreType ore = OreType.randomType();
        if(!rarity.canMine(ore)){
            return 0;
        }
        ore.decreaseAmount();
        return ore.getWeightPerTick();
    }

}
