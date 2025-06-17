package LAB05.src.Main;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import LAB05.src.Entidades.Interfaces.Comunicavel;
import LAB05.src.Entidades.Interfaces.Entidade.TipoEntidade;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Entidades.Obstaculos.Obstaculo.TipoObstaculo;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.*;
import LAB05.src.Entidades.Robos.RoboBombeiro;
import LAB05.src.Entidades.Robos.RoboLimpador;
import LAB05.src.Entidades.Robos.Robo.EstadoRobo;
import LAB05.src.Exceptions.*;


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
            System.out.println("Teste [FALHA]" + e.getMessage());
        }
        
        // --- Testes RoboBombeiro ---
        System.out.print("---------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("\n>> Testando RoboBombeiro...\n");
        try { ambiente.moverRobo(roboBombeiro, 90, 3, 5); System.out.println("Teste [OK] Mover Bombeiro (Perto fogo).\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.aprimorar(); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.apagar_fogo(comunicador); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.adicionar_agua(); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { ambiente.moverRobo(roboBombeiro, 5, 5, 5); System.out.println("Teste [OK] Mover Bombeiro (Lago).\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.adicionar_agua(); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { ambiente.moverRobo(roboBombeiro, 5, 5, 1); System.out.println("Teste [OK] Mover Bombeiro (Lago).\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }        
        try { roboBombeiro.adicionar_agua(); System.out.println("Teste [OK] Abasteceu Bombeiro.\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.apagar_fogo(comunicador); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { ambiente.moverRobo(roboBombeiro, 75, 5, 1); System.out.println("Teste [OK] Mover Bombeiro (Próximo do fogo).\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }        
        try { roboBombeiro.enviarMensagem(comunicador,"AJUDA"); System.out.println("Teste [OK] Bombeiro pediu ajuda.\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { ambiente.moverRobo(roboBombeiro, 90, 3, 0); System.out.println("Teste [OK] Mover Bombeiro (Perto Fogo).\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.apagar_fogo(comunicador); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { ambiente.moverRobo(roboBombeiro, 90, 3, 5); System.out.println("Teste [OK] Subir Bombeiro.\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.apagar_fogo(comunicador); System.out.println("Teste [OK] Apagou Fogo.\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); } // Deve apagar FOGO
        try { roboBombeiro.enviarMensagem(comunicador,"AJUDA"); System.out.println("Teste [OK] Bombeiro pediu ajuda.\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { ambiente.moverRobo(roboBombeiro, 0, 40, 101); System.out.println("Teste [OK] Mover Bombeiro (Perto Prédio Chamas).\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.apagar_fogo(comunicador); System.out.println("Teste [OK] Apagou Prédio em chamas.\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); } // Deve apagar FOGO
        try { ambiente.moverRobo(roboBombeiro, 60, 46, 0); System.out.println("Teste [OK] Mover Oficina.\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.aprimorar(); System.out.println("Teste [OK] Aprimorado com sucesso.\n"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.aprimorar(); } catch (Exception e) { ambiente.getLogger().logErr(e); }

        // teste de comunicação
        Comunicavel robozin = new RoboBombeiro("jao", 10, 15, 45, 'K', 1000, ambiente, 1000, 10);
        try { roboBombeiro.enviarMensagem(robozin, "NAO VAI ENVIAR NADA GAROTAO"); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        
        // testes com o robo desligado
        try { roboBombeiro.adicionar_agua(); } catch (Exception e) { ambiente.getLogger().logErr(e); }
        try { roboBombeiro.apagar_fogo(comunicador);; } catch (Exception e) { ambiente.getLogger().logErr(e); }


        System.out.println("\n--- ✅ Testes finalizados! Preparando para interação... ---");

    }
}

   