package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Player implements Runnable {

    private int id;
    private GameInfo gameInfo;
    private int totalNumbersFound;
    private final static int MAXNO = 10;       //10 numbers in a ticket

    private int[] ticket = new int[MAXNO];

    public Player(GameInfo gameInfo, int id) {

        this.id = id;
        this.gameInfo = gameInfo;
        this.totalNumbersFound = 0;

        ArrayList numbers = new ArrayList();
        for(int i = 0;i<50;i++) numbers.add(i+1);
        Collections.shuffle(numbers);
        for(int j = 0;j < MAXNO;j++) {
            ticket[j] = (int) numbers.get(j);
        }
        System.out.format("Player - %d :",id+1);

        for(int i = 0;i<MAXNO;i++){
            System.out.format("%d " ,ticket[i]);
        }
        System.out.println();
    }

    public void run() {
        synchronized(gameInfo.lock) {
            while(!gameInfo.gameCompleted) {
               while(!gameInfo.announced || gameInfo.playerCheckFlag[id]) {
                    try {
                       gameInfo.lock.wait();
                   } catch (InterruptedException e) {
                        e.printStackTrace();
                   }
               }
               if(!gameInfo.gameCompleted) {
                    for(int i = 0; i < MAXNO; i++) {
                        if(gameInfo.generatedNumber == ticket[i]) {
                            this.totalNumbersFound++;
                            gameInfo.playerScore[id]=this.totalNumbersFound;
                            break;
                        }
                    }
                    if(this.totalNumbersFound == 3) {
                        gameInfo.playerWinsFlag[this.id] = true;
                    }
                    gameInfo.playerCheckFlag[id] = true;
                    gameInfo.lock.notifyAll();
               }
            }
        }
    }
}


