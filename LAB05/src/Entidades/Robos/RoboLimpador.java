package LAB05.src.Entidades.Robos;
import java.util.ArrayList;
import LAB05.src.Entidades.Interfaces.Sensoreavel;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Exceptions.*;
import LAB05.src.Entidades.SensorDeVarredura;
import LAB05.src.Entidades.Obstaculos.*;

public class RoboLimpador extends AgenteInteligente implements Sensoreavel {
    private int raio_limpeza;
    private boolean aprimorado = false;
    private ArrayList<Obstaculo> lixos;
    private SensorDeVarredura sensor;

    public RoboLimpador(String id, int x_1, int y_1, int z_1, char representacao, Ambiente ambiente, int raio_limpeza) {
        super("RoboLimpador_"+id, x_1, y_1, z_1, 'L', ambiente);
        this.raio_limpeza = raio_limpeza;
        sensor = new SensorDeVarredura();
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
    public void executarSensores(){
        sensor.varreruda(this);
        System.out.println("O sensor foi ativado com sucesso, o robô " + getId() + " tem a localidade de todos os lixos");
    }

    
    //Getters e Setters
    public ArrayList<Obstaculo> getLixos() { return lixos; }
    public int getRaioLimpeza() { return raio_limpeza; }
    public boolean estahAprimorado() { return aprimorado; }

    public void setRaioLimpeza(int raio) { this.raio_limpeza = raio; }
    public void setAprimorar(boolean aprimorar) { this.aprimorado = aprimorar; }
}
