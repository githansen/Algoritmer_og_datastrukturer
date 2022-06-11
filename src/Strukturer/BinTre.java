package Strukturer;

import java.util.*;

public class BinTre<T> {
    private static final class Node<T>  // en indre nodeklasse
    {
        private T verdi;            // nodens verdi
        private Node<T> venstre;    // referanse til venstre barn/subtre
        private Node<T> høyre;      // referanse til høyre barn/subtre

        private Node(T verdi, Node<T> v, Node<T> h)    // konstruktør
        {
            this.verdi = verdi; venstre = v; høyre = h;
        }

        private Node(T verdi) { this.verdi = verdi; }  // konstruktør

    } // class Node<T>

    private Node<T> rot;      // referanse til rotnoden
    private int antall;       // antall noder i treet

    public BinTre() { rot = null; antall = 0; }          // konstruktør
    public BinTre(int[] posisjon, T[] verdi)  // konstruktør
    {
        if (posisjon.length > verdi.length) throw new
                IllegalArgumentException("Verditabellen har for få elementer!");

        for (int i = 0; i < posisjon.length; i++) leggInn(posisjon[i],verdi[i]);
    }
    private Node<T> finnNode(int posisjon)  // finner noden med gitt posisjon
    {
        if (posisjon < 1) return null;

        Node<T> p = rot;   // nodereferanse
        int filter = Integer.highestOneBit(posisjon >> 1);   // filter = 100...00

        for (; p != null && filter > 0; filter >>= 1)
            p = (posisjon & filter) == 0 ? p.venstre : p.høyre;

        return p;   // p blir null hvis posisjon ikke er i treet
    }

    public boolean finnes(int posisjon)
    {
        return finnNode(posisjon) != null;
    }

