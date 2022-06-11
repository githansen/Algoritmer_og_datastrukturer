package Strukturer;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class TabellKø <T> implements Kø<T>{
    private T[] a;      // en tabell
    private int fra;    // posisjonen til den første i køen
    private int til;    // posisjonen til første ledige plass

    @SuppressWarnings("unchecked")      // pga. konverteringen: Object[] -> T[]
    public TabellKø(int lengde)
    {
        if (lengde < 1)
            throw new IllegalArgumentException("Må ha positiv lengde!");

        a = (T[])new Object[lengde];

        fra = til = 0;    // a[fra:til> er tom
    }

    public TabellKø()   // standardkonstruktør
    {
        this(8);
    }

    @Override
    public boolean leggInn(T verdi) {
        a[til] = verdi;                                 // ny verdi bakerst
        til++;                                          // øker til med 1
        if (til == a.length) til = 0;                   // hopper til 0
        if (fra == til) a = utvidTabell(2*a.length);    // sjekker og dobler
        return true;                                    // vellykket innlegging
    }
    private T[] utvidTabell(int lengde)
    {
        @SuppressWarnings("unchecked")      // pga. konverteringen: Object[] -> T[]
        T[] b = (T[])new Object[lengde];  // ny tabell

        // kopierer intervallet a[fra:a.length> over i b
        System.arraycopy(a,fra,b,0,a.length - fra);

        // kopierer intervallet a[0:fra> over i b
        System.arraycopy(a,0,b,a.length - fra, fra);

        fra = 0; til = a.length;
        return b;
    }
    @Override
    public T kikk() {
        if (fra == til)
            throw new NoSuchElementException("Køen er tom!");
        return a[fra];
    }

    @Override
    public T taUt() {
        if (fra == til) throw new         // sjekker om køen er tom
                NoSuchElementException("Køen er tom!");

        T temp = a[fra];                  // tar vare på den første i køen
        a[fra] = null;                    // nuller innholdet
        fra++;                            // øker fra med 1
        if (fra == a.length) fra = 0;     // hopper til 0
        return temp;                      // returnerer den første
    }

    @Override
    public int antall() {
        return fra <= til ? til - fra : a.length + til - fra;
    }

    @Override
    public boolean tom() {
        return til == fra;
    }

    @Override
    public void nullstill() {
        while (fra != til)
        {
            a[fra++] = null;
            if (fra == a.length) fra = 0;
        }
    }
    public String toString()
    {
        if (til == fra) return "[]";

        int sfra = fra, stil = til;

        StringBuilder s = new StringBuilder();
        s.append('[').append(a[sfra]);
        sfra++;
        if (sfra == a.length) sfra = 0;

        while (sfra != stil)
        {
            s.append(',').append(' ').append(a[sfra]);
            sfra++;
            if (sfra == a.length) sfra = 0;
        }

        s.append(']');

        return s.toString();
    }
    public void snu(){
        Kø<T> hjelp1 = new TabellKø<T>(a.length);
        int n = antall()-1;
        while (n >= 0){
            for(int j = 0; j < n; j++) leggInn(taUt());
            hjelp1.leggInn(taUt());
            n--;
        }
        while(hjelp1.antall() > 0) leggInn(hjelp1.taUt());
    }
    public static <T> T min(Kø<T> kø, Comparator<? super T> c){
        T temp;
        T min = kø.kikk();
        Kø<T> hjelp = new TabellKø<>();

        while(kø.antall() > 0){
            temp = kø.kikk();
            hjelp.leggInn(kø.taUt());
            if (c.compare(temp, min) < 0) min = temp;
        }
        while(hjelp.antall() > 0) kø.leggInn(hjelp.taUt());
        return min;
    }
    public static <T> T fjernMin(Kø<T> kø, Comparator<? super T> c){
        T min = min(kø,c);
        T temp = kø.kikk();
        Kø<T> hjelp  = new TabellKø<>();
        while(temp != min){
            hjelp.leggInn(kø.taUt());
            temp = kø.kikk();
        }
        if(kø.antall() > 0) {
            kø.taUt();
        }
        while(hjelp.antall() > 0) kø.leggInn(hjelp.taUt());
        return min;
    }
    public int indeksTil(T verdi){
        for(int i = fra; i < til; i++){
            if(a[i] == verdi) return i;
        }
        return -1;
    }
    public static<T> void snu(Kø<T> a){
        Stakk<T> hjelp = new TabellStakk<>();
        while(a.antall() > 0) hjelp.leggInn(a.taUt());
        while(hjelp.antall() > 0)a.leggInn(hjelp.taUt());
    }
    public static <T> void sorter(Kø<T> kø, Stakk<T> stakk, Comparator<? super T> c){
        int n = kø.antall();

        while (n > 0)
        {
            stakk.leggInn(kø.taUt());       // kandidat for å være den største

            for (int i = 1; i < n; i++)
            {
                T verdi = kø.taUt();
                if (c.compare(verdi, stakk.kikk()) > 0)
                {
                    kø.leggInn(stakk.taUt());   // fant en som var større - den
                    stakk.leggInn(verdi);       // legges øverst på stakken
                }
                else
                {
                    kø.leggInn(verdi);
                }
            }
            n--;
        }

        while (!stakk.tom()) kø.leggInn(stakk.taUt());  // flytter fra stakk til kø
    }
}
