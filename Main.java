import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaAeroporto sistema = new SistemaAeroporto();
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n========== AEROPORTO ==========");
            System.out.println("1 - Cadastrar voo");
            System.out.println("2 - Solicitar reserva (passageiro)");
            System.out.println("3 - Realizar check-in (passageiro)");
            System.out.println("4 - Realizar embarque (passageiro)");
            System.out.println("5 - Entrar como administrador");
            System.out.println("6 - Consultar voos disponíveis");
            System.out.println("7 - Consultar voos cheios");
            System.out.println("8 - Consultar fila de reservas pendentes");
            System.out.println("9 - Consultar pilha de check-in de um voo");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1: sistema.cadastrarVoo(); break;
                case 2: sistema.solicitarReserva(); break;
                case 3: sistema.realizarCheckIn(); break;
                case 4: sistema.realizarEmbarque(); break;
                case 5: menuAdministrador(sistema, sc); break;
                case 6: sistema.consultarDisponiveis(); break;
                case 7: sistema.consultarCheios(); break;
                case 8: sistema.consultarFilaReservas(); break;
                case 9: sistema.consultarPilhaCheckIn(); break;
                case 0: System.out.println("Encerrando..."); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuAdministrador(SistemaAeroporto sistema, Scanner sc) {
        System.out.println("\n===== MODO ADMINISTRADOR =====");
        System.out.println("1 - Processar reservas pendentes");
        System.out.println("2 - Ver todos os voos");
        System.out.println("3 - Voltar");
        System.out.print("Opção: ");
        int op = Integer.parseInt(sc.nextLine());
        switch (op) {
            case 1: sistema.processarReservasComConfirmados(); break;
            case 2: sistema.consultarVoos(); break;
            case 3: break;
            default: System.out.println("Opção inválida.");
        }
    }
}