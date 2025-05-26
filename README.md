# INTRO
Repositório contendo todos os laboratórios da matéria MC322 (Programação orientada a objetos) da UNICAMP.\
A disciplina é ministrada em JAVA exclusivamente e essa a linguagem em que os laboratórios foram programados.\
Cada laboratório foi dividido em seu próprio conjunto de pastas e os comandos devem ser rodados a partir da pasta root do repositório.
-
Todos os laboratórios foram feitos em JAVA exclusivamente
# IDE e JAVA
JAVA versão 21+\
IDE utilizado para codar foi o [Visual Studio Code](https://code.visualstudio.com/)

# LAB 2

### COMPILAÇÃO:
    
    javac -d LAB02/Classes LAB02/Code/*.java

### PARA RODAR:

    java -cp LAB02/Classes LAB02.Code.Main 


# LAB 3
Diagrama de classes:\
As classes são representadas de maneira simplificada, com algumas relações e métodos ocultados para evitar confusão visual.\
Métodos Getters e Setters foram ocultados e a enumeração do tipoObstáculo foi separada da classe Obstáculo.\
Além disso, as setas de agregação e dependência entre subclasses de robôs e subclasses de sensores não foram desenhadas.
```mermaid
---
title: Diagrama de classes - LAB03
---
classDiagram
    note "notação: public -> +, private -> -"
    
    class Robo {
        - nome : String
        - posicaoX : int
        - posicaoY : int
        - sensor : Sensor
        - ambiente : Ambiente
        + mover(deltaX : int, deltaY : int)
        + identificarObstaculo()
        + exibirPosicao()
    }

    class RoboTerrestre {
        - velocidadeMaxima : int
        + mover(deltaX : int, deltaY : int)
    }

    class RoboAereo {
        - altitude : int
        - altitudeMaxima : int
        + subir(deltaZ : int)
        + descer(deltaZ : int)
        + exibirPosicao()
        + identificarObstaculo()
    }

    class RoboLimpador {
        - tipo_limpeza : int
        - sensorDeLixo : SensorDeLixo
        - raioDeLimpeza : int
        + definir_tipo_limpeza(tipo : int)
        + limpar()
        + indentificar_lixo()
        + aprimorar(aumento_raio_limpeza : int)
    }

    class RoboBombeiro {
        - peso_max : int
        - reservatorio : int
        - sensorDeFogo : SensorDeFogo
        + adicionar_agua(litros : int)
        + apagar_fogo()
        + indentificar_fogo()
        + aprimorar(peso_adicional : int)
    }

    class Sensor {
        - raio : double
        + monitorar(x : int, y : int, altura : int, ambiente : Ambiente)
    }

    class SensorDeFogo {
        + monitorar(x : int, y : int, altura : int, ambiente : Ambiente)
    }

    class SensorDeLixo {
        + monitorar(x : int, y : int, altura : int, ambiente : Ambiente)
    }

    class SensorPosicaoSegura {
        - ambiente : Ambiente
        + posicao_segura(pos_x : int, pos_y : int, x_atual : int, y_atual : int) : boolean
    }

    class Ambiente {
        - largura : int
        - altura : int
        - nome : String
        - altitudeMaxima : int
        - obstaculos : ArrayList<Obstaculo>
        - robos : ArrayList<Robo>
        - altitudeMinima : int
        - origemX : int
        - origemY : int
        + adicionarRobo(r : Robo)
        + removerRobo(r : Robo)
        + detectarColisoes()
        + adicionarObstaculo(o : Obstaculo)
        + removerObstaculo(o : Obstaculo)
        + dentroDosLimites(x : int, y : int, altitude : int) : boolean
    }

    class Obstaculo {
        - posicaoX1 : int
        - posicaoY1 : int
        - posicaoX2 : int
        - posicaoY2 : int
        - tipo : TipoObstaculo
        - altura : int
    }

    class TipoObstaculo {
        <<enumeration>>
        + LAGO
        + FOGO
        + PREDIOEMCHAMAS
        + PREDIO
        + SUJEIRAENCARDIDA
        + COMIDANOCHAO
        + SACOLAPLASTICA
        + OFICINA
    }

    Robo <|-- RoboAereo : Herança
    Robo <|-- RoboTerrestre : Herança
    RoboTerrestre <|-- RoboLimpador : Herança
    RoboAereo <|-- RoboBombeiro : Herança
    Ambiente *-- Robo : Composição
    Ambiente *-- Obstaculo : Composição
    Robo o-- Sensor : Agregação
    Robo <.. Sensor : Dependência
    Sensor <|-- SensorDeFogo : Herança
    Sensor <|-- SensorDeLixo : Herança
    Sensor <|-- SensorPosicaoSegura : Herança
```
![Diagrama_Ambiente](https://github.com/user-attachments/assets/ce31c75d-47a6-4824-86eb-2c68f99437bd)

### COMPILAÇÃO:

    javac -d LAB03/Classes LAB03/Code/*.java

### PARA RODAR:

    java -cp LAB03/Classes LAB03.Code.Main 

# LAB 4

### COMPILAÇÃO:

    javac LAB04/Code/**/*.java LAB04/Code/Exceptions/*.java  LAB04/Code/Interfaces/*.java  LAB04/Code/AbstractClasses/*.java -d LAB04/Classes

### PARA RODAR:

    java -cp LAB04/Classes LAB04.Code.Main