public interface Enemigo {
    void mover(Mapa mapa, Posicion snakePos);
    boolean detectarSnake(Posicion snakePos);
}
