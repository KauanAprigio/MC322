/*
 *  Grupo: Diego Martins e Kauan Aprigio
 *  
 *  Última modificação:
 * 
 *  Laboratório 3 - MC322 - Programação orientada a objetos
 *  
 */

package LAB03.Code;

import LAB03.Code.Obstaculo.TipoObstaculo;
import java.util.Scanner;

/*
 * Essa classe contém o método main que cria um ambiente e robôs,
 * adiciona os robôs ao ambiente e executa testes de movimentação,
 * funcionalidades e exibição do ambiente.
 * Ela printa todos os resultados no console;
 * o menu interativo para utilização das classes e metódos.
 */


/*
 * Classe Main:
 * 
 * 
 * 
 * 
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // Criação do ambiente
        Ambiente ambiente = new Ambiente(100, 100, "Ambiente", 110);

        //Criação dos obstáculos
        Obstaculo lago = new Obstaculo(2, 2, TipoObstaculo.LAGO); //Coordenadas X(2,32), Y(2,32), no caso eu digo o começo e o fim do X, depois do Y, já a altura sempre começa do 0, logo dou o valor dela, se nao tiver é porque o objeto nao tem altura
        Obstaculo fogo = new Obstaculo(95, 0, TipoObstaculo.FOGO); //Coordenadas X(95,100), Y(0,5) e Z = 5m
        Obstaculo predio_chamas = new Obstaculo(0, 40, TipoObstaculo.PREDIOEMCHAMAS); //Coordenadas X(0,30), Y(40,70) e Z = 30m
        Obstaculo predio = new Obstaculo(40, 0, TipoObstaculo.PREDIO); //Coordenadas X(40,70), Y(0,30) e Z = 30m
        Obstaculo sujeira = new Obstaculo(50, 40, TipoObstaculo.SUJEIRAENCARDIDA); //Coordenadas (50,40)
        Obstaculo comida = new Obstaculo(52, 41, TipoObstaculo.COMIDANOCHAO); //Coordenadas (52,41)
        Obstaculo sacola = new Obstaculo(54, 42, TipoObstaculo.SACOLAPLASTICA); //Coordenadas (54,42)
        Obstaculo oficina = new Obstaculo(55, 45, TipoObstaculo.OFICINA); //Coordenadas X(55,75), Y(45,65) e Z = 20m

        //Adicionando os obstáculos ao ambiente
        System.out.println("Adicionando obstáculos ao ambiente:\n");
        ambiente.adicionarObstaculo(lago);
        ambiente.adicionarObstaculo(fogo);
        ambiente.adicionarObstaculo(predio_chamas);
        ambiente.adicionarObstaculo(predio);
        ambiente.adicionarObstaculo(sujeira);
        ambiente.adicionarObstaculo(comida);
        ambiente.adicionarObstaculo(sacola);
        ambiente.adicionarObstaculo(oficina);
        System.out.println("Colocando um obstáculo inválido:\n");
        Obstaculo obstaculo_invalido = new Obstaculo(100, 0, TipoObstaculo.LAGO); //Coordenadas X(100,130), Y(0,30) e Z = 0m
        ambiente.adicionarObstaculo(obstaculo_invalido); // Esse obstáculo não será adicionado, pois está fora do ambiente

        // Criação dos robôs
        //SuperClasse e Subclasse: Terrestre e Limpador
        RoboTerrestre roboTerrestre = new RoboTerrestre("RoboTerrestre", 90, 0, 8, 10, ambiente);
        RoboLimpador roboLimpador = new RoboLimpador("RoboLimpador", 50, 45, 10, 10, ambiente, 10); 
        
        ////SuperClasse e Subclasse: Aereo e Bombeiro
        RoboAereo roboAereo = new RoboAereo("RoboAereo", 60, 35, 90, 10, ambiente);
        RoboBombeiro roboBombeiro = new RoboBombeiro("RoboBombeiro", 30, 30, 105, 10, ambiente, 3000, 10);
    
        //Adicionando os robôs ao ambiente
        System.out.println("-------------------------");
        System.out.println("Adicionando robôs ao ambiente:\n");
        ambiente.adicionarRobo(roboTerrestre);
        ambiente.adicionarRobo(roboLimpador);
        ambiente.adicionarRobo(roboAereo);
        ambiente.adicionarRobo(roboBombeiro);
        
        // Movimentação do Robo terrestre
        System.out.println("-------------------------");
        System.out.println("Teste do sensor e da movimentação do Rôbo Terrestre:\n");
        // o sensor que irei utilizar é o da subclasse SensorPosicaoSegura.
        roboTerrestre.mover(4, 5); // Movimentação normal
        roboTerrestre.mover(-5, 8); // Excede a velocidade máxima
        roboTerrestre.mover(-10, 10); // Movimentação excede o raio do sensor, logo não consegue saber se é seguro e não se move
        roboTerrestre.mover(55, 55); // Movimentação excede o tamanho do ambiente
        roboTerrestre.mover(1, 0); // Há fogo no local, logo o rôbo não pode mover para lá
        roboTerrestre.identificarObstaculo(); // Testando o sensor, detectará apenas 1 obstáculo, no caso o fogo
        
        ////////////////////////////////////////////
        //TALVEZ É BOM USAR O MONITORAR PARA OS TESTES DESSA ABA DE TESTE DE SENSOR E MOVIMENTAÇAO ROBO TERRESTRE
        ////////////////////////////////////////////
        
        // Movimentação do Robo Aéreo
        System.out.println("-------------------------");
        System.out.println("Teste de movimentação do Rôbo Aéreo:\n");
        roboAereo.mover(5, 5); // Movimentação normal utilizando método da SuperClasse, ou seja, altitude = 0
        roboAereo.subir(10); // Sobe normal
        roboAereo.subir(110); // Não pode sair da altitude máxima do ambiente
        roboAereo.subir(96); // Não pode ir além da Altitude_máxima pré-definida.
        roboAereo.descer(5); // Desce normal
        roboAereo.descer(15); // Não pode descer abaixo do solo
        roboAereo.descer(5); // Voltou a altitude = 0, logo pousou!
        roboAereo.identificarObstaculo(); // Detectará a oficina e o prédio, logo 2 obstáculos

        // Testes robo limpador + testes do sensor de lixo
        System.out.println("-------------------------");
        System.out.println("Teste do sensor de lixo e do rôbo limpador:\n");
        roboLimpador.definir_tipo_limpeza(7); // Seleciona um modo inválido
        roboLimpador.indentificar_lixo(); // Usa o sensor para ver se tem lixo perto
        roboLimpador.limpar(); // Limpará somente somente a sacola
        roboLimpador.definir_tipo_limpeza(1); // Seleciona o tipo de limpeza pesada, para limpar comida
        roboLimpador.limpar(); // Limpará comida no chão
        roboLimpador.definir_tipo_limpeza(2); // Seleciona o tipo de limpeza mais pesado, pode limpar tudo
        roboLimpador.limpar(); // Limpou a sujeira encardida
        roboLimpador.aprimorar(10); // Robo limpador está fora da oficina
        roboLimpador.mover(5, 0); // Movi o robo para a oficina
        roboLimpador.aprimorar(10); // Robo será aprimorado com sucesso
        roboLimpador.indentificar_lixo(); // Sensor não irá identificar nenhum lixo, pois já limpou tudo

        // Testes robo bombeiro + testes do sensor de fogo
        System.out.println("-------------------------");
        System.out.println("Teste do sensor de fogo e do rôbo bombeiro:\n");
        roboBombeiro.adicionar_agua(2500); // Adiciona água sem problemas.
        roboBombeiro.adicionar_agua(1000); // Excede a quantidade máxima, logo não abastecerá.
        roboBombeiro.mover(2, 10); // Movi o robo para perto do prédio em chamas e fora do lago
        roboBombeiro.adicionar_agua(5000); // Não está no lago, logo não pode adicionar
        roboBombeiro.indentificar_fogo(); // Irá identificar o prédio em fogo
        roboBombeiro.apagar_fogo(1500); // Não irá apagar o fogo, pois não está na altura dele
        roboBombeiro.subir(100); // Subirá até a altura do fogo.
        roboBombeiro.apagar_fogo(4000); // Aqui ele não consegue apagar o fogo porque falta água
        roboBombeiro.apagar_fogo(1500); // Conseguirá apagar o fogo.
        roboBombeiro.descer(100); // Voltou para o solo
        roboBombeiro.aprimorar(1000); // Não está na oficina, logo não aprimorará
        roboBombeiro.mover(23, 5); // Foi para a Oficina
        roboBombeiro.aprimorar(1000); // Será aprimorado sem problemas


        // Menu interativo
        System.out.println("-------------------------");
        System.out.println("Criando um ambiente novo:\n");
        Ambiente ambiente2 = new Ambiente(100, 100, "Ambiente Novo", 110);
        Obstaculo Fogo1 = new Obstaculo(50, 85, TipoObstaculo.FOGO); //Coordenadas X(50,55), Y(85,90) e Z = 5m
        Obstaculo Fogo2 = new Obstaculo(55, 55, TipoObstaculo.FOGO); //Coordenadas X(55,60), Y(55,60) e Z = 5m
        Obstaculo Fogo3 = new Obstaculo(80, 25, TipoObstaculo.FOGO); //Coordenadas X(80,85), Y(25,30) e Z = 5m
        Obstaculo Predio_em_chamas1 = new Obstaculo(0, 40, TipoObstaculo.PREDIOEMCHAMAS); //Coordenadas X(0,30), Y(40,70) e Z = 30m
        Obstaculo Predio1 = new Obstaculo(20, 0, TipoObstaculo.PREDIO); //Coordenadas X(20,50), Y(0,30) e Z = 30m
        Obstaculo Comida1 = new Obstaculo(15, 25, TipoObstaculo.COMIDANOCHAO); //Coordenadas (15,25)
        Obstaculo Comida2 = new Obstaculo(65, 65, TipoObstaculo.COMIDANOCHAO); //Coordenadas (65,65)
        Obstaculo Sujeira1 = new Obstaculo(90, 20, TipoObstaculo.SUJEIRAENCARDIDA); //Coordenadas (90,20)
        Obstaculo Sujeira2 = new Obstaculo(70, 85, TipoObstaculo.SUJEIRAENCARDIDA); //Coordenadas (70,85)
        Obstaculo Sacola1 = new Obstaculo(52, 68, TipoObstaculo.SACOLAPLASTICA); //Coordenadas (52,68)
        Obstaculo Sacola2 = new Obstaculo(55, 32, TipoObstaculo.SACOLAPLASTICA); //Coordenadas (55,32)
        Obstaculo Lago1 = new Obstaculo(0, 70, TipoObstaculo.LAGO); //Coordenadas X(0,30), Y(70,100) e Z = 0m
        Obstaculo Oficina1 = new Obstaculo(80, 80, TipoObstaculo.OFICINA); //Coordenadas X(80,100), Y(80,100) e Z = 20m
        //Adicionando os obstáculos ao ambiente
        System.out.println("Adicionando obstáculos ao ambiente:\n");
        ambiente2.adicionarObstaculo(Fogo1);
        ambiente2.adicionarObstaculo(Fogo2);
        ambiente2.adicionarObstaculo(Fogo3);
        ambiente2.adicionarObstaculo(Predio_em_chamas1);
        ambiente2.adicionarObstaculo(Predio1);
        ambiente2.adicionarObstaculo(Comida1);
        ambiente2.adicionarObstaculo(Comida2);
        ambiente2.adicionarObstaculo(Sujeira1);
        ambiente2.adicionarObstaculo(Sujeira2);
        ambiente2.adicionarObstaculo(Sacola1);
        ambiente2.adicionarObstaculo(Sacola2);
        ambiente2.adicionarObstaculo(Lago1);
        ambiente2.adicionarObstaculo(Oficina1);
        
        MenuInterativo(ambiente2); // Chama o menu interativo para o novo ambiente
        scanner.close(); // Fecha o scanner
    }
    

        
    public static void MenuInterativo(Ambiente ambiente) {
        System.out.println("-------------------------");
        System.out.println("Menu interativo:\n");
        System.out.println("Você pode usar o menu interativo para testar as funcionalidades do ambiente e dos robôs.");
        System.out.println("Você controla um robô, e pode usar os métodos de movimentação, sensor e aprimoramento.");
        System.out.println("Escolha o robô que você quer controlar, digitando o número correspondente:");
        System.out.println("1 - RoboLimpador");
        System.out.println("2 - RoboBombeiro");
        // Using the global scanner instance
        while (!scanner.hasNextInt()) {
            System.out.println("Você digitou algo inválido, tente novamente.");
            scanner.next(); // Limpa o buffer
        }
        int comando = scanner.nextInt();
        while ((comando != 1 && comando != 2)) {
            System.out.println("Você digitou algo inválido, tente novamente.");
            comando = scanner.nextInt();
        }
        if (comando == 1){
            int quantidade_lixo = 0;
            RoboLimpador Player = new RoboLimpador("Player", 0, 0, 20, 20, ambiente, 20);
            System.out.println("Você escolheu o RoboLimpador. Raio de limpeza: 20m");
            System.out.println("O seu objetivo é limpar o ambiente.");
            System.out.println("Mova, evitando os incêndios, e limpe o ambiente");
            System.out.println("Utilize o seus sensores para detectar lixo no ambiente e encontrar oficinas.");
            System.out.println("Lembre-se que seus sensores possuem um raio limitado então mova-se para explorar o ambiente.");
            ambiente.adicionarRobo(Player);
            while (comando != 6) {
                // Exibir quantidade de lixo no ambiente
                for (Obstaculo o : ambiente.getObstaculos()) {
                    if (o.getTipo().isLixo()) {
                        quantidade_lixo++;
                    }
                }
                System.out.println("Quantidade de lixo no ambiente: " + quantidade_lixo);
                // Menu de opções
                System.out.println("Digite o número correspondente ao movimento que você quer fazer:");
                System.out.println("1 - Mover");
                System.out.println("2 - Scannear por lixo");
                System.out.println("3 - Limpar");
                System.out.println("4 - Aprimorar");
                System.out.println("5 - Definir intensidade de limpeza:");
                System.out.println("6 - Sair");
                while (!scanner.hasNextInt()) {
                    System.out.println("Você digitou algo inválido, tente novamente.");
                    scanner.next(); // Limpa o buffer
                }
                comando = scanner.nextInt();
                while ((comando < 1 && comando > 6)) {
                    System.out.println("Você digitou algo inválido, tente novamente.");
                    System.out.println("Digite o número correspondente ao movimento que você quer fazer:");
                    System.out.println("1 - Mover");
                    System.out.println("2 - Scannear por lixo");
                    System.out.println("3 - Limpar");
                    System.out.println("4 - Aprimorar");
                    System.out.println("5 - Definir intensidade de limpeza:");
                    System.out.println("6 - Sair");
                }
                switch (comando) {
                    case 1:
                        MoverPlayer(Player);
                        break;
                    case 2:
                        Player.indentificar_lixo();
                        break;
                    case 3:
                        Player.limpar();
                        break;
                    case 4:
                        System.out.println("Digite o valor do raio que você quer adicionar: ");
                        int DeltaRaio = scanner.nextInt();
                        Player.aprimorar(DeltaRaio);
                        break;
                    case 5:
                        System.out.println("Digite o tipo de limpeza que você quer fazer:");
                        System.out.println("0 - Limpeza leve");
                        System.out.println("1 - Limpeza pesada");
                        System.out.println("2 - Limpeza muito pesada");
                        int tipo = scanner.nextInt();
                        Player.definir_tipo_limpeza(tipo);
                        break;
                    case 6:
                        System.out.println("Você saiu do menu interativo.");
                        break;
                    default:
                        break;
                }
                quantidade_lixo = 0; // Reseta a quantidade de lixo
            }

            
        } else if (comando == 2){
            System.out.println("Você escolheu o RoboBombeiro.");
            System.out.println("O seu objetivo é apagar o fogo no ambiente.");

        } 
 
    }
    private static void MoverPlayer(Robo Player) {
        System.out.println("Você escolheu mover.");
        System.out.println("Digite o valor de X e Y em metros que você quer mover:");
        System.out.println("DeltaX: ");
        int deltaX = scanner.nextInt();
        System.out.println("DeltaY: ");
        int deltaY = scanner.nextInt();
        Player.mover(deltaX, deltaY);
    }

}
