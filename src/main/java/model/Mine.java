package model;

import model.enums.OreType;

public class Mine {
    private Ore ore;

    public void generateOre(){
        Ore ore = new Ore();
        this.ore = ore;
    }

    public OreType getOreType(){
        return this.ore.getOreType();
    }

    public void mineOre(int minerWeight){

    }

}
