import java.util.Random;
import java.util.Scanner;

public class MisionUno {
    Mapa mapa;
    Snake snake;
    Personaje[] enemigos;
    boolean agarroLaTarjeta = false;
    Posicion posicionTarjeta;
    Posicion posicionPuerta;

    public MisionUno() {
        mapa = new Mapa(7, 7);
        // coloca a snake en el centro del mapa (numeros del 0 al 6) la primera posicion seria 0,0 hasta el 0,6 horizontalmente
        snake = new Snake(3, 3);
        mapa.colocarPersonaje(snake);

        // coloca la tarjeta de acceso en la posicion (1,1)
        posicionTarjeta = new Posicion(1, 1);
        mapa.colocarItem(new Item("Tarjeta"), posicionTarjeta.x, posicionTarjeta.y);

        // coloca la puerta de salida en la posicion (5,5)
        posicionPuerta = new Posicion(5, 5);
        mapa.bloquearPuerta(posicionPuerta.x, posicionPuerta.y);

        // crea 3 enemigos y los coloca posiciones aleatorias, como minimo a 2 posiciones de distancia
        Random rand = new Random();
        int cantidadEnemigos = 3;
        // almacena enemigos en un array
        enemigos = new Personaje[cantidadEnemigos];

        for (int i = 0; i < cantidadEnemigos; i++) {
            int filaEnemigo, columnaEnemigo;
            do {
                filaEnemigo = rand.nextInt(7);
                columnaEnemigo = rand.nextInt(7);
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

    public void jugar() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            // imprime el mapa, snake y a los enemigos
            mapa.imprimirMapa(snake.getPosicion(), enemigos);
            System.out.println("Tarjeta obtenida: " + agarroLaTarjeta);
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

            // calcula la posicion de snake en base al input del jugador
            int nuevaFila = snake.getPosicion().x + movimientoFila;
            int nuevaColumna = snake.getPosicion().y + movimientoColumna;
            // en caso de que se termine el mapa, el movimiento será inválido
            if (!mapa.esPosicionValida(nuevaFila, nuevaColumna)) {
                System.out.println("Movimiento fuera del mapa");
                continue;
            }
            // verifica si no tiene la tarjeta y si la puerta está bloqueada
            if (mapa.celdas[nuevaFila][nuevaColumna].puertaBloqueada && !agarroLaTarjeta) {
                System.out.println("La puerta está bloqueada. Necesitas la tarjeta.");
                continue;
            }

            // elimina a snake de la posicion y lo coloca en la nueva
            mapa.celdas[snake.getPosicion().x][snake.getPosicion().y].personaje = null;
            snake.posicion = new Posicion(nuevaFila, nuevaColumna);
            mapa.celdas[nuevaFila][nuevaColumna].personaje = snake;

            // verifica si hay un item en la nueva posición
            if (mapa.celdas[nuevaFila][nuevaColumna].item != null) {
                Item it = mapa.recogerItem(nuevaFila, nuevaColumna);
                // en caso de que sea una tarjeta, agarroLaTarjeta será true
                if (it.tipo.equals("Tarjeta")) {
                    agarroLaTarjeta = true;
                    System.out.println("Has recogido la tarjeta de acceso.");
                }
            }

            // movimiento del enemigo en el mapa
            for (int i = 0; i < enemigos.length; i++) {
                Personaje e = enemigos[i];
                Posicion posAnt = e.getPosicion();
                // elimina al enemigo de la posición
                mapa.celdas[posAnt.x][posAnt.y].personaje = null;
                // mueve al enemigo hacia snake
                ((Enemigo) e).mover(mapa, snake.getPosicion());
                Posicion posNuevo = e.getPosicion();
                mapa.celdas[posNuevo.x][posNuevo.y].personaje = e;

                // verifica si el enemigo nos detecto
                if (((Enemigo) e).detectarSnake(snake.getPosicion())) {
                    System.out.println("¡Has sido capturado por un guardia! Reiniciando misión...");
                    reiniciar();
                    return;
                }
            }

            // verifica si tiene la tarjeta y esta en la puerta
            if (snake.getPosicion().equals(posicionPuerta) && agarroLaTarjeta) {
                System.out.println("¡Misión completada! Has salido del hangar.");
                System.out.println("***AUTO GUARDADO EXITOSO!***");
                System.out.println("CÓDIGO:MISION2#3389");
                // inicia la siguiente misión
                MisionDos mision2 = new MisionDos();
                mision2.jugar();
                break;
            }
        }
    }

    private void reiniciar() {
        MisionUno nueva = new MisionUno();
        nueva.jugar();
    }
}
