package com.company.Print;

import java.text.DecimalFormat;

public class Printer {
    public void print(double[][] mass, int[] variables, int[] new_v, int[]basicVariables){

        int k = 1;

        System.out.print("      ");
        for (int i = 0; i < mass[0].length; i++) {
            if(i < new_v.length)System.out.print("x" + (new_v[i]));
            else{
                if(i < mass[0].length-1){
                    System.out.print("x" + (int)(new_v[new_v.length-1] + k));
                    k++;
                }else
                    System.out.print("B");
            }

            System.out.print("     ");
        }
        System.out.println("");
        for (int i = 0; i < mass.length; i++){
            if (i < basicVariables.length )
                System.out.print("x" + basicVariables[i] + " | ");
            else System.out.print("Z  | ");
            for (int j = 0; j < mass[i].length; j++){
                System.out.printf("%4.1f",mass[i][j]);
                System.out.print(" | ");
            }
            System.out.println("");

            for (int j = 0; j < mass[i].length; j++)
                System.out.print("-------");
            System.out.print("---");
            System.out.println("");
        }
        System.out.println("");
    }
}
