package software.magnes.solutions;

public class Maquina {
    String numeracaoMaquina;
    int percentualCpu;
    int percentualDisco;
    int percentualMemoria;
    boolean gargaloCpu;
    boolean gargaloDisco;
    boolean gargaloMemoria;

    public Maquina(String numeracaoMaquina) {
        this.numeracaoMaquina = numeracaoMaquina;
    }

    public void buscarDados() {
        // implementar busca de dados do csv que esta na AWS
        this.percentualCpu = (int) (Math.random() * 101);
        this.percentualDisco = (int) (Math.random() * 101);
        this.percentualMemoria = (int) (Math.random() * 101);

        this.imprimirDados();

        if (this.verificarSeExisteProblema()) {
            Chamado chamado = new Chamado(this);
            chamado.emitirAlerta();
        }
    }

    public void imprimirDados() {
        System.out.println("\n=================================================");
        System.out.println("Percentual de uso de CPU: " + this.percentualCpu + "%");
        System.out.println("Percentual de uso de Memória: " + this.percentualMemoria + "%");
        System.out.println("Percentual de uso de Disco: " + this.percentualDisco + "%");

    }

    public boolean verificarSeExisteProblema() {
        boolean problemaIdentificado = false;
        if (this.percentualCpu > 70) {
            gargaloCpu = true;
            problemaIdentificado = true;
        }
        else {
            gargaloCpu = false;
        }
        if (this.percentualDisco > 70) {
            gargaloDisco = true;
            problemaIdentificado = true;
        }
        else {
            gargaloDisco = false;
        }
        if (this.percentualMemoria > 70) {
            gargaloMemoria = true;
            problemaIdentificado = true;
        }
        else {
            gargaloMemoria = false;
        }

        return problemaIdentificado;
    }
}
