package model.enums;

import lombok.Getter;

import java.util.Random;

public enum OreType {
    IRON(50, 10),
    GOLD(30, 20),
    DIAMOND(20, 40);


    private int amount;
    @Getter
    private int weightPerTick;
    private static final Random PRNG = new Random();

    OreType(int amount, int weightPerTick){
        this.amount = amount;
        this.weightPerTick = weightPerTick;

    }

    public static OreType randomType(){
        OreType[] oreTypes = values();
        return oreTypes[PRNG.nextInt(oreTypes.length)];
    }
}
