package LAB05.src.Main;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.ComunicadorCentral;
import LAB05.src.Entidades.Interfaces.Entidade.TipoEntidade;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Entidades.Obstaculos.Obstaculo.TipoObstaculo;
import LAB05.src.Entidades.Robos.Robo;

public class LeitorConfig {
    private Scanner scanner;
    private File arquivo;

    public void inicializarAmbiente(Ambiente ambiente, String filepath) {
        arquivo = new File(filepath);
        try {
            this.scanner = new Scanner(arquivo); // escaneia o arquivo
        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo de configuração não encontrado: " + e.getMessage());
            ambiente.getLogger().logErr(e);
        }

        ambiente.getLogger().inicializarAcao("inicializarAmbiente", "Main");
        try {
            // loop para ler o arquivo linha por linha
            while (scanner.hasNextLine()) {
                String linhaCompleta = scanner.nextLine().trim(); // le a linha e remove espaços em branco

                // ignora linhas vazias
                if (linhaCompleta.isEmpty()) {
                    continue;
                }

                // quando tiver comentario só pula eles mesmo
                if (linhaCompleta.startsWith("#")) {
                    continue;
                }

                // instancio um scanner auxiliar 
                Scanner scannerAuxiliar = new Scanner(linhaCompleta);

                // confirmo se tem algo no scanner
                if (!scannerAuxiliar.hasNext()) {
                    scannerAuxiliar.close();
                    continue;
                }

                String tipoPrincipal = scannerAuxiliar.next(); // armazena a primeira palavra para ver o case que vai cair

                switch (tipoPrincipal) { //divisao dos casos aqui, nao precisaria mudar o nome das variaveis x,y e z se a ordem do config.txt fosse diferente
                    case "FOGO":
                        String tipoFogo = scannerAuxiliar.next();
                        int xFogo = scannerAuxiliar.nextInt();
                        int yFogo = scannerAuxiliar.nextInt();
                        if (tipoFogo.equals("FOGO")) {
                            ambiente.adicionarEntidade(new Obstaculo(xFogo, yFogo, TipoObstaculo.FOGO, ambiente, TipoEntidade.FOGO), true);
                        } else if (tipoFogo.equals("PREDIOEMCHAMAS")) {
                            ambiente.adicionarEntidade(new Obstaculo(xFogo, yFogo, TipoObstaculo.PREDIOEMCHAMAS, ambiente, TipoEntidade.FOGO), true);
                        }
                        break;
                    case "LIXO":
                        String tipoLixo = scannerAuxiliar.next();
                        int xLixo = scannerAuxiliar.nextInt();
                        int yLixo = scannerAuxiliar.nextInt();
                        switch (tipoLixo) {
                            case "COMIDANOCHAO":
                                ambiente.adicionarEntidade(new Obstaculo(xLixo, yLixo, TipoObstaculo.COMIDANOCHAO, ambiente, TipoEntidade.LIXO), true);
                                break;
                            case "SACOLAPLASTICA":
                                ambiente.adicionarEntidade(new Obstaculo(xLixo, yLixo, TipoObstaculo.SACOLAPLASTICA, ambiente, TipoEntidade.LIXO), true);
                                break;
                            case "SUJEIRAENCARDIDA":
                                ambiente.adicionarEntidade(new Obstaculo(xLixo, yLixo, TipoObstaculo.SUJEIRAENCARDIDA, ambiente, TipoEntidade.LIXO), true);
                                break;
                        }
                        break;
                    case "LOCAL":
                        String tipoLocal = scannerAuxiliar.next();
                        int xLocal = scannerAuxiliar.nextInt();
                        int yLocal = scannerAuxiliar.nextInt();
                        switch (tipoLocal) {
                            case "LAGO":
                                ambiente.adicionarEntidade(new Obstaculo(xLocal, yLocal, TipoObstaculo.LAGO, ambiente, TipoEntidade.LOCAL), true);
                                break;
                            case "PREDIO":
                                ambiente.adicionarEntidade(new Obstaculo(xLocal, yLocal, TipoObstaculo.PREDIO, ambiente, TipoEntidade.LOCAL), true);
                                break;
                            case "OFICINA":
                                ambiente.adicionarEntidade(new Obstaculo(xLocal, yLocal, TipoObstaculo.OFICINA, ambiente, TipoEntidade.LOCAL), true);
                                break;
                        }
                        break;
                    case "ROBO":
                        String tipoRobo = scannerAuxiliar.next();
                        String nomeRobo = scannerAuxiliar.next();
                        int xRobo = scannerAuxiliar.nextInt();
                        int yRobo = scannerAuxiliar.nextInt();
                        int zRobo = scannerAuxiliar.nextInt(); // Para o robo bombeiro
                        if (tipoRobo.equals("RoboBombeiro")) {
                            ambiente.adicionarEntidade(new Robo(nomeRobo, xRobo, yRobo, zRobo, 'y', ambiente), true);
                        } else if (tipoRobo.equals("RoboLimpador")) {
                            ambiente.adicionarEntidade(new Robo(nomeRobo, xRobo, yRobo, zRobo, 'x', ambiente), true);
                        }
                        break;
                    case "COMUNICADOR":
                        int xCom = scannerAuxiliar.nextInt();
                        int yCom = scannerAuxiliar.nextInt();
                        ambiente.adicionarEntidade(new ComunicadorCentral(xCom, yCom, ambiente), true);
                        break;
                    default:
                        System.out.println("Aviso: Linha de configuração desconhecida ou inválida: " + linhaCompleta);
                        break;
                }
                scannerAuxiliar.close(); // Fechar o Scanner da linha
            }
            ambiente.getLogger().finalizarAcao("Inicialização do ambiente concluída!");
        } catch (Exception e) {
            System.err.println("Erro durante a leitura do arquivo de configuração: " + e.getMessage());
            ambiente.getLogger().logErr(e);
        } finally { // quando acabar tudo e passar por tudo fecha o scanner principal
            if (this.scanner != null) {
                this.scanner.close(); 
            }
        }
    }
}