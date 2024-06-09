package model;

import model.enums.PickaxeRarity;

public class Tool {
    private PickaxeRarity rarity;
    private int miningSpeed;

    public Tool(PickaxeRarity rarity){
        this.rarity = rarity;
    }

    public int getMiningSpeed(){
        return this.miningSpeed;
    }

    public PickaxeRarity getRarity() {
        return this.rarity;
    }


}
