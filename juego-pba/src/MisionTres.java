import java.util.Random;
import java.util.Scanner;

public class MisionTres {
    public void iniciarBatallaFinal() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int vidaSnake = 100;
        int vidaREX = 100;

        System.out.println("¡Metal Gear REX te quiere destruir!");
        System.out.println("Tu vida: " + vidaSnake + " HP | Vida de REX: " + vidaREX + " HP");

        while (vidaSnake > 0 && vidaREX > 0) {
            System.out.println("Turno de Snake:");
            System.out.println("1 - Disparar misil");
            System.out.println("2 - Esquivar");
            System.out.print("Elige una opción: ");

            int opcion = scanner.nextInt();

            if (opcion == 1) {
                int dano = 10 + random.nextInt(34);
                vidaREX -= dano;
                System.out.println("¡Le diste a REX! (-" + dano + " HP)");
            } else if (opcion == 2) {
                System.out.println("¡Intentas esquivar!");
            } else {
                System.out.println("Opción inválida. Pierdes el turno.");
            }

            if (vidaREX <= 0) break;

            System.out.println("Vida de REX: " + vidaREX + " HP");

            // Turno de REX
            int ataqueREX = 15 + random.nextInt(26);
            int danoRecibido;

            if (opcion == 2) {
                int reduccion = 15 + random.nextInt(30);
                danoRecibido = ataqueREX - reduccion;
                if (danoRecibido < 0){
                    System.out.println("¡Esquivaste! te has movido tan rapido que succionaste el daño " + danoRecibido + " HP.");
                }
                System.out.println("¡Esquivaste! Daño reducido a " + danoRecibido + " HP.");
            } else {
                danoRecibido = ataqueREX;
                System.out.println("¡Metal Gear ataca con un Cañón Láser! -" + danoRecibido + " HP");
            }

            vidaSnake -= danoRecibido;
            System.out.println("Tu vida: " + vidaSnake + " HP");
        }

        // Resultado final
        if (vidaSnake <= 0 && vidaREX <= 0) {
            System.out.println("\n¡Ambos han caído! Empate!.");
            reiniciar();
        } else if (vidaREX <= 0) {
            System.out.println("\n¡Has destruido a Metal Gear REX! ¡Misión cumplida!");
        } else {
            System.out.println("\n¡Has sido derrotado por Metal Gear REX!");
            reiniciar();
        }
    }
    private void reiniciar() {
        MisionTres nueva = new MisionTres();
        nueva.iniciarBatallaFinal();
    }
}

