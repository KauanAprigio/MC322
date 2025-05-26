package LAB04.Code;

import LAB04.Code.AbstractClasses.Robo;
import LAB04.Code.AbstractClasses.Robo.EstadoRobo;
import LAB04.Code.Exceptions.*;
import LAB04.Code.Interfaces.Entidade;
import LAB04.Code.Interfaces.Entidade.TipoEntidade;
import LAB04.Code.Obstaculo.TipoObstaculo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Classe Main:
 * Ponto de entrada do programa para o Laboratório 4.
 * Configura o ambiente, robôs e obstáculos.
 * Executa uma série de testes automatizados para validar funcionalidades e exceções.
 * Apresenta um menu interativo para o usuário interagir com a simulação.
 * Grupo: Diego Martins e Kauan Aprigio
 * RA's: 260205 e 288809
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Ambiente ambiente;
    private static ComunicadorCentral comunicador;
    private static RoboLimpador roboLimpador;
    private static RoboBombeiro roboBombeiro;

    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println("🚀 Iniciando Simulação - MC322 - LAB04 🚀");
        System.out.println("=============================================");

        inicializarAmbiente();
        executarTestesAutomatizados();
        menuInterativo();

        scanner.close(); // Fecha o scanner ao final
        System.out.println("=============================================");
        System.out.println("👋 Simulação Finalizada! Até a próxima! 👋");
        System.out.println("=============================================");
    }

    /**
     * Configura o ambiente, obstáculos, robôs e o comunicador central.
     */
    private static void inicializarAmbiente() {
        System.out.println("\n--- 🛠️  Configurando o Mundo Virtual 🛠️  ---");
        TipoEntidade[][][] mapa = new TipoEntidade[101][101][111]; // Aumentado para evitar OutOfBounds
        char[][] planoXY = new char[101][101];
        ambiente = new Ambiente(100, 100, 110, mapa, planoXY);
        ambiente.inicializarMapa();

        System.out.println("Adicionando obstáculos...");
        try {
            // Obstáculos do LAB03
            ambiente.adicionarEntidade(new Obstaculo(2, 2, TipoObstaculo.LAGO, ambiente, TipoEntidade.OBSTACULO));
            ambiente.adicionarEntidade(new Obstaculo(95, 0, TipoObstaculo.FOGO, ambiente, TipoEntidade.OBSTACULO));
            ambiente.adicionarEntidade(new Obstaculo(0, 40, TipoObstaculo.PREDIOEMCHAMAS, ambiente, TipoEntidade.OBSTACULO));
            ambiente.adicionarEntidade(new Obstaculo(40, 0, TipoObstaculo.PREDIO, ambiente, TipoEntidade.OBSTACULO));
            ambiente.adicionarEntidade(new Obstaculo(50, 40, TipoObstaculo.SUJEIRAENCARDIDA, ambiente, TipoEntidade.OBSTACULO));
            ambiente.adicionarEntidade(new Obstaculo(52, 41, TipoObstaculo.COMIDANOCHAO, ambiente, TipoEntidade.OBSTACULO));
            ambiente.adicionarEntidade(new Obstaculo(54, 42, TipoObstaculo.SACOLAPLASTICA, ambiente, TipoEntidade.OBSTACULO));
            ambiente.adicionarEntidade(new Obstaculo(55, 45, TipoObstaculo.OFICINA, ambiente, TipoEntidade.OBSTACULO));
            // Comunicador Central
            comunicador = new ComunicadorCentral(50, 50, 0, ambiente);
            ambiente.adicionarEntidade(comunicador);

        } catch (ForaDosLimitesException | LocalOcupadoException e) {
            System.err.println("🚨 Erro ao adicionar entidade inicial: " + e.getMessage());
        }

        System.out.println("Adicionando robôs...");
        try {
            roboLimpador = new RoboLimpador("Faxinildo_01", 0, 0, 0, ambiente, 15.0, 10);
            roboBombeiro = new RoboBombeiro("Chama_Boy_02", 10, 10, 0, ambiente, 105, 10.0, 3000, 15);
            ambiente.adicionarEntidade(roboLimpador);
            ambiente.adicionarEntidade(roboBombeiro);
        } catch (ForaDosLimitesException | LocalOcupadoException e) {
            System.err.println("🚨 Erro ao adicionar robô inicial: " + e.getMessage());
        }
        System.out.println("--- ✅ Mundo configurado! ---");
    }

    /**
     * Executa uma série de testes para validar as funcionalidades e exceções.
     */
    private static void executarTestesAutomatizados() {
        System.out.println("\n--- 🧪 Executando Bateria de Testes 🧪 ---");

        Robo roboQualquer = new Robo("RoboGenerico", EstadoRobo.ON, 90, 90, 0, ambiente) {
            @Override
            public String getDescricao() { return "Sou um robô genérico para testes."; }
        };

        try {
             ambiente.adicionarEntidade(roboQualquer);
        } catch (ForaDosLimitesException | LocalOcupadoException e) {
            System.err.println("Teste: " + e.getMessage());
        }

        // Testes Gerais e Exceções
        System.out.println("\n>> Testando Exceções Gerais...");
        try {
            ambiente.moverEntidade(roboLimpador, 0, 0, 0);
        } catch (Exception e) { System.err.println("Teste RoboDesligadoException (Mover): " + e.getMessage()); }
        try {
            roboLimpador.ligar();
            ambiente.moverEntidade(roboLimpador, 101, 0, 0);
        } catch (Exception e) { System.err.println("Teste ForaDosLimitesException: " + e.getMessage()); }
        try {
            ambiente.moverEntidade(roboLimpador, 2, 2, 0);
        } catch (Exception e) { System.err.println("Teste LocalOcupadoException: " + e.getMessage()); }
        try {
            ambiente.moverEntidade(roboLimpador, 1, 1, 5);
        } catch (Exception e) { System.err.println("Teste NaoPodeVoarException: " + e.getMessage()); }
        try {
            ambiente.removerEntidade(new Obstaculo(200, 200, TipoObstaculo.FOGO, ambiente, TipoEntidade.OBSTACULO), true);
        } catch (Exception e) { System.err.println("Teste EntidadeNaoEncontradaException: " + e.getMessage()); }


        // Testes RoboLimpador
        System.out.println("\n>> Testando RoboLimpador ("+ roboLimpador.getId() +")...");
        roboLimpador.desligar(); // Já está desligado
        roboLimpador.ligar();
        try { roboLimpador.acionarSensores(); } catch (Exception e) { System.err.println("Teste (OK): " + e.getMessage()); }
        try { roboLimpador.definir_tipo_limpeza(5); } catch (Exception e) { System.err.println("Teste ErrorLimpezaException (Tipo): " + e.getMessage()); }
        try { roboLimpador.definir_tipo_limpeza(0); } catch (Exception e) { System.err.println("Erro inesperado: " + e.getMessage()); }
        try { roboLimpador.limpar(); } catch (Exception e) { System.err.println("Teste ErrorLimpezaException (Sem Lixo Perto/Tipo): " + e.getMessage()); }
        try { ambiente.moverEntidade(roboLimpador, 53, 41, 0); } catch (Exception e) { System.err.println("Erro inesperado: " + e.getMessage()); }
        try { roboLimpador.definir_tipo_limpeza(0); roboLimpador.limpar(); } catch (Exception e) { System.err.println("Teste ErrorLimpezaException (Tipo Incorreto): " + e.getMessage()); }
        try { roboLimpador.definir_tipo_limpeza(1); roboLimpador.limpar(); } catch (Exception e) { System.err.println("Erro inesperado: " + e.getMessage()); } // Deve limpar Comida
        try { roboLimpador.aprimorar(10); } catch (Exception e) { System.err.println("Teste ErrorAprimoramentoException (Fora Oficina): " + e.getMessage()); }
        try { ambiente.moverEntidade(roboLimpador, 56, 46, 0); } catch (Exception e) { System.err.println("Erro inesperado: " + e.getMessage()); }
        try { roboLimpador.aprimorar(-5); } catch (Exception e) { System.err.println("Teste ErrorAprimoramentoException (Valor Inválido): " + e.getMessage()); }
        try { roboLimpador.aprimorar(5); } catch (Exception e) { System.err.println("Erro inesperado: " + e.getMessage()); } // Deve aprimorar
        roboLimpador.desligar();
        try { roboLimpador.acionarSensores(); } catch (Exception e) { System.err.println("Teste RoboDesligadoException (Sensores): " + e.getMessage()); }

        // Testes RoboBombeiro
        System.out.println("\n>> Testando RoboBombeiro (" + roboBombeiro.getId() +")...");
        roboBombeiro.ligar();
        try { roboBombeiro.adicionar_agua(100); } catch (Exception e) { System.err.println("Teste ErrorAbastecimentoException (Fora Lago): " + e.getMessage()); }
        try { ambiente.moverEntidade(roboBombeiro, 5, 5, 0); } catch (Exception e) { System.err.println("Erro inesperado: " + e.getMessage()); }
        try { roboBombeiro.adicionar_agua(-10); } catch (Exception e) { System.err.println("Teste ErrorAbastecimentoException (Valor Inválido): " + e.getMessage()); }
        try { roboBombeiro.adicionar_agua(4000); } catch (Exception e) { System.err.println("Teste ErrorAbastecimentoException (Capacidade): " + e.getMessage()); }
        try { roboBombeiro.adicionar_agua(500); } catch (Exception e) { System.err.println("Erro inesperado: " + e.getMessage()); } // Deve abastecer
        try { roboBombeiro.apagar_fogo(); } catch (Exception e) { System.err.println("Teste ErrorApagarFogoException (Sem Fogo Perto): " + e.getMessage()); }
        try { ambiente.moverEntidade(roboBombeiro, 90, 3, 0); } catch (Exception e) { System.err.println("Erro inesperado: " + e.getMessage()); }
        try { roboBombeiro.apagar_fogo(); } catch (Exception e) { System.err.println("Teste ErrorApagarFogoException (Altura): " + e.getMessage()); }
        try { ambiente.moverEntidade(roboBombeiro, 90, 3, 5); } catch (Exception e) { System.err.println("Erro inesperado: " + e.getMessage()); }
        try { roboBombeiro.apagar_fogo(); } catch (Exception e) { System.err.println("Erro inesperado: " + e.getMessage()); } // Deve apagar FOGO
        try { ambiente.moverEntidade(roboBombeiro, 5, 45, 100); } catch (Exception e) { System.err.println("Erro inesperado: " + e.getMessage()); }
        try { roboBombeiro.apagar_fogo(); } catch (Exception e) { System.err.println("Teste ErrorApagarFogoException (Água Insuficiente): " + e.getMessage()); }

        // Testes ComunicadorCentral
        System.out.println("\n>> Testando ComunicadorCentral...");
        comunicador.listarFogos(); // Deve listar o Prédio em Chamas
        try { comunicador.avisoFogoProximo(roboLimpador); } catch (Exception e) { System.err.println("Teste ErroComunicacaoException: " + e.getMessage()); }
        try { comunicador.avisoFogoProximo(roboBombeiro); } catch (Exception e) { System.err.println("Erro inesperado: " + e.getMessage()); }
        roboBombeiro.enviarMensagem(comunicador, roboBombeiro.getId() + ": Mensagem recebida, central!");
        comunicador.exibirMensagens();

        System.out.println("--- ✅ Testes finalizados! ---");
        // Reinicia robos para o menu
        try {
            ambiente.removerEntidade(roboLimpador, false);
            ambiente.removerEntidade(roboBombeiro, false);
            roboLimpador = new RoboLimpador("Faxinildo_01", 0, 0, 0, ambiente, 15.0, 10);
            roboBombeiro = new RoboBombeiro("Chama_Boy_02", 10, 10, 0, ambiente, 105, 10.0, 3000, 15);
            ambiente.adicionarEntidade(roboLimpador);
            ambiente.adicionarEntidade(roboBombeiro);
        } catch (Exception e) { System.err.println("Erro ao reiniciar robôs: " + e.getMessage());}
    }

    /**
     * Apresenta o menu interativo para o usuário.
     */
    private static void menuInterativo() {
        System.out.println("\n--- 🤖 Bem-vindo ao Menu Interativo! 🤖 ---");
        System.out.println("Chegou a hora de pilotar! Escolha suas ações e divirta-se (ou salve o mundo!).");

        int opcao;
        do {
            imprimirMenuPrincipal();
            opcao = lerOpcao();

            switch (opcao) {
                case 1: listarRobos(); break;
                case 2: escolherRoboParaInteragir(); break;
                case 3: visualizarMapa(); break;
                case 4: listarMensagens(); break;
                case 5: System.out.println("\nSaindo do menu interativo. Foi um prazer, piloto!"); break;
                default: System.out.println("Opção inválida! Parece que você apertou o botão errado. Tente de novo!"); break;
            }
        } while (opcao != 5);
    }

    private static void imprimirMenuPrincipal() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Listar todos os robôs");
        System.out.println("2. Escolher um robô para interagir");
        System.out.println("3. Visualizar o mapa 2D (visão de cima)");
        System.out.println("4. Listar mensagens trocadas (via Central)");
        System.out.println("5. Sair do Simulador");
        System.out.print("Escolha sua ação, comandante: ");
    }

    private static void listarRobos() {
        System.out.println("\n--- Frota de Robôs ---");
        List<Robo> robos = ambiente.getEntidades().stream()
                .filter(e -> e.getTipo() == TipoEntidade.ROBO)
                .map(e -> (Robo) e)
                .collect(Collectors.toList());

        if (robos.isEmpty()) {
            System.out.println("Nenhum robô no hangar! Estranho...");
            return;
        }

        System.out.println("Robôs por tipo:");
        robos.stream()
            .filter(r -> r instanceof RoboLimpador)
            .forEach(r -> System.out.printf("  - %s (Limpador) - Estado: %s\n", r.getId(), ((Robo)r).getEstado()));
        robos.stream()
            .filter(r -> r instanceof RoboBombeiro)
            .forEach(r -> System.out.printf("  - %s (Bombeiro) - Estado: %s\n", r.getId(), ((Robo)r).getEstado()));
         robos.stream()
            .filter(r -> !(r instanceof RoboLimpador) && !(r instanceof RoboBombeiro))
            .forEach(r -> System.out.printf("  - %s (Genérico) - Estado: %s\n", r.getId(), ((Robo)r).getEstado()));


        System.out.println("\nRobôs por estado:");
        System.out.println("  Ligados:");
        robos.stream()
            .filter(r -> ((Robo)r).getEstado() == EstadoRobo.ON)
            .forEach(r -> System.out.printf("    - %s\n", r.getId()));
        System.out.println("  Desligados:");
        robos.stream()
            .filter(r -> ((Robo)r).getEstado() == EstadoRobo.OFF)
            .forEach(r -> System.out.printf("    - %s\n", r.getId()));
    }

    private static void escolherRoboParaInteragir() {
        System.out.println("\n--- Seleção de Robô ---");
        List<Robo> robos = ambiente.getEntidades().stream()
                .filter(e -> e.getTipo() == TipoEntidade.ROBO)
                .map(e -> (Robo) e)
                .collect(Collectors.toList());

        if (robos.isEmpty()) {
            System.out.println("Não há robôs para escolher.");
            return;
        }

        for (int i = 0; i < robos.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, robos.get(i).getId());
        }
        System.out.print("Digite o número do robô que você quer controlar (ou 0 para voltar): ");

        int escolha = lerOpcao();
        if (escolha > 0 && escolha <= robos.size()) {
            Robo roboSelecionado = robos.get(escolha - 1);
            System.out.printf("\nVocê selecionou %s. O que faremos?\n", roboSelecionado.getId());
            menuRobo(roboSelecionado);
        } else if (escolha != 0) {
            System.out.println("Número inválido! Tente novamente.");
        }
    }

    private static void menuRobo(Robo robo) {
        int opcao;
        do {
            System.out.println("\n--- Painel de Controle: " + robo.getId() + " ---");
            System.out.println("1. Visualizar Status");
            System.out.println("2. Mover Robô");
            System.out.println("3. Ativar/Desligar Robô");
            System.out.println("4. Executar Ações Específicas");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Sua ordem: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1: visualizarStatus(robo); break;
                case 2: controlarMovimento(robo); break;
                case 3: ativarDesligar(robo); break;
                case 4: executarAcoesEspecificas(robo); break;
                case 5: System.out.println("Retornando ao menu principal..."); break;
                default: System.out.println("Comando não reconhecido!"); break;
            }
        } while (opcao != 5);
    }

    private static void visualizarStatus(Robo robo) {
        System.out.println("\n--- Status: " + robo.getId() + " ---");
        System.out.printf("Posição: (%d, %d, %d)\n", robo.getX(), robo.getY(), robo.getZ());
        System.out.println("Estado: " + robo.getEstado());
        System.out.println("Descrição: " + robo.getDescricao());
        if (robo instanceof RoboLimpador) {
            RoboLimpador rl = (RoboLimpador) robo;
            System.out.println("Tipo Limpeza: " + rl.getTipoLimpeza());
            System.out.println("Raio Limpeza: " + rl.getRaioDeLimpeza());
        } else if (robo instanceof RoboBombeiro) {
            RoboBombeiro rb = (RoboBombeiro) robo;
            System.out.println("Altitude: " + rb.getAltitude());
            System.out.println("Reservatório: " + rb.getReservatorio() + "/" + rb.getCapacidade() + "L");
            System.out.println("Altitude Máx.: " + rb.getAltitudeMaxima());
        }
    }

    private static void controlarMovimento(Robo robo) {
        System.out.println("\n--- Controle de Movimento ---");
        System.out.println("Use W (Frente), S (Trás), A (Esquerda), D (Direita).");
        System.out.println("Use U (Cima), J (Baixo) - Apenas para aéreos.");
        System.out.print("Digite seu comando (ex: W): ");
        String comando = scanner.next().toUpperCase();
        int deltaX = 0, deltaY = 0, deltaZ = 0;

        switch (comando) {
            case "W": deltaY = 1; break;
            case "S": deltaY = -1; break;
            case "A": deltaX = -1; break;
            case "D": deltaX = 1; break;
            case "U": deltaZ = 1; break;
            case "J": deltaZ = -1; break;
            default: System.out.println("Direção inválida!"); return;
        }

        try {
            int novoX = robo.getX() + deltaX;
            int novoY = robo.getY() + deltaY;
            int novoZ = robo.getZ() + deltaZ;
            ambiente.moverEntidade(robo, novoX, novoY, novoZ);
            System.out.printf("Movido para (%d, %d, %d)!\n", novoX, novoY, novoZ);
        } catch (RoboDesligadoException | LocalOcupadoException | ForaDosLimitesException | NaoPodeVoarException e) {
            System.err.println("🚨 Ops! Não deu pra mover: " + e.getMessage());
        } catch (Exception e) {
             System.err.println("🚨 Erro inesperado ao mover: " + e.getMessage());
        }
    }

    private static void ativarDesligar(Robo robo) {
        if (robo.getEstado() == EstadoRobo.ON) {
            robo.desligar();
        } else {
            robo.ligar();
        }
        System.out.println("Estado atual: " + robo.getEstado());
    }

    private static void executarAcoesEspecificas(Robo robo) {
        if (robo instanceof RoboLimpador) {
            menuRoboLimpador((RoboLimpador) robo);
        } else if (robo instanceof RoboBombeiro) {
            menuRoboBombeiro((RoboBombeiro) robo);
        } else {
            System.out.println("Este robô não tem ações específicas. Que pena!");
        }
    }

    private static void menuRoboLimpador(RoboLimpador rl) {
         int opcao;
         do {
            System.out.println("\n--- Ações: " + rl.getId() + " ---");
            System.out.println("1. Definir Tipo Limpeza");
            System.out.println("2. Limpar Área");
            System.out.println("3. Acionar Sensores de Lixo");
            System.out.println("4. Aprimorar Raio");
            System.out.println("5. Voltar");
            System.out.print("Sua ordem: ");
            opcao = lerOpcao();

            try {
                switch (opcao) {
                    case 1:
                        System.out.print("Digite o tipo (0=Leve, 1=Média, 2=Pesada): ");
                        int tipo = lerOpcao();
                        rl.definir_tipo_limpeza(tipo);
                        break;
                    case 2: rl.limpar(); break;
                    case 3: rl.acionarSensores(); break;
                    case 4:
                        System.out.print("Digite o valor do upgrade para o raio: ");
                        int up = lerOpcao();
                        rl.aprimorar(up);
                        break;
                    case 5: break;
                    default: System.out.println("Ação inválida!"); break;
                }
            } catch (ErrorLimpezaException | RoboDesligadoException | ErrorAprimoramentoException e) {
                System.err.println("🚨 Falha na ação: " + e.getMessage());
            }
         } while (opcao != 5);
    }

    private static void menuRoboBombeiro(RoboBombeiro rb) {
        int opcao;
         do {
            System.out.println("\n--- Ações: " + rb.getId() + " ---");
            System.out.println("1. Adicionar Água");
            System.out.println("2. Apagar Fogo");
            System.out.println("3. Aprimorar Reservatório");
            System.out.println("4. Enviar Mensagem (para Central)");
            System.out.println("5. Voltar");
            System.out.print("Sua ordem: ");
            opcao = lerOpcao();

            try {
                switch (opcao) {
                    case 1:
                        System.out.print("Digite a quantidade de litros: ");
                        int litros = lerOpcao();
                        rb.adicionar_agua(litros);
                        break;
                    case 2: rb.apagar_fogo(); break;
                    case 3:
                        System.out.print("Digite o valor do upgrade para o peso: ");
                        int up = lerOpcao();
                        rb.aprimorar(up);
                        break;
                    case 4:
                        System.out.print("Digite sua mensagem: ");
                        scanner.nextLine(); // Consome a nova linha
                        String msg = scanner.nextLine();
                        rb.enviarMensagem(comunicador, rb.getId() + ": " + msg);
                        System.out.println("Mensagem enviada!");
                        break;
                    case 5: break;
                    default: System.out.println("Ação inválida!"); break;
                }
            } catch (ErrorAbastecimentoException | ErrorApagarFogoException | ErrorAprimoramentoException e) {
                System.err.println("🚨 Falha na ação: " + e.getMessage());
            }
         } while (opcao != 5);
    }


    private static void visualizarMapa() {
        System.out.println("\n--- 🗺️  Mapa do Ambiente (Visão Superior Z=0) 🗺️  ---");
        ambiente.visualizarAmbiente();
        System.out.println("Legenda: r = Robô, o = Obstáculo, c = Comunicador, v = Vazio");
    }

    private static void listarMensagens() {
        System.out.println("\n--- 📡 Central de Comunicação - Histórico 📡 ---");
        comunicador.exibirMensagens();
    }

    /**
     * Lê uma opção inteira do usuário, com tratamento de erros.
     * @return O número inteiro lido.
     */
    private static int lerOpcao() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, digite um número.");
                scanner.next(); // Limpa o buffer
                System.out.print("Tente novamente: ");
            }
        }
    }
}