package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleSummaryProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, obj.date, obj.amount, obj.seller.name) "
    + "FROM Sale obj WHERE obj.date BETWEEN :min AND :max AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))"
                , countQuery = "SELECT COUNT(obj) FROM Sale obj")
    Page<SaleMinDTO> getSalesReport(LocalDate min, LocalDate max, String name, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT tb_seller.name, SUM(tb_sales.amount) AS amount FROM tb_seller INNER JOIN tb_sales ON tb_seller.id = tb_sales.seller_id " + 
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " + 
            "GROUP BY tb_seller.name")
    Page<SaleSummaryProjection> getSaleSummary(LocalDate minDate, LocalDate maxDate, Pageable pageable);
}
