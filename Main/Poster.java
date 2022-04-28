package Main;


public final class Poster<T> {
    private T status;

    public Poster(T status) {
        this.status = status;
    }

    public T get() {
        return status;
    }

    void set(T status) {
        this.status = status;
    }
}
