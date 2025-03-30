package LAB02;

public class Main {
    public static void main(String[] args) {
        // Criação do ambiente
        Ambiente ambiente = new Ambiente(100, 100, "Ambiente 1", 20);
        // Criação dos robôs
        RoboTerrestre roboTerrestre = new RoboTerrestre("RoboTerrestre", 0, 0, 10);
        RoboAereo roboAereo = new RoboAereo("RoboAereo", 0, 0, 100);
        RoboLimpador roboLimpador = new RoboLimpador("RoboLimpador", 0, 0, 10, true, 5);
        RoboGarcom roboGarcom = new RoboGarcom("RoboGarcom", 0, 0, 10, 5000);
        // Adicionando robôs ao ambiente
        ambiente.adicionarRobo(roboTerrestre);
        ambiente.adicionarRobo(roboAereo);
        ambiente.adicionarRobo(roboLimpador);
        ambiente.adicionarRobo(roboGarcom);

        // Movimentação terrestre
        roboTerrestre.mover(5, 5);
        // Movimentação aérea
        roboAereo.mover(5, 5);
        // Limites de velocidade
        roboTerrestre.mover(20, 20); // Excede a velocidade máxima
        // Altura máxima
        roboAereo.mover(5, 5); // Excede a altura máxima

        // Testes de altitude
        roboAereo.subir(50); // Sobe 50 metros
        roboAereo.subir(60); // Não pode subir além da altura máxima
        roboAereo.descer(20); // Desce 20 metros
        roboAereo.descer(100); // Não pode descer abaixo do nível do solo
        roboAereo.subir(70); // Sobe 70 metros
       

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

        // Testes do ambiente
        System.out.println("Ambiente: " + ambiente.getNome());
        System.out.println("Largura: " + ambiente.getLargura());
        System.out.println("Altura: " + ambiente.getAltura());
        System.out.println("Altura máxima: " + ambiente.getAltitudeMaxima());
        System.out.println("Robôs no ambiente:");
        // Teste de limites
        for (Robo robo : ambiente.getRobos()) {
            if (robo instanceof RoboTerrestre){
                System.out.println(robo.getNome() + " está na posição (" + robo.getPosicaoX() + ", " + robo.getPosicaoY() + ")");
                if (ambiente.dentroDosLimites(robo.getPosicaoX(), robo.getPosicaoY(), 0)) {
                    System.out.println(robo.getNome() + " está dentro dos limites do ambiente.");
                } else {
                    System.out.println(robo.getNome() + " está fora dos limites do ambiente.");
                }
            }
            if (robo instanceof RoboAereo){
                System.out.println(robo.getNome() + " está na posição (" + robo.getPosicaoX() + ", " + robo.getPosicaoY() + ", " + ((RoboAereo) robo).getAltitude() + ")");
                if (ambiente.dentroDosLimites(robo.getPosicaoX(), robo.getPosicaoY(), ((RoboAereo) robo).getAltitude())) {
                    System.out.println(robo.getNome() + " está dentro dos limites do ambiente.");
                } else {
                    System.out.println(robo.getNome() + " está fora dos limites do ambiente.");
                }
            }
    }




    }
    
}
