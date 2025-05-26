package LAB04.Code.Interfaces;

import LAB04.Code.Exceptions.RoboDesligadoException;
/*
 * Interface Sensoreavel:
 * Define o contrato para entidades que possuem sensores.
 * Contém o método acionarSensores, que deve ser implementado por robôs que possuem sensores.
 * Lança uma exceção RoboDesligadoException se o robô estiver desligado ao tentar acionar os sensores.
 * Apenas robôs limpadores implementam essa interface.
 */
public interface Sensoreavel {
    /**
     * Método acionarSensores:
     * Deve ser implementado por robôs que possuem sensores.
     * Lança uma exceção RoboDesligadoException se o robô estiver desligado ao tentar acionar os sensores.
     * O método deve conter a lógica para acionar os sensores do robô.
     * @throws RoboDesligadoException Se o robô estiver desligado ao tentar acionar os sensores.
     */
    public void acionarSensores() throws RoboDesligadoException;
}
