public  class Posicion {
    int x;
    int y;
    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean equals(Object o) {
        if (!(o instanceof Posicion)) return false;
        Posicion p = (Posicion) o;
        return this.x == p.x && this.y == p.y;
    }
}
