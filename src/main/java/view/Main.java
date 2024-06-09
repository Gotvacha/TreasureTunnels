package view;

import model.InputProcessor;
import model.Shop;
import model.ThreadClock;

public class Main {
    public static void main(String[] args) {
        ThreadClock threadClock = ThreadClock.getInstance();
        threadClock.start();
        int startingMiners = 5;

        Shop shop = new Shop(startingMiners);

        InputProcessor inputProcessor = new InputProcessor(shop);
        new Thread(inputProcessor).start();

        System.out.println("Program started with five working miners.");
    }
}