    public void nivåorden(Oppgave<? super T> oppgave)    // ny versjon
    {
        if (tom()) return;                   // tomt tre
        Kø<Node<T>> kø = new TabellKø<>();   // Se Avsnitt 4.2.3
        kø.leggInn(rot);                     // legger inn roten

        while (!kø.tom())                    // så lenge køen ikke er tom
        {
            Node<T> p = kø.taUt();             // tar ut fra køen
            oppgave.utførOppgave(p.verdi);     // den generiske oppgaven

            if (p.venstre != null) kø.leggInn(p.venstre);
            if (p.høyre != null) kø.leggInn(p.høyre);
        }
    }
    public int[] nivåer()   // returnerer en tabell som inneholder nivåantallene
    {
        if (tom()) return new int[0];       // en tom tabell for et tomt tre

        int[] a = new int[8];               // hjelpetabell
        Kø<Node<T>> kø = new TabellKø<>();  // hjelpekø
        int nivå = 0;                       // hjelpevariabel

        kø.leggInn(rot);    // legger roten i køen

        while (!kø.tom())   // så lenge som køen ikke er tom
        {
            // utvider a hvis det er nødvendig
            if (nivå == a.length) a = Arrays.copyOf(a,2*nivå);

            int k = a[nivå] = kø.antall();  // antallet på dette nivået

            for (int i = 0; i < k; i++)  // alle på nivået
            {
                Node<T> p = kø.taUt();

                if (p.venstre != null) kø.leggInn(p.venstre);
                if (p.høyre != null) kø.leggInn(p.høyre);
            }

            nivå++;  // fortsetter på neste nivå
        }

        return Arrays.copyOf(a, nivå);  // fjerner det overflødige
    }
    public void nullstill()
    {
        if (!tom()) nullstill(rot);  // nullstiller
        rot = null; antall = 0;      // treet er nå tomt
    }
    public boolean ermintre( Comparator<? super T> c){
        if(rot == null) return true;
        return mintre(rot, c);
    }
    public String minimumsGrenen(Comparator<? super T> c){
        Node<T> curr = rot;
        StringJoiner sj = new StringJoiner(", ", "[", "]");

        while(curr != null){
            sj.add(curr.verdi+"");
            if(curr.venstre != null && curr.venstre != null){
                if(c.compare(curr.venstre.verdi, curr.høyre.verdi) < 0) curr = curr.venstre;
                else curr = curr.høyre;
            }
            else{
                if(curr.venstre != null) curr = curr.venstre;
                else  curr = curr.høyre;

            }
        }

        return sj.toString();
    }
    public boolean ermintreiterativ(Comparator<? super T> c){
        Node<T> curr = rot;
        Kø <Node <T>> kø = new TabellKø<>();
        kø.leggInn(rot);
        while(!kø.tom()){
            curr = kø.taUt();
            if(curr.venstre != null){
                kø.leggInn(curr.venstre);
                if(c.compare(curr.verdi, curr.venstre.verdi) > 0)return false;
            }
            if(curr.høyre != null){
                kø.leggInn(curr.høyre);
                if(c.compare(curr.verdi, curr.høyre.verdi) > 0)return false;
            }
        }


        return true;
    }
    private boolean mintre(Node<T> q, Comparator<? super T> c){

        if(q.venstre != null){
            if(c.compare(q.verdi, q.venstre.verdi) > 0){
                return false;
            }
            if(!mintre(q.venstre, c)) return false;
        }
        if(q.høyre != null){
            if(c.compare(q.verdi, q.høyre.verdi)>0){
                return false;
            }
            if(!mintre(q.høyre, c)) return false;
        }
        return true;
    }
    private void nullstill(Node<T> p)
    {
        if (p.venstre != null)
        {
            nullstill(p.venstre);      // venstre subtre
            p.venstre = null;          // nuller peker
        }
        if (p.høyre != null)
        {
            nullstill(p.høyre);        // høyre subtre
            p.høyre = null;            // nuller peker
        }
        p.verdi = null;              // nuller verdien
    }
    private static <T> void preorden(Node<T> p, Oppgave<? super T> oppgave)
    {
        if (p != null)  // metoden returnerer hvis p == null
        {
            oppgave.utførOppgave(p.verdi);

            preorden(p.venstre,oppgave);
            preorden(p.høyre,oppgave);
        }
    }
    public void nederstenivå(){
        int x = 0;
        Node<T> curr = rot;
        while(curr.venstre != null && curr.høyre != null){
            while (curr.venstre != null){
                x++;
                curr = curr.venstre;
            }
            while (curr.høyre != null){
                curr = curr.høyre;
                x++;
            }

        }
        nederst(rot, x, 0);
    }
    private void nederst(Node <T> curr, int lavest, int nivå){
        if(nivå == lavest) System.out.print(curr.verdi + " ");
        if(curr.venstre != null) nederst(curr.venstre, lavest, nivå+1);
        if(curr.høyre != null) nederst(curr.høyre, lavest, nivå+1);
    }
    public T  preindeks(int indeks){
        ArrayList<T> liste = new ArrayList<>();
        finn(liste,rot);
        if(indeks > liste.size()) throw new NoSuchElementException();
        return liste.get(indeks-1);
    }
    public  void finn(ArrayList<T> liste, Node<T> p){
        if(p != null){
            liste.add(p.verdi);
            finn(liste,p.venstre);
            finn(liste,p.høyre);
        }
    }
    private static <T> void postorden(Node<T> p, Oppgave<? super T> oppgave)
    {
        if (p.venstre != null) postorden(p.venstre,oppgave);
        if (p.høyre != null) postorden(p.høyre,oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    public void postorden(Oppgave<? super T> oppgave)
    {
        if (rot != null) postorden(rot,oppgave);
    }
    public void preorden(Oppgave <? super T> oppgave)
    {
        preorden(rot,oppgave);
    }
    private static <T> void inorden(Node<T> p, Oppgave<? super T> oppgave)
    {
        if (p.venstre != null) inorden(p.venstre,oppgave);
        oppgave.utførOppgave(p.verdi);
        if (p.høyre != null) inorden(p.høyre,oppgave);
    }

    public void inorden(Oppgave <? super T> oppgave)
    {
        if (!tom()) inorden(rot,oppgave);
    }

    public T fjern(int posisjon){
        if (posisjon < 1) throw new
                IllegalArgumentException("Posisjon(" + posisjon + ") < 1!");

        Node<T> p = rot, q = null;               // hjelpepekere
        int filter = Integer.highestOneBit(posisjon >> 1);   // binært siffer

        while (p != null && filter > 0)
        {
            q = p;
            p = (filter & posisjon) == 0 ? p.venstre : p.høyre;
            filter >>= 1;
        }

        if (p == null) throw new
                IllegalArgumentException("Posisjon(" + posisjon + ") er utenfor treet!");

        if (p.venstre != null || p.høyre != null) throw new
                IllegalArgumentException("Posisjon(" + posisjon + ") er ingen bladnode!");

        if (p == rot) rot = null;
        else if (p == q.venstre) q.venstre = null;
        else q.høyre = null;

        antall--;  //
        return p.verdi;
    }
    public T hent(int posisjon)
    {
        Node<T> p = finnNode(posisjon);

        if (p == null) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") finnes ikke i treet!");

        return p.verdi;
    }

    public T oppdater(int posisjon, T nyverdi)
    {
        Node<T> p = finnNode(posisjon);

        if (p == null) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") finnes ikke i treet!");

        T gammelverdi = p.verdi;
        p.verdi = nyverdi;

        return gammelverdi;
    }
    public ArrayList<T> lastlevel(){
        ArrayList<T> retur = new ArrayList<>();
        last(retur, rot, 2, 0);
        System.out.println(retur.toString());
        return retur;
    }
    private void last(ArrayList<T> retur,Node<T> q, int lastlevel, int currlevel){
        if(lastlevel == currlevel) retur.add(q.verdi);
        if(q.venstre != null)
            last(retur, q.venstre, lastlevel, currlevel+1);
        if(q.høyre != null)
            last(retur, q.høyre, lastlevel, currlevel+1);
    }

