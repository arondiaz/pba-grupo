public class MetalGear extends Personaje implements Enemigo {
    public MetalGear(int x, int y) {
        super(x, y);
    }
    @Override
    public void mover(Mapa mapa, Posicion snakePos) {
    }
    @Override
    public boolean detectarSnake(Posicion snakePos) {
        int dx = Math.abs(posicion.x - snakePos.x);
        int dy = Math.abs(posicion.y - snakePos.y);
        return (dx + dy == 1);
    }
}
