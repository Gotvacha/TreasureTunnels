package model.enums;

import lombok.Getter;

import java.util.Random;

@Getter
public enum OreType {
    IRON(10, PickaxeRarity.IRON_PICKAXE),
    GOLD(20, PickaxeRarity.GOLD_PICKAXE),
    DIAMOND(40, PickaxeRarity.DIAMOND_PICKAXE);

    private int weightPerTick;
    private final PickaxeRarity requiredRarity;
    private static final Random PRNG = new Random();

    OreType(int weightPerTick, PickaxeRarity requiredRarity) {
        this.weightPerTick = weightPerTick;
        this.requiredRarity = requiredRarity;
    }

    public static OreType randomType() {
        OreType[] oreTypes = values();
        return oreTypes[PRNG.nextInt(oreTypes.length)];
    }
}
