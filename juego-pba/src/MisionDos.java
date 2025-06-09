import java.util.Random;
import java.util.Scanner;

public class MisionDos {
    Mapa mapa;
    Snake snake;
    Personaje[] enemigos;
    boolean agarroElC4 = false;
    Posicion posicionC4;
    Posicion posicionPuerta;

    public MisionDos() {
        mapa = new Mapa(9, 9);
        // coloca a snake en la posicion (4,4)
        snake = new Snake(4, 4);
        mapa.colocarPersonaje(snake);

        // coloca el c4 en la posicion (1,1)
        posicionC4 = new Posicion(1, 1);
        mapa.colocarItem(new Item("C4"), posicionC4.x, posicionC4.y);

        // coloca la puerta de salida en la posicion (7,7)
        posicionPuerta = new Posicion(7, 7);

        Random rand = new Random();
        int cantidadEnemigos = 4;
        enemigos = new Personaje[cantidadEnemigos];

        for (int i = 0; i < cantidadEnemigos; i++) {
            int filaEnemigo, columnaEnemigo;
            do {
                filaEnemigo = rand.nextInt(9);
                columnaEnemigo = rand.nextInt(9);
            } while (distancia(filaEnemigo, columnaEnemigo, snake.getPosicion().x, snake.getPosicion().y) < 2 || !mapa.celdas[filaEnemigo][columnaEnemigo].estaLibre());

            Guardia guard = new Guardia(filaEnemigo, columnaEnemigo);
            enemigos[i] = guard;
            mapa.colocarPersonaje(guard);
        }
    }

    // calcula la distancia entre dos puntos
    public int distancia(int x1, int y1, int x2, int y2) {
        int diferenciaX = x1 - x2;
        if (diferenciaX < 0) {
            diferenciaX = -diferenciaX;
        }
        int diferenciaY = y1 - y2;
        if (diferenciaY < 0) {
            diferenciaY = -diferenciaY;
        }
        return diferenciaX + diferenciaY;
    }

    // esta funcion es reemplazada por la de abajo
    // lo vemos en discord cual dejamos

    /*private boolean enemigosCercaDePuerta() {
        for (int i = 0; i < enemigos.length; i++) {
            Posicion p = enemigos[i].getPosicion();
            if (distancia(p.x, p.y, posicionPuerta.x, posicionPuerta.y) <= 3) {
                return true;
            }
        }
        return false;
    }*/

    private boolean enemigosCercaDePuerta() {
        // iterar sobre todos los enemigos del mapa
        for (int i = 0; i < enemigos.length; i++) {
            Posicion posicionEnemigo = enemigos[i].getPosicion();

            // calcular  la distancia entre el enemigo y la puerta
            int distanciaAPuerta = distancia(posicionEnemigo.x, posicionEnemigo.y,
                    posicionPuerta.x, posicionPuerta.y);

            // si el enemigo está a 3 celdas o menos, retornar true
            if (distanciaAPuerta <= 3) {
                return true;
            }
        }

        // caso contrario retorna false
        return false;
    }

    public void jugar() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            mapa.imprimirMapaConPuerta(snake.getPosicion(), enemigos, posicionPuerta);
            System.out.println("Explosivo C4 obtenido: " + agarroElC4);
            System.out.println("Mover Snake: w=arriba, s=abajo, a=izquierda, d=derecha");
            String input = sc.nextLine();
            int movimientoFila = 0, movimientoColumna = 0;
            switch (input) {
                // movimiento hacia arriba
                case "w": movimientoFila = -1;
                break;
                // movimiento hacia abajo
                case "s": movimientoFila = 1;
                break;
                // movimiento hacia izquierda
                case "a": movimientoColumna = -1;
                break;
                // movimiento hacia derecha
                case "d": movimientoColumna = 1;
                break;
                default:
                    System.out.println("Entrada inválida");
                    continue;
            }

            int nuevaFila = snake.getPosicion().x + movimientoFila;
            int nuevaColumna = snake.getPosicion().y + movimientoColumna;
            // en caso de que se termine el mapa, el movimiento será inválido
            if (!mapa.esPosicionValida(nuevaFila, nuevaColumna)) {
                System.out.println("Movimiento fuera del mapa");
                continue;
            }

            if (mapa.estaOcupado(nuevaFila, nuevaColumna)) {
                System.out.println("Celda ocupada, movimiento inválido");
                continue;
            }

            mapa.celdas[snake.getPosicion().x][snake.getPosicion().y].personaje = null;
            snake.posicion = new Posicion(nuevaFila, nuevaColumna);
            mapa.celdas[nuevaFila][nuevaColumna].personaje = snake;

            if (!agarroElC4 && nuevaFila == posicionC4.x && nuevaColumna == posicionC4.y && mapa.celdas[nuevaFila][nuevaColumna].item != null) {
                Item it = mapa.recogerItem(nuevaFila, nuevaColumna);
                if (it.tipo.equals("C4")) {
                    agarroElC4 = true;
                    System.out.println("Has agarrado el explosivo C4.");
                }
            }

            for (int i = 0; i < enemigos.length; i++) {
                Personaje e = enemigos[i];
                Posicion posAnt = e.getPosicion();
                mapa.celdas[posAnt.x][posAnt.y].personaje = null;
                ((Enemigo) e).mover(mapa, snake.getPosicion());
                Posicion posNuevo = e.getPosicion();
                mapa.celdas[posNuevo.x][posNuevo.y].personaje = e;

                if (((Enemigo) e).detectarSnake(snake.getPosicion())) {
                    System.out.println("Has sido capturado por un guardia!");
                    reiniciar();
                    return;
                }
            }

            if (snake.getPosicion().equals(posicionPuerta)) {
                if (!agarroElC4) {
                    System.out.println("Debes agarrar el explosivo C4 antes de salir.");
                } else if (enemigosCercaDePuerta()) {
                    System.out.println("No puedes salir, hay enemigos cerca de la puerta (a menos de 3 celdas).");
                } else {
                    System.out.println("¡Misión completada! Has salido del almacén de armas.");
                    System.out.println("***AUTO GUARDADO EXITOSO!***");
                    System.out.println("CÓDIGO:MISION3#9234");
                    MisionTres mision3 = new MisionTres();
                    mision3.iniciarBatallaFinal();
                    break;
                }
            }
        }
    }

    private void reiniciar() {
        MisionDos nueva = new MisionDos();
        nueva.jugar();
    }
}
