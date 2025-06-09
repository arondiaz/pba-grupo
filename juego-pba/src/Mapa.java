public class Mapa {
    int filas;
    int columnas;
    Celda[][] celdas;

    public Mapa(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        celdas = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++)
                celdas[i][j] = new Celda();
    }

    public boolean esPosicionValida(int x, int y) {
        return x >= 0 && x < filas && y >= 0 && y < columnas;
    }

    public boolean estaOcupado(int x, int y) {
        return celdas[x][y].personaje != null || celdas[x][y].puertaBloqueada;
    }

    public void colocarPersonaje(Personaje p) {
        Posicion pos = p.getPosicion();
        celdas[pos.x][pos.y].personaje = p;
    }

    /*public void moverPersonaje(Personaje p, Posicion nuevaPos) {
        Posicion vieja = p.getPosicion();
        celdas[vieja.x][vieja.y].personaje = null;
        p.posicion = nuevaPos;
        celdas[nuevaPos.x][nuevaPos.y].personaje = p;
    }*/

    public void colocarItem(Item item, int x, int y) {
        celdas[x][y].item = item;
    }

    public Item recogerItem(int x, int y) {
        Item it = celdas[x][y].item;
        celdas[x][y].item = null;
        return it;
    }

    public void bloquearPuerta(int x, int y) {
        celdas[x][y].puertaBloqueada = true;
    }

    /*public void desbloquearPuerta(int x, int y) {
        celdas[x][y].puertaBloqueada = false;
    }*/

    public void imprimirMapa(Posicion snakePos, Personaje[] enemigos) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (snakePos.x == i && snakePos.y == j) {
                    System.out.print("S ");
                } else {
                    boolean enemigoAqui = false;
                    for (int k = 0; k < enemigos.length; k++) {
                        Posicion p = enemigos[k].getPosicion();
                        if (p.x == i && p.y == j) {
                            System.out.print("G ");
                            enemigoAqui = true;
                            break;
                        }
                    }
                    if (enemigoAqui) continue;
                    // llave
                    if (celdas[i][j].item != null) System.out.print("L ");
                    // puerta
                    else if (celdas[i][j].puertaBloqueada) System.out.print("H ");
                    else System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public void imprimirMapaConPuerta(Posicion snakePos, Personaje[] enemigos, Posicion posPuerta) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (snakePos.x == i && snakePos.y == j) {
                    System.out.print("S ");
                } else if (posPuerta.x == i && posPuerta.y == j) {
                    System.out.print("P ");
                } else {
                    boolean enemigoAqui = false;
                    for (int k = 0; k < enemigos.length; k++) {
                        Posicion p = enemigos[k].getPosicion();
                        if (p.x == i && p.y == j) {
                            System.out.print("G ");
                            enemigoAqui = true;
                            break;
                        }
                    }
                    if (enemigoAqui) continue;
                    if (celdas[i][j].item != null) {
                        if (celdas[i][j].item.tipo.equals("C4")) {
                            System.out.print("C ");
                        }
                    } else if (celdas[i][j].puertaBloqueada) {
                        System.out.print("H ");
                    } else {
                        System.out.print(". ");
                    }
                }
            }
            System.out.println();
        }
    }
}
