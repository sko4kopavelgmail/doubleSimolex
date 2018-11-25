package com.company.Methods;

public class ArtificialBasis {

    private static int[] new_v;                 //для вывода переменных сверху таблицы
    private static int[] basicVariables;        //базисные переменные
    private static int[] addedVariables;        //переменные с М
    private static int[] indexOfAddedVafiables; //позиции в массиве добавленных переменных с M

    public ArtificialBasis(double[][] mass, double[] funct, int[] variables) {
        formSimplexTable(mass, funct, variables);
    }

    private void init(double[][] mass, int[] variables){


    }

    private double[][] formSimplexTable(double[][] mass, double[] funct, int[] variables){
        int k = 0;
        for (int i = 0; i < mass.length;i++){
            if(mass[i][mass[i].length-2] == 0) k++;
        }
        indexOfAddedVafiables = new int[k];
        addedVariables = new int[k];

        //запоминаем на каких местах стоит переменная с M
        for (int i = 0; i< mass.length;i++){
            if(mass[i][mass[i].length-2] == 0)indexOfAddedVafiables[i] = i;
        }

        double[][] res = new double[mass.length+2][mass.length+ mass[0].length];

        for (int i = 0; i< mass.length;i++){
            for (int j = 0; j < mass[i].length;j++){
                if (i < variables.length){
                    res[i][j] = mass[i][j];
                }

            }
        }

        return mass;
    }

}
