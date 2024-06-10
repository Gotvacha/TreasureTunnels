package view;

import model.InputProcessor;
import model.Mine;
import model.Miner;
import model.ThreadClock;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        ThreadClock threadClock = ThreadClock.getInstance();
//        List<Miner> miners = new ArrayList<>();
//        Mine mine = new Mine(3);
//
//        threadClock.start();
//
//        for (int i = 0; i < 3; i++) {
//            Miner newMiner = new Miner(mine);
//            miners.add(newMiner);
//            new Thread(newMiner).start();
//        }

//        while(true){
//            for(Miner miner : miners){
//                System.out.println(miner.toString());
//            }
//        }
        InputProcessor inputProcessor = new InputProcessor();
        new Thread(inputProcessor).start();
    }
}
