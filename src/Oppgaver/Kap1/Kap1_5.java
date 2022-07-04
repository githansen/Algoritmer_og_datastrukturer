package Oppgaver.Kap1;
public class Kap1_5 {
    //Oppgaver om rekursjon
    public static int fakultet(int n){
        if(n == 1) return 1;
        return n*fakultet(n-1);
    }
    //Finn tverrsummen rekursivt
    public static int tverrsum(int n){
        if (n == 0) return 0;
        return n % 10 + tverrsum(n/10);
    }



    public static int rekursBinsøk(int [] a , int v, int h, int verdi){
        if(v>h) return -(v+1);
        int m = (v+h)/2;
        if(a[m] == verdi) return m;
        else if(a[m] > verdi){
            return rekursBinsøk(a,v,m-1,verdi);
        }
        else {
            return rekursBinsøk(a,m+1,h,verdi);
        }

    }

    //1.5.1
    /*
    Oppgave 1: Lag en rekursiv versjon av metoden a i Programkode 1.5.1 a).
    Oppgave 3: Programkode 1.5.1 b) er rekursiv. Lag en iterativ løsning. (tverrsum)
    Oppgave 4:
        gjentatt tverrsum til et tall n får vi ved å ta tverrsummen til n, så tverrsummen av dette, osv.
        til vi står igjen med et tall med kun ett siffer. Vi bruker navnet sifferrot på dette istedenfor gjentatt tverrsum.
        Det svarer til det engelske navnet digital root. Ta tallet 956847 som eksempel: tverrsum(956847) = 39, tverrsum(39) = 12
        og tverrsum(12) = 3. Dermed blir sifferrot(956847) = 3. Lag metoden public static int sifferrot(int n). Den skal returnere sifferroten til n.

    Oppgave 6: b)  Lag en iterativ versjon av Euklids algoritme.
    Oppgave 7: 	Lag en rekursiv metode som finner summen av kvadrattallene fra 1 til n, dvs. finner summen 1^2 + 2^2 + 3^2 + . . . + n^2
    Oppgave 8: Summen av heltallene fra 1 til n er et spesialtilfelle av det å finne summen av heltallene fra k til n der k <= n.
    Lag en metode public static int sum(int k, int n) som finner denne summen, og gjør det ved å bruke «splitt og hersk».
     */

    //Oppgave 1
    public static int a(int n)           // n må være et ikke-negativt tall
    {

        if (n == 0) return 1;              // a0 = 1
        else if (n == 1) return 2;         // a1 = 2
        else return 2*a(n-1) + 3*a(n-2);   // to rekursive kall
    }
    //Oppgave 3
    public static int tverrsumIterativ(int n){
        int sum = 0;
        while(n > 0){
            sum += n%10;
            n = n/10;
        }
        return sum;
    }
    //Oppgave 4
    public static int sifferrot(int n){
        while(n > 10){
            n = tverrsum(n);
        }

        return n;
    }
    // Oppgave 6
    public static int euklid(int a, int b)
    {
        if (b == 0) return a;
        int r = a % b;            // r er resten
        return euklid(b,r);       // rekursivt kall
    }
    public static int euklidIterativ(int a, int b){
        while(b != 0){
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    //Oppgave 7
    public static int kvadratsum(int n){
        if(n == 1) return 1;
        return (int)Math.pow(n,2) + kvadratsum(n-1);
    }

    public static void main(String [] args){
        System.out.println(euklid(362, 251));
        System.out.println(euklidIterativ(362,251));
    }
}
