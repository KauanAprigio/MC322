package LAB05.src.Main;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


import LAB05.src.Entidades.Interfaces.Entidade.TipoEntidade;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.*;
import LAB05.src.Entidades.Robos.RoboBombeiro;
import LAB05.src.Entidades.Robos.RoboLimpador;
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
    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println("🚀 Iniciando Simulação - MC322 - LAB05 🚀");
        System.out.println("=============================================");

        inicializarAmbiente();

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
        LeitorConfig inicializdor = new LeitorConfig();
        TipoEntidade[][][] mapa = new TipoEntidade[110][110][110];// depois tem q comentar o pq do z ir até 111, mas acho que é para o robo bombeiro subir acima dos predios para apagar o fogo
        char[][] planoXY = new char[110][110];
        Ambiente ambiente = new Ambiente(110, 110, 110, mapa, planoXY, "Ambiente de Teste");
        ambiente.inicializarMapa();

        inicializdor.inicializarAmbiente(ambiente, "LAB05/src/Main/config.txt");

        //fecho o logger para printar no log.txt
        ambiente.getLogger().fecharLogger();
    }
}

   