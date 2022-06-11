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
    private static void flett(int[] a, int[] b, int fra, int m, int til)
    {
        int n = m - fra;                // antall elementer i a[fra:m>
        System.arraycopy(a,fra,b,0,n);  // kopierer a[fra:m> over i b[0:n>

        int i = 0, j = m, k = fra;      // løkkevariabler og indekser

        while (i < n && j < til)        // fletter b[0:n> og a[m:til> og
        {                               // legger resultatet i a[fra:til>
            a[k++] = b[i] <= a[j] ? b[i++] : a[j++];
        }

        while (i < n) a[k++] = b[i++];  // tar med resten av b[0:n>
    }
    private static void flettesortering(int[] a, int[] b, int fra, int til)
    {
        if (til - fra <= 1) return;   // a[fra:til> har maks ett element
        int m = (fra + til)/2;        // midt mellom fra og til

        flettesortering(a,b,fra,m);   // sorterer a[fra:m>
        flettesortering(a,b,m,til);   // sorterer a[m:til>

        if (a[m-1] > a[m]) flett(a,b,fra,m,til);  // fletter a[fra:m> og a[m:til>
    }
    public static void flettesortering(int[] a)
    {
        int[] b = Arrays.copyOf(a, a.length/2);   // en hjelpetabell for flettingen
        flettesortering(a,b,0,a.length);          // kaller metoden over
    }
    public static  <T extends Comparable<? super T>> void bubblesort(T[] liste){
        Comparator c = Comparator.naturalOrder();
        for(int i = 0; i < liste.length; i++){
            for(int j = 1; j < liste.length; j++) {
                if (c.compare(liste[j - 1], liste[j]) > 0) {
                    bytt(liste, j - 1, j);
                }
            }
        }
    }
    public static  <T extends Comparable<? super T>> void bytt(T[] liste, int i, int j){
        T temp = liste[i];
        liste[i] = liste[j];
        liste[j] = temp;
    }
    public static void main(String[] args){
        Integer [] a = {4,6,1,2};
        bubblesort(a);
        System.out.println(Arrays.toString(a));
    }

}
