package LAB04.Code;

import LAB04.Code.AbstractClasses.Robo;
import LAB04.Code.AbstractClasses.Robo.EstadoRobo;
import LAB04.Code.Exceptions.*;
import LAB04.Code.Interfaces.Entidade.TipoEntidade;
import LAB04.Code.Obstaculo.TipoObstaculo;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Classe Main:
 * Ponto de entrada do programa para o Laboratório 4.
 * Configura o ambiente, robôs e obstáculos.
 * Executa testes automatizados para validar funcionalidades e exceções.
 * Apresenta um menu interativo para o usuário.
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

        scanner.close();
        System.out.println("\n=============================================");
        System.out.println("👋 Simulação Finalizada! Até a próxima! 👋");
        System.out.println("=============================================");
    }

    /**
     * Configura o ambiente, obstáculos, robôs e o comunicador central.
     */
    private static void inicializarAmbiente() {
        System.out.println("\n--- 🛠️  Configurando o Mundo Virtual 🛠️  ---\n");
        // Ajustando tamanho para evitar OutOfBounds (índices vão de 0 a 100, logo precisa de 101)
        TipoEntidade[][][] mapa = new TipoEntidade[101][101][111]; // depois tem q comentar o pq do z ir até 111, mas acho que é para o robo bombeiro subir acima dos predios para apagar o fogo
        char[][] planoXY = new char[101][101];
        ambiente = new Ambiente(101, 101, 111, mapa, planoXY);
        ambiente.inicializarMapa();

        System.out.println("Adicionando obstáculos...\n");
        try {
            ambiente.adicionarEntidade(new Obstaculo(2, 2, TipoObstaculo.LAGO, ambiente, TipoEntidade.OBSTACULO), true);
            ambiente.adicionarEntidade(new Obstaculo(95, 0, TipoObstaculo.FOGO, ambiente, TipoEntidade.OBSTACULO),true); // Fogo para apagar
            ambiente.adicionarEntidade(new Obstaculo(0, 40, TipoObstaculo.PREDIOEMCHAMAS, ambiente, TipoEntidade.OBSTACULO), true); // Prédio para apagar
            ambiente.adicionarEntidade(new Obstaculo(40, 0, TipoObstaculo.PREDIO, ambiente, TipoEntidade.OBSTACULO), true);
            ambiente.adicionarEntidade(new Obstaculo(50, 40, TipoObstaculo.SUJEIRAENCARDIDA, ambiente, TipoEntidade.OBSTACULO), true); // Lixo 1
            ambiente.adicionarEntidade(new Obstaculo(52, 41, TipoObstaculo.COMIDANOCHAO, ambiente, TipoEntidade.OBSTACULO), true); // Lixo 2
            ambiente.adicionarEntidade(new Obstaculo(54, 42, TipoObstaculo.SACOLAPLASTICA, ambiente, TipoEntidade.OBSTACULO), true); // Lixo 3
            ambiente.adicionarEntidade(new Obstaculo(55, 45, TipoObstaculo.OFICINA, ambiente, TipoEntidade.OBSTACULO), true); // Oficina
            comunicador = new ComunicadorCentral(49, 50, ambiente);
            ambiente.adicionarEntidade(comunicador, true);

        } catch (ForaDosLimitesException | LocalOcupadoException e) {
            System.err.println("🚨 Erro Crítico ao adicionar entidade inicial: " + e.getMessage());
            System.exit(1); // Sai se o ambiente base não puder ser criado
        }

        System.out.println("Adicionando robôs...\n");
        try {
            // Construtor RoboLimpador: String id, EstadoRobo estado, int pos_x, int pos_y, int pos_z,Ambiente ambiente, double raio, int raioDeLimpeza
            roboLimpador = new RoboLimpador("Faxinildo_01", EstadoRobo.OFF, 0, 0, 0, ambiente, 15.0, 10);
            // Construtor RoboBombeiro: String id, EstadoRobo estado, int pos_x, int pos_y, int altitude, Ambiente ambiente, int altitudeMaxima, double raiosensor, int peso_max, int raio_de_cessar_fogo
            roboBombeiro = new RoboBombeiro("Chama_Boy_02", EstadoRobo.OFF, 45, 45, 0, ambiente, 105, 3000, 15);
            ambiente.adicionarEntidade(roboLimpador, true);
            ambiente.adicionarEntidade(roboBombeiro, true);
        } catch (ForaDosLimitesException | LocalOcupadoException e) {
            System.err.println("🚨 Erro Crítico ao adicionar robô inicial: " + e.getMessage());
             System.exit(1);
        }
        System.out.println("--- ✅ Mundo configurado! ---");
    }

    /**
     * Executa uma série de testes para validar as funcionalidades e exceções.
     */
    private static void executarTestesAutomatizados() {
        System.out.println("\n--- 🧪 Executando Bateria de Testes 🧪 ---");
        System.out.println("(Silencie-se, mundo! Os testes estão começando!)\n");

        // --- Testes Gerais e Exceções ---
        System.out.println(">> Testando Exceções Gerais...\n");
        try { ambiente.moverEntidade(roboLimpador, 1, 1, 0); } catch (Exception e) { System.err.println("Teste [OK] RoboDesligadoException (Mover): " + e.getMessage()); }
        roboLimpador.ligar();
        roboBombeiro.ligar();
        //mudei para 102 ao inves de 101, nao sei pq tava dando OutOfBounds no 101...TEM QUE VER ISSO 
        try { ambiente.moverEntidade(roboLimpador, 102, 0, 0); } catch (Exception e) { System.err.println("Teste [OK] ForaDosLimitesException: " + e.getMessage()); }
        try { ambiente.moverEntidade(roboLimpador, 2, 2, 0); } catch (Exception e) { System.err.println("Teste [OK] LocalOcupadoException: " + e.getMessage()); }
        try { ambiente.moverEntidade(roboLimpador, 1, 1, 5); } catch (Exception e) { System.err.println("Teste [OK] NaoPodeVoarException: " + e.getMessage()); }
        try {
            Obstaculo fantasma = new Obstaculo(60, 60, TipoObstaculo.FOGO, ambiente, TipoEntidade.OBSTACULO);
            ambiente.removerEntidade(fantasma, true);
        } catch (Exception e) { System.err.println("Teste [OK] EntidadeNaoEncontradaException: " + e.getMessage()); }

        // --- Testes RoboLimpador ---
        System.out.print("---------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("\n>> Testando RoboLimpador ("+ roboLimpador.getId() +")...\n");
        try { roboLimpador.acionarSensores(); System.out.println("Teste [OK] Acionar Sensores (Ligado).\n"); } catch (Exception e) { System.err.println("Teste [FALHA]: " + e.getMessage()); }
        try { roboLimpador.definir_tipo_limpeza(5); } catch (Exception e) { System.err.println("Teste [OK] ErrorLimpezaException (Tipo Inválido): " + e.getMessage()); }
        try { roboLimpador.definir_tipo_limpeza(0); System.out.println("Teste [OK] Definir Tipo Limpeza (0).\n"); } catch (Exception e) { System.err.println("Teste [FALHA]: " + e.getMessage()); }
        try { roboLimpador.limpar(); } catch (Exception e) { System.err.println("Teste [OK] ErrorLimpezaException (Sem Lixo Perto): " + e.getMessage()); }
        try { ambiente.moverEntidade(roboLimpador, 53, 41, 0); System.out.println("Teste [OK] Mover Limpador.\n"); } catch (Exception e) { System.err.println("Teste [FALHA]: " + e.getMessage()); }        
        try { roboLimpador.limpar(); } catch (Exception e) { System.err.println("Teste [OK] ErrorLimpezaException (Tipo Incorreto): " + e.getMessage()); }
        try { roboLimpador.definir_tipo_limpeza(1); roboLimpador.limpar(); System.out.println("Teste [OK] Limpou Comida.\n"); } catch (Exception e) { System.err.println("Teste [FALHA]: " + e.getMessage()); } // Deve limpar Comida
        try { roboLimpador.aprimorar(10); } catch (Exception e) { System.err.println("Teste [OK] ErrorAprimoramentoException (Fora Oficina): " + e.getMessage()); }
        try { ambiente.moverEntidade(roboLimpador, 56, 46, 0); System.out.println("Teste [OK] Mover Limpador (Oficina).\n"); } catch (Exception e) { System.err.println("Teste [FALHA]: " + e.getMessage()); }
        try { roboLimpador.aprimorar(-5); } catch (Exception e) { System.err.println("Teste [OK] ErrorAprimoramentoException (Valor Inválido): " + e.getMessage()); }
        try { roboLimpador.aprimorar(5); System.out.println("Teste [OK] Aprimorou Limpador.\n"); } catch (Exception e) { System.err.println("Teste [FALHA]: " + e.getMessage()); }
        roboLimpador.desligar();
        try { roboLimpador.acionarSensores(); } catch (Exception e) { System.err.println("Teste [OK] RoboDesligadoException (Sensores): " + e.getMessage()); }

        // --- Testes RoboBombeiro ---
        System.out.print("---------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("\n>> Testando RoboBombeiro (" + roboBombeiro.getId() +")...\n");
        roboBombeiro.ligar();
        try { roboBombeiro.adicionar_agua(100); } catch (Exception e) { System.err.println("Teste [OK] ErrorAbastecimentoException (Fora Lago): " + e.getMessage()); }
        try { ambiente.moverEntidade(roboBombeiro, 5, 5, 1); System.out.println("Teste [OK] Mover Bombeiro (Lago).\n"); } catch (Exception e) { System.err.println("Teste [FALHA]: " + e.getMessage()); }
        try { roboBombeiro.adicionar_agua(-10); } catch (Exception e) { System.err.println("Teste [OK] ErrorAbastecimentoException (Valor Inválido): " + e.getMessage()); }
        try { roboBombeiro.adicionar_agua(4000); } catch (Exception e) { System.err.println("Teste [OK] ErrorAbastecimentoException (Capacidade): " + e.getMessage()); }
        try { roboBombeiro.adicionar_agua(500); System.out.println("Teste [OK] Abasteceu Bombeiro.\n"); } catch (Exception e) { System.err.println("Teste [FALHA]: " + e.getMessage()); }
        try { roboBombeiro.apagar_fogo(comunicador); System.out.println("Teste [OK] Apagar Fogo (Sem Fogo Perto).\n"); } catch (Exception e) { System.err.println("Teste [FALHA - Inesperado]: " + e.getMessage()); }
        try { ambiente.moverEntidade(roboBombeiro, 90, 3, 0); System.out.println("Teste [OK] Mover Bombeiro (Perto Fogo).\n"); } catch (Exception e) { System.err.println("Teste [FALHA]: " + e.getMessage()); }
        try { roboBombeiro.apagar_fogo(comunicador); } catch (Exception e) { System.err.println("Teste [OK] ErrorApagarFogoException (Altura): " + e.getMessage()); }
        try { ambiente.moverEntidade(roboBombeiro, 90, 3, 5); System.out.println("Teste [OK] Subir Bombeiro.\n"); } catch (Exception e) { System.err.println("Teste [FALHA]: " + e.getMessage()); }
        try { roboBombeiro.apagar_fogo(comunicador); System.out.println("Teste [OK] Apagou Fogo.\n"); } catch (Exception e) { System.err.println("Teste [FALHA]: " + e.getMessage()); } // Deve apagar FOGO
        try { ambiente.moverEntidade(roboBombeiro, 0, 40, 101); System.out.println("Teste [OK] Mover Bombeiro (Perto Prédio Chamas).\n"); } catch (Exception e) { System.err.println("Teste [FALHA]: " + e.getMessage()); }
        
        // ERRO AO TENTAR READICIONAR O PREDIO EM CHAMAS
        try { roboBombeiro.apagar_fogo(comunicador); } catch (Exception e) { System.err.println("Teste [OK] ErrorApagarFogoException (Água Insuficiente): " + e.getMessage()); }
        
        roboBombeiro.desligar();

        // AQUI ELE FALA SO Q TA FORA DO LAGO, DEVE VER PRIMEIRO SE ELE TA DESLIGADO ACHO EU 
        try { roboBombeiro.adicionar_agua(100); } catch (Exception e) { System.err.println("Teste [OK] ErrorAbastecimentoException (Desligado): " + e.getMessage()); }

        // --- Testes ComunicadorCentral ---
         System.out.print("---------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("\n>> Testando ComunicadorCentral...\n");
        comunicador.listarFogos(); // Deve listar o Prédio em Chamas
        roboLimpador.ligar(); // Ligar limpador para testar comunicação
        roboBombeiro.ligar(); // Ligar bombeiro
        try { comunicador.avisoFogoProximo(roboLimpador); } catch (Exception e) { System.err.println("Teste [OK] ErroComunicacaoException: " + e.getMessage()); }
        try { comunicador.avisoFogoProximo(roboBombeiro); System.out.println("Teste [OK] Aviso Fogo (Bombeiro).\n");} catch (Exception e) { System.err.println("Teste [FALHA]: " + e.getMessage()); }
        //roboBombeiro.enviarMensagem(comunicador, roboBombeiro.getId() + ": Mensagem de teste para a central.\n");
        comunicador.exibirMensagens();

        System.out.println("\n--- ✅ Testes finalizados! Preparando para interação... ---");
        // Reinicia robos para o menu, garantindo que estejam em posições e estados conhecidos
        char[][] plano_Menu_interativo = new char[105][105];
        TipoEntidade[][][] mapa_Menu_interativo = new TipoEntidade[105][105][110]; // Aumentando o tamanho do mapa para o menu interativo
        Ambiente Menu_interativo = new Ambiente(105, 105, 105, mapa_Menu_interativo, plano_Menu_interativo);
        Menu_interativo.inicializarMapa();
        try {
            Menu_interativo.adicionarEntidade(new RoboBombeiro("Diego", EstadoRobo.OFF, 0, 0, 0, Menu_interativo, 105, 3000, 20), true);
            Menu_interativo.adicionarEntidade( new RoboLimpador("Gustavo", EstadoRobo.OFF, 0, 2, 0, Menu_interativo, 15, 15), true);
            Menu_interativo.adicionarEntidade( new RoboBombeiro("Kauan", EstadoRobo.OFF, 50, 50, 0, Menu_interativo, 85, 4000, 20), true);
            Menu_interativo.adicionarEntidade( new RoboLimpador("Esther", EstadoRobo.OFF, 100, 100, 0, Menu_interativo, 30, 15), true);
        } catch (Exception e) { System.err.println("Erro ao iniciar robôs: " + e.getMessage());}
        try {
            Menu_interativo.adicionarEntidade(new Obstaculo(5, 5, TipoObstaculo.FOGO, Menu_interativo, TipoEntidade.OBSTACULO), true);
            Menu_interativo.adicionarEntidade(new Obstaculo(15, 15, TipoObstaculo.PREDIOEMCHAMAS, Menu_interativo, TipoEntidade.OBSTACULO), true);
            Menu_interativo.adicionarEntidade(new Obstaculo(3, 3, TipoObstaculo.SACOLAPLASTICA, Menu_interativo, TipoEntidade.OBSTACULO), false);
            Menu_interativo.adicionarEntidade(new Obstaculo(4, 4, TipoObstaculo.COMIDANOCHAO, Menu_interativo, TipoEntidade.OBSTACULO), false);
            Menu_interativo.adicionarEntidade(new Obstaculo(6, 2, TipoObstaculo.SUJEIRAENCARDIDA, Menu_interativo, TipoEntidade.OBSTACULO), false);
            Menu_interativo.adicionarEntidade(new Obstaculo(60, 5, TipoObstaculo.OFICINA, Menu_interativo, TipoEntidade.OBSTACULO), true);
            Menu_interativo.adicionarEntidade(new Obstaculo(5, 60, TipoObstaculo.LAGO, Menu_interativo, TipoEntidade.OBSTACULO), true);
            Menu_interativo.adicionarEntidade(new Obstaculo(69, 69, TipoObstaculo.PREDIO, Menu_interativo, TipoEntidade.OBSTACULO), true);
            ComunicadorCentral Menu_comunicador = new ComunicadorCentral(51, 51, Menu_interativo);
            comunicador = Menu_comunicador; // Atualiza o comunicador para o novo ambiente
            Menu_interativo.adicionarEntidade(comunicador, true);
        } catch (Exception e) {
            System.err.println("Erro ao iniciar os Obstáculos: " + e.getMessage());
        }
        ambiente = Menu_interativo; // Atualiza o ambiente para o menu interativo
    }

//DEPOIS IREI VER O MENU INTERATIVO, MAS ENQUANTO ISSO VOU CONSERTAR O QUE VI ATÉ AGORA NOS CASOS DE TESTE


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
                case 5: break; // Apenas sai do loop
                default: System.out.println("Opção inválida! Parece que você apertou o botão errado. Tente de novo!"); break;
            }
        } while (opcao != 5);
        System.out.println("\nSaindo do menu interativo. Foi um prazer, piloto!");
    }

    private static void imprimirMenuPrincipal() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Listar todos os robôs (por tipo e estado)");
        System.out.println("2. Escolher um robô para interagir");
        System.out.println("3. Visualizar o mapa 2D (visão de cima)");
        System.out.println("4. Listar mensagens trocadas (via Central)");
        System.out.println("5. Sair do Simulador");
        System.out.print("Escolha sua ação, comandante: ");
    }

     private static void listarRobos() {
        System.out.println("\n--- 🛰️  Nossa Frota Atual 🛰️  ---");
        List<Robo> robos = ambiente.getEntidades().stream()
                .filter(e -> e.getTipo() == TipoEntidade.ROBO)
                .map(e -> (Robo) e)
                .collect(Collectors.toList());

        if (robos.isEmpty()) {
            System.out.println("Ué? O hangar está vazio! Cadê os robôs?");
            return;
        }

        System.out.println("\n>> Robôs por Tipo:");
        robos.stream()
            .filter(r -> r instanceof RoboLimpador)
            .forEach(r -> System.out.printf("  - %s (Limpador)\n", r.getId()));
        robos.stream()
            .filter(r -> r instanceof RoboBombeiro)
            .forEach(r -> System.out.printf("  - %s (Bombeiro)\n", r.getId()));

        System.out.println("\n>> Robôs por Estado:");
        robos.forEach(r -> System.out.printf("  - %s: %s\n", r.getId(), r.getEstado()));
    }


    private static void escolherRoboParaInteragir() {
        System.out.println("\n--- 🎮 Seleção de Robô 🎮 ---");
         List<Robo> robos = ambiente.getEntidades().stream()
                .filter(e -> e.getTipo() == TipoEntidade.ROBO)
                .map(e -> (Robo) e)
                .collect(Collectors.toList());

        if (robos.isEmpty()) {
            System.out.println("Não há robôs para escolher. Que estranho...");
            return;
        }

        System.out.println("Qual máquina vamos pilotar agora?");
        for (int i = 0; i < robos.size(); i++) {
            System.out.printf("%d. %s (%s)\n", i + 1, robos.get(i).getId(), robos.get(i).getClass().getSimpleName());
        }
        System.out.print("Digite o número do robô (ou 0 para voltar): ");

        int escolha = lerOpcao();
        if (escolha > 0 && escolha <= robos.size()) {
            Robo roboSelecionado = robos.get(escolha - 1);
            System.out.printf("\nAssumindo controle de %s. Vamos lá!\n", roboSelecionado.getId());
            menuRobo(roboSelecionado);
        } else if (escolha != 0) {
            System.out.println("Número inválido! Escolha um da lista.");
        }
    }

    private static void menuRobo(Robo robo) {
        int opcao;
        do {
            System.out.println("\n--- Painel de Controle: " + robo.getId() + " ---");
            System.out.println("1. Visualizar Status e Posição");
            System.out.println("2. Mover Robô");
            System.out.println("3. Ligar/Desligar Robô");
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
                default: System.out.println("Comando não reconhecido! Tente de novo."); break;
            }
        } while (opcao != 5);
    }

    private static void visualizarStatus(Robo robo) {
        System.out.println("\n--- 📊 Status: " + robo.getId() + " 📊 ---");
        System.out.printf("Posição: (%d, %d, %d)\n", robo.getX(), robo.getY(), robo.getZ());
        System.out.println("Estado: " + robo.getEstado());
        System.out.println("Descrição: " + robo.getDescricao());
        if (robo instanceof RoboLimpador) {
            RoboLimpador rl = (RoboLimpador) robo;
            System.out.println("Tipo Limpeza Atual: " + rl.getTipoLimpeza());
            System.out.println("Raio de Limpeza: " + rl.getRaioDeLimpeza());
        } else if (robo instanceof RoboBombeiro) {
            RoboBombeiro rb = (RoboBombeiro) robo;
            System.out.println("Altitude Atual: " + rb.getZ()); // Usamos getZ() pois altitude não é mais um atributo direto
            System.out.println("Reservatório: " + rb.getReservatorio() + "/" + rb.getCapacidade() + "L");
            System.out.println("Altitude Máxima Permitida: " + rb.getAltitudeMaxima());
        }
         System.out.println("-------------------------");
    }

    private static void controlarMovimento(Robo robo) {
        System.out.println("\n--- 🧭 Controle de Movimento 🧭 ---");
        System.out.println("Use W (Norte), S (Sul), A (Oeste), D (Leste).");
        System.out.println("Use U (Cima), J (Baixo) - Apenas para Bombeiros.");
        System.out.print("Digite seu comando (ex: W): ");
        String comando = scanner.next().toUpperCase();
        int deltaX = 0, deltaY = 0, deltaZ = 0;

        switch (comando) {
            case "W": deltaY = 5; break;
            case "S": deltaY = -5; break;
            case "A": deltaX = -5; break;
            case "D": deltaX = 5; break;
            case "U": deltaZ = 5; break; // Sobe 5 unidades
            case "J": deltaZ = -5; break; // Desce 5 unidades
            default: System.out.println("Direção inválida!"); return;
        }

        try {
            System.out.printf("Posição_anterior: (%d, %d, %d)\n", robo.getX(), robo.getY(), robo.getZ());
            int novoX = robo.getX() + deltaX;
            int novoY = robo.getY() + deltaY;
            int novoZ = robo.getZ() + deltaZ;
            ambiente.moverEntidade(robo, novoX, novoY, novoZ);
            System.out.printf(">>> %s movido para (%d, %d, %d)!\n", robo.getId(), novoX, novoY, novoZ);
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
        System.out.println(">>> " + robo.getId() + " está agora: " + robo.getEstado());
    }

    private static void executarAcoesEspecificas(Robo robo) {
         if (robo.getEstado() == EstadoRobo.OFF) {
            System.out.println("O robô "+ robo.getId() +" está dormindo... Digo, desligado! Ligue-o primeiro.");
            return;
        }

        if (robo instanceof RoboLimpador) {
            menuRoboLimpador((RoboLimpador) robo);
        } else if (robo instanceof RoboBombeiro) {
            menuRoboBombeiro((RoboBombeiro) robo);
        }
    }

    private static void menuRoboLimpador(RoboLimpador rl) {
         int opcao;
         do {
            System.out.println("\n--- Ações Específicas: " + rl.getId() + " ---");
            System.out.println("1. Definir Tipo Limpeza");
            System.out.println("2. Limpar Área");
            System.out.println("3. Acionar Sensores de Lixo");
            System.out.println("4. Aprimorar Raio (Ir à Oficina)");
            System.out.println("5. Voltar");
            System.out.print("O que o " + rl.getId() + " vai fazer?: ");
            opcao = lerOpcao();

            try {
                switch (opcao) {
                    case 1:
                        System.out.print("Digite o tipo (0=Sacola, 1=Comida, 2=Encardido): ");
                        int tipo = lerOpcao();
                        rl.definir_tipo_limpeza(tipo);
                        break;
                    case 2: rl.limpar(); System.out.println("Faxina concluída (ou tentada)!"); break;
                    case 3: rl.acionarSensores(); break;
                    case 4:
                        System.out.print("Digite o valor do upgrade para o raio (ex: 5): ");
                        int up = lerOpcao();
                        rl.aprimorar(up);
                        break;
                    case 5: break;
                    default: System.out.println("Ação inválida!"); break;
                }
            } catch (ErrorLimpezaException | RoboDesligadoException | ErrorAprimoramentoException e) {
                System.err.println("🚨 Falha na ação: " + e.getMessage());
            } catch (Exception e) {
                 System.err.println("🚨 Erro inesperado: " + e.getMessage());
            }
         } while (opcao != 5);
    }

    private static void menuRoboBombeiro(RoboBombeiro rb) {
        int opcao;
         do {
            System.out.println("\n--- Ações Específicas: " + rb.getId() + " ---");
            System.out.println("1. Adicionar Água (Só nos lagos, hein!)");
            System.out.println("2. Apagar Fogo (Ao resgate!)");
            System.out.println("3. Aprimorar Reservatório (Ir à Oficina)");
            System.out.println("4. Enviar Mensagem (para Central)");
            System.out.println("5. Voltar");
            System.out.print("O que o " + rb.getId() + " vai fazer?: ");
            opcao = lerOpcao();

            try {
                switch (opcao) {
                    case 1:
                        System.out.print("Digite a quantidade de litros (ex: 1000): ");
                        int litros = lerOpcao();
                        rb.adicionar_agua(litros);
                        break;
                    case 2: rb.apagar_fogo(comunicador); System.out.println("Operação anti-fogo executada!"); break;
                    case 3:
                        System.out.print("Digite o valor do upgrade para o peso (ex: 500): ");
                        int up = lerOpcao();
                        rb.aprimorar(up);
                        break;
                    case 4:
                        System.out.println("Digite \"AJUDA\" para receber a localização do incêndio mais próximo.");
                        System.out.print("Digite sua mensagem para a central: ");
                        String msg = scanner.nextLine();
                        rb.enviarMensagem(comunicador, msg);
                        break;
                    case 5: break;
                    default: System.out.println("Ação inválida!"); break;
                }
            } catch (ErrorAbastecimentoException | ErrorApagarFogoException | ErrorAprimoramentoException e) {
                System.err.println("🚨 Falha na ação: " + e.getMessage());
            } catch (Exception e) {
                 System.err.println("🚨 Erro inesperado: " + e.getMessage());
            }
         } while (opcao != 5);
    }

    private static void visualizarMapa() {
        System.out.println("\n--- 🗺️  Mapa do Ambiente (Visão Superior Z=0) 🗺️  ---");
        ambiente.visualizarAmbiente();
        System.out.println("Legenda: r = Robô, o = Obstáculo, c = Comunicador, v = Vazio");
        System.out.println("(Lembre-se: O mapa 2D não mostra a altitude!)");
    }

    private static void listarMensagens() {
        System.out.println("\n--- 📡 Central de Comunicação - Histórico 📡 ---");
        comunicador.exibirMensagens();
        if (comunicador.getTotalMensagens() == 0) {
            System.out.println("O rádio está quieto... Nenhuma mensagem registrada.");
        }
         System.out.println("---------------------------------------------");
    }

    /**
     * Lê uma opção inteira do usuário, com tratamento de erros.
     * @return O número inteiro lido.
     */
    private static int lerOpcao() {
        while (true) {
            try {
                int valor = scanner.nextInt();
                scanner.nextLine(); // Consome a nova linha pendente após ler o int
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, digite um número inteiro.");
                scanner.next(); // Limpa o buffer da entrada inválida
                System.out.print("Tente novamente: ");
            }
        }
    }
}