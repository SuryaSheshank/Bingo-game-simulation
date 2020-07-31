package com.company;

import java.util.Random;

import static java.lang.System.*;

public class Moderator implements Runnable {
    static int N = Playercount.getInstance();
    private GameInfo gameInfo;
    private int numberAnnounced = 0;

    public Moderator(GameInfo gameInfo) {
        this.gameInfo = gameInfo;

    }

    public void run() {

        int count = 0;
        int[] mod_num = new int[10];

        synchronized (gameInfo.lock) {

            boolean flag = false;
            for(int i=0;i<N;i++) if(gameInfo.playerWinsFlag[i]){
                flag=true;
                break;
            }
            while (!flag && count<10) {
                flag=false;
                count++;

                gameInfo.announced = false;
                for(int i=0;i<N;i++) gameInfo.playerCheckFlag[i]=false;

                boolean isuniq =true;                      //Generating Random numbers
                while(isuniq && count<=10){
                    isuniq=false;
                    RandomNumber.setRandnum(new Random().nextInt(50)+1);
                    numberAnnounced = RandomNumber.getRandnum();
                    mod_num[count-1]=numberAnnounced;
                    for(int i=0;i<count-1;i++) if(numberAnnounced==mod_num[i]){
                      isuniq=true;
                      out.println("Moderator:\t"+numberAnnounced);
                      count++;
                      break;
                    }
                }
                gameInfo.generatedNumber = RandomNumber.getRandnum();
                out.println("Moderator:\t"+ gameInfo.generatedNumber);

                try {                                          //sleep is invoked
                    Thread.sleep(1000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                numberAnnounced = 0;
                gameInfo.announced = true;

                if(gameInfo.announced == true) {
                    gameInfo.lock.notifyAll();
                }
                boolean check = false;
                for(int i=0;i<N;i++)if(!gameInfo.playerCheckFlag[i]){         //Checking if the player has cross checked
                    check=true;
                    break;
                }

                while (check) {
                    check=false;
                    try {
                        gameInfo.lock.wait();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for(int i=0;i<N;i++)if(!gameInfo.playerCheckFlag[i]){
                        check=true;
                        break;
                    }
                }
                for(int i=0;i<N;i++) if(gameInfo.playerWinsFlag[i]){
                    flag=true;
                    break;
                }
            }
            for(int i=0;i<N;i++) out.format("Player - %d score:\t%d\n",i+1, gameInfo.playerScore[i]);
            boolean win=false;
            for(int i=0;i<N;i++){
                if(gameInfo.playerWinsFlag[i]){
                    out.format("Player-%d won\n",i+1);
                    win=true;
                    break;
                }
            }
            if(!win) out.println("No one won");

            gameInfo.gameCompleted = true;


        }
    }
}
