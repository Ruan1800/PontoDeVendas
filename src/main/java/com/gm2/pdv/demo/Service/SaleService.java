package com.gm2.pdv.demo.Service;

import com.gm2.pdv.demo.DTO.ProductDTO;
import com.gm2.pdv.demo.DTO.ProductInfoDTO;
import com.gm2.pdv.demo.DTO.SaleDTO;
import com.gm2.pdv.demo.DTO.SaleinfoDTO;
import com.gm2.pdv.demo.Entity.ItemSale;
import com.gm2.pdv.demo.Entity.Product;
import com.gm2.pdv.demo.Entity.Sale;
import com.gm2.pdv.demo.Entity.User;
import com.gm2.pdv.demo.Exception.NoItemException;
import com.gm2.pdv.demo.Repository.ItemSaleRepository;
import com.gm2.pdv.demo.Repository.ProductRepository;
import com.gm2.pdv.demo.Repository.SaleRepository;
import com.gm2.pdv.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {


    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final ItemSaleRepository itemSaleRepository;

    public List<SaleinfoDTO> findaAll() {
        return saleRepository.findAll().stream().map(sale -> getSaleInfo(sale)).collect(Collectors.toList());
    }

    private SaleinfoDTO getSaleInfo(Sale sale) {
        SaleinfoDTO saleinfoDTO = new SaleinfoDTO();
        saleinfoDTO.setUser(sale.getUser().getName());
        saleinfoDTO.setDate(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        saleinfoDTO.setProduct(getProductInfo(sale.getItems()));

        return saleinfoDTO;
    }

    private List<ProductInfoDTO> getProductInfo(List<ItemSale> items) {
        return  items.stream().map(item -> {
            ProductInfoDTO productInfoDTO = new ProductInfoDTO();
            productInfoDTO.setDescription(item.getProduct().getDescription());
            productInfoDTO.setQuantity((item.getQuantity()));
            return productInfoDTO;
        }).collect(Collectors.toList());
    }

    @Transactional
    public long save(SaleDTO sale) {

        User user = userRepository.findById(sale.getUserid()).get();

        Sale newSale = new Sale();
        newSale.setUser(user);
        newSale.setDate(LocalDate.now());
        List<ItemSale> items = getItemSale(sale.getItens());

         newSale  = saleRepository.save(newSale);

         saveItemSale(items, newSale);

         return newSale.getId();
    }

    private void saveItemSale(List<ItemSale> items, Sale newSale) {
        for (ItemSale item: items) {
                item.setSale(newSale);
                itemSaleRepository.save(item);
        }


    }

    private List<ItemSale> getItemSale(List<ProductDTO> products) {

        return products.stream().map(item -> {
            Product product = productRepository.getReferenceById(item.getProductid());

                ItemSale itemSale = new ItemSale();
                itemSale.setProduct(product);
                itemSale.setQuantity(item.getQuantity());

                if (product.getQuantity() == 0 ) {
                    throw new NoItemException("produto sem estoque.");
                } else if (product.getQuantity() < item.getQuantity()) {
                    throw new NoItemException(String.format("A quantidade de itens da venda (%s)" + "é maior do que a quantidade disponivel no estoque(%s", item.getQuantity(), product.getQuantity()));
                }

            int total = product.getQuantity() - item.getQuantity();
                product.setQuantity(total);
                productRepository.save(product);

                return itemSale;
            }).collect(Collectors.toList());

    }

    public SaleinfoDTO getById(long id) {
      Sale sale = saleRepository.findById(id).get();
      return getSaleInfo(sale);

    }
}
