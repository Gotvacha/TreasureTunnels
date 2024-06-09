package model.enums;

import model.enums.OreType;

public enum PickaxeRarity {
    IRON_PICKAXE(2),
    GOLD_PICKAXE(4),
    DIAMOND_PICKAXE(6);

    private final int miningSpeed;

    PickaxeRarity(int miningSpeed) {
        this.miningSpeed = miningSpeed;
    }

    public int getMiningSpeed() {
        return miningSpeed;
    }

    public boolean canMine(OreType oreType) {
        return this.ordinal() >= oreType.getRequiredRarity().ordinal();
    }
}
