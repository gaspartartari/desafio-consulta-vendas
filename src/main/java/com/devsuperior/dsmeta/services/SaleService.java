package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> getSalesReport(String minDate, String maxDate, String name, Pageable pageable){
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate max = maxDate.isEmpty() ? LocalDate.now() : LocalDate.parse(maxDate, format);
		LocalDate min = minDate.isEmpty() ? max.minusYears(1L) : LocalDate.parse(minDate, format);
		Page<SaleMinDTO> page = repository.getSalesReport(min, max, name, pageable);
		return page;
	}
}
