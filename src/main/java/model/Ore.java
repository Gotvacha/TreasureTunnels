package model;

import lombok.Getter;
import model.enums.OreType;

@Getter
public class Ore {
    private OreType oreType;

    public Ore(){
        this.oreType = OreType.randomType();
    }

}
