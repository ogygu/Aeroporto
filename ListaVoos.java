public class ListaVoos {
    private Voo[] voos;
    private int quantidade;

    public ListaVoos(int capacidade) {
        voos = new Voo[capacidade];
        quantidade = 0;
    }

    public boolean inserir(Voo voo) {
        if (quantidade == voos.length) {
            System.out.println("Lista de voos cheia.");
            return false;
        }
        voos[quantidade] = voo;
        quantidade++;
        return true;
    }

    public Voo buscarPorNumero(int numero) {
        for (int i = 0; i < quantidade; i++) {
            if (voos[i].getNumero() == numero) {
                return voos[i];
            }
        }
        return null;
    }

    public void exibirTodos() {
        if (quantidade == 0) {
            System.out.println("Nenhum voo cadastrado.");
            return;
        }
        for (int i = 0; i < quantidade; i++) {
            voos[i].exibir();
        }
    }

    public void exibirDisponiveis() {
        boolean encontrou = false;
        for (int i = 0; i < quantidade; i++) {
            if (voos[i].temVaga()) {
                voos[i].exibir();
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("Nenhum voo com vagas.");
    }

    public void exibirCheios() {
        boolean encontrou = false;
        for (int i = 0; i < quantidade; i++) {
            if (!voos[i].temVaga()) {
                voos[i].exibir();
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("Nenhum voo cheio.");
    }

    public int getQuantidade() { return quantidade; }
}

//controle de quantidade de voos separado por capacidade