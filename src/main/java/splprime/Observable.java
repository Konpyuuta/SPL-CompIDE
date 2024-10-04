package splprime;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maurice Amon
 */

public abstract class Observable {

    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers(String text) {
        for (Observer observer : this.observers) {
            observer.update(text);
        }
    }
}
