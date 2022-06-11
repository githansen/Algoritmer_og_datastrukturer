package Oppgaver.Kap1;

import Ekstremalpunkter.Ekstremalpunkter;

import java.util.ArrayList;
import java.util.Arrays;

public class Kap1_2 {
    /*
    Lag metoden public static void bytt(char[] c, int i, int j).
     Den skal bytte om innholdet i posisjon i og j  i char-tabellen c.
     */
    public static void bytt(char[] c, int i, int j){
        char temp = c[i];
        c[i] = c[j];
        c[j] = temp;
    }
    public static void bytt(int[] c, int i, int j){
        int temp = c[i];
        c[i] = c[j];
        c[j] = temp;
    }
    /* 	Lag metoden public static void skriv(int[] a, int fra, int til).
     Den skal skrive ut tallene i intervallet a[fra:til> til konsollet -
     alle på én linje og et mellomrom mellom hvert tall.
     Ikke mellomrom og ikke linjeskift etter siste verdi.
     Lag så metoden public static void skriv(int[] a). Den skal skrive ut hele tabellen - alle på én linje, en blank mellom hvert tall.
      Ikke mellomrom og ikke linjeskift etter siste verdi.*/
    public static void skriv(int [] a, int fra, int til){
        StringBuilder sb = new StringBuilder();
        for(int i = fra; i <= til; i++){
            sb.append(a[i] + " ");
        }
        System.out.print(sb.toString());
    }
    public static void skriv(int []a){
        skriv(a, 0, a.length-1);
    }


    //Som i Oppgave 4 men med en tabell c av typen char[]
    public static void skriv(char [] a, int fra, int til){
        StringBuilder sb = new StringBuilder();
        for(int i = fra; i <= til; i++){
            sb.append(a[i] + " ");
        }
        System.out.print(sb.toString());
    }
    public static void skriv(char []a){
        skriv(a, 0, a.length-1);
    }

    /*
    Lag metoden public static int[] naturligeTall(int n). Den skal returnere en heltallstabell som inneholder tallene 1, 2, . . . , n.
     Hvis n er mindre enn 1 skal det kastes et unntak. Lag også den mer generelle metoden public static int[] heleTall(int fra, int til).
      Den skal returnere en heltallstabell som inneholder tallene fra og med fra og til, men ikke med, tallet til. For eksempel skal kallet heleTall(1,6)
       gi tabellen {1, 2, 3, 4, 5}. Hvis fra er større enn til kastes et unntak. Hvis fra er lik til returneres en tom tabell
     */
    public static int[] naturligeTall(int n){
        if(n < 1) throw new ArrayIndexOutOfBoundsException("n kan ikke være mindre enn 1");
        int [] a = new int[n];
        for(int i = 0; i < n; i++){
            a[i] = i+1;
        }
        return a;
    }
    public static int[] heleTall(int fra, int til){
        if(fra >= til) throw new ArrayIndexOutOfBoundsException("fra kan ikke være større eller lik til");
        int [] a = new int[til-fra];
        for(int i = 0; fra < til; fra++, i++){
            a[i] = fra;
        }
        return a;
    }
    //	Lag metoden public static void snu(int[] a, int v, int h). Metoden skal snu rekkefølgen på verdiene i intervallet a[v:h].
    public static void snu(int [] a, int v, int h){
        if(v>=h)    throw new ArrayIndexOutOfBoundsException("v må være mindre enn h");
       while(v<h){
           bytt(a,v++,h--);
       }
    }
    // gjør det samme som over, med char
    public static void snu(char[] a, int v, int h){
        if(v>=h)    throw new ArrayIndexOutOfBoundsException("v må være mindre enn h");
        while(v<h){
            bytt(a,v++,h--);
        }
    }
    // returner et array med Nest størst og størst verdi
    public static int[] nestMaks(int[] a){
        int maks = a[0];
        int nmaks = Integer.MIN_VALUE;
        for(int i = 1; i < a.length; i++){
            if(a[i] > nmaks){
                if(a[i] > maks){
                    nmaks = maks;
                    maks = a[i];
                }
                else{
                    nmaks = a[i];
                }
            }
        }
        return new int []{nmaks, maks};
    }
        public static void main(String[] args){
            int [] a = {1,2,3,4,5,6,7,8,9,10};

            System.out.println(Arrays.toString(nestMaks(a)));

        }
}

