package LAB04.Code;

import java.util.Iterator;
import LAB04.Code.Obstaculo.TipoObstaculo;
import LAB04.Code.Exceptions.ErrorLimpezaException;
import LAB04.Code.Exceptions.RoboDesligadoException;
import LAB04.Code.Exceptions.ErrorAprimoramentoException;

/* 
 * SubClasse de RoboTerrestre
 * 
 * Atributos:
 * - int tipo_limpeza
 * - int raio de limpeza
 * - SensorDeLixo sensorDeLixo
 * Métodos:
 * - definir_tipo_limpeza(int tipo)
 * - limpar()
 * - identificar_lixo()
 * - aprimorar (int aumentar_raio_limpeza)
 */

class RoboLimpador extends Robo implements Sensoreavel, SujeiraZero, Aprimoravel {
    // Atributos adicionais
    private int tipo_limpeza = 0; // 0 = limpeza leve, 1 = limpeza pesada, 2 = limpeza muito pesada
    private SensorDeLixo sensorDeLixo;// sensor de lixo com raio 10
    private int raioDeLimpeza; // raio de limpeza do robô
    Ambiente ambiente; // ambiente onde o robô está operando

    // Construtor
    public RoboLimpador(String id, EstadoRobo estado, TipoEntidade tipo, int pos_x,
                        int pos_y, int pos_z,Ambiente ambiente, double raio, int raioDeLimpeza) {
        super("Limpador_"+id, estado, pos_x, pos_y, pos_z, ambiente);
        this.sensorDeLixo = new SensorDeLixo(raio); // sensor de lixo com raio 10
        this.raioDeLimpeza = raioDeLimpeza;
    }

    // Metodos 
    @Override
    public void aprimorar(int upgrade) throws ErrorAprimoramentoException {
        // Verifica se o upgrade é válido
        if (upgrade <= 0) {
            throw new ErrorAprimoramentoException("Upgrade inválido! O valor deve ser maior que zero.");
        }
        //Procura a oficina e vê se o robô está dentro dos limites dela
        for (Entidade e : getAmbiente().getEntidades()) {
            if (e.getTipo() == TipoEntidade.OBSTACULO){
                Obstaculo o = (Obstaculo) e;
                if (o.getTipoObstaculo() == TipoObstaculo.OFICINA) {
                    if (getX() <= o.getPosicaoX2() && getX() >= o.getX() && getY() <= o.getPosicaoY2() && getY() >= o.getY()) {
                        // se o robo está na oficina, será aprimorado.
                        System.out.println(getId() + " está dentro da oficina e pode ser aprimorado.");
                        raioDeLimpeza += upgrade;
                        System.out.println(getId() + " teve seu raio de limpeza aumentado para " + raioDeLimpeza + ".\n");
                        return;
                    } 
                }
            }
        }
        // caso o robo não esteja na oficina
        String msg = getId() + " não está dentro da oficina e não pode ser aprimorado!";
        throw new ErrorAprimoramentoException(msg);
    }

    //Métodos da interface SujeiraZero
    @Override
    public void definir_tipo_limpeza(int tipo) throws ErrorLimpezaException {
        // Verifica se o tipo de limpeza é válido
        // 0 = limpeza leve, 1 = limpeza pesada, 2 = limpeza muito pesada
        if (tipo >= 0 && tipo <= 2) {
            tipo_limpeza = tipo;
            switch (tipo_limpeza) {
                case 0:
                    System.out.println(getId() + " está configurado para remover objetos do chão.\n");
                    break;
                case 1:
                    System.out.println(getId() + " está configurado para limpar comida do chão.\n");
                    break;
                case 2:
                    System.out.println(getId() + " está configurado para remover sujeiras encardidas.\n");
                    break;
            }
        } else {
            String msg = "Tipo de limpeza inválido! Escolha entre 0 (leve), 1 (pesada) ou 2 (muito pesada).";
            throw new ErrorLimpezaException(msg);
        }
    }

    @Override
    public void limpar() throws ErrorLimpezaException {
        System.out.println("Tipo de limpeza atual: " + tipo_limpeza + "."); // Fala qual o tipo de limpeza
        Iterator<Entidade> iterator = getAmbiente().getEntidades().iterator();
        //mesma lógica do laço for para achar os lixos e caso ainda tenha um obstaculo ele continua vendo se é lixo
        while (iterator.hasNext()) {
            Entidade e = iterator.next();
            if (e.getTipo() == TipoEntidade.OBSTACULO){
                Obstaculo o = (Obstaculo) e;
                if (o.getTipoObstaculo().isLixo()) {
                    // se o obstáculo for um lixo irá verificar se ele está dentro do raio de limpeza                
                    int distancia = (int) Math.sqrt(Math.pow(o.getX() - getX(), 2) + Math.pow(o.getY() - getY(), 2));
                    if (distancia < raioDeLimpeza) { 
                        //caso esteja dentro do raio de limpeza vê se o modo atual consegue limpar o lixo
                        if (o.getTipoObstaculo() == TipoObstaculo.SUJEIRAENCARDIDA && tipo_limpeza == 2) {
                            System.out.println(getId() + " limpou " + o.getTipoObstaculo().getNome() + ".\n");
                            iterator.remove();
                        } else if (o.getTipoObstaculo() == TipoObstaculo.COMIDANOCHAO && tipo_limpeza == 1) {
                            System.out.println(getId() + " limpou " + o.getTipoObstaculo().getNome() + ".\n");
                            iterator.remove();
                        } else if (o.getTipoObstaculo() == TipoObstaculo.SACOLAPLASTICA && tipo_limpeza == 0) {
                            System.out.println(getId() + " limpou " + o.getTipoObstaculo().getNome() + ".\n");
                            iterator.remove();
                            
                            // se não pode limpar
                        } else {
                            String msg = getId() + " não pode limpar " + o.getTipoObstaculo().getNome() + ", tente mudar o tipo de limpeza";
                            throw new ErrorLimpezaException(msg);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getDescricao() {
        String descricao = "Robô limpador: Capaz de limpar diversos tipos sujeira (contanto que o tipo de limpeza ativo seja apropriado!)," + 
         " aprimore seu alcance limpeza na oficina e ande pelo ambiente, evitando fogos e limpado tudo de lixo que houver por sua frente! Você está salvando o planeta!";
        return descricao;
    }

    //Métodos do sensoreavel
    @Override
    public void acionarSensores() throws RoboDesligadoException{ // basicamente irá utilizar o sensor de fogo para ver se tem fogo próximo
        if (getEstado() == EstadoRobo.OFF) {
            throw new RoboDesligadoException("O robô está desligado e não pode acionar os sensores.");
        }
        sensorDeLixo.monitorar(getX(), getY(), getZ(), getAmbiente());
    }

    //Getters e Setters
    public int getTipoLimpeza() { return tipo_limpeza; }
    public int getRaioDeLimpeza() { return raioDeLimpeza; }
    public Ambiente getAmbiente() { return ambiente; }
}

