package LAB02;

public class Main {
    public static void main(String[] args) {
        RoboTerrestre roboTerrestre = new RoboTerrestre("RoboTerrestre", 0, 0, 10);
        RoboAereo roboAereo = new RoboAereo("RoboAereo", 0, 0, 100);
        RoboLimpador roboLimpador = new RoboLimpador("RoboLimpador", 0, 0, 10, true, 5);
        RoboGarcom roboGarcom = new RoboGarcom("RoboGarcom", 0, 0, 10, 5000);
        // Movimentação terrestre
        roboTerrestre.mover(5, 5);
        // Movimentação aérea
        roboAereo.mover(5, 5);
        // Limites de velocidade
        roboTerrestre.mover(20, 20); // Excede a velocidade máxima
        // Altura máxima
        roboAereo.mover(5, 5); // Excede a altura máxima

        // Testes robo limpador
        roboLimpador.ligar();
        roboLimpador.mover(5, 5);
        roboLimpador.definir_tipo_limpeza(1);
        roboLimpador.definir_tipo_limpeza(2);
        roboLimpador.desligar();
        roboLimpador.definir_tipo_limpeza(0); // Não pode definir tipo de limpeza enquanto desligado
        roboLimpador.mover(5, 5); // Não pode mover enquanto desligado

        // Testes robo garcom
        roboGarcom.adicionar_estoque(5001); // Excede o limite de estoque
        roboGarcom.entregar_comida(2000); // Não possui estoque suficiente
        roboGarcom.adicionar_estoque(1000); // Adiciona 1000 ao estoque
        roboGarcom.entregar_comida(2000); // Entrega 1000 de comida zerando o estoque
        roboGarcom.adicionar_estoque(2000); // Adiciona 2000 ao estoque
        roboGarcom.adicionar_estoque(500); // Adiciona 500 ao estoque
        roboGarcom.entregar_comida(500); // Entrega 500 de comida
        roboGarcom.adicionar_estoque(3000); // estoque cheio
        roboGarcom.mudar_carga(200); // Não pode reduzir carga
        roboGarcom.mudar_carga(6000); // Aumenta a carga para 6000
        roboGarcom.adicionar_estoque(1000); // Adiciona 1000 ao estoque


    }
    
}
