package com.algaworks.brewer.repository.helper.cerveja;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.dto.CervejaDTO;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.CervejaFilter;

public interface CervejasQueries {

    Page<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable);

    List<CervejaDTO> listarPorSkuOuNome(String skuOuNome);

}
