package Ekstremalpunkter;

import java.util.Comparator;

public class Ekstremalpunkter {
    public static <T extends Comparable<?super T>> T max(T[] liste){
        T max = liste[0];
        Comparator c = Comparator.naturalOrder();
        for(int i = 1; i < liste.length; i++){
            if(c.compare(liste[i], max) > 0){
                max = liste[i];
            }
        }
        return max;
    }
    public static <T extends Comparable<?super T>> T min(T[] liste){
        T min = liste[0];
        Comparator c = Comparator.naturalOrder();
        for(int i = 1; i < liste.length; i++){
            if(c.compare(liste[i], min) < 0){
                min = liste[i];
            }
        }
        return min;
    }
    public static void main(String [] args){
        Integer [] a = {2,4,11,2,9};
        System.out.println(max(a));
    }
}
