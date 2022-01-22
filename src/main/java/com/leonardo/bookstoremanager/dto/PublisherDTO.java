package com.leonardo.bookstoremanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {

    private Long id;

    @NotBlank(message = "Informe o campo: nome")
    @Size(max = 255)
    private String name;

    @NotBlank(message = "Informe o campo: code")
    @Size(max = 50)
    private String code;

    @NotNull(message = "Informe o campo: foundationDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate foundationDate;
}
