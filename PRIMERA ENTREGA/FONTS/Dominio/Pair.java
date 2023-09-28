package Dominio;

public class Pair<T1, T2> {
    private T1 primero;
    private T2 segundo;

    public Pair(T1 primero, T2 segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }

    public T1 obtenPrimero() {
        return primero;
    }

    public T2 obtenSegundo() {
        return segundo;
    }

    public void informaPrimero(T1 primero) {
        this.primero = primero;
    }

    public void informaSegundo(T2 segundo) {
        this.segundo = segundo;
    }

}
