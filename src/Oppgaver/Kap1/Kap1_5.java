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
    // SUM av N^2 + (N-1)^2 + (N-2)^2 etc.
    public static int kvadratsum(int n){
        if(n == 1) return 1;
        return (int) (Math.pow(n,2) + kvadratsum(n-1));
    }
    public static int euklid(int a, int b)
    {
        if (b == 0) return a;
        int r = a % b;            // r er resten
        return euklid(b,r);       // rekursivt kall
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
    public static void main(String [] args){
        int [] a = {1,2,3,4,5};
        System.out.println( rekursBinsøk(a,0,a.length-1,1));
    }
}
