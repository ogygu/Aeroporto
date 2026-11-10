public  class Reserva {

    private Passageiro passageiro;
    private int numeroVoo;
    private StatusReserva status;

    public Reserva(Passageiro passageiro, int numeroVoo) {
        this.passageiro = passageiro;
        this.numeroVoo = numeroVoo;
        this.status = StatusReserva.PENDENTE;
    }

    public Passageiro getPassageiro() {
        return passageiro;
    }
    public int getNumeroVoo() {
        return numeroVoo;
    }
    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }

    public void exibir() {
        System.out.println("Reserva para o voo " + numeroVoo + ":");
        passageiro.exibir();
        System.out.println("Status: " + status);
    }

}

//liga um passageiro a um voo, e define o status da reserva. O status é definido como pendente inicialmente, e pode ser alterado para confirmada ou recusada posteriormente.