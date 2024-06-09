package model;

import model.errors.NotEnoughBalanceExpection;

import java.util.Scanner;

public class InputProcessor implements Runnable {
    private final Shop shop;
    private final Scanner scanner;

    public InputProcessor(Shop shop) {
        this.shop = shop;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Current Hour: " + ThreadClock.getInstance().getHours());
            System.out.println("Current Balance: " + Treasury.getInstance().getBalance());
            System.out.println("What would you like to do?");
            System.out.println("1. Add a new miner");
            System.out.println("2. Upgrade a miner's tool");
            System.out.println("3. Increase a miner's weight threshold");
            System.out.println("4. Buy more space in the mine");
            System.out.println("5. Hydrate all miners");
            System.out.println("6. Print all miners");
            System.out.println("7. Exit");

            System.out.print("choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            try{
                switch (choice) {
                    case 1:
                        shop.addMiners();
                        break;
                    case 2:
                        Miner minerToUpgradeTool = chooseMiner();
                        if (minerToUpgradeTool != null) {
                            shop.upgradeMinerTool(minerToUpgradeTool);
                        }
                        break;
                    case 3:
                        Miner minerToUpgradeWeight = chooseMiner();
                        if (minerToUpgradeWeight != null) {
                            shop.upgradeMinerWeightThreshold(minerToUpgradeWeight);
                        }
                        break;
                    case 4:
                        shop.buyMoreSpace();
                        break;
                    case 5:
                        shop.hydration();
                        break;
                    case 6:
                        System.out.println(shop.printMiners());
                        break;
                    case 7:
                        System.out.println("Exiting the shop.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch(NotEnoughBalanceExpection e){
                System.out.println(e.getMessage());
            }
        }
    }

    private Miner chooseMiner() {
        System.out.println("Choose a miner by number:");
        System.out.println(shop.printMiners());
        System.out.print("choice: ");
        int minerIndex = scanner.nextInt();
        scanner.nextLine();

        if (minerIndex > 0 && minerIndex <= shop.getMiners().size()) {
            return shop.getMiners().get(minerIndex - 1);
        } else {
            System.out.println("Invalid miner number. Please try again.");
            return null;
        }
    }

}
