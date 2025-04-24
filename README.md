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
ESSE DIAGRAMA ESTA INCOMPLETO TERMINAR DPS
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
        - n_pas : int
        - power : boolean
        - tipo_limpeza : int
        + ligar() : boolean
        + desligar() : boolean
        + definir_tipo_limpeza(tipo : int)
        + mover(deltaX : int, deltaY : int)
    }

    class RoboGarcom {
        - estoque : int
        - carga_maxima : int
        + adicionar_estoque(peso_adicional_comida : int)
        + entregar_comida(comida : int)
        + mudar_carga(nova_carga : int)
    }

    class RoboLetreiro {
        - max_caracteres : int
        - visor : String
        + escrever_visor(texto : String, altura : int)
        + limpar_visor()
        + aumentarVisor(tamanho_adicional : int)
    }

    class RoboBombeiro {
        - peso_max : int
        - peso_tripulantes : int
        - reservatorio : int
        + adicionar_agua(litros : int)
        + apagar_fogo(litros_necessarios : int)
        + resgate(peso_civis : int)
        + liberar_tripulantes()
        + aprimora(peso_adicional : int)
    }

    class Sensor {
        - raio : double
        + monitorar(x : int, y : int, altura : int, ambiente : Ambiente)
    }

    class SensorDeProximidade {
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
        + ARVORE
        + PREDIO
        + BURACO
        + PAREDE
        + ROCHA
        + CERCAELETRICA
        + AREIAMOVEDICA
        + LAGO
    }

    Robo <|-- RoboAereo : Herança
    Robo <|-- RoboTerrestre : Herança
    RoboTerrestre <|-- RoboLimpador : Herança
    RoboTerrestre <|-- RoboGarcom : Herança
    RoboAereo <|-- RoboLetreiro : Herança
    RoboAereo <|-- RoboBombeiro : Herança
    Ambiente *-- Robo : Composição
    Ambiente *-- Obstaculo : Composição
    Robo o-- Sensor : Agregação
    Robo <.. Sensor : Dependência
    Sensor <|-- SensorDeProximidade : Herança
    Sensor <|-- SensorPosicaoSegura : Herança
```


### COMPILAÇÃO:

    javac -d LAB03/Classes LAB03/Code/*.java

### PARA RODAR:

    java -cp LAB03/Classes LAB03.Code.Main 

