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
        RoboBombeiro roboBombeiro = new RoboBombeiro("RoboBombeiro", 0, 0, 100, 3000);
        RoboLetreiro roboLetreiro = new RoboLetreiro("RoboLetreiro", 100, 101, 10, 100);

        // Adicionando robôs ao ambiente
        System.out.println("Adicionando robôs ao ambiente:");
        ambiente.adicionarRobo(roboTerrestre);
        ambiente.adicionarRobo(roboAereo);
        ambiente.adicionarRobo(roboLimpador);
        ambiente.adicionarRobo(roboGarcom);
        ambiente.adicionarRobo(roboBombeiro);

        System.out.println("-------------------------");
        System.out.println("Teste de movimentação e funcionalidades dos robôs:");
        // Movimentação terrestre
        roboTerrestre.mover(5, 5);
        // Movimentação aérea
        roboAereo.mover(5, 5);
        // Limites de velocidade
        roboTerrestre.mover(20, 20); // Excede a velocidade máxima
        // Altura máxima
        roboAereo.mover(5, 5); // Excede a altura máxima

        System.out.println("-------------------------");
        System.out.println("Teste de identificação de obstáculos:");
        // Identificação de obstáculos
        roboTerrestre.identificarObstaculo();
        roboAereo.identificarObstaculo();
        roboAereo.setDirecao("Leste");
        roboAereo.identificarObstaculo();
        roboTerrestre.setDirecao("Sul");
        roboTerrestre.identificarObstaculo();


        System.out.println("-------------------------");
        System.out.println("Teste de altitude robos aéreos:");
        // Testes de altitude
        roboAereo.subir(50); // Sobe 50 metros
        roboAereo.subir(60); // Não pode subir além da altura máxima
        roboAereo.descer(20); // Desce 20 metros
        roboAereo.descer(100); // Não pode descer abaixo do nível do solo
        roboAereo.subir(70); // Sobe 70 metros
       
        System.out.println("-------------------------");
        System.out.println("Teste do limpador:");
        // Testes robo limpador
        roboLimpador.ligar();
        roboLimpador.mover(5, 5);
        roboLimpador.definir_tipo_limpeza(1);
        roboLimpador.definir_tipo_limpeza(2);
        roboLimpador.desligar();
        roboLimpador.definir_tipo_limpeza(0); // Não pode definir tipo de limpeza enquanto desligado
        roboLimpador.mover(5, 5); // Não pode mover enquanto desligado

        System.out.println("-------------------------");
        System.out.println("Teste do garcom:");
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

        System.out.println("-------------------------");
        System.out.println("Teste do bombeiro:");
        // Testes robo bombeiro
        roboBombeiro.mover(20, 20);
        roboBombeiro.adicionar_agua(1000); // Adiciona 1000 litros de água
        roboBombeiro.subir(50);
        roboBombeiro.apagar_fogo(2000);
        roboBombeiro.adicionar_agua(2000);
        roboBombeiro.apagar_fogo(2000);
        roboBombeiro.adicionar_agua(2000); // Adiciona 500 litros de água
        roboBombeiro.resgate(500);
        roboBombeiro.liberar_tripulantes();
        roboBombeiro.aprimora(1000);
        roboBombeiro.descer(50);
        roboBombeiro.liberar_tripulantes();
        roboBombeiro.aprimora(1000);

        System.out.println("-------------------------");
        System.out.println("Teste do letreiro:");
        // Testes robo letreiro
        roboLetreiro.mover(-5, 5);
        roboLetreiro.escrever_visor("Diego Esteve por aqui", 10);
        System.out.println("Texto no visor: " + roboLetreiro.getTexto());
        roboLetreiro.escrever_visor("Kauan esteve por aqui", 100);
        roboLetreiro.limpar_visor();
        roboLetreiro.aumentarVisor(100);
        roboLetreiro.descer(10);
        roboLetreiro.aumentarVisor(100);

        System.out.println("-------------------------");
        System.out.println("Teste de exibição do Ambiente:");
        // Testes do ambiente
        System.out.println("Ambiente: " + ambiente.getNome());
        System.out.println("Largura: " + ambiente.getLargura());
        System.out.println("Altura: " + ambiente.getAltura());
        System.out.println("Altura máxima: " + ambiente.getAltitudeMaxima());
        System.out.println("Robôs no ambiente:");
        // Teste de limites
        for (Robo robo : ambiente.getRobos()) {
            if (robo instanceof RoboTerrestre){
                robo.exibirPosicao();
                if (ambiente.dentroDosLimites(robo.getPosicaoX(), robo.getPosicaoY(), 0)) {
                    System.out.println(robo.getNome() + " está dentro dos limites do ambiente.");
                } else {
                    System.out.println(robo.getNome() + " está fora dos limites do ambiente.");
                }
            }
            if (robo instanceof RoboAereo){
                robo.exibirPosicao();
                if (ambiente.dentroDosLimites(robo.getPosicaoX(), robo.getPosicaoY(), ((RoboAereo) robo).getAltitude())) {
                    System.out.println(robo.getNome() + " está dentro dos limites do ambiente.");
                } else {
                    System.out.println(robo.getNome() + " está fora dos limites do ambiente.");
                }
            }
    }




    }
    
}
