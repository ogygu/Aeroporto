public class PilhaCheckIn {
    private Passageiro[] passageiros;
    private int topo;

    public PilhaCheckIn(int capacidade) {
        passageiros = new Passageiro[capacidade];
        topo = -1; // -1 significa pilha vazia
    }

    public boolean empilhar(Passageiro p) {
        if (topo == passageiros.length - 1) {
            System.out.println("Pilha cheia.");
            return false;
        }
        topo++;
        passageiros[topo] = p;
        return true;
    }

    public Passageiro desempilhar() {
        if (topo == -1) return null;
        Passageiro p = passageiros[topo];
        passageiros[topo] = null;
        topo--;
        return p;
    }

    public Passageiro consultarTopo() {
        if (topo == -1) return null;
        return passageiros[topo];
    }

    public boolean estaVazia() {
        return topo == -1;
    }

    public void exibir() {
        if (topo == -1) {
            System.out.println("  Pilha vazia (ninguém fez check-in ainda).");
            return;
        }
        for (int i = topo; i >= 0; i--) {
            System.out.println("  [" + (topo - i + 1) + "] " + passageiros[i].getNome() +
                               " - CPF: " + passageiros[i].getCpf());
        }
    }
}

//pilha LIFO. Ultimo a entrar e o primeiro a sair