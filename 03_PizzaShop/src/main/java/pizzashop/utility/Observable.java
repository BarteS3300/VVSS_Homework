package pizzashop.utility;

public interface Observable<E extends Observer> {
    void addObserver(E observer);
    void removeObserver(E observer);
    void notifyObservers();
}
