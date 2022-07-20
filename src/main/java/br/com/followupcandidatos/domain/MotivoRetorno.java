package br.com.followupcandidatos.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "motivo_de_retorno")
public class MotivoRetorno {
    @Id
    private Integer id;
    @Column(length = 60)
    private String descricao;
    private Boolean isAtivo;

    public MotivoRetorno(Integer id, String descricao, Boolean isAtivo){
        this.id = id;
        this.descricao = descricao;
        this.isAtivo = isAtivo;
    }
}
