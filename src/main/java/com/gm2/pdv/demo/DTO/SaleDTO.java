package com.gm2.pdv.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleDTO {

    private long userid;

    List<ProductDTO> itens;


}
