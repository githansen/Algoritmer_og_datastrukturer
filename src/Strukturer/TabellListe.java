package Strukturer;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TabellListe <T> implements Liste<T>{
    private T[] a;
    private int antall;
    private int endringer;
    // konstruktører og metoder kommer her
    @SuppressWarnings("unchecked")          // pga. konverteringen: Object[] -> T[]
    public TabellListe(int størrelse)       // konstruktør
    {
        a = (T[])new Object[størrelse];       // oppretter tabellen
        antall = 0;                           // foreløpig ingen verdier
    }

    public TabellListe()                    // standardkonstruktør
    {
        this(10);                             // startstørrelse på 10
    }
    public TabellListe(T[] b)                    // en T-tabell som parameter
    {
        this(b.length);                            // kaller den andre konstruktøren

        for (T verdi : b)
        {
            if (verdi != null) a[antall++] = verdi;  // hopper over null-verdier
        }
    }
    public int antall()
    {
        return antall;          // returnerer antallet
    }

    public boolean tom()
    {
        return antall == 0;     // listen er tom hvis antall er 0
    }


    public void nullstill() {
        if(antall > 10){
            antall = 10;
            a = Arrays.copyOf(a,10);
        }
        else{
            for(int i = 0; i < antall; i++) {
                a[i] = null;
            }
            antall = 0;
            endringer++;
        }
    }



    public T hent(int indeks)
    {
        indeksKontroll(indeks, false);   // false: indeks = antall er ulovlig
        return a[indeks];                // returnerer er tabellelement
    }
    public int indeksTil(T verdi)
    {
        for (int i = 0; i < antall; i++)
        {
            if (a[i].equals(verdi)) return i;   // funnet!
        }
        return -1;   // ikke funnet!
    }
    public boolean fjern(T verdi){
        Objects.requireNonNull(verdi, "null er ulovlig!");

        for (int i = 0; i < antall; i++)
        {
            if (a[i].equals(verdi))
            {
                antall--;
                System.arraycopy(a, i + 1, a, i, antall - i);
                a[antall] = null;
                endringer++;
                return true;
            }
        }
        return false;
    }
    @Override
    public T oppdater(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "null er ulovlig!");
        indeksKontroll(indeks, false);  // false: indeks = antall er ulovlig

        T gammelverdi = a[indeks];      // tar vare på den gamle verdien
        a[indeks] = verdi;              // oppdaterer
        endringer++;
        return gammelverdi;             // returnerer den gamle verdien
    }
    @Override
    public T fjern(int indeks) {
        T verdi = a[indeks];
        System.arraycopy(a,indeks+1,a,indeks,antall-indeks-1);
        a = Arrays.copyOf(a,antall-1);
        antall--;
        endringer++;
        return verdi;
    }


    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "null er ulovlig!");
        if(a.length == antall) a = Arrays.copyOf(a,antall + 1);
        a[antall++] = verdi;
        endringer++;
        return true;
    }

    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "null er ulovlig!");
        indeksKontroll(indeks, true);  // true: indeks = antall er lovlig
        if(antall == a.length) a = Arrays.copyOf(a,antall+1);
        System.arraycopy(a,indeks,a,indeks+1,a.length-indeks-1);
        a[indeks] = verdi;
        antall++;
        endringer++;
    }

    public boolean inneholder(T verdi)
    {
        return indeksTil(verdi) != -1;
    }
    public String toString(){
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for(int i = 0; i < antall; i++){

            sj.add( a[i] + "");
        }
        return sj.toString();
    }
    public boolean fjernhvis(Predicate<? super T> p){
        Objects.requireNonNull(p);                       // kaster unntak
        boolean fjernet = false;
        int nyttantall = antall;
        for(int i = 0, j = 0; j < antall; j++){
            if(p.test(a[j])) nyttantall--;
            else a[i++] = a[j];
        }
        for(int i = nyttantall; i <antall; i++){
            a[i] = null;
        }
        fjernet = nyttantall < antall;
        antall = nyttantall;
        return fjernet;
    }
    public Iterator<T> iterator()
    {
        return new TabellListeIterator();
    }
    public void forEach(Consumer<? super T> action)
    {
        for (int i = 0; i < antall; i++)
        {
            action.accept(a[i]);
        }
    }

    private class TabellListeIterator implements Iterator<T>
    {
        private boolean fjernOK = false;
        private int denne = 0;       // instansvariabel
        private int iteratorendringer = endringer;

        public boolean hasNext()     // sjekker om det er flere igjen
        {
            return denne < antall;     // sjekker verdien til denne
        }
        public void forEachRemaining(Consumer<? super T> action)
        {
            while (denne < antall)
            {
                action.accept(a[denne++]);
            }
        }
        public T next()
        {
            System.out.println("Endringer " + endringer + " Iteratorendringer "  +  iteratorendringer);
            if (iteratorendringer != endringer)
            {
                throw new ConcurrentModificationException("Listen er endret!");
            }

            if (!hasNext())
            {
                throw new NoSuchElementException("Tomt eller ingen verdier igjen!");
            }

            T denneVerdi = a[denne];   // henter aktuell verdi
            denne++;                   // flytter indeksen
            fjernOK = true;            // nå kan remove() kalles
            return denneVerdi;         // returnerer verdien
        }

        public void remove()
        {

            if (iteratorendringer != endringer) throw new
                    ConcurrentModificationException("Listen er endret!");

            if (!fjernOK) throw
                    new IllegalStateException("Ulovlig tilstand!");

            fjernOK = false;           // remove() kan ikke kalles på nytt

            // verdien i denne - 1 skal fjernes da den ble returnert i siste kall
            // på next(), verdiene fra og med denne flyttes derfor en mot venstre

            antall--;           // en verdi vil bli fjernet
            denne--;            // denne må flyttes til venstre

            // tetter igjen
            System.arraycopy(a, denne + 1, a, denne, antall - denne);

            a[antall] = null;   // verdien som lå lengst til høyre nulles
            System.out.println("Endringer " + endringer + " Iteratorendringer "  +  iteratorendringer);
            iteratorendringer++;
            endringer++;

        }

    }  // TabellListeIterator
}
