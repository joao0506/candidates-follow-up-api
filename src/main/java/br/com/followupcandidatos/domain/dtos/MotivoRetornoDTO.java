package br.com.followupcandidatos.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MotivoRetornoDTO {
    private Integer id;

    @NotBlank(message = "Descrição é obrigatório!")
    @Size(min = 2, max = 60, message = "Descrição deve conter entre 2 e 60 caracteres!")
    private String descricao;

    private Boolean isAtivo;
}
