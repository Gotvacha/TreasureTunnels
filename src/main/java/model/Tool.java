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
    }

    public boolean canMine(OreType oreType) {
        return this.rarity.canMine(oreType);
    }

    public void upgradeRarity(){
        PickaxeRarity[] rarities = PickaxeRarity.values();
        int currentIndex = this.rarity.ordinal();

        if (currentIndex < rarities.length - 1) {
            this.rarity = rarities[currentIndex + 1];
            this.miningSpeed = this.rarity.getMiningSpeed();
        } else {
            System.out.println("Cannot upgrade. Pickaxe is already at the highest rarity.");
        }
    }

    @Override
    public String toString() {

        return "Pickaxe rarity: " + this.rarity.toString() + "\n"
                + "Pickaxe mining speed: " + this.miningSpeed;
    }
}
