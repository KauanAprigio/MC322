package LAB03.Code;

import java.util.Iterator;

import LAB03.Code.Obstaculo.TipoObstaculo;

/* 
 * SubClasse de RoboTerrestre
 * 
 * Atributos:
 * - int tipo_limpeza
 * - int raio de limpeza
 * Métodos:
 * - definir_tipo_limpeza(int tipo)
 * - limpar()
 * - identificar_lixo()
 * - aprimorar (int aumentar_raio_limpeza)
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
                    System.out.println(getNome() + " está configurado para remover objetos do chão.\n");
                    break;
                case 1:
                    System.out.println(getNome() + " está configurado para limpar comida do chão.\n");
                    break;
                case 2:
                    System.out.println(getNome() + " está configurado para remover sujeiras encardidas.\n");
                    break;
            }
        } else {
            System.out.println("Tipo de limpeza inválido! Escolha entre 0 (leve), 1 (pesada) ou 2 (muito pesada).\n");
        }
    }

    public void limpar() {
        System.out.println("Tipo de limpeza atual: " + tipo_limpeza + "."); // Fala qual o tipo de limpeza
        Iterator<Obstaculo> iterator = getAmbiente().getObstaculos().iterator();
        //mesma lógica do laço for para achar os lixos e caso ainda tenha um obstaculo ele continua vendo se é lixo
        while (iterator.hasNext()) {
            Obstaculo o = iterator.next();
            if (o.getTipo().isLixo()) {
                // se o obstáculo for um lixo irá verificar se ele está dentro do raio de limpeza                
                int distancia = (int) Math.sqrt(Math.pow(o.getPosicaoX1() - getX(), 2) + Math.pow(o.getPosicaoY1() - getY(), 2));
                if (distancia < raioDeLimpeza) { 
                    //caso esteja dentro do raio de limpeza vê se o modo atual consegue limpar o lixo
                    if (o.getTipo() == TipoObstaculo.SUJEIRAENCARDIDA && tipo_limpeza == 2) {
                        System.out.println(getNome() + " limpou " + o.getTipo().getNome() + ".\n");
                        iterator.remove();
                    } else if (o.getTipo() == TipoObstaculo.COMIDANOCHAO && tipo_limpeza == 1) {
                        System.out.println(getNome() + " limpou " + o.getTipo().getNome() + ".\n");
                        iterator.remove();
                    } else if (o.getTipo() == TipoObstaculo.SACOLAPLASTICA && tipo_limpeza == 0) {
                        System.out.println(getNome() + " limpou " + o.getTipo().getNome() + ".\n");
                        iterator.remove();
                     
                    // se não pode limpar
                    } else {
                        System.out.println(getNome() + " não pode limpar " + o.getTipo().getNome() + ", tente mudar o tipo de limpeza\n");
                    }
                }
            }
        }
    }
    public void indentificar_lixo() {
        sensorDeLixo.monitorar(getX(), getY(), 0, getAmbiente());
    }
    public void aprimorar(int aumento_raio_limpeza){
        //Procura a oficina e vê se o robô está dentro dos limites dela
        for (Obstaculo o : getAmbiente().getObstaculos()) {
            if (o.getTipo() == TipoObstaculo.OFICINA) {
                if (getX() <= o.getPosicaoX2() && getX() >= o.getPosicaoX1() && getY() <= o.getPosicaoY2() && getY() >= o.getPosicaoY1()) {
                    // se o robo está na oficina, será aprimorado.
                    System.out.println(getNome() + " está dentro da oficina e pode ser aprimorado.");
                    raioDeLimpeza += aumento_raio_limpeza;
                    System.out.println(getNome() + " teve seu raio de limpeza aumentado para " + raioDeLimpeza + ".\n");
                    return;
                } 
            }
        }
        // caso o robo não esteja na oficina
        System.out.println(getNome() + " não está dentro da oficina e não pode ser aprimorado!\n");

    }

    //Getters e Setters
    public int getTipoLimpeza() { return tipo_limpeza; }
    public int getRaioDeLimpeza() { return raioDeLimpeza; }
}

