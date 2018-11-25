package com.company.Input;

import com.company.Input.tools.tools;
import com.company.Manager.Manager;

import java.util.Scanner;

public class Input extends tools{

    private static Scanner in = new Scanner(System.in);
    private static int numOfVariables;      //количество неизвестных
    private static int[] variables;       //названия переменных
    private static boolean flag;            //max или min
    private static String[] signs;          //< , > или =


    public Input() {
        printHello();
    }

    private static void printHello(){
        System.out.println("Данное приложение призвано помочь решать задачи линейного программирования.\n" +
                "Оно поддерживает решения методами\n" +
                "\t1. Симплекс методом\n" +
                "\t2. Двойственным симплекс методом\n" +
                "\t3. Методом исскусственного базаса\n" +
                "все что вам нужно - ввести исходные данные и получить результат. Исходными данными являются:\n" +
                "\t1. Количество неизвестных\n" +
                "\t2. Целевая функция\n" +
                "\t3. Количество функциональных ограничений\n" +
                "\t4. Функциональные ограничения\n" +
                "Примеры ввода целевой функции: \n" +
                "\t1. 1x1 + 2x2 -> min\n" +
                "\t2. 21x1 + 3x2 -> max\n" +
                "Примеры ввода ограничений:\n" +
                "\t1. 2x1+3x2=4\n" +
                "\t2. 9x1+3x2<3\n" +
                "\t3. -4x1 -3x2 > 5\n" +
                "Обратите внимание на то, что в вводимых ограничениях должно быть одно и то же количество неизвестных\n" +
                "Если у вас ограничения вида:\n" +
                "x2 - 4x3 < 4\n" +
                "x1 + 2x4 > 8\n" +
                "Вводите уравнения следующим образом:\n" +
                "0x1 + 1x2 - 4x3 + 0x4 < 4\n" +
                "1x1 + 0x2 + 0x3 + 2x4 > 9\n");
    }

    public void run(){

        System.out.print("Введите количество неизвестных -> ");
        numOfVariables = in.nextInt();

        System.out.print("Введите колиичество ограничений -> ");
        int numOfConstrains = in.nextInt();
        in.nextLine();

        variables = new int[numOfVariables];

        System.out.print("Введите целевую функцию -> ");
        String s = in.nextLine();
        double[] funct = findFumctionNumbers(s);            //коэффициенты функции
        System.out.println("Введите ограничения:");
        double[][] mass = new double[numOfConstrains][numOfVariables];
        signs = new String[numOfConstrains];                //знаки в ограничениях
        for (int i = 0; i < numOfConstrains; i++){
            String str = in.nextLine();
            mass[i] = findConstrainsNumbers(str,i);
        }


        new Manager(toCanonicalForm(mass,signs,flag, funct),funct,variables);

        String tmp = in.nextLine();

    }

    private static double[] findConstrainsNumbers(String str, int index){
        str = deleteSpaces(str);
        char[] buf = str.toCharArray();
        for (int i = 0, k = 0; i < buf.length; i++){
            if(buf[i] == 'x'){
                variables[k] = Integer.parseInt(buf[i+1] + "");
                k++;
                buf[i] = ',';
                buf[i+1] = ' ';
                continue;
            }
            if(buf[i] == '>'){
                signs[index] = ">";
                buf[i] = ' ';
            }
            if(buf[i] == '<'){
                signs[index] = "<";
                buf[i] = ' ';
            }
            if(buf[i] == '='){
                signs[index] = "=";
                buf[i] = ' ';
            }
        }
        str = deleteSpaces(new String(buf));
        double res[] = new double[numOfVariables+1];
        String[] buff = str.split(",");
        for (int i = 0; i<res.length;i++)
            res[i] = Double.parseDouble(buff[i]);
        return res;
    }

    private static double[][] toCanonicalForm(double[][] mass, String[] signs,boolean flag, double[] funct){

        double[][] res = new double[mass.length][mass[0].length+1];

        if(flag)
            for (int i = 0; i < funct.length;i++)
                funct[i] *= -1;

        for (int i = 0; i < signs.length;i++){
            if (signs[i].equals("=")){
                res[i][res[i].length-2] = 0;
            }

            if (signs[i].equals(">")){
                res[i][res[i].length-2] = 1;
                for (int j = 0; j < mass[i].length;j++)
                    mass[i][j] *= -1;
            }
            if(signs[i].equals("<")){
                res[i][res[i].length-2] = 1;
            }
        }

        for (int i = 0; i < res.length;i++){
           for (int j = 0; j < res[i].length;j++){

               if (j < res[i].length-2){
                   res[i][j] = mass[i][j];
               }
               if (j > res[i].length-2){
                    res[i][j] = mass[i][j-1];
               }
           }
        }

        return res;
    }

    private static double[] findFumctionNumbers(String str){
        str = deleteSpaces(str);
        char[] buf = str.toCharArray();
        for (int i = 0; i < buf.length; i++){
            if (buf[i] == 'x'){
                if (isDigit(buf[i+1])){
                    buf[i+1] = ' ';
                    buf[i] = ',';
                    continue;
                }
            }
            if(buf[i] == '-' && buf[i+1] == '>'){
                if(buf[i+3] == 'a') flag = true;
                else  flag = false;
                for (int j = i;j<buf.length;j++){
                    buf[j] = ' ';
                }
            }
        }
        str = deleteSpaces(new String(buf));
        String[] tmp = str.split(",");
        double[] res = new double[tmp.length];
        for (int i = 0; i < res.length;i++)
            res[i] = Double.parseDouble(tmp[i]);
        return res;

    }

}
