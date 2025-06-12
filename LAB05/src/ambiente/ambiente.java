package LAB05.src.Ambiente;

import java.util.ArrayList;


import LAB05.src.Entidades.Interfaces.Entidade;
import LAB05.src.Entidades.Interfaces.Entidade.TipoEntidade;
import LAB05.src.Entidades.Robos.*;
import LAB05.src.Exceptions.*;

/**
 * Classe Ambiente representa um ambiente tridimensional onde entidades podem ser adicionadas, removidas e movidas.
 * Possui métodos para inicializar o mapa, adicionar e remover entidades, verificar colisões, mover entidades,
 * e visualizar o ambiente.
 */
public class Ambiente {
    //Atributos final
    private final int altitudeMinima = 0;
    private final int origemX = 0;
    private final int origemY = 0;

    //Atributos normais / para o construtor
    private int larguraX; 
    private int larguraY; 
    private int larguraZ; 
    private ArrayList<Entidade> entidades;
    private TipoEntidade[][][] mapa;
    private String nome; // nome do ambiente, pode ser usado para identificar o ambiente
    private char[][] planoXY;

    //Construtor
    public Ambiente(int larguraZ, int larguraX, int larguraY, TipoEntidade[][][] mapa, char[][] planoXY, String nome) {
        this.larguraX = larguraX;
        this.larguraY = larguraY;
        this.larguraZ = larguraZ;
        this.mapa = mapa;
        this.planoXY = planoXY;
        entidades = new ArrayList<Entidade>();
        this.nome = nome; // nome padrão do ambiente
    }


    //Metodos
    // Inicializa o mapa e o planoXY com espaços vazios
    // O planoXY é um recorte 2D do ambiente no plano Z = 0, onde cada posição é representada por um caractere.
    public void inicializarMapa(){
        for (int x = 0; x < larguraX; x++){
            for (int y = 0; y < larguraY; y++){
                planoXY[x][y] = 'v'; // faz a representação do plano xy no z = 0. O 'v' é a representação para o vazio
                for (int z = 0; z < larguraZ; z++){
                    mapa[x][y][z] = TipoEntidade.VAZIO;
                }
            }
        }
    }
    /**
     *  Método adicionarEntidade:
     *  Adiciona uma entidade ao ambiente, verificando se a posição está dentro dos limites e se não há colisões com outras entidades.
     *  Se a posição for válida, a entidade é adicionada ao mapa e ao planoXY.
     *  Se não for válida, lança exceções apropriadas.
     * 
     * @param e Entidade a ser adicionada ao ambiente.
     * @throws ForaDosLimitesException Se a entidade está fora dos limites do ambiente.
     * @throws LocalOcupadoException Se a região onde a entidade será adicionada já está ocupada por outra entidade.
     */
    public void adicionarEntidade(Entidade e, boolean printar) throws MovimentoInvalidoexception{
        verificarColisoes(e, e.getX_1(), e.getY_1(), e.getZ_1());
        int X_max = e.getX_2();
        int Y_max = e.getY_2();
        int Z_max = e.getZ_2();
        entidades.add(e);
        for (int x = e.getX_1(); x <= X_max; x++) {
            for (int y = e.getY_1(); y <= Y_max; y++) {
                planoXY[x][y] = e.getRepresentacao();
                for (int z = e.getZ_1(); z <= Z_max; z++) {
                    mapa[x][y][z] = e.getTipo();
                }
            }
        } 
        if (printar){
            // IMPLEMENTAR ALGO PARA ESCREVER PARA UM ARQUIVO DE TEXTO
            // System.out.println("Entidade do tipo: "+ e.getTipo() +" adicionada ao ambiente.");
            // System.out.println("Posição do canto inferior esquerdo: " + "(" + e.getX() + ", " + e.getY() + ", " + e.getZ() + ")");
            // System.out.println("Posição do canto superior direito: " + "(" + X_max + ", " + Y_max + ", " + Z_max + ")\n");
        }
    }

