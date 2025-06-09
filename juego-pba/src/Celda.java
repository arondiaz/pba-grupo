public class Celda {
    Personaje personaje;
    Item item;
    boolean puertaBloqueada;
    public Celda() {
        this.personaje = null;
        this.item = null;
        this.puertaBloqueada = false;
    }
    public boolean estaLibre() {
        return personaje == null && !puertaBloqueada;
    }
}
