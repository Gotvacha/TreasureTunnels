package model;

import model.enums.OreType;

public class Mine {
    private Ore ore;

    public void generateOre(){
        this.ore = new Ore();
    }

    public OreType getOreType(){
        return this.ore.getOreType();
    }

    public void mineOre(int minerWeight){

    }

}
