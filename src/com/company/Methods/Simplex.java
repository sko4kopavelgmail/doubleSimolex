package com.company.Methods;

import com.company.Print.Printer;

public class Simplex {

    private static int[] new_v;             //для вывода переменных сверху таблицы
    private static int[] basicVariables;    //базисные переменные

    public Simplex(double[][] mass, double[] funct, int[] variables) {
        init(mass,variables);
        run(formSimplexTable(mass,funct,variables),variables,funct);
    }


    private void init(double[][] mass, int[] variables){
        new_v = new int[variables.length];
        basicVariables = new int[mass.length];
        for (int i = 0; i < new_v.length; i++){
            new_v[i] = variables[i];
        }
        for (int i = 0; i < basicVariables.length;i++)
            basicVariables[i] = variables[variables.length-1]+i+1;
    }

    private double[][] formSimplexTable(double[][] mass, double[] funct, int[] variables) {
        int numOfVariables = variables.length;
        double[][] res = new double[mass.length+1][mass[0].length + mass.length - 1];

        for (int i = 0; i < res.length-1; i++) {
            for (int j = 0; j < res[i].length; j++) {
                if (j < numOfVariables) {
                    res[i][j] = mass[i][j];
                }else{
                    if (i + numOfVariables == j){
                        res[i][j] = 1;
                    }else res[i][j] = 0;
                    if (j == res[i].length-1){
                        res[i][j] = mass[i][mass[i].length-1];
                    }
                }
            }
        }

        for (int i = 0; i < res[0].length;i++){
            if(i < funct.length) res[res.length-1][i] = -1*funct[i];
            else res[res.length-1][i] = 0;
        }


        System.out.println("Начальная симплекс таблица");
        new Printer().print(res,variables,new_v,basicVariables);
        return res;
    }

    private static boolean findDecition(double[][] mass) {
        for (int i = 0; i < mass.length; i++)
            if (mass[mass.length-1][i] > 0) return true;
        return false;
    }

    private static double[][] run(double[][] mass, int[] variables,double[] funct){
        int index = -1;
        int index_2 = -1;
        double basisVariable = 0;

        while (findDecition(mass)){
            double toFindMax = Double.MIN_VALUE;
            //**********step1
            for (int i = 0; i < mass.length; i++) {
                if (mass[mass.length-1][i] > 0) {
                    if (mass[mass.length-1][i] > toFindMax){
                        toFindMax = mass[mass.length-1][i];
                        index=i;
                    }
                }
            }

            //**********step2
            double[] tmp = new double[mass.length];
            for (int i = 0; i < mass.length-1; i++) {
                if (mass[i][index] != 0 && mass[i][index] > 0) {
                    tmp[i] = mass[i][mass[i].length-1] / mass[i][index];
                }
            }
            double toFindmin = Double.MAX_VALUE;
            for (int i = 0; i < tmp.length; i++) {
                if (tmp[i] < toFindmin && tmp[i] != 0) {
                    toFindmin = tmp[i];
                    index_2 = i;
                }
            }
            basisVariable = mass[index_2][index];

            //***********step3
            for (int i = 0; i < mass[index_2].length; i++) {
                mass[index_2][i] /= basisVariable;
            }
            basicVariables[index_2] = index + 1;
            tmp = new double[mass[index_2].length];
            for (int i = 0; i < mass.length; i++) {
                if (i != index_2) {
                    for (int j = 0; j < mass[i].length; j++) {
                        tmp[j] = mass[index_2][j];
                        tmp[j] *= mass[i][index] * -1;
                    }
                    for (int j = 0; j < mass[i].length; j++) {
                        mass[i][j] += tmp[j];
                    }
                }
            }
            new Printer().print(mass,variables,new_v,basicVariables);
        }
        double res = 0;

        for (int i = 0; i < variables.length;i++){
            for (int j = 0;j<basicVariables.length;j++){
                if(variables[i] == basicVariables[j]){
                    res += funct[variables[i]-1] * mass[j][mass[i].length-1];
                }
            }
        }



        System.out.print("Z(x) = ");
        System.out.printf("%6.1f",res);
        System.out.print(" -> min ");


        return mass;
    }

}
