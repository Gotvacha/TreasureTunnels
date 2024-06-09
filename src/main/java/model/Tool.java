package model;

import lombok.Getter;
import model.enums.OreType;
import model.enums.PickaxeRarity;

@Getter
public class Tool {
    private PickaxeRarity rarity;
    private int miningSpeed;

    public Tool(PickaxeRarity rarity){
        this.rarity = rarity;
        miningSpeed = rarity.getMiningSpeed();
    }

    public boolean canMine(OreType oreType) {
        return rarity.canMine(oreType);
    }

    public void upgradeRarity(){
        PickaxeRarity[] rarities = PickaxeRarity.values();
        int currentIndex = rarity.ordinal();

        if (currentIndex < rarities.length - 1) {
            rarity = rarities[currentIndex + 1];
            miningSpeed = rarity.getMiningSpeed();
        } else {
            System.out.println("Cannot upgrade. Pickaxe is already at the highest rarity.");
        }
    }

    @Override
    public String toString() {

        return "Pickaxe rarity: " + rarity.toString() + "\n"
                + "Pickaxe mining speed: " + miningSpeed;
    }
}
