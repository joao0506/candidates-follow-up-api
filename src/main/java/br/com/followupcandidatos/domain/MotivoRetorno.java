package br.com.followupcandidatos.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "motivo_de_retorno")
public class MotivoRetorno {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "INCREMENT", strategy = "INCREMENT")
    @Column(columnDefinition = "SERIAL")
    private Integer id;

    @Column(length = 60)
    private String descricao;

    private Boolean isAtivo;

    public MotivoRetorno(Integer id, String descricao, Boolean isAtivo){
        this.id = id;
        this.descricao = descricao;
        this.isAtivo = isAtivo;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ",\"descricao\":\"" + descricao + "\"" +
                ",\"isAtivo\":" + isAtivo +
                '}';
    }
}