    public final void leggInn(int posisjon, T verdi) {
        if (posisjon < 1) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") < 1!");

        Node<T> p = rot, q = null;    // nodereferanser

        int filter = Integer.highestOneBit(posisjon) >> 1;   // filter = 100...00

        while (p != null && filter > 0)
        {
            q = p;
            p = (posisjon & filter) == 0 ? p.venstre : p.høyre;
            filter >>= 1;  // bitforskyver filter
        }

        if (filter > 0) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") mangler forelder!");
        else if (p != null) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") finnes fra før!");

        p = new Node<>(verdi);          // ny node

        if (q == null) rot = p;         // tomt tre - ny rot
        else if ((posisjon & 1) == 0)   // sjekker siste siffer i posisjon
            q.venstre = p;                // venstre barn til q
        else
            q.høyre = p;                  // høyre barn til q

        antall++;                       // en ny verdi i treet
    }

    public int antall() { return antall; }               // returnerer antallet

    public boolean tom() { return antall == 0; }         // tomt tre?
    public String toString()
    {
        StringJoiner s = new StringJoiner(", ", "[", "]");
        if (!tom()) inorden(x -> s.add(x != null ? x.toString() : "null"));
        return s.toString();
    }
    public int maks(Comparator comp){
        Kø kø = new TabellKø();
        kø.leggInn(rot);
        T max = rot.verdi;
        while(!kø.tom()){
            Node temp = (Node) kø.taUt();
            if(comp.compare(temp.verdi,max) > 0) max = (T)temp.verdi;
            if(temp.venstre != null)kø.leggInn(temp.venstre);
            if(temp.høyre != null)kø.leggInn(temp.høyre);
        }
        return (int)max;
    }
}
