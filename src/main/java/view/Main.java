package view;

import model.InputProcessor;
import model.Shop;

public class Main {
    public static void main(String[] args) {
        int startingMiners = 5;

        Shop shop = new Shop(startingMiners);

        InputProcessor inputProcessor = new InputProcessor(shop);
        new Thread(inputProcessor).start();

        System.out.println("Program started with five working miners.");
    }
}
