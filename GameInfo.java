package com.company;


public class GameInfo {
    static int N = Playercount.getInstance();
    public int generatedNumber = 0;
    public boolean gameCompleted = false;
    public boolean announced = false;
    public boolean[] playerWinsFlag = new boolean[N];
    public boolean[] playerCheckFlag = new boolean[N];
    public int[] playerScore = new int[N];
    public Object lock = new Object();

}