package br.com.alurafood.pagamentos.repository;

import br.com.alurafood.pagamentos.model.PagamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<PagamentoModel, Long> {
}
