package model.enums;

public enum PickaxeRarity {
    IRON_PICKAXE(10),
    GOLD_PICKAXE(20),
    DIAMOND_PICKAXE(30);

    private final int miningSpeed;
    PickaxeRarity(int miningSpeed){
        this.miningSpeed = miningSpeed;
    }

    public int getMiningSpeed(){
        return this.miningSpeed;
    }
}
