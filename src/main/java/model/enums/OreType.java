package model.enums;

import lombok.Getter;

import java.util.Random;

@Getter
public enum OreType {
    IRON(50, 10, PickaxeRarity.IRON_PICKAXE),
    GOLD(30, 20, PickaxeRarity.GOLD_PICKAXE),
    DIAMOND(20, 40, PickaxeRarity.DIAMOND_PICKAXE);

    private int amount;
    private int weightPerTick;
    private final PickaxeRarity requiredRarity;
    private static final Random PRNG = new Random();

    OreType(int amount, int weightPerTick, PickaxeRarity requiredRarity) {
        this.amount = amount;
        this.weightPerTick = weightPerTick;
        this.requiredRarity = requiredRarity;
    }

    public void decreaseAmount() {
        this.amount--;
    }

    public static OreType randomType() {
        OreType[] oreTypes = values();
        return oreTypes[PRNG.nextInt(oreTypes.length)];
    }
}
