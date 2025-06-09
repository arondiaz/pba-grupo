import java.util.Random;
import java.util.Scanner;

public class MisionDos {
    Mapa mapa;
    Snake snake;
    Personaje[] enemigos;
    boolean c4Agarrado = false;
    Posicion posC4;
    Posicion posPuerta;

    public MisionDos() {
        mapa = new Mapa(9, 9);
        snake = new Snake(4, 4);
        mapa.colocarPersonaje(snake);

        posC4 = new Posicion(1, 1);
        mapa.colocarItem(new Item("C4"), posC4.x, posC4.y);

        posPuerta = new Posicion(7, 7);

        Random rand = new Random();
        int cantEnemigos = 4;
        enemigos = new Personaje[cantEnemigos];

        for (int i = 0; i < cantEnemigos; i++) {
            int ex, ey;
            do {
                ex = rand.nextInt(9);
                ey = rand.nextInt(9);
            } while (distancia(ex, ey, snake.getPosicion().x, snake.getPosicion().y) < 2 || !mapa.celdas[ex][ey].estaLibre());

            Guardia g = new Guardia(ex, ey);
            enemigos[i] = g;
            mapa.colocarPersonaje(g);
        }
    }

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

    private boolean enemigosCercaDePuerta() {
        for (int i = 0; i < enemigos.length; i++) {
            Posicion p = enemigos[i].getPosicion();
            if (distancia(p.x, p.y, posPuerta.x, posPuerta.y) <= 3) {
                return true;
            }
        }
        return false;
    }

    public void jugar() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            mapa.imprimirMapaConPuerta(snake.getPosicion(), enemigos, posPuerta);
            System.out.println("Explosivo C4 obtenido: " + c4Agarrado);
            System.out.println("Mover Snake: w=arriba, s=abajo, a=izquierda, d=derecha");
            String input = sc.nextLine();
            int dx = 0, dy = 0;
            switch (input) {
                case "w": dx = -1;
                break;
                case "s": dx = 1;
                break;
                case "a": dy = -1;
                break;
                case "d": dy = 1;
                break;
                default:
                    System.out.println("Entrada inválida");
                    continue;
            }

            int nx = snake.getPosicion().x + dx;
            int ny = snake.getPosicion().y + dy;
            if (!mapa.esPosicionValida(nx, ny)) {
                System.out.println("Movimiento fuera del mapa");
                continue;
            }
            if (mapa.estaOcupado(nx, ny)) {
                System.out.println("Celda ocupada, movimiento inválido");
                continue;
            }

            mapa.celdas[snake.getPosicion().x][snake.getPosicion().y].personaje = null;
            snake.posicion = new Posicion(nx, ny);
            mapa.celdas[nx][ny].personaje = snake;

            if (!c4Agarrado && nx == posC4.x && ny == posC4.y && mapa.celdas[nx][ny].item != null) {
                Item it = mapa.recogerItem(nx, ny);
                if (it.tipo.equals("C4")) {
                    c4Agarrado = true;
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

            if (snake.getPosicion().equals(posPuerta)) {
                if (!c4Agarrado) {
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
