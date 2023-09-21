package service;

public abstract class IDservice {
    
    protected int generateID() {
        // random Id generated between 1 - 10000, so semi-unique 
        return (int) (Math.random() * 10000 + 1);
    }
}
