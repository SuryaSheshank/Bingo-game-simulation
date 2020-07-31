package com.company;
// Singleton Design pattern
public class Playercount {
    static int num = 5;

    private Playercount(){

    }
    public static int getInstance()
    {
        return num;
    }
}
