import java.util.Random;

public class Guardia extends Personaje implements Enemigo {
    private Random rand = new Random();

    public Guardia(int x, int y) {
        super(x, y);
    }


    @Override
    public void mover(Mapa mapa, Posicion snakePos) {

        int[][] direcciones = {
                {1, 0},  // derecha
                {-1, 0}, // izquierda
                {0, 1},  // abajo
                {0, -1}  // arriba
        };

        // direcciones aleatorias
        int start = rand.nextInt(direcciones.length);

        for (int i = 0; i < direcciones.length; i++) {
            int idx = (start + i) % direcciones.length;
            int dx = direcciones[idx][0];
            int dy = direcciones[idx][1];
            int nx = posicion.x + dx;
            int ny = posicion.y + dy;

            if (mapa.esPosicionValida(nx, ny) && !mapa.estaOcupado(nx, ny)) {
                posicion.x = nx;
                posicion.y = ny;
                return;
            }
        }
    }

    @Override
    public boolean detectarSnake(Posicion snakePos) {
        // detectar si Snake esta a 1 celda
        int dx = Math.abs(posicion.x - snakePos.x);
        int dy = Math.abs(posicion.y - snakePos.y);
        return (dx + dy == 1);
    }
}

