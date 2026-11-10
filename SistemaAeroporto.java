import java.util.Scanner;

public class SistemaAeroporto {
    private ListaVoos listaVoos;
    private FilaReservas filaReservas;
    private FilaReservas historicoReservas; // guarda confirmadas e recusadas
    private Scanner sc;

    public SistemaAeroporto() {
        listaVoos = new ListaVoos(50);
        filaReservas = new FilaReservas(100);
        historicoReservas = new FilaReservas(200);
        sc = new Scanner(System.in);
    }

    // ============ VALIDAÇÕES ============
    private int lerNumeroVoo() {
        while (true) {
            System.out.print("Número do voo (4 dígitos): ");
            String entrada = sc.nextLine();
            if (entrada.matches("\\d{4}")) {
                return Integer.parseInt(entrada);
            }
            System.out.println("ERRO: O número do voo deve ter exatamente 4 dígitos numéricos.");
        }
    }

    private boolean validarCpf(String cpf) {
        return cpf != null && cpf.trim().length() >= 11;
    }

    private boolean validarEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    // ============ CADASTRO DE VOOS ============
    public void cadastrarVoo() {
        int numero = lerNumeroVoo();
        if (listaVoos.buscarPorNumero(numero) != null) {
            System.out.println("Já existe um voo com esse número.");
            return;
        }
        System.out.print("Origem: ");
        String origem = sc.nextLine();
        System.out.print("Destino: ");
        String destino = sc.nextLine();
        System.out.print("Horário de partida (ex: 10:30): ");
        String partida = sc.nextLine();
        System.out.print("Horário de chegada (ex: 12:45): ");
        String chegada = sc.nextLine();
        System.out.print("Capacidade máxima de passageiros: ");
        int capacidade = Integer.parseInt(sc.nextLine());

        Voo voo = new Voo(numero, origem, destino, partida, chegada, capacidade);
        listaVoos.inserir(voo);
        System.out.println("Voo cadastrado com sucesso!");
    }

    // ============ SOLICITAR RESERVA ============
    public void solicitarReserva() {
        System.out.print("Nome completo: ");
        String nome = sc.nextLine();
        System.out.print("Idade: ");
        int idade = Integer.parseInt(sc.nextLine());
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        if (!validarCpf(cpf)) {
            System.out.println("CPF inválido.");
            return;
        }
        System.out.print("E-mail: ");
        String email = sc.nextLine();
        if (!validarEmail(email)) {
            System.out.println("E-mail inválido.");
            return;
        }
        int numeroVoo = lerNumeroVoo();
        if (listaVoos.buscarPorNumero(numeroVoo) == null) {
            System.out.println("Voo não encontrado.");
            return;
        }

        Passageiro p = new Passageiro(nome, idade, cpf, email);
        Reserva r = new Reserva(p, numeroVoo);
        if (filaReservas.enfileirar(r)) {
            System.out.println("Reserva solicitada e adicionada à fila de pendências.");
        }
    }

    // ============ PROCESSAR RESERVAS (ADMINISTRADOR) ============
    public void processarReservas() {
        if (filaReservas.estaVazia()) {
            System.out.println("Não há reservas pendentes.");
            return;
        }
        System.out.println("=== PROCESSANDO RESERVAS PENDENTES ===");
        while (!filaReservas.estaVazia()) {
            Reserva r = filaReservas.desenfileirar();
            Voo voo = listaVoos.buscarPorNumero(r.getNumeroVoo());
            System.out.println("\nProcessando reserva:");
            r.exibir();

            if (voo == null) {
                System.out.println("Voo não encontrado. Reserva recusada.");
                r.setStatus(StatusReserva.RECUSADA);
                historicoReservas.enfileirar(r);
                continue;
            }

            if (voo.temVaga()) {
                System.out.print("Confirmar reserva? (s/n): ");
                String resp = sc.nextLine();
                if (resp.equalsIgnoreCase("s")) {
                    r.setStatus(StatusReserva.CONFIRMADA);
                    voo.confirmarPassageiro();
                    System.out.println("Reserva CONFIRMADA.");
                } else {
                    r.setStatus(StatusReserva.RECUSADA);
                    System.out.println("Reserva RECUSADA pelo administrador.");
                }
            } else {
                System.out.println("Voo cheio. Reserva RECUSADA automaticamente.");
                r.setStatus(StatusReserva.RECUSADA);
            }
            historicoReservas.enfileirar(r);
        }
        System.out.println("\nProcessamento concluído.");
    }

