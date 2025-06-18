package LAB05.src.Main;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import LAB05.src.Entidades.Interfaces.Comunicavel;
import LAB05.src.Entidades.Interfaces.Entidade;
import LAB05.src.Entidades.Interfaces.Entidade.TipoEntidade;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Entidades.Obstaculos.Obstaculo.TipoObstaculo;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.*;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Entidades.Robos.RoboBombeiro;
import LAB05.src.Entidades.Robos.RoboLimpador;
import LAB05.src.Entidades.Robos.Robo.EstadoRobo;
import LAB05.src.Exceptions.*;
import LAB05.src.Missoes.MissaoAprimorar;
import LAB05.src.Missoes.MissaoLimpezaAuto;
import LAB05.src.Missoes.MissaoLimpezaProxima;


/**
 * Classe Main:
 * Ponto de entrada do programa para o Laboratório 5.
 * Configura o ambiente, robôs e obstáculos.
 * Executa testes automatizados para validar funcionalidades e exceções.
 * Apresenta um menu interativo para o usuário.
 * Grupo: Diego Martins e Kauan Aprigio
 * RA's: 260205 e 288809
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static ComunicadorCentral comunicador;
    private static RoboLimpador roboLimpador;
    private static RoboBombeiro roboBombeiro;
    private static Ambiente ambiente;
    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println("🚀 Iniciando Simulação - MC322 - LAB05 🚀");
        System.out.println("=============================================");

        inicializarAmbiente();
        executarTestesAutomatizados();
        

        scanner.close();
        System.out.println("\n=============================================");
        System.out.println("👋 Simulação Finalizada! Até a próxima! 👋");
        System.out.println("=============================================");

        //fecho o logger para printar no log.txt
        ambiente.getLogger().fecharLogger();
    }

    /**
     * Configura o ambiente, obstáculos, robôs e o comunicador central.
     */
    private static void inicializarAmbiente() {
        System.out.println("\n--- 🛠️  Configurando o Mundo Virtual 🛠️  ---\n");
        LeitorConfig inicializdor = new LeitorConfig();
        TipoEntidade[][][] mapa = new TipoEntidade[110][110][110];// depois tem q comentar o pq do z ir até 111, mas acho que é para o robo bombeiro subir acima dos predios para apagar o fogo
        char[][] planoXY = new char[110][110];
        ambiente = new Ambiente(110, 110, 110, mapa, planoXY, "Ambiente de Teste");
        ambiente.inicializarMapa();

        inicializdor.inicializarAmbiente(ambiente, "LAB05/src/Main/config.txt");

        System.out.println("--- ✅ Mundo configurado! ---");
        
    }

     /**
     * Executa uma série de testes para validar as funcionalidades e exceções.
     */
    private static void executarTestesAutomatizados() {
        // instanciando os robos que já alocamos com o leitor config
        for (Entidade e : ambiente.getEntidades()){
            if (e.getRepresentacao() == 'L'){
                roboLimpador = (RoboLimpador) e;
            } else if (e.getRepresentacao() == 'B'){
                roboBombeiro = (RoboBombeiro) e;
            } else if (e.getRepresentacao() == 'c'){
                comunicador = (ComunicadorCentral) e;
            }
        }
        System.out.println("\n--- 🧪 Executando Bateria de Testes 🧪 ---");
        System.out.println("(Silencie-se, mundo! Os testes estão começando!)\n");

        // --- Testes Gerais e Exceções ---
        System.out.println(">> Testando Exceções Gerais...\n");

        try {
            Obstaculo entidade_out = new Obstaculo(200, 200, TipoObstaculo.FOGO, ambiente, TipoEntidade.FOGO);
            ambiente.adicionarEntidade(entidade_out, true);
        } catch ( Exception e) { ambiente.getLogger().logErr(e); }
        
        try {
            Obstaculo entidade_Ocupada = new Obstaculo(0, 0, TipoObstaculo.FOGO, ambiente, TipoEntidade.FOGO);
            ambiente.adicionarEntidade(entidade_Ocupada, true);
        } catch ( Exception e) { ambiente.getLogger().logErr(e); }

        try {
            Obstaculo fantasma = new Obstaculo(60, 60, TipoObstaculo.FOGO, ambiente, TipoEntidade.FOGO);
            ambiente.removerEntidade(fantasma, true);
        } catch (Exception e) { ambiente.getLogger().logErr(e); }
        
        // Aqui é só para printar que o Obstaculo realmente foi removido
        try {
        Obstaculo entidade_teste = new Obstaculo(50, 70, TipoObstaculo.FOGO, ambiente, TipoEntidade.FOGO);    
            ambiente.adicionarEntidade(entidade_teste, false);
            ambiente.removerEntidade(entidade_teste, true);
        } catch (Exception e){
            ambiente.getLogger().logErr(e);;
        }
        
        // --- Testes RoboBombeiro ---
        System.out.print("---------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("\n>> Testando RoboBombeiro...\n");
        // Vale ressaltar que os casos de testes do roboBombeiro não serão tão amplos tendo em vista que eles já foram testados no lab 04
        // Também não ficarei colocando para printar as exception no terminal somente no log, pois já fizemos esses prints no lab 04!!
        roboBombeiro.escolheCentral(comunicador); // aqui faço o Bombeiro instanciar a central correta
        
        // casos de teste quando o bombeiro está desligado
        try { roboBombeiro.adicionar_agua(); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.apagar_fogo(comunicador);; } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.aprimorar(); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        
        roboBombeiro.ligar();
        try { ambiente.moverRobo(roboBombeiro, 50, 99, 0); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { ambiente.moverRobo(roboBombeiro, 150, 99, 0); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.apagar_fogo(comunicador); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.adicionar_agua(); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.enviarMensagem(comunicador, "ABASTECIMENTO"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { ambiente.moverRobo(roboBombeiro, 5, 60, 0); System.out.println("Teste [OK] Mover Bombeiro (Próximo do lago, mas z != 1).\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.adicionar_agua(); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { ambiente.moverRobo(roboBombeiro, 5, 60, 1); System.out.println("Teste [OK] Mover Bombeiro (Próximo do lago).\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.adicionar_agua(); System.out.println("Teste [OK] Abasteceu com sucesso.\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { ambiente.moverRobo(roboBombeiro,0 , 0, 0); System.out.println("Teste [OK] Mover Bombeiro (Próximo do prédio em chamas).\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }        
        try { roboBombeiro.apagar_fogo(comunicador); System.out.println("Teste [OK] Prédio não está mais em chamas.\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.aprimorar(); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.enviarMensagem(comunicador, "APRIMORAMENTO"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { ambiente.moverRobo(roboBombeiro, 60, 5, 0); System.out.println("Teste [OK] Mover Bombeiro (Próximo da oficina).\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }        
        try { roboBombeiro.aprimorar(); System.out.println("Teste [OK] Aprimorou com sucesso.\n");} catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.aprimorar(); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.apagar_fogo(comunicador); System.out.println("Teste [OK] Apagou fogo com sucesso.\n");} catch (Exception e) { ambiente.getLogger().logErr(e); }
        
        // teste de comunicação
        Comunicavel robozin = new RoboBombeiro("jao", 10, 15, 45, 'K', 1000, ambiente, 1000, 10);
        try { roboBombeiro.enviarMensagem(robozin, "NAO VAI ENVIAR NADA GAROTAO"); } catch (Exception e) { ambiente.getLogger().logErr(e); }


        // --- Testes RoboLimpador ---
        System.out.print("---------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("\n>> Testando RoboLimpador...\n");
        MissaoAprimorar aprimorar = new MissaoAprimorar();
        MissaoLimpezaProxima limpeza_proxima = new MissaoLimpezaProxima();
        MissaoLimpezaAuto auto = new MissaoLimpezaAuto();
        
        // Testes com o robo desligado, logo ele irá cair na exception em todos
        try { ambiente.moverRobo(roboLimpador, 50, 99, 10); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboLimpador.executarMissao(ambiente); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboLimpador.executarSensores(); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        
        roboLimpador.setMissao(limpeza_proxima);
        try { roboLimpador.executarMissao(ambiente); } catch (Exception e) { ambiente.getLogger().logErr(e); }

        roboLimpador.setMissao(aprimorar);
        try { roboLimpador.executarMissao(ambiente); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        
        // Testes com o robo ligado
        roboLimpador.ligar();
        try { roboLimpador.executarMissao(ambiente); System.out.println("Teste [OK] Aprimorou com sucesso.\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboLimpador.executarMissao(ambiente); } catch (Exception e) { ambiente.getLogger().logErr(e); } // exception já está aprimorado

        // Vai cair na exception, porque o arrayList não foi instanciado
        roboLimpador.setMissao(limpeza_proxima);
        try { roboLimpador.executarMissao(ambiente); } catch (Exception e) { ambiente.getLogger().logErr(e); }

        // Instancio o arraylist de lixos
        try { roboLimpador.executarSensores(); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        
        // Executará as missões corretamente
        try { roboLimpador.executarMissao(ambiente); } catch (Exception e) { ambiente.getLogger().logErr(e); }

        roboLimpador.setMissao(auto);
        try { roboLimpador.executarMissao(ambiente); } catch (Exception e) { ambiente.getLogger().logErr(e); }

        // Agora cairá nas exceptions que não tem mais lixos
        roboLimpador.setMissao(limpeza_proxima);
        try { roboLimpador.executarMissao(ambiente); } catch (Exception e) { ambiente.getLogger().logErr(e); }

        
        System.out.println("\n--- ✅ Testes finalizados! Preparando para interação... ---");
    }

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
        System.out.println("3. Visualizar o mapa 2D (Z = 0)");
        System.out.println("4. Listar mensagens trocadas");
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
            if (roboSelecionado instanceof RoboLimpador){
                RoboLimpador limpador = (RoboLimpador) roboSelecionado;
                menuAgente(limpador);
            } else if (roboSelecionado instanceof RoboBombeiro){
                RoboBombeiro bombeiro = (RoboBombeiro) roboSelecionado;
                menuRobo(bombeiro);
            }
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

    private static void menuAgente(RoboLimpador agente) {
        int opcao;
        do {
            System.out.println("\n--- Painel de Controle: " + agente.getId() + " ---");
            System.out.println("1. Visualizar Status e Posição");
            System.out.println("2. Ligar/Desligar Robô");
            System.out.println("3. Executar Missões Específicas");
            System.out.println("4. Voltar ao Menu Principal");
            System.out.print("Sua ordem: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1: visualizarStatus(agente); break;
                case 2: ativarDesligar(agente); break;
                case 3: executarMissoesEspecificas(agente); break;
                case 4: System.out.println("Retornando ao menu principal..."); break;
                default: System.out.println("Comando não reconhecido! Tente de novo."); break;
            }
        } while (opcao != 4);
    }

    private static void visualizarStatus(Robo robo) {
        System.out.println("\n--- 📊 Status: " + robo.getId() + " 📊 ---");
        System.out.printf("Posição: (%d, %d, %d)\n", robo.getX_1(), robo.getY_1(), robo.getZ_1());
        System.out.println("Estado: " + robo.getEstado());
        System.out.println("Descrição: " + robo.getDescricao());
        if (robo instanceof RoboLimpador) {
            RoboLimpador rl = (RoboLimpador) robo;
            System.out.println("Raio de Limpeza: " + rl.getRaioLimpeza());
            if (rl.estahAprimorado()) {
                System.out.println(rl.getId() + " já foi aprimorado ao máximo!");
            } else {
                System.out.println(rl.getId() + " Não foi aprimorado ainda!");
                System.out.println("Vá até uma oficina para acrescentar 15m ao raio de detecção de lixos.");
            }
        } else if (robo instanceof RoboBombeiro) {
            RoboBombeiro rb = (RoboBombeiro) robo;
            System.out.println("Altitude Atual: " + rb.getZ_1()); // Usamos getZ() pois altitude não é mais um atributo direto
            System.out.println("Reservatório: " + rb.getReservatorio() + "/" + rb.getCapacidade() + "L");
            if (rb.estahAprimorado()) {
                System.out.println(rb.getId() + " já foi aprimorado ao máximo!");
            } else {
                System.out.println(rb.getId() + " Não foi aprimorado ainda!");
                System.out.println("Vá até uma oficina para acrescentar 1500L de capacidade máxima no reservatório.");
            }
        }
        System.out.println("-------------------------");
    }

    private static void controlarMovimento(Robo robo) {
        System.out.println("\n--- 🧭 Controle de Movimento 🧭 ---");
        System.out.println("Andar 5 metros em uma direção: Use W (Norte), S (Sul), A (Oeste), D (Leste).");
        System.out.println("Subir ou abaixar 20 metros use U (Cima), J (Baixo) - Apenas para Bombeiros.");
        System.out.println("Escolha a variação exata da posição: Use E (Escolher)");
        System.out.print("Digite seu comando (ex: W): ");
        String comando = scanner.next().toUpperCase();
        int deltaX = 0, deltaY = 0, deltaZ = 0;

        switch (comando) {
            case "W": deltaY = -5; break;
            case "S": deltaY = 5; break;
            case "A": deltaX = -5; break;
            case "D": deltaX = 5; break;
            case "U": deltaZ = 20; break; // Sobe 20 unidades
            case "J": deltaZ = -20; break; // Desce 20 unidades
            case "E":
                System.out.println("Escolha quanto andar em x, y e z robô:");
                System.out.print("Digite a variação em X: ");
                deltaX = lerOpcao();
                System.out.print("Digite a variação em Y: ");
                deltaY = lerOpcao();
                if (robo instanceof RoboBombeiro) {
                    System.out.print("Digite a variação em Z (altura): ");
                    deltaZ = lerOpcao();
                } else {
                    deltaZ = 0; // RoboLimpador não usa Z
                }
                break;
            default: System.out.println("Opção inválida!"); return;
        }

        try {
            System.out.printf("Posição_anterior: (%d, %d, %d)\n", robo.getX_1(), robo.getY_1(), robo.getZ_1());
            int novoX = robo.getX_1() + deltaX;
            int novoY = robo.getY_1() + deltaY;
            int novoZ = robo.getZ_1() + deltaZ;
            ambiente.moverRobo(robo, novoX, novoY, novoZ);
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
        menuRoboBombeiro((RoboBombeiro) robo);
    }

    private static void executarMissoesEspecificas(RoboLimpador agente) {
         if (agente.getEstado() == EstadoRobo.OFF) {
            System.out.println("O robô "+ agente.getId() +" está dormindo... Digo, desligado! Ligue-o primeiro.");
            return;
        }
        menuRoboLimpador(agente);
    }

    private static void menuRoboLimpador(RoboLimpador rl) {
         int opcao;
         do {
            System.out.println("\n--- Missões do Agente: " + rl.getId() + " ---");
            System.out.println("1. Limpeza Autônoma");
            System.out.println("2. Limpeza de lixo próximo");
            System.out.println("3. ExecutarSensores");
            System.out.println("4. Aprimorar");
            System.out.println("5. Voltar");
            System.out.print("O que o " + rl.getId() + " vai fazer?: ");
            opcao = lerOpcao();

            try {
                switch (opcao) {
                    case 1: 
                        MissaoLimpezaAuto auto = new MissaoLimpezaAuto();
                        rl.setMissao(auto);
                        rl.executarMissao(ambiente);
                        break;
                    case 2:
                        MissaoLimpezaProxima proxima = new MissaoLimpezaProxima();
                        rl.setMissao(proxima);
                        rl.executarMissao(ambiente);
                        break;
                    case 3:
                        rl.executarSensores();
                        break;
                    case 4:
                        MissaoAprimorar aprimorar = new MissaoAprimorar();
                        rl.setMissao(aprimorar);
                        rl.executarMissao(ambiente);
                        break;
                    case 5:
                        break;
                    default: System.out.println("Ação inválida!"); break;
                }
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
                        rb.adicionar_agua();
                        break;
                    case 2: rb.apagar_fogo(comunicador); System.out.println("Operação anti-fogo executada!"); break;
                    case 3:
                        rb.aprimorar();
                        break;
                    case 4:
                        System.out.println("Digite \"APRIMORAMENTO\" ou \"ABASTECIMENTO\" para receber a localização da oficina ou onde está o lago.");
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
        System.out.println("\n--- 🗺️  Mapa do Ambiente (Visão Z=0) 🗺️  ---");
        ambiente.visualizarAmbiente();
        System.out.println("Legenda: r = Robô, v = Vazio");
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

   