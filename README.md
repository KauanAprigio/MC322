# INTRO
Repositório contendo todos os laboratórios da matéria MC322 (Programação orientada a objetos) da UNICAMP.
A disciplina é ministrada em JAVA e essa é a linguagem em que os laboratórios foram programados.
Cada laboratório foi dividido em seu próprio conjunto de pastas e os comandos devem ser rodados a partir da pasta root do repositório.

- Todos os laboratórios foram feitos em JAVA exclusivamente.
- **Autores**: Kauan Aprigio Estevão (260205) e Diego Martins Santos (288809)

# IDE e JAVA
- **JAVA versão**: 21+
- **IDE**: [Visual Studio Code](https://code.visualstudio.com/)

---

# LAB 2

### COMPILAÇÃO:
    javac -d LAB02/Classes LAB02/Code/*.java

### PARA RODAR:
    java -cp LAB02/Classes LAB02.Code.Main

---

# LAB 3
Diagrama de classes:
As classes são representadas de maneira simplificada, com algumas relações e métodos ocultados para evitar confusão visual.
Métodos Getters e Setters foram ocultados e a enumeração do tipoObstáculo foi separada da classe Obstáculo.
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

---

# LAB 4

## Principais Mudanças (LAB03 -> LAB04)

O Laboratório 4 introduziu conceitos mais avançados de Orientação a Objetos, focando em **Interfaces**, **Classes Abstratas** e **Tratamento de Exceções**. As principais evoluções foram:

1.  **Interfaces**: Foram criadas diversas interfaces (`Entidade`, `Aprimoravel`, `Comunicavel`, `FogoZero`, `Sensoreavel`, `SujeiraZero`) para definir contratos de comportamento. Isso permitiu "simular" herança múltipla e desacoplar as classes, tornando o sistema mais flexível e extensível.
2.  **Classes Abstratas**: `Robo` e `Sensor` foram transformadas em classes abstratas, definindo comportamentos e atributos comuns, mas forçando subclasses a implementar métodos específicos. `CentralComunicacao` também foi introduzida como abstrata.
3.  **Tratamento de Exceções**: Um conjunto robusto de exceções personalizadas (`ForaDosLimitesException`, `LocalOcupadoException`, `ErrorLimpezaException`, etc.) foi implementado para lidar com erros de forma mais específica e clara.
4.  **`Ambiente` 3D**: A classe `Ambiente` foi aprimorada para gerenciar um mapa 3D (`TipoEntidade[][][]`) e um plano 2D (`char[][]`), permitindo um controle de posição e colisão mais preciso e complexo. A gestão de entidades foi unificada através da interface `Entidade`.
5.  **`ComunicadorCentral`**: Uma nova classe foi adicionada para gerenciar a comunicação entre entidades, especialmente para alertar sobre perigos como fogo.
6.  **Refatoração**: As classes de Robôs e Obstáculos foram refatoradas para implementar as novas interfaces e utilizar o novo `Ambiente` e o sistema de exceções.
7.  **Menu Interativo**: O menu foi aprimorado para refletir as novas funcionalidades e seguir as especificações, permitindo uma interação mais rica com a simulação.

## Diagrama de Classes - LAB04

```mermaid
---
title: Diagrama de classes - LAB04
---
classDiagram
    direction TB

    class I_Entidade {
        <<Interface>>
        +getX() int
        +getY() int
        +getZ() int
        +getTipo() TipoEntidade
        +getDescricao() String
        +getRepresentacao() char
        +getId() String
        +mover(int, int, int) void
        +getLarguraX() int
        +getLarguraY() int
        +getAltura() int
        +getAmbiente() Ambiente
        +isComunicavel() boolean
    }

    class A_Robo {
        <<Abstract>>
        -id : String
        -estado : EstadoRobo
        -pos_x : int
        -pos_y : int
        -pos_z : int
        #ambiente : Ambiente
        +mover(int, int, int) void
        +ligar() void
        +desligar() void
        +getEstado() EstadoRobo
    }
    A_Robo ..|> I_Entidade : Implementa

    class A_Sensor {
        <<Abstract>>
        -raio : double
        +monitorar(int, int, int, Ambiente) void
    }

    class A_CentralComunicacao {
        <<Abstract>>
        -mensagens : List~String~
        +registrarMensagem(String, String) void
        +exibirMensagens() void
    }

    class I_Aprimoravel {
        <<Interface>>
        +aprimorar(int) void
    }
    class I_Comunicavel {
        <<Interface>>
        +enviarMensagem(Comunicavel, String) void
        +receberMensagem(String) void
    }
    class I_FogoZero {
        <<Interface>>
        +adicionar_agua(int) void
        +apagar_fogo() void
    }
    class I_Sensoreavel {
        <<Interface>>
        +acionarSensores() void
    }
    class I_SujeiraZero {
        <<Interface>>
        +limpar() void
        +definir_tipo_limpeza(int) void
    }

    class RoboLimpador {
        -tipo_limpeza : int
        -sensorDeLixo : SensorDeLixo
        -raioDeLimpeza : int
    }
    RoboLimpador --|> A_Robo : Herda de
    RoboLimpador ..|> I_Sensoreavel : Implementa
    RoboLimpador ..|> I_SujeiraZero : Implementa
    RoboLimpador ..|> I_Aprimoravel : Implementa

    class RoboBombeiro {
        -altitudeMaxima : int
        -peso_max : int
        -reservatorio : int
        -raio_de_cessar_fogo : int
    }
    RoboBombeiro --|> A_Robo : Herda de
    RoboBombeiro ..|> I_FogoZero : Implementa
    RoboBombeiro ..|> I_Comunicavel : Implementa
    RoboBombeiro ..|> I_Aprimoravel : Implementa

    class SensorDeLixo {
        %% Atributos da classe SensorDeLixo, se houver
    }
    SensorDeLixo --|> A_Sensor : Herda de

    class Obstaculo {
        -pos_x : int
        -pos_y : int
        -tipoObstaculo : TipoObstaculo
    }
    Obstaculo ..|> I_Entidade : Implementa

    class ComunicadorCentral {
        %% Atributos da classe ComunicadorCentral, se houver
    }
    ComunicadorCentral --|> A_CentralComunicacao : Herda de
    ComunicadorCentral ..|> I_Entidade : Implementa
    ComunicadorCentral ..|> I_Comunicavel : Implementa

    class Ambiente {
        -largura : int
        -profundidade : int
        -altura : int
        -entidades : List~I_Entidade~
        -mapa : object
        -planoXY : object
        +adicionarEntidade(I_Entidade) void
        +removerEntidade(I_Entidade, boolean) void
        +moverEntidade(I_Entidade, int, int, int) void
        +visualizarAmbiente() void
    }

    %% Relações (com legendas como no LAB03)
    RoboLimpador "1" o-- "1" SensorDeLixo : Agregação (possui)
    RoboBombeiro "1" -- "1" ComunicadorCentral : Associação (comunicaCom) %% Usando associação simples, pode ser 'o--' se for agregação
    ComunicadorCentral "1" -- "*" RoboBombeiro : Associação (podeAvisar) %% Ou 'o--'

    A_Robo "*" o-- "1" Ambiente : Agregação (operaEm)
    Obstaculo "*" o-- "1" Ambiente : Agregação (contidoEm)
    ComunicadorCentral "1" o-- "1" Ambiente : Agregação (localizadoEm)

    %% Enums
    class EstadoRobo { <<enumeration>> ON; OFF }
    class TipoObstaculo { <<enumeration>> LAGO; FOGO /*...*/ }
    class TipoEntidade { <<enumeration>> VAZIO; ROBO /*...*/ }

    %% Relações com Enums (associação/dependência)
    A_Robo -- EstadoRobo : Usa
    Obstaculo -- TipoObstaculo : Usa
    I_Entidade -- TipoEntidade : Usa
