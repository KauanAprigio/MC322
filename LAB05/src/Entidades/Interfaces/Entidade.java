package LAB05.src.Entidades.Interfaces;
import LAB05.src.Ambiente.Ambiente;


public interface Entidade {
    // Métodos para obter a posição da entidade no ambiente
    public int getX_1();
    public int getY_1();
    public int getZ_1();
    public int getX_2();
    public int getY_2();
    public int getZ_2();

    // Métodos para obter informações sobre a entidade
    public TipoEntidade getTipo(); // Método para obter o tipo da entidade (ex: ROBO, OBSTACULO, etc.)
    
    public String getDescricao();// Inclui uma breve descrição da entidade

    // Método para obter a representação da entidade no ambiente no plano XY
    public char getRepresentacao();
    
    public String getId();// Método para obter o ID da entidade
    
    // Dimensões da entidade
    public int getLarguraX ();
    public int getLarguraY ();
    public int getLarguraZ();

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
        FOGO,
        LIXO,
        LOCAL,          // Robôs podem entrar e sair livremente de locais
        COMUNICADOR;
    }
}
