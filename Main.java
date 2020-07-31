package com.company;

public class Main{
       static int N = Playercount.getInstance();
       public static void main(String[] args) {

              final GameInfo game = new GameInfo();
              final Moderator moderator = new Moderator(game);

              final Player[] play = new Player[N];
              for(int i=0;i<N;i++) play[i] = new Player(game,i);

              Thread moderatorthread = new Thread(moderator);
              Thread[] players = new Thread[N];
              for(int i=0;i<N;i++)players[i] = new Thread(play[i]);

              moderatorthread.start();

              for(int i=0;i<N;i++) players[i].start();
       }
}