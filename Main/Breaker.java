package Main;

public final class Breaker {
    private boolean status = false;

    public boolean get() {
        return status;
    }

    public void set() {
        this.status = true;
    }

    void reset() {
        this.status = false;
    }

}

