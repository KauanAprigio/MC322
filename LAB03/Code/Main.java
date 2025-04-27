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
        ////////////////////////////////////////////
        // ALTEREI O SENSOR DE POSICAO PARA CONSIDERAR A OFICINA LOCAL QUE PODE MOVER + COLOQUEI UM ATRIBUTO A MAIS NO TIPOOBSTACULO, CHAMADO APRIMORA, PARA FACILITAR PARA O ROBO LIMPADOR
        ////////////////////////////////////////////

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
 
    }
}
