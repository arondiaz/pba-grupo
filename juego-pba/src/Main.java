import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int misionesCompletadas = 0;

        while (true) {
            System.out.println("--- Escape de la Base ---");
            System.out.println("1. Iniciar misión");
            System.out.println("2. Cargar estado");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    if (misionesCompletadas == 0) {
                        MisionUno mision1 = new MisionUno();
                        mision1.jugar();
                        misionesCompletadas = 1;
                    }
                    break;
                case "2":
                    System.out.println("INTRODUCE EL CÓDIGO:");
                    String cargar = sc.nextLine();
                    switch (cargar) {
                        case "MISION2#3389":
                            System.out.println("***PARTIDA CARGADA CORRECTAMENTE***");
                            System.out.println("MISIÓN 2");
                            MisionDos mision2 = new MisionDos();
                            mision2.jugar();
                            break;
                        case "MISION3#9234":
                            System.out.println("***PARTIDA CARGADA CORRECTAMENTE***");
                            System.out.println("MISIÓN 3");
                            MisionTres mision3 = new MisionTres();
                            mision3.iniciarBatallaFinal();
                            break;
                        default:
                            System.out.println("CÓDIGO INCORRECTO");
                            break;
                    }
                    break;
                case "3":
                    System.out.println("Saliendo del juego. ¡Hasta luego!");
                    sc.close();
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}
