package com.company.Manager;

import com.company.Methods.ArtificialBasis;
import com.company.Methods.DoubleSimplex;
import com.company.Methods.Simplex;

/*
двойственный симплекс метод
-1x1 + 8x2 + 3x3 -> min

3x1 + 1x2 + 2x3 > 6
1x1 + 1x2 + 1x3 > 4
-1x1 + 3x2 -1x3 > 4

3x1 + 2x2 -> min

3x1 + 1x2 > 3
4x1 + 3x2 > 6
1x1 + 2x2 < 3

симплекс метод
10x1+12x2+8x3->max

3x1+4x2+2x3<1020
4x1+3x2+3x3<940
5x1+3x2+5x3<1010

8x1+6x2+3x3->max

5x1+3x2+4x3<960
4x1+2x2+3x3<860
3x1+4x2+2x3<752

метод искусственного базиса
4x1 + 1x2 -> min

2x1+1x2=12
2x1-1x2>8
1x1+1x2<20


методичка

1.
3x1+2x2-6x3->max

2x1-3x2+6x3<18
-3x1+2x2-2x3<24
1x1+3x2-4x3<36


2.

2x1+3x2-1x3->max

2x1-1x2-2x3<16
3x1+2x2-3x3<18
-1x1+3x2+4x3<24

3.

-4x1-7x2-8x3-5x4->max

1x1+1x2+0x3+2x4>4
2x1+1x2+2x3+0x4>6
*/
public class Manager{

    public Manager(double[][] mass, double[] funct, int[] variables) {
        makeAChoise(mass,funct,variables);
    }

    private void makeAChoise(double[][] mass, double[] funct, int[] variables){



        boolean flag = false; // есть ли отрицательные элементы в правой части
        boolean flag1 = false; //есть ли нулевые коэффициенты в базисных переменных

        for (int i = 0; i < mass.length; i++){

            if(mass[i][mass[i].length-2] == 0){
                flag1 = true;
                break;
            }

            if(mass[i][mass[i].length-1] < 0){
                flag = true;
            }

        }

        if(flag1){
            printHello_A();
            new ArtificialBasis(mass,funct,variables);
        }else{
            if (flag){
                printHello_DS();
                new DoubleSimplex(mass,funct,variables);
            }
            else{
                printHello();
                new Simplex(mass,funct,variables);
            }
        }

    }

    private void printHello_A(){
        System.out.println("Так как в результате приведения к каноническому виду мы не можем решать ни симплекс методом,\n" +
                "ни двойственным симплекс методом, применим метод искусственного базиса.\n");
    }

    private void printHello_DS(){
        System.out.println("\nТак как в результате приведения к каноническому виду в правой части присутствуют отрицательные элементы, \n" +
                "будем решать двойственным симплекс методом\n");
    }

    private void printHello(){
        System.out.println("\nТак как в результате приведения к каноническому виду в правой части отсутствуют отрицательные элементы, \n" +
                "будем решать симплекс методом\n");
    }

}
