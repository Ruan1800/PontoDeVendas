package com.gm2.pdv.demo.Repository;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemSaleRepository extends JpaRepository<AbstractReadWriteAccess.Item, Long> {
}
