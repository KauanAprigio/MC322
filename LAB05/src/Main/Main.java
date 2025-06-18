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
import LAB05.src.Entidades.Robos.RoboBombeiro;
import LAB05.src.Entidades.Robos.RoboLimpador;
import LAB05.src.Entidades.Robos.Robo.EstadoRobo;
import LAB05.src.Exceptions.*;
import LAB05.src.Missoes.MissaoAprimorar;
import LAB05.src.Missoes.MissaoLimpar;
import LAB05.src.Missoes.MissaoMoverProximo;


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
        MissaoLimpar limpar = new MissaoLimpar();
        MissaoMoverProximo moverProximo = new MissaoMoverProximo();
        
        try { roboLimpador.executarSensores(); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboLimpador.executarMissao(ambiente); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { ambiente.moverRobo(roboLimpador, 50, 99, 10); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        roboLimpador.setMissao(aprimorar);
        System.out.println(roboLimpador.getAmbiente().getplanoXY()[80][25]);
        try { roboLimpador.executarMissao(ambiente); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        roboLimpador.ligar();
        try { roboLimpador.executarMissao(ambiente); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboLimpador.executarMissao(ambiente); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        
        
        System.out.println("\n--- ✅ Testes finalizados! Preparando para interação... ---");

        

    }
}

   