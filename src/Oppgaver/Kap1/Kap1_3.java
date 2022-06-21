package Oppgaver.Kap1;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

import static Oppgaver.Kap1.Kap1_2.bytt;

public class Kap1_3 {

    /*
    1.3.1
1.	Legg metodene fra Programkode 1.3.1 a) og Programkode 1.3.1 b) i samleklassen Tabell.
2.	Gitt flg. permutasjoner av tallene fra 1 til 6: a) 2 3 6 1 4 5, b) 2 3 6 1 5 4,
c) 2 3 1 6 5 4, d) 2 3 6 5 4 1 og e) 2 6 5 4 3 1. Finn, for hver av dem, den neste i leksikografisk rekkefølge. Bruk så metoden nestePermutasjon som fasit.
3.	Skriv opp de 10 første permutasjonene som kommer etter 3 1 4 9 7 10 8 6 5 2 leksikografisk. Bruk metoden nestePermutasjon som fasit.
4.	Lag kode som først skriver ut de 6 permutasjonene (én per linje) av tallene 1,2,3. Gjenta dette med de 24 permutasjonene av 1,2,3,4.

    */
    // OPPGAVE 2
    public void opp2(){
        int f [] = {2,3,6,1,4,5};
        int g [] = {2, 3 ,6 ,1,5,4};
        int h [] = {2,3,1,6,5,4};
        int j [] = {2,3,6,5,4,1};
        int k [] = {2,6,5,4,3,1};
        nestePermutasjon(f);
        nestePermutasjon(g);
        nestePermutasjon(h);
        nestePermutasjon(j);
        nestePermutasjon(k);
        System.out.println(Arrays.toString(f));
        System.out.println(Arrays.toString(g));
        System.out.println(Arrays.toString(h));
        System.out.println(Arrays.toString(j));
        System.out.println(Arrays.toString(k));
    }
    //Oppgave 3
    public void opp3(){
        int [] x = {3,1,4,9,7,10,8,6,5,2};
        for(int i = 0; i < 10; i++){
            nestePermutasjon(x);
            System.out.println(Arrays.toString(x));
        }
    }
    //Oppgave 4
    public void opp4(){
        int [] a = {1,2,3};
        int [] b = {1,2,3,4};
        while(nestePermutasjon(a)){
            System.out.println(Arrays.toString(a));
        }
    }
    /*
 1.3.2
1.	Hvor mange inversjoner har premutasjonen 3 5 4 7 6 8 1 2 9 10 ?
2.	Finn en permutasjon av tallene fra 1 til 10 med 22 inversjoner og en som har 23 stykker.

     */
    //OPPGAVE 1
    public int antall(){
        int [] x = {3,5,4,7,6,8,1,2,9,10};
        int sum = 0;
        for(int i = 1; i < x.length; i++){
            if(x[i] > x[i-1]){
                sum++;
            }
        }
        return sum;
    }
    // OPPGAVE 2
    public static void finn(){
        int []x = {1,2,3,4,5,6,7,8,9,10};
        while(nestePermutasjon(x)){
            if(antallinversjoner(x) == 8){
                break;
            }
        }
        System.out.println(Arrays.toString(x));
    }
    /*
    1.3.3
    1. Lag et program som tar et antall n som parameter. Lag et array med n elementer, fra 1 til n i tilfeldig rekkefølge
     */
    // OPPGAVE 1
    public static int [] tilfeldig(int n){
        int [] x = new int[n];
        Arrays.setAll(x, i -> i++);
        Random r = new Random();
        for(int i = 0; i < n; i++){
            bytt(x, i, r.nextInt(n));
        }

        return x;
    }

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


        public static int antallinversjoner(int[] x){
        int sum = 0;
        for(int i = 1; i < x.length; i++){
            if(x[i] > x[i-1])sum++;
        }
        return sum;
        }
    public static void main(String[] args){
        for(int i = 0; i<50; i++) {
            System.out.println(Arrays.toString(tilfeldig(15)));
        }
    }
}
