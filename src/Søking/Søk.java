package Søking;

import java.util.Comparator;

public class Søk {
    public static<T extends Comparable<?super T>> int lineærsøk(T [] liste, T verdi){
       Comparator  c = Comparator.naturalOrder();
            for(int i = 0; i < liste.length; i++){
                if(c.compare(liste[i], verdi) == 0)return i;
            }
       return -1;
    }
    public static <T extends  Comparable<?super T>> int binærsøk(T[] liste, T verdi){
        if(!erSortert(liste)) return -1;
        int v = 0, h = liste.length-1;
        Comparator c = Comparator.naturalOrder();
        while(v <= h){
            int m = (v+h)/2;
            if(c.compare(liste[m], verdi) == 0) return m;
            else if(c.compare(liste[m], verdi) < 0){
                v = m+1;
            }
            else h = m-1;
        }
        return -1;

    }
    public static  <T extends Comparable <?super T>> boolean erSortert(T[] liste){
        Comparator c = Comparator.naturalOrder();
        for(int i = 0; i < liste.length-1; i++){
            if(c.compare(liste[i+1], liste[i]) < 0) return false;
        }
        return true;
    }
    public static void main(String [] args){
        Integer [] a = {1,2,3,4,5,6,11};
        System.out.println(lineærsøk(a,11));
    }
}