    /**
     * Método removerEntidade:
     * Remove uma entidade do ambiente, verificando se ela existe no mapa.
     * Se a entidade não for encontrada, lança uma exceção.
     * Se for removida com sucesso, atualiza o mapa e o planoXY para refletir a remoção.
     * 
     * @param e Entidade a ser removida do ambiente.
     * @param printar Se true, imprime uma mensagem de sucesso após a remoção.
     * @throws EntidadeNaoEncontradaException Se a entidade não está no ambiente ou não pode ser encontrada.
     */
    public void removerEntidade(Entidade e, boolean printar) throws EntidadeNaoEncontradaException {
        if (!entidades.contains(e)) 
            throw new EntidadeNaoEncontradaException("Entidade não está no ambiente ou não pôde ser encontrada!\n");
        for (int x = e.getX_1(); x <= e.getX_2(); x++) {
            for (int y = e.getY_1(); y <= e.getY_2(); y++) {
                planoXY[x][y] = 'v'; 
                for (int z = e.getZ_1(); z <= e.getZ_2(); z++) {
                    mapa[x][y][z] = TipoEntidade.VAZIO;
                }
            }
        }
        entidades.remove(e);
        if (printar)
            System.out.println("A Entidade " + e.getId() + ", do tipo " + e.getTipo() + ", foi removida com sucesso.\n");
    }

    // ve se esta dentro dos limites de x,y e altitude, caso contrário retorna false
    public boolean dentroDosLimites(int x, int y, int altitude) { 
        if ((origemX <= x && x <= larguraX) && (origemY <= y && y <= larguraY) && (altitudeMinima <= altitude && altitude <= larguraZ)) return true;
        return false;
    }

    // Verifica se a posição (x, y, z) está Vazia ou ocupada por outra entidade.
    public boolean estaOcupado(int x, int y, int z, Entidade e) { 
        TipoEntidade posicao = mapa[x][y][z];
        if (posicao == TipoEntidade.VAZIO) return false; // A posição está vazia
        if (posicao == TipoEntidade.LOCAL && e.getTipo() == TipoEntidade.ROBO) {
            return false;
        }   
        return true; // A posição está ocupada
    }

