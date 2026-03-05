package software.magnes.solutions;

public class Main {
    public static void main(String[] args) {
        Maquina maquina = new Maquina("AF21GH67");

        while (true) {
            maquina.buscarDados();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