```

## Interfaces Criadas

* **`I_Entidade`**: Contrato base para qualquer objeto que possa existir no `Ambiente` (Robôs, Obstáculos, Comunicador). Define métodos para posição, tipo, descrição, movimento e dimensões.
    * Implementada por: `A_Robo`, `Obstaculo`, `ComunicadorCentral`.
* **`I_Aprimoravel`**: Define o comportamento de entidades que podem ser melhoradas.
    * Implementada por: `RoboLimpador`, `RoboBombeiro`.
* **`I_Comunicavel`**: Define a capacidade de enviar e receber mensagens.
    * Implementada por: `RoboBombeiro`, `ComunicadorCentral`.
* **`I_FogoZero`**: Define as ações de um robô bombeiro para lidar com fogo.
    * Implementada por: `RoboBombeiro`.
* **`I_Sensoreavel`**: Define a capacidade de usar sensores.
    * Implementada por: `RoboLimpador`.
* **`I_SujeiraZero`**: Define as ações de um robô limpador.
    * Implementada por: `RoboLimpador`.

## Exceções Personalizadas

* **`ErrorAbastecimentoException`**: Lançada por `RoboBombeiro` ao tentar adicionar água de forma inadequada.
* **`ErrorApagarFogoException`**: Lançada por `RoboBombeiro` ao falhar em apagar fogo.
* **`ErrorAprimoramentoException`**: Lançada por `RoboLimpador` e `RoboBombeiro` ao falhar no aprimoramento.
* **`ErroComunicacaoException`**: Lançada por `ComunicadorCentral` em falhas de comunicação.
* **`ErrorLimpezaException`**: Lançada por `RoboLimpador` em falhas de limpeza.
* **`EntidadeNaoEncontradaException`**: Lançada por `Ambiente` ao não encontrar uma entidade.
* **`ForaDosLimitesException`**: Lançada por `Ambiente` para ações fora do mapa.
* **`LocalOcupadoException`**: Lançada por `Ambiente` para ações em locais já ocupados.
* **`NaoPodeVoarException`**: Lançada por `Ambiente` quando um robô não aéreo tenta voar.
* **`RoboDesligadoException`**: Lançada por `Robo` ou subclasses ao tentar ações enquanto desligado.

## Compilação e Execução

### COMPILAÇÃO:
    javac LAB04/Code/**/*.java LAB04/Code/Exceptions/*.java LAB04/Code/Interfaces/*.java LAB04/Code/AbstractClasses/*.java -d LAB04/Classes

### PARA RODAR:
    java -cp LAB04/Classes LAB04.Code.Main