    /**
     * Método moverRobo:
     * Move um robô para uma nova posição (novoX, novoY, novoZ) no ambiente.
     * Verifica se a nova posição está ocupada ou fora dos limites antes de mover.
     * 
     * @param e Entidade a ser movida.
     * @param novoX Nova coordenada X da entidade.
     * @param novoY Nova coordenada Y da entidade.
     * @param novoZ Nova coordenada Z da entidade.
     * @throws LocalOcupadoException Se a nova posição já estiver ocupada por outra entidade.
     * @throws ForaDosLimitesException Se a nova posição estiver fora dos limites do ambiente.
     * @throws RoboDesligadoException Se a entidade for um robô desligado e não puder ser movida.
     * @throws NaoPodeVoarException Se a entidade tentar voar sem permissão (apenas robôs bombeiros podem voar).
     */
    public void moverRobo(Robo r, int novoX, int novoY, int novoZ ) throws RoboDesligadoException, MovimentoInvalidoexception, EntidadeNaoEncontradaException { 
       if (!entidades.contains(r)) 
            throw new EntidadeNaoEncontradaException("Robô não está no ambiente ou não pôde ser encontrado!\n");
    
        if (r.getZ_1() != novoZ) { // Se há mudança na altitude
            if (!(r instanceof RoboBombeiro)) {
                throw new MovimentoInvalidoexception("Robô " + r.getId() + " não pode voar (apenas Robôs Bombeiros podem)!\n");
            }
        }
        int oldX = r.getX_1();
        int oldY = r.getY_1();
        int oldZ = r.getZ_1();

        // Verifica se a nova posição está ocupada
        verificarColisoes(r, novoX, novoY, novoZ); 

        // Se a nova posição está vazia ou é um local =>
        // Limpeza da posição antiga:
         // se estava em um local volta a posição para tipoLocal
        if (r.EstahEmLocal()) mapa[oldX][oldY][oldZ] = TipoEntidade.LOCAL;
        // se não estava em um local a posição anterior vota a ser vazia
        else mapa[oldX][oldZ][oldZ] = TipoEntidade.VAZIO;
        // se estava no chão, atualiza o plano
        if (oldZ == 0) planoXY[oldX][oldY] = r.getLocalAtualRep();

        r.mover(novoX - oldX, novoY - oldY, novoZ - oldZ); 


        if (r.getZ_1() == 0) planoXY[r.getX_1()][r.getY_1()] = 'r';
        mapa[r.getX_1()][r.getY_1()][r.getZ_1()] = TipoEntidade.ROBO;
        
        
        // DENOVO PRINTAR PARA ARQUIVO TXT
        // System.out.println("Entidade: " + e.getId() + " movida com sucesso para a nova posição.");
        // System.out.println("Nova posição do canto inferior esquerdo: " + "(" + e.getX() + ", " + e.getY() + ", " + e.getZ() + ")");
        // System.out.println("Nova posição do canto superior direito: " + "(" + (e.getX() + e.getLarguraX()) + ", " + (e.getY() + e.getLarguraY()) + ", " + (e.getZ() + e.getAltura()) + ")\n");
    }
    /**
     * Método verificarColisoes:
     * Verifica se a região ocupada por uma entidade está livre ou se está fora dos limites do ambiente.
     * Se a região estiver ocupada, lança uma exceção LocalOcupadoException.
     * Se a região estiver fora dos limites, lança uma exceção ForaDosLimitesException.
     * 
     * @param e Entidade cuja região será verificada.
     * @param novoX Nova coordenada X da entidade.
     * @param novoY Nova coordenada Y da entidade.
     * @param novoZ Nova coordenada Z da entidade.
     * @throws LocalOcupadoException Se a região ocupada já estiver ocupada por outra entidade.
     * @throws ForaDosLimitesException Se a região ocupada estiver fora dos limites do ambiente.
     */
    public void verificarColisoes(Entidade e, int novoX, int novoY, int novoZ) throws MovimentoInvalidoexception{ 
        for (int x = novoX; x <= e.getLarguraX() + novoX; x++) {
            for (int y = novoY; y <= e.getLarguraY() + novoY; y++) {
                for (int z = novoZ; z <= novoZ + e.getLarguraZ(); z++) { 
                    if (!dentroDosLimites(x, y, z)) {
                        throw new MovimentoInvalidoexception("A região desejada pelo(a) " + e.getTipo() + " está fora dos limites do ambiente!\n");
                    }
                    else if (estaOcupado(x, y, z, e)){
                        throw new MovimentoInvalidoexception("A região desejada pelo(a) " + e.getTipo() + " está ocupada!\n");
                    } 
                }
            }
        }
    }

    // Imprime o PlanoXY do ambiente, para visualizar o ambiente em 2D.
    public void visualizarAmbiente(){
        for (int x = 0; x < getLarguraX(); x++){
            for (int y = 0; y < getLarguraY(); y++){
                System.out.print(planoXY[x][y] + " "); // quando usa espaço entre os caracteres dá uma zoada não dá para ver direito as coisas...
            }
            System.out.print("\n");
        }
    }


    //Geters e Setters
    public ArrayList<Entidade> getEntidades() { return entidades; }
    public char[][] getplanoXY() { return planoXY; }
    public TipoEntidade[][][] getMapa() { return mapa; }
    public int getLarguraX() { return larguraX; }
    public int getLarguraY() { return larguraY; }
    public int getLarguraZ() { return larguraZ; }
    public String getNome() { return nome; }
    
}
