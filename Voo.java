public class Voo {
    private int numero;
    private String origem;
    private String destino;
    private String horarioPartida;
    private String horarioChegada;
    private int capacidadeMaxima;
    private int passageirosConfirmados;
    private PilhaCheckIn pilhaCheckIn; // cada voo tem sua pilha!

    public Voo(int numero, String origem, String destino,
               String horarioPartida, String horarioChegada, int capacidadeMaxima) {
        this.numero = numero;
        this.origem = origem;
        this.destino = destino;
        this.horarioPartida = horarioPartida;
        this.horarioChegada = horarioChegada;
        this.capacidadeMaxima = capacidadeMaxima;
        this.passageirosConfirmados = 0;
        this.pilhaCheckIn = new PilhaCheckIn(capacidadeMaxima);
    }

    public int getNumero() { 
        return numero; 
    }
    public String getOrigem() { 
        return origem; 
    }
    public String getDestino() { 
        return destino; 
    }
    public String getHorarioPartida() { 
        return horarioPartida; 
    }
    public String getHorarioChegada() { 
        return horarioChegada; 
    }
    public int getCapacidadeMaxima() { 
        return capacidadeMaxima; 
    }
    public int getPassageirosConfirmados() { 
        return passageirosConfirmados; 
    }
    public PilhaCheckIn getPilhaCheckIn() { 
        return pilhaCheckIn; 
    }

    public boolean temVaga() {
        return passageirosConfirmados < capacidadeMaxima;
    }

    public void confirmarPassageiro() {
        passageirosConfirmados++;
    }

    public void exibir() {
        System.out.println("Voo " + numero + " | " + origem + " -> " + destino +
                           " | Partida: " + horarioPartida +
                           " | Chegada: " + horarioChegada +
                           " | Vagas: " + (capacidadeMaxima - passageirosConfirmados) +
                           "/" + capacidadeMaxima);
    }
}

//representa um voo funcionando, cada voo tem sua propria pilha - isso atende ao requisito de uma pilha por voo