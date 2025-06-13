package LAB05.src.main;

import java.util.Scanner;

import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.ComunicadorCentral;
import LAB05.src.Entidades.Interfaces.Entidade.TipoEntidade;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Entidades.Obstaculos.Obstaculo.TipoObstaculo;
import LAB05.src.Entidades.Robos.Robo;

public class LeitorConfig {
    private Scanner scanner;

    public void inicializarAmbiente(Ambiente a, String filepath) {
        this.scanner = new Scanner(filepath);  
        a.getLogger().inicializarAcao("inicializarAmbiente", "Main");
        try{
            do {
                switch (scanner.next()) {
                    case "#":
                        scanner.nextLine();
                        break;
                    case "FOGO":
                        switch (scanner.next()) {
                            case "FOGO":
                                a.adicionarEntidade(new Obstaculo(scanner.nextInt(), scanner.nextInt(), TipoObstaculo.FOGO, a, TipoEntidade.FOGO));
                                break;
                            case "PREDIOEMCHAMAS":
                                a.adicionarEntidade(new Obstaculo(scanner.nextInt(), scanner.nextInt(), TipoObstaculo.PREDIOEMCHAMAS, a, TipoEntidade.FOGO));
                                break;
                            default:
                                break;
                        }
                    case "LIXO":
                        switch (scanner.next()) {
                            case "COMIDANOCHAO":
                                a.adicionarEntidade(new Obstaculo(scanner.nextInt(), scanner.nextInt(), TipoObstaculo.COMIDANOCHAO, a, TipoEntidade.LIXO));
                                break;
                            case "SACOLAPLASTICA":
                                a.adicionarEntidade(new Obstaculo(scanner.nextInt(), scanner.nextInt(), TipoObstaculo.SACOLAPLASTICA, a, TipoEntidade.LIXO));
                                break;
                            case "SUJEIRAENCARDIDA":
                                a.adicionarEntidade(new Obstaculo(scanner.nextInt(), scanner.nextInt(), TipoObstaculo.SUJEIRAENCARDIDA, a, TipoEntidade.LIXO));
                                break;
                            default:
                                break;
                        }
                    case "LOCAL":
                        switch (scanner.next()) {
                            case "LAGO":
                                a.adicionarEntidade(new Obstaculo(scanner.nextInt(), scanner.nextInt(), TipoObstaculo.LAGO, a, TipoEntidade.LOCAL));
                                break;
                            case "PREDIO":
                                a.adicionarEntidade(new Obstaculo(scanner.nextInt(), scanner.nextInt(), TipoObstaculo.PREDIO, a, TipoEntidade.LOCAL));
                                break;
                            case "OFICINA":
                                a.adicionarEntidade(new Obstaculo(scanner.nextInt(), scanner.nextInt(), TipoObstaculo.OFICINA, a, TipoEntidade.LOCAL));
                                break;
                            default:
                                break;
                        }
                    case "ROBO":
                        switch (scanner.next()) {
                            case "RoboBombeiro":
                                a.adicionarEntidade(new Robo(scanner.next(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(),'y', a));
                                break;
                            case "RoboLimpador":
                                a.adicionarEntidade(new Robo(scanner.next(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(),'x', a));
                                break;
                            default:
                                break;
                        }
                    case "COMUNICADOR":
                        a.adicionarEntidade(new ComunicadorCentral(scanner.nextInt(), scanner.nextInt(), a));
                    default:
                        scanner.nextLine();
                        break;
                };
            } while (scanner.hasNextLine());
            a.getLogger().finalizarAcao("Inicialização do ambiente concluída!");
    } catch (Exception e) {
        a.getLogger().logErr(e);
    }
    } 
}
