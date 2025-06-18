package LAB05.src.Entidades.Robos;
import java.util.ArrayList;
import java.util.Iterator;

import LAB05.src.Entidades.Interfaces.Sensoreavel;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Exceptions.*;
import LAB05.src.Entidades.CentralComunicacao;
import LAB05.src.Entidades.SensorDeVarredura;
import LAB05.src.Entidades.Obstaculos.*;

public class RoboLimpador extends AgenteInteligente implements Sensoreavel {
    private int raio_limpeza;
    private boolean aprimorado = false;
    private ArrayList<Obstaculo> lixos;
    private SensorDeVarredura sensor;
    private CentralComunicacao comunicador;

    public RoboLimpador(String id, int x_1, int y_1, int z_1, char representacao, Ambiente ambiente, int raio_limpeza) {
        super("RoboLimpador_"+id, x_1, y_1, z_1, 'L', ambiente);
        this.raio_limpeza = raio_limpeza;
        sensor = new SensorDeVarredura();
        comunicador = new CentralComunicacao();
    }

    public void limpar() throws ErrorLimpezaException {
        getAmbiente().getLogger().inicializarAcao("Limpar lixo", getId());

        Iterator<Obstaculo> iterator = getLixos().iterator();

        getAmbiente().getLogger().logAcao("Confirmando a limpeza...");
        while (iterator.hasNext()) {
            Obstaculo lixo = iterator.next();              
            int distancia = (int) Math.sqrt(Math.pow(lixo.getX_1() - getX_1(), 2) + Math.pow(lixo.getY_1() - getY_1(), 2));
            if (distancia < getRaioLimpeza()) { 
                try {
                    getAmbiente().removerEntidade(lixo,false); // remove do ambiente
                    iterator.remove(); // remove do arraylist<Obstaculo> lixos usando o iterator
                    getComunicador().registrarMensagem(getId(), "Limpeza de " + lixo.getId() + " foi concluída com sucesso.");
                    getAmbiente().getLogger().logAcao(getId() + " limpou " + lixo.getTipoObstaculo().getNome() + ".");
                    break; // aqui ele irá limpar somente um lixo
                } catch (Exception e){
                    getAmbiente().getLogger().logErr(e);
                }
            }
        }
        getAmbiente().getLogger().finalizarAcao("Limpeza do lixo finalizada com sucesso.\n");

    }


    @Override
    public void executarMissao(Ambiente a) throws SemMissaoException, MissaoInvalidaException {
        if (temMissao()) {
            missao.executar(this, a);
        } else {
            throw new SemMissaoException("Nenhuma missão atribuída ao RoboLimpador.");
        }
    }

    @Override
    public String getDescricao() {
        String descricao = "Robô limpador: Capaz de limpar diversos tipos sujeira (contanto que o tipo de limpeza ativo seja apropriado!)," + 
         " aprimore seu alcance limpeza na oficina e ande pelo ambiente, evitando fogos e limpado tudo de lixo que houver por sua frente! Você está salvando o planeta!";
        return descricao;
    }

    @Override
    public void executarSensores() throws RoboDesligadoException{
        if (this.getEstado() == EstadoRobo.OFF){
            throw new RoboDesligadoException("O robô " + getId() + " não pode acionar os sensores, pois está desligado!\n");
        }
        getAmbiente().getLogger().inicializarAcao("executarSensor", getId());
        lixos = new ArrayList<Obstaculo>();
        sensor.varreruda(this);
        getAmbiente().getLogger().finalizarAcao("A execução do sensor foi finalizada com sucesso.\n");
    }

    
    //Getters e Setters
    public ArrayList<Obstaculo> getLixos() { return lixos; }
    public int getRaioLimpeza() { return raio_limpeza; }
    public boolean estahAprimorado() { return aprimorado; }
    public CentralComunicacao getComunicador() { return comunicador; }

    public void setRaioLimpeza(int raio) { this.raio_limpeza = raio; }
    public void aprimorar() { this.aprimorado = true; }
}
