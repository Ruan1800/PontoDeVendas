package com.gm2.pdv.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleinfoDTO {

    private String user;
    private String date;
    private List<ProductInfoDTO> product;

}
