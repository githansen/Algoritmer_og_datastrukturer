package Sortering;

import java.util.Arrays;
import java.util.Comparator;

public class Sortering {
    public static  <T extends Comparable<? super T>> void quicksort(T [] liste){
        quicksort0(liste, 0, liste.length-1, Comparator.naturalOrder());
    }
    public static  <T extends Comparable<? super T>> void quicksort0(T [] liste, int fra, int til, Comparator<? super T> c){
        if(fra >= til) return;
        int m = (fra+til)/2;
        bytt(liste, m, til);
        int k = parter(liste, fra, til-1, liste[til], c);
        bytt(liste, k, til);
        quicksort0(liste,fra,k-1, c);
        quicksort0(liste,k+1,til, c);
    }
    public static  <T extends Comparable<? super T>> int parter(T[] liste, int v, int h, T skilleverdi, Comparator<? super T> c){
        while(true) {
            while (v <= h && c.compare(liste[v], skilleverdi) < 0) {
                v++;
            }

            while (v <= h && c.compare(liste[h], skilleverdi) >= 0) {
                h--;
            }
            if(v < h) {
                bytt(liste, v++, h--);
            }
            else return v;
        }
    }
    public static  <T extends Comparable<? super T>> void mergesort(T[] liste){

    }
    public static  <T extends Comparable<? super T>> void bubblesort(T[] liste){

    }
    public static  <T extends Comparable<? super T>> void bytt(T[] liste, int i, int j){
        T temp = liste[i];
        liste[i] = liste[j];
        liste[j] = temp;
    }
    public static void main(String[] args){
        String [] a = {"a", "c", "b"};
        quicksort(a);
        System.out.println(Arrays.toString(a));
    }

}
