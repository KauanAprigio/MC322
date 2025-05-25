package LAB04.Code;
import java.util.ArrayList;

import LAB04.Code.Entidade.TipoEntidade;
import LAB04.Code.Exceptions.EntidadeNaoEncontradaException;
import LAB04.Code.Exceptions.ForaDosLimitesException;
import LAB04.Code.Exceptions.LocalOcupadoException;
import LAB04.Code.Exceptions.RoboDesligadoException;

// VALE RESSALTAR QUE SE EU FOR USAR O VERIFICAR COLISOES PARA BOTAR UM OBSTACULO EM UM LUGAR QUE NÃO TENHA OUTRO DEVO REVER O CODIGO

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
    Ambiente(int largura, int profundidade, int altura, TipoEntidade[][][] mapa, char[][] planoXY){
        this.largura = largura;
        this.profundidade = profundidade;
        this.altura = altura;
        this.mapa = mapa;
        this.planoXY = planoXY;
        entidades = new ArrayList<Entidade>();
    }


    //Metodos
    public void inicializarMapa(){
        for (int x = 0; x < largura; x++){
            for (int y = 0; y < profundidade; y++){
                planoXY[x][y] = 'v'; // aqui irei fazer a representação do plano xy no z = 0. O 'v' é a representação para o vazio
                for (int z = 0; z < altura; z++){
                    mapa[x][y][z] = TipoEntidade.VAZIO;
                }
            }
        }
    }
    public void adicionarEntidade(Entidade e) throws ForaDosLimitesException, LocalOcupadoException{
        int X_max = e.getX() + e.getLarguraX();
        int Y_max = e.getY() + e.getLarguraY();
        int Z_max = e.getZ() + e.getAltura();
        verificarColisoes(e, e.getX(), e.getY(), e.getZ());
        entidades.add(e);
        for (int x = e.getX(); x <= X_max; x++) {
            for (int y = e.getY(); y <= Y_max; y++) {
                planoXY[x][y] = e.getRepresentacao();
                for (int z = e.getZ(); z <= Z_max; z++) {
                    mapa[x][y][z] = e.getTipo();
                }
            }
        } 
        System.out.println("Entidade do tipo: "+ e.getTipo() +" adicionada ao ambiente");
        System.out.println("Posição do canto inferior esquerdo: " + "(" + e.getX() + ", " + e.getY() + ", " + e.getZ() + ")");
        System.out.println("Posição do canto superior direito: " + "(" + X_max + ", " + Y_max + ", " + Z_max + ")");
    }

    public void removerEntidade(Entidade e) throws EntidadeNaoEncontradaException {
         if (mapa[e.getX()][e.getY()][e.getZ()] == TipoEntidade.VAZIO) 
            throw new EntidadeNaoEncontradaException("Entidade não está no ambiente e não pode ser removida!\n");
        int x = e.getX();
        int y = e.getY();
        int z = e.getZ();
        for (; x <= e.getLarguraX() + e.getX(); x++) {
            for (; y <= e.getLarguraY() + e.getY(); y++) {
                planoXY[x][y] = 'v'; 
                for (; z <= e.getAltura() + e.getZ(); z++) {
                    mapa[x][y][z] = TipoEntidade.VAZIO;
                }
            }
        }
        entidades.remove(e);
        System.out.println("A Entidade do tipo: " + e.getTipo() + " foi removida com sucesso.");
    }

    public boolean dentroDosLimites(int x, int y, int altitude) { // ve se esta dentro dos limites de x,y e altitude, caso contrário retorna false
        if ((origemX <= x && x <= largura) && (origemY <= y && y <= altura) && (altitudeMinima <= altitude && altitude <= altura)) return true;
        return false;
    }

    public boolean estaOcupado(int x, int y, int z){ 
        return mapa[x][y][z] != TipoEntidade.VAZIO;
    }

    public void moverEntidade(Entidade e, int novoX, int novoY,
        int novoZ ) throws LocalOcupadoException, ForaDosLimitesException, RoboDesligadoException { // aqui eu nao sei como faria para mover o z, porque um predio por exemplo na pode começar sem ser do 0 + PODE TER UM EXCEPTION SE A ENTIDADE NAO EXISTIR
        try{
            removerEntidade(e); // aqui reutilizarei o metodo para remover a entidade e colocar espaços vazios
        } catch (EntidadeNaoEncontradaException exception) {
            System.out.println("Entidade não está no ambiente e não pode ser movida!\n");
            return;
        }
        if (e.getTipo() == TipoEntidade.OBSTACULO) novoZ = 0; 
        int deltaX = novoX - e.getX();
        int deltaY = novoY - e.getY();
        int deltaZ = novoZ - e.getZ();
        // Verifica se o novo local está ocupado ou se está fora dos limites
        verificarColisoes(e, novoX, novoY, novoZ);
        
        e.mover(deltaX, deltaY, deltaZ, mapa, planoXY);
    }

    public void executarSensores(){ // AQUI NAO VEJO MUITO USO PARA ISSO 
       
    }

    public void verificarColisoes(Entidade e, int novoX, int novoY, int novoZ) throws LocalOcupadoException, ForaDosLimitesException{ 
        int x = novoX;
        int y = novoY;
        int z = novoZ;
        for (; x <= e.getLarguraX() + novoX; x++) {
            for (; y <= e.getLarguraY() + novoY; y++) {
                for (; z <= e.getAltura() + novoZ; z++) {
                    if (estaOcupado(x, y, z)){
                        throw new LocalOcupadoException("A região ocupada pelo(a) " + e.getTipo() + " está ocupada!\n");
                    } else if (!dentroDosLimites(x, y, z)) {
                        throw new ForaDosLimitesException("A região ocupada pelo(a) " + e.getTipo() + " está fora dos limites do ambiente!\n");
                    }
                }
            }
        }
    }

    public void visualizarAmbiente(){
        for (int x = 0; x < largura; x++){
            for (int y = 0; y < profundidade; y++){
                System.out.println(planoXY[x][y] + " ");// aqui irei printar os caracteres dando um espaço entre eles
            }
        }
        System.err.println(); // uma quebra de linha para deixar dividido a matriz do resto na main
    }


    //Geters e Setters
    public ArrayList<Entidade> getEntidades() { return entidades; }
    public char[][] getplanoXY() { return planoXY; }
}
