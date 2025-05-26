package LAB04.Code.Interfaces;


import LAB04.Code.Ambiente;
import LAB04.Code.Exceptions.RoboDesligadoException;

public interface Entidade {
    // Métodos para obter a posição da entidade no ambiente
    public int getX();
    public int getY();
    public int getZ();

    // Métodos para obter informações sobre a entidade
    public TipoEntidade getTipo(); // Método para obter o tipo da entidade (ex: ROBO, OBSTACULO, etc.)
    
    public String getDescricao();// Inclui uma breve descrição da entidade

    // Método para obter a representação da entidade no ambiente no plano XY
    public char getRepresentacao();
    
    public String getId();// Método para obter o ID da entidade
    
    /**
     * Move a entidade no ambiente.
     * @param deltaX Deslocamento no eixo X.
     * @param deltaY Deslocamento no eixo Y.
     * @param deltaZ Deslocamento no eixo Z.
     * @throws RoboDesligadoException Se a entidade estiver desligada e não puder ser movida.
     */
    public void mover(int deltaX, int deltaY, int deltaZ) throws RoboDesligadoException;

    // Dimensões da entidade
    public int getLarguraX ();
    public int getLarguraY ();
    public int getAltura();

    // Método para obter o ambiente onde a entidade está localizada
    public Ambiente getAmbiente();
    
    
    public default boolean isComunicavel() {
        return false; // Por padrão, Entidade não é comunicável, sobrescrever em subclasses se necessário
    }


    /**
     * Enumeração que define os tipos de entidades que podem existir no ambiente.
     */
    public static enum TipoEntidade{
        VAZIO,
        ROBO,
        OBSTACULO,
        LOCAL,
        DESCONHECIDO,
        COMUNICADOR;
    }
}


