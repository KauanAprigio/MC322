package LAB05.src.Entidades.Interfaces;
import LAB05.src.Ambiente.Ambiente;

/**
 * Interface Entidade
 * <p>
 * Define os métodos que todas as entidades no ambiente devem implementar.
 * As entidades podem ser robôs, obstáculos, fogo, lixo, etc.
 * </p>
 *
 * <ul>
 *   <li><b>getX_1(), getY_1(), getZ_1()</b>: Obtém a posição inicial da entidade.</li>
 *   <li><b>getX_2(), getY_2(), getZ_2()</b>: Obtém a posição final da entidade.</li>
 *   <li><b>getTipo()</b>: Obtém o tipo da entidade (ex: ROBO, OBSTACULO, etc.).</li>
 *   <li><b>getDescricao()</b>: Obtém uma breve descrição da entidade.</li>
 *   <li><b>getRepresentacao()</b>: Obtém a representação da entidade no ambiente no plano XY.</li>
 *   <li><b>getId()</b>: Obtém o ID da entidade.</li>
 *   <li><b>getLarguraX(), getLarguraY(), getLarguraZ()</b>: Obtém as dimensões da entidade.</li>
 *   <li><b>getAmbiente()</b>: Obtém o ambiente onde a entidade está localizada.</li>
 * </ul>
 */
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
