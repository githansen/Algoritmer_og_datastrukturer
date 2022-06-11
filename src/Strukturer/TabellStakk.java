package Strukturer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class TabellStakk<T> implements Stakk<T> {
    private T[] a;                     // en T-tabell
    private int antall;                // antall verdier på stakken

    public TabellStakk()               // konstruktør - tabellengde 8
    {
        this(8);
    }

    @SuppressWarnings("unchecked")     // pga. konverteringen: Object[] -> T[]
    public TabellStakk(int lengde)     // valgfri tabellengde
    {
        if (lengde < 0)
            throw new IllegalArgumentException("Negativ tabellengde!");

        a = (T[]) new Object[lengde];     // oppretter tabellen
        antall = 0;                      // stakken er tom
    }

    @Override
    public void leggInn(T verdi) {
        if (antall == a.length)
            a = Arrays.copyOf(a, antall == 0 ? 1 : 2 * antall);   // dobler

        a[antall++] = verdi;
    }

    @Override
    public T kikk() {
        if (antall == 0)       // sjekker om stakken er tom
            throw new NoSuchElementException("Stakken er tom!");

        return a[antall - 1];    // returnerer den øverste verdien
    }

    @Override
    public T taUt() {
        if (antall == 0)       // sjekker om stakken er tom
            throw new NoSuchElementException("Stakken er tom!");

        antall--;             // reduserer antallet

        T temp = a[antall];   // tar var på det øverste objektet
        a[antall] = null;     // tilrettelegger for resirkulering

        return temp;          // returnerer den øverste verdien
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public void nullstill() {
        for (T i : a) {
            i = null;
        }
        antall = 0;
    }

    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for(int i = antall-1; i >= 0; i--){
            if(a[i] != null) sj.add(a[i].toString());
        }
        return sj.toString();
    }

    public static <T> void kopier(Stakk<T> A, Stakk<T> B) {
        T temp;
        Stakk<T> hjelp = new TabellStakk<T>(A.antall());
        while (A.antall() > 0) {
            hjelp.leggInn(A.taUt());
        }
        while (hjelp.antall() > 0) {
            temp = hjelp.kikk();
            A.leggInn(temp);
            B.leggInn(hjelp.taUt());
        }
    }

    public static <T> void snu(Stakk<T> a) {
        Stakk<T> hjelp = new TabellStakk<T>(a.antall());
        Stakk<T> hjelp2 = new TabellStakk<T>(a.antall());
        while (a.antall() != 0) {
            hjelp.leggInn(a.taUt());
        }
        while (hjelp.antall() != 0) {
            hjelp2.leggInn(hjelp.taUt());
        }
        while (hjelp2.antall() != 0) {
            a.leggInn(hjelp2.taUt());
        }
    }

    public static <T> void snu2(Stakk<T> a) {
        T temp;
        Stakk<T> hjelp = new TabellStakk<>(a.antall());
        for (int i = 0; i < a.antall(); i++) {
            temp = a.taUt();
            while (a.antall() > i) {
                hjelp.leggInn(a.taUt());
            }
            a.leggInn(temp);
            while (hjelp.antall() > 0) a.leggInn(hjelp.taUt());
        }
    }

    public static <T> void Kopier2(Stakk<T> a, Stakk<T> b) {
        int n = a.antall();

        while (n > 0) {
            for (int j = 0; j < n; j++) b.leggInn(a.taUt());
            T temp = b.kikk();
            for (int j = 0; j < n; j++) a.leggInn(b.taUt());
            b.leggInn(temp);
            n--;
        }
    }
    public static <T> void sorter(Stakk<T> A, Comparator<? super T> c){
        Stakk<T> B = new TabellStakk<T>();
        T temp; int n = 0;

        while (!A.tom())
        {
            temp = A.taUt();
            n = 0;
            while (!B.tom() && c.compare(temp,B.kikk()) < 0)
            {
                n++; A.leggInn(B.taUt());
            }
            B.leggInn(temp);
            for (int i = 0; i < n; i++) B.leggInn(A.taUt());
        }

        while (!B.tom()) A.leggInn(B.taUt());
    }
}
