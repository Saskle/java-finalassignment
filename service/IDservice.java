package service;

public abstract class IDservice {
    
    // generates a random Id between 1 - 10000, so semi-unique 
    protected int generateID() {
        return (int) (Math.random() * 10000 + 1);
    }
}
