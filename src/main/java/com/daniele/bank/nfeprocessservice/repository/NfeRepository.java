package com.daniele.bank.nfeprocessservice.repository;

import com.daniele.bank.nfeprocessservice.model.NotaFiscal;
import org.springframework.data.repository.CrudRepository;

public interface NfeRepository extends CrudRepository<NotaFiscal, Integer> {
    NotaFiscal findByArquivoId(String arquivoId);
}
