package com.gm2.pdv.demo.Controller;

import com.gm2.pdv.demo.DTO.SaleDTO;
import com.gm2.pdv.demo.Service.SaleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/sale")
public class SaleController {


    private SaleService saleService;

    @GetMapping()
    public ResponseEntity getAll() {
        return new ResponseEntity<>(saleService.findaAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable long id){
        return new ResponseEntity<>(saleService.getById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SaleDTO saleDTO) {
        try {
            long id = saleService.save(saleDTO);
            return new ResponseEntity<>("venda realizada com sucesso"+ id, HttpStatus.CREATED);

        } catch(Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
