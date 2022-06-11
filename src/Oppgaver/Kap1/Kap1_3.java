package Oppgaver.Kap1;

import java.util.Arrays;

import static Oppgaver.Kap1.Kap1_2.bytt;

public class Kap1_3 {
    // Finn neste permutasjon
    public static void snu(int[] a, int v, int h)  // snur intervallet a[v:h]
    {
        while (v < h) bytt(a, v++, h--);
    }

    public static void snu(int[] a, int v)  // snur fra og med v og ut tabellen
    {
        snu(a, v, a.length - 1);
    }

    public static void snu(int[] a)  // snur hele tabellen
    {
        snu(a, 0, a.length - 1);
    }
    public static boolean nestePermutasjon(int[] a)
    {
        int i = a.length - 2;                    // i starter nest bakerst
        while (i >= 0 && a[i] > a[i + 1]) i--;   // går mot venstre
        if (i < 0) return false;                 // a = {n, n-1, . . . , 2, 1}

        int j = a.length - 1;                    // j starter bakerst
        while (a[j] < a[i]) j--;                 // stopper når a[j] > a[i]
        bytt(a,i,j); snu(a,i + 1);               // bytter og snur

        return true;                             // en ny permutasjon
    }
    //finn antall inversjoner i en tabell
    public static int inversjoner(int [] a){
        int antall = 0;
        for(int i = 0; i < a.length-1; i++){
            if(a[i+1] < a[i]) antall++;
        }
        return antall;
    }

    public static void main(String[] args){
        int [] a = {1,2,3,4,5,1,4,1};
        System.out.println(inversjoner(a));

        }
}
