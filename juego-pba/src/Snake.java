public class Snake extends Personaje{
    public Snake(int x, int y) {
        super(x, y);
    }

    public void mover(Mapa mapa, Posicion snakePos) {
    }
    public void moverHacia(int dx, int dy, Mapa mapa) {
        int nx = posicion.x + dx;
        int ny = posicion.y + dy;
        if (mapa.esPosicionValida(nx, ny)) {
            posicion.x = nx;
            posicion.y = ny;
        } else {
            System.out.println("Movimiento inválido");
        }
    }
}
