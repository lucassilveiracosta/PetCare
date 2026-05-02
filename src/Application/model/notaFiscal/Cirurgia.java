package model.notaFiscal;
import model.animal.Animal;

import java.time.LocalDateTime;

public class Cirurgia extends Procedimento{

    public Cirurgia(Double preco, Animal paciente, LocalDateTime dataHora, String descricao) {
        super(preco, paciente, dataHora, descricao);

    }
}
