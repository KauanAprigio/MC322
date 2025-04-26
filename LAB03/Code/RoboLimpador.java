package LAB03.Code;

import LAB03.Code.Obstaculo.TipoObstaculo;

/* 
 * Classe criada RoboLimpador
 * 
 * Atributos:
 * - tipo_limpeza
 * - raio de limpeza
 * Métodos:
 * - definir_tipo_limpeza(int tipo)
 * - limpar()
 * 
 */
class RoboLimpador extends RoboTerrestre {
    // Atributos adicionais
    private int tipo_limpeza = 0; // 0 = limpeza leve, 1 = limpeza pesada, 2 = limpeza muito pesada
    private SensorDeLixo sensorDeLixo;// sensor de lixo com raio 10
    private int raioDeLimpeza; // raio de limpeza do robô

    // Construtor
    public RoboLimpador(String nome, int posicaoX, int posicaoY, int velocidadeMaxima,
                            double raio, Ambiente ambiente, int raioDeLimpeza) {
        super(nome, posicaoX, posicaoY, velocidadeMaxima, raio, ambiente);
        this.sensorDeLixo = new SensorDeLixo(raio); // sensor de lixo com raio 10
        this.raioDeLimpeza = raioDeLimpeza;
    }

    // Metodos

    void definir_tipo_limpeza(int tipo){
        // Verifica se o tipo de limpeza é válido
        // 0 = limpeza leve, 1 = limpeza pesada, 2 = limpeza muito pesada
        if (tipo >= 0 && tipo <= 2) {
            tipo_limpeza = tipo;
            switch (tipo_limpeza) {
                case 0:
                    System.out.println(getNome() + " está configurado para limpeza leve.\n");
                    break;
                case 1:
                    System.out.println(getNome() + " está configurado para limpeza pesada.\n");
                    break;
                case 2:
                    System.out.println(getNome() + " está configurado para limpeza muito pesada.\n");
                    break;
            }
        } else {
            System.out.println("Tipo de limpeza inválido. Escolha entre 0 (leve), 1 (pesada) ou 2 (muito pesada).\n");
        }
    }

    public void limpar() {
        System.out.println("Tipo de limpeza atual: " + tipo_limpeza ); // Fala qual o tipo de limpeza
        for (Obstaculo o : getAmbiente().getObstaculos()) {
            if (o.getTipo().isLixo()){
                int distancia = (int) Math.sqrt(Math.pow(o.getPosicaoX1() - getX(), 2) + Math.pow(o.getPosicaoY1() - getY(), 2));
                if (distancia < raioDeLimpeza){ // verifica se o robô esta perto do lixo
                    if (o.getTipo() == TipoObstaculo.SUJEIRAENCARDIDA && tipo_limpeza >= 2) {
                        System.out.println(getNome() + " limpou " + o.getTipo().getNome() );
                        getAmbiente().removerObstaculo(o);
                    } else if (o.getTipo() == TipoObstaculo.COMIDANOCHAO && tipo_limpeza >= 1) {
                        System.out.println(getNome() + " limpou " + o.getTipo().getNome() );
                        getAmbiente().removerObstaculo(o);
                    } else if (o.getTipo() == TipoObstaculo.SACOLAPLASTICA && tipo_limpeza >= 0) {
                        System.out.println(getNome() + " limpou " + o.getTipo().getNome() );
                        getAmbiente().removerObstaculo(o);
                    } else {
                        System.out.println(getNome() + " não pode limpar " + o.getTipo().getNome() + ", tente aumentar a intensidade da limpeza!");
                    }
                }
            }
        }
    }
    public void indentificar_lixo() {
        sensorDeLixo.monitorar(getX(), getY(), 0, getAmbiente());
    }
    public void aprimorar(int aumento_raio_limpeza){
        //verifica se o robô esta dentro de uma oficina
        for (Obstaculo o : getAmbiente().getObstaculos()) {
            if (o.getTipo() == TipoObstaculo.OFICINA) {
                if (getX() <= o.getPosicaoX2() && getX() >= o.getPosicaoX1() && getY() <= o.getPosicaoY2() && getY() >= o.getPosicaoY1()) {
                    System.out.println(getNome() + " está dentro da oficina e pode ser aprimorado.");
                    raioDeLimpeza += aumento_raio_limpeza;
                    System.out.println(getNome() + " teve seu raio de limpeza aumentado para " + raioDeLimpeza + " ! \n");
                    return;
                } 
            }
        }
        System.out.println(getNome() + " não está dentro da oficina e não pode ser aprimorado!\n");

    }

    //Getters e Setters
    public int getTipoLimpeza() { return tipo_limpeza; }
    public int getRaioDeLimpeza() { return raioDeLimpeza; }
}

