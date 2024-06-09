package model;

import lombok.Getter;
import model.enums.OreType;

import java.util.concurrent.Semaphore;

@Getter
public class Mine {
    private OreType ore; // may be array of OreType or may be deleted and only OreType used
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

    public int tryMineOre(Tool pickaxe){
        OreType ore = OreType.randomType();
        if(!pickaxe.canMine(ore)){
            return 0;
        }
        ore.decreaseAmount();
        return ore.getWeightPerTick();
    }

    public void addNumberOfActiveMiners(int newMaxActiveMiners){
        semaphore.release(newMaxActiveMiners - semaphore.availablePermits()); // or create new semaphore
    }

}
