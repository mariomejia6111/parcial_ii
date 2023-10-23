package models;
public class Pair<T, U> {
    private final T attr;
    private final U datum;
    public Pair(T a, U b) {
        this.attr = a;
        this.datum = b;
    }
    public T getT() {
        return attr;
    }
    public U getU() {
        return datum;
    }
}