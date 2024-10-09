package com.tinkoff.controller.dto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CustomerDTO {

    @NotNull(message = "Should be initialized")
    private Long id;

    @NotNull(message = "Should be initialized")
    private String name;

    @NotNull(message = "Should be initialized")
    private String surname;

    private List<BillDTO> billList;
}
