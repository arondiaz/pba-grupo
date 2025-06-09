public abstract class Personaje {
    protected Posicion posicion;
    public Personaje(int x, int y) {
        this.posicion = new Posicion(x, y);
    }
    public Posicion getPosicion() {
        return posicion;
    }
    public abstract void mover(Mapa mapa, Posicion snakePos);
}
