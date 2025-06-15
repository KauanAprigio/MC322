package LAB05.src.Entidades;


import java.util.ArrayList;

import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Interfaces.Comunicavel;
import LAB05.src.Entidades.Interfaces.Entidade;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Entidades.Robos.Robo;

public class ComunicadorCentral extends CentralComunicacao implements Entidade, Comunicavel{

    private final int larguraX = 5;
    private final int larguraY = 5;
    private final int larguraZ = 100;

    private ArrayList<Obstaculo> fogos;
    private final char representacao = 'c';
    private int pos_x;
    private int pos_y;
    private final int pos_z = 0;
    private Ambiente ambiente; // Ambiente onde o obstáculo está localizado

    public ComunicadorCentral(int pos_x, int pos_y, Ambiente a) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.ambiente = a;
        fogos = new ArrayList<Obstaculo>();
    }

    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) {
        
    }

    @Override
    public void receberMensagem(String mensagem, Comunicavel remetente) {
        
    }

    public void listarFogos(Ambiente ambiente){ 
        for (Entidade e : ambiente.getEntidades()){
            if (e.getTipo() == TipoEntidade.FOGO){
                Obstaculo fogo = (Obstaculo) e;
                fogos.add(fogo);
            }
        }
        System.out.println("O Comunicador sabe de todos os fogos que estão pelo ambiente");
    }

    public Obstaculo LocalizarFogoMaisProx (Robo robozin) {
        double menorDistancia = Double.MAX_VALUE; // Inicializa com o maior valor possível
        Obstaculo maisProximo = null; // Inicializa como null para verificar se encontrou algum lixo
        for (Obstaculo fogo : fogos) {
            // Calcula a distância entre a entidade e o fogo
            int DistanciaX = Math.max(robozin.getX_1(), fogo.getX_1()) - Math.min (robozin.getX_1(), fogo.getX_1());
            int DistanciaY = Math.max(robozin.getY_1(), fogo.getY_1()) - Math.min (robozin.getY_1(), fogo.getY_1());
            double distancia = Math.sqrt(Math.pow(DistanciaX, 2) + Math.pow(DistanciaY, 2));
            
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                maisProximo = fogo; // Atualiza o fogo mais próximo
            }
        }
        return maisProximo;
    }

    public ArrayList<Obstaculo> getFogos(){ return fogos; }

    @Override
    public int getX_1() { 
        return pos_x;
    }
    @Override
    public int getY_1() {
        return pos_y;
    }
    @Override
    public int getZ_1() {
        return pos_z;
    }
    @Override
    public Ambiente getAmbiente() {
        return ambiente;
    }
    @Override
    public String getId() {
        return "Comunicador Central";
    }
    @Override
    public String getDescricao() {
        String descricao = "nn sei ainda";
        return descricao;
    }
    @Override
    public int getLarguraX() {
        return larguraX;
    }
    @Override
    public int getLarguraY() {
        return larguraY;
    }
    @Override
    public int getLarguraZ() {
        return larguraZ;
    }
    @Override
    public char getRepresentacao() {
        return representacao;
    }
    @Override
    public TipoEntidade getTipo() {
        return TipoEntidade.COMUNICADOR;
    }
    @Override
    public int getX_2() {
        return pos_x + getLarguraX();
    }
    @Override
    public int getY_2() {
        return pos_y + getLarguraY();
    }
    @Override
    public int getZ_2() {
        return pos_z + getLarguraZ();
    }
}
