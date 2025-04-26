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
        Ambiente ambiente = new Ambiente(100, 100, "Ambiente", 100);

        //Criação dos obstáculos
        Obstaculo lago = new Obstaculo(2, 2, TipoObstaculo.LAGO); //Coordenadas X(2,32), Y(2,32), no caso eu digo o começo e o fim do X, depois do Y, já a altura sempre começa do 0, logo dou o valor dela, se nao tiver é porque o objeto nao tem altura
        Obstaculo fogo = new Obstaculo(95, 0, TipoObstaculo.FOGO); //Coordenadas X(95,100), Y(0,5) e Z = 5m
        Obstaculo predio_chamas = new Obstaculo(0, 40, TipoObstaculo.PREDIOEMCHAMAS); //Coordenadas X(0,30), Y(40,70) e Z = 30m
        Obstaculo predio = new Obstaculo(40, 0, TipoObstaculo.PREDIO); //Coordenadas X(40,70), Y(0,30) e Z = 30m
        Obstaculo sujeira = new Obstaculo(50, 40, TipoObstaculo.SUJEIRAENCARDIDA); //Coordenadas (50,40)
        Obstaculo comida = new Obstaculo(60, 60, TipoObstaculo.COMIDANOCHAO); //Coordenadas (60,60)
        Obstaculo sacola = new Obstaculo(70, 80, TipoObstaculo.SACOLAPLASTICA); //Coordenadas (70,80)
        Obstaculo oficina = new Obstaculo(0, 80, TipoObstaculo.OFICINA); //Coordenadas X(0,20), Y(80,100) e Z = 20m

        //Adicionando os obstáculos ao ambiente
        System.out.println("Adicionando obstáculos ao ambiente:");
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
        RoboTerrestre roboTerrestre = new RoboTerrestre("RoboTerrestre", 0, 0, 10, 10, ambiente);
        RoboLimpador roboLimpador = new RoboLimpador("RoboLimpador", 0, 0, 10, 10, ambiente, 10); 
        
        ////SuperClasse e Subclasse: Aereo e Bombeiro
        RoboAereo roboAereo = new RoboAereo("RoboAereo", 0, 0, 100, 10, ambiente);
        RoboBombeiro roboBombeiro = new RoboBombeiro("RoboBombeiro", 0, 0, 90, 15, ambiente, 3000);
    
        //Adicionando os robôs ao ambiente
        System.out.println("Adicionando robôs ao ambiente:");
        ambiente.adicionarRobo(roboTerrestre);
        ambiente.adicionarRobo(roboLimpador);
        ambiente.adicionarRobo(roboAereo);
        ambiente.adicionarRobo(roboBombeiro);
    
    }
}
