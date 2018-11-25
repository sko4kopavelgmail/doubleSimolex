package com.company.Input.tools;

public class tools {

    protected static String deleteSpaces(String str){
        return str.replaceAll("\\s+","");
    }

    protected static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected static boolean isDigit(char c){
        if (!(c >= '0' && c <= '9')) return false;
        else return true;
    }
}
