package LAB04.Code;
import java.util.ArrayList;

import LAB04.Code.AbstractClasses.Robo;
import LAB04.Code.Exceptions.EntidadeNaoEncontradaException;
import LAB04.Code.Exceptions.ForaDosLimitesException;
import LAB04.Code.Exceptions.LocalOcupadoException;
import LAB04.Code.Exceptions.NaoPodeVoarException;
import LAB04.Code.Exceptions.RoboDesligadoException;
import LAB04.Code.Interfaces.Entidade;
import LAB04.Code.Interfaces.Entidade.TipoEntidade;
import LAB04.Code.Obstaculo.TipoObstaculo;

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
    int largura; // eixo X
    int profundidade; // eixo Y
    int altura; // eixo Z
    ArrayList<Entidade> entidades;
    TipoEntidade[][][] mapa;
    char[][] planoXY; // aqui irei usar esse plano para representar o ambiente 2d, no caso o plano xy com z = 0

    
    //Construtor
    public Ambiente(int largura, int profundidade, int altura, TipoEntidade[][][] mapa, char[][] planoXY){
        this.largura = largura;
        this.profundidade = profundidade;
        this.altura = altura;
        this.mapa = mapa;
        this.planoXY = planoXY;
        entidades = new ArrayList<Entidade>();
    }


    //Metodos
    // Inicializa o mapa e o planoXY com espaços vazios
    // O planoXY é um recorte 2D do ambiente no plano Z = 0, onde cada posição é representada por um caractere.
    public void inicializarMapa(){
        for (int x = 0; x < largura; x++){
            for (int y = 0; y < profundidade; y++){
                planoXY[x][y] = 'v'; // faz a representação do plano xy no z = 0. O 'v' é a representação para o vazio
                for (int z = 0; z < altura; z++){
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
    public void adicionarEntidade(Entidade e, boolean printar) throws ForaDosLimitesException, LocalOcupadoException{
        int X_max = e.getX() + e.getLarguraX();
        int Y_max = e.getY() + e.getLarguraY();
        int Z_max = e.getZ();
        verificarColisoes(e, e.getX(), e.getY(), e.getZ());
        entidades.add(e);
        for (int x = e.getX(); x <= X_max; x++) {
            for (int y = e.getY(); y <= Y_max; y++) {
                planoXY[x][y] = e.getRepresentacao();
                for (int z = 0; z <= Z_max; z++) {
                    mapa[x][y][z] = e.getTipo();
                }
            }
        } 
        if (printar){
            System.out.println("Entidade do tipo: "+ e.getTipo() +" adicionada ao ambiente.");
            System.out.println("Posição do canto inferior esquerdo: " + "(" + e.getX() + ", " + e.getY() + ", " + e.getZ() + ")");
            System.out.println("Posição do canto superior direito: " + "(" + X_max + ", " + Y_max + ", " + Z_max + ")\n");
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
        if (!entidades.contains(e)) //mudei para ver se tem essa entidade na lista de entidade, se nao tiver é pq ela nao foi adicionada
            throw new EntidadeNaoEncontradaException("Entidade não está no ambiente ou não pôde ser encontrada!\n");
        for (int x = e.getX(); x <= e.getLarguraX() + e.getX(); x++) {
            for (int y = e.getY(); y <= e.getLarguraY() + e.getY(); y++) {
                planoXY[x][y] = 'v'; 
                for (int z = 0; z <= e.getZ(); z++) {
                    mapa[x][y][z] = TipoEntidade.VAZIO;
                }
            }
        }
        entidades.remove(e);
        if (printar)
            System.out.println("A Entidade do tipo: " + e.getTipo() + " foi removida com sucesso.\n");
    }

    // ve se esta dentro dos limites de x,y e altitude, caso contrário retorna false
    public boolean dentroDosLimites(int x, int y, int altitude) { 
        if ((origemX <= x && x <= largura) && (origemY <= y && y <= altura) && (altitudeMinima <= altitude && altitude <= altura)) return true;
        return false;
    }

    // Verifica se a posição (x, y, z) está Vazia ou ocupada por outra entidade.
    public boolean estaOcupado(int x, int y, int z, TipoEntidade tipo) { 
        TipoEntidade posicao = mapa[x][y][z];
        if (posicao == TipoEntidade.VAZIO) return false; // A posição está vazia
        if (posicao == TipoEntidade.OBSTACULO && tipo == TipoEntidade.ROBO){
            for (Entidade entidade : entidades){
                if ((entidade.getX() <= x && entidade.getLarguraX() + entidade.getX() >= x) 
                && (entidade.getY() <= y && entidade.getLarguraY() + entidade.getY() >= y)
                && entidade.getTipo() == TipoEntidade.OBSTACULO){
                    Obstaculo aprimorar = (Obstaculo) entidade;
                    if (aprimorar.getTipoObstaculo() == TipoObstaculo.OFICINA){
                        return false; // A posição é um local e a entidade é um robô, então pode ser ocupada
                    }
                }
            }
        } 
        return true; // A posição está ocupada
    }

    /**
     * Método moverEntidade:
     * Move uma entidade para uma nova posição (novoX, novoY, novoZ) no ambiente.
     * Verifica se a nova posição está ocupada ou fora dos limites antes de mover.
     * Se a entidade não existir no ambiente, lança uma exceção.
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
    public void moverEntidade(Entidade e, int novoX, int novoY,
                            int novoZ ) throws LocalOcupadoException, ForaDosLimitesException, RoboDesligadoException, NaoPodeVoarException { // aqui eu nao sei como faria para mover o z, porque um predio por exemplo na pode começar sem ser do 0 + PODE TER UM EXCEPTION SE A ENTIDADE NAO EXISTIR
        Entidade entidade = e; // no caso irei usar uma entidade auxiliar so para saber qual eu estou removendo e adicionando no ambiente   
        // Apenas Robos bombeiros podem voar.
        if (novoZ != 0 && entidade.getTipo() != TipoEntidade.OBSTACULO) {
            Robo r = (Robo) entidade;
            if (!(r instanceof RoboBombeiro))
                throw new NaoPodeVoarException("Movimento inválido! Entidade não pode sair do chão!\n");
        } else if (entidade.getTipo() == TipoEntidade.OBSTACULO  && novoZ != 0) {
            throw new NaoPodeVoarException("Movimento inválido! Obstáculo não pode voar!\n");
        }
                                
        int deltaX = novoX - entidade.getX();
        int deltaY = novoY - entidade.getY();
        int deltaZ = novoZ - entidade.getZ();
        
        // Verifica se o novo local está ocupado ou se está fora dos limites
        verificarColisoes(entidade, novoX, novoY, novoZ);
        
        //PELO OQ EU VI O MOVER E ADICIONAR SOBREPOE CODIGO, TIPO ELES ATUALIZAM 2X O MAPA 3D E 2D, MUDA ISSO SERA?
        entidade.mover(deltaX, deltaY, deltaZ);

        adicionarEntidade(entidade, false); // adiciona a entidade na nova posição
        try{
            removerEntidade(e, false); // aqui reutilizarei o metodo para remover a entidade e colocar espaços vazios
        } catch (EntidadeNaoEncontradaException exception) {
            System.out.println("Entidade não está no ambiente e não pode ser movida!\n");
            return;
        }
        
        System.out.println("Entidade: " + e.getId() + " movida com sucesso para a nova posição.");
        System.out.println("Nova posição do canto inferior esquerdo: " + "(" + e.getX() + ", " + e.getY() + ", " + e.getZ() + ")");
        System.out.println("Nova posição do canto superior direito: " + "(" + (e.getX() + e.getLarguraX()) + ", " + (e.getY() + e.getLarguraY()) + ", " + e.getZ() + ")\n");
    }

    public void executarSensores(){ 
        // pode ser usado para ativar os sensores de todos os robôs no ambiente. 
        // Tipo só chamar a função ativar sensores deles...
       
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
    public void verificarColisoes(Entidade e, int novoX, int novoY, int novoZ) throws LocalOcupadoException, ForaDosLimitesException{ 
        
        
        
        for (int x = novoX; x <= e.getLarguraX() + novoX; x++) {
            for (int y = novoY; y <= e.getLarguraY() + novoY; y++) {
                for (int z = novoZ; z <= novoZ; z++) { //tendo em vista que somente o roboBombeiro pode mudar a altura...vou deixar com um for vendo do novoZ até ele mesmo
                    if (!dentroDosLimites(x, y, z)) {
                        throw new ForaDosLimitesException("A região desejada pelo(a) " + e.getTipo() + " está fora dos limites do ambiente!\n");
                    }
                    else if (estaOcupado(x, y, z, e.getTipo())){
                        throw new LocalOcupadoException("A região desejada pelo(a) " + e.getTipo() + " está ocupada!\n");
                    } 
                }
            }
        }
    }

    // Imprime o PlanoXY do ambiente, para visualizar o ambiente em 2D.
    public void visualizarAmbiente(){
        for (int x = 0; x < largura; x++){
            for (int y = 0; y < profundidade; y++){
                System.out.print(planoXY[x][y]);// printa os caracteres dando um espaço entre eles
            }
            System.out.print("\n");
        }
    }


    //Geters e Setters
    public ArrayList<Entidade> getEntidades() { return entidades; }
    public char[][] getplanoXY() { return planoXY; }
    public TipoEntidade[][][] getMapa() { return mapa; }
}
