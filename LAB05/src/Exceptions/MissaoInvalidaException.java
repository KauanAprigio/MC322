package LAB05.src.Exceptions;

/*
 *  Caso uma missão de robô bombeiro seja dada para um robô que não é bombeiro,
 *  ou quando uma missão de robô limpador seja dada para um robô que não é limpador,
 *  ou quando na hora de criar uma missão, as instruções não forem válidas,
 *  essa exceção será lançada.
 */
public class MissaoInvalidaException extends Exception {
    public MissaoInvalidaException(String message) {
        super(message);
    }
}
