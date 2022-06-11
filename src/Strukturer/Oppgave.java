package Strukturer;

@FunctionalInterface
public interface Oppgave<T> {
    void utførOppgave(T t);    // f.eks. utskrift til konsollet
}