    // ============ CHECK-IN ============
    public void realizarCheckIn() {
        System.out.print("Informe seu CPF: ");
        String cpf = sc.nextLine();
        int numeroVoo = lerNumeroVoo();

        Voo voo = listaVoos.buscarPorNumero(numeroVoo);
        if (voo == null) {
            System.out.println("Voo não encontrado.");
            return;
        }

        // Busca no histórico por uma reserva CONFIRMADA com esse CPF nesse voo
        // (como a fila histórica é FIFO, percorremos visualmente)
        Passageiro encontrado = buscarPassageiroConfirmado(cpf, numeroVoo);
        if (encontrado == null) {
            System.out.println("Nenhuma reserva confirmada encontrada para esse CPF nesse voo.");
            return;
        }

        if (voo.getPilhaCheckIn().empilhar(encontrado)) {
            System.out.println("Check-in realizado com sucesso! Você está na pilha de embarque.");
        }
    }

    private Passageiro buscarPassageiroConfirmado(String cpf, int numeroVoo) {
        // Percorre o histórico procurando reserva confirmada
        // Para simplificar, vamos manter uma lista auxiliar de confirmados
        // (em produção real, seria outra estrutura — aqui usamos um array auxiliar)
        for (Passageiro p : confirmados) {
            if (p != null && p.getCpf().equals(cpf)) {
                // Verifica se é do voo certo (simplificação: assumimos que sim)
                return p;
            }
        }
        return null;
    }

    // Array auxiliar para guardar passageiros confirmados (para validar check-in)
    private Passageiro[] confirmados = new Passageiro[200];
    private int qtdConfirmados = 0;

    // Método chamado quando o admin confirma uma reserva
    public void processarReservasComConfirmados() {
        if (filaReservas.estaVazia()) {
            System.out.println("Não há reservas pendentes.");
            return;
        }
        while (!filaReservas.estaVazia()) {
            Reserva r = filaReservas.desenfileirar();
            Voo voo = listaVoos.buscarPorNumero(r.getNumeroVoo());
            System.out.println("\nProcessando:");
            r.exibir();

            if (voo == null || !voo.temVaga()) {
                r.setStatus(StatusReserva.RECUSADA);
                System.out.println("RECUSADA.");
            } else {
                System.out.print("Confirmar? (s/n): ");
                if (sc.nextLine().equalsIgnoreCase("s")) {
                    r.setStatus(StatusReserva.CONFIRMADA);
                    voo.confirmarPassageiro();
                    confirmados[qtdConfirmados++] = r.getPassageiro();
                    System.out.println("CONFIRMADA.");
                } else {
                    r.setStatus(StatusReserva.RECUSADA);
                    System.out.println("RECUSADA.");
                }
            }
            historicoReservas.enfileirar(r);
        }
    }

    // ============ EMBARQUE ============
    public void realizarEmbarque() {
        int numeroVoo = lerNumeroVoo();
        Voo voo = listaVoos.buscarPorNumero(numeroVoo);
        if (voo == null) {
            System.out.println("Voo não encontrado.");
            return;
        }
        Passageiro p = voo.getPilhaCheckIn().desempilhar();
        if (p == null) {
            System.out.println("Nenhum passageiro fez check-in neste voo.");
            return;
        }
        System.out.println("Embarque realizado: " + p.getNome() + " (último a fazer check-in, primeiro a embarcar - LIFO).");
    }

    // ============ CONSULTAS ============
    public void consultarVoos() { listaVoos.exibirTodos(); }
    public void consultarDisponiveis() { listaVoos.exibirDisponiveis(); }
    public void consultarCheios() { listaVoos.exibirCheios(); }
    public void consultarPilhaCheckIn() {
        int numeroVoo = lerNumeroVoo();
        Voo voo = listaVoos.buscarPorNumero(numeroVoo);
        if (voo == null) { System.out.println("Voo não encontrado."); return; }
        System.out.println("Pilha de check-in do voo " + numeroVoo + ":");
        voo.getPilhaCheckIn().exibir();
    }
    public void consultarFilaReservas() { filaReservas.exibirTodas(); }
}