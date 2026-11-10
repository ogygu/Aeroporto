public class FilaReservas {
    private Reserva[] reservas;
    private int inicio;
    private int fim;
    private int quantidade;

    public FilaReservas(int capacidade) {
        reservas = new Reserva[capacidade];
        inicio = 0;
        fim = 0;
        quantidade = 0;
    }

    public boolean enfileirar(Reserva reserva) {
        if (quantidade == reservas.length) {
            System.out.println("Fila cheia.");
            return false;
        }
        reservas[fim] = reserva;
        fim = (fim + 1) % reservas.length; // circular
        quantidade++;
        return true;
    }

    public Reserva desenfileirar() {
        if (quantidade == 0) return null;
        Reserva r = reservas[inicio];
        reservas[inicio] = null;
        inicio = (inicio + 1) % reservas.length;
        quantidade--;
        return r;
    }

    public Reserva consultarProxima() {
        if (quantidade == 0) return null;
        return reservas[inicio];
    }

    public boolean estaVazia() {
        return quantidade == 0;
    }

    public int getQuantidade() { return quantidade; }

    public void exibirTodas() {
        if (quantidade == 0) {
            System.out.println("Fila vazia.");
            return;
        }
        int pos = inicio;
        for (int i = 0; i < quantidade; i++) {
            System.out.println("--- Reserva " + (i + 1) + " ---");
            reservas[pos].exibir();
            pos = (pos + 1) % reservas.length;
        }
    }
}

//utiliza uma fila circular : FIFO. o primeiro que entra é o primeiro que sai. A formula %capacidade faz o indice dar a volta no array ao inves de deslocar tudo