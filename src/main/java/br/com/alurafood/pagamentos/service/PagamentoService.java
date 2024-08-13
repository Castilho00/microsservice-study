package br.com.alurafood.pagamentos.service;

import br.com.alurafood.pagamentos.dto.PagamentoDto;
import br.com.alurafood.pagamentos.model.PagamentoModel;
import br.com.alurafood.pagamentos.model.Status;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ModelMapper modelMapper;

    public Page<PagamentoDto> findAll(Pageable pageable) {
        return pagamentoRepository
                .findAll(pageable)
                .map(p -> modelMapper.map(p, PagamentoDto.class));
    }

    public PagamentoDto findById(Long id) {
        PagamentoModel pagamentoModel = pagamentoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(pagamentoModel, PagamentoDto.class);
    }

    public PagamentoDto create(PagamentoDto pagamentoDto) {
        PagamentoModel pagamentoModel = modelMapper.map(pagamentoDto, PagamentoModel.class);
        pagamentoModel.setStatus(Status.CRIADO);
        pagamentoRepository.save(pagamentoModel);

        return modelMapper.map(pagamentoModel, PagamentoDto.class);
    }

    public PagamentoDto update(Long id, PagamentoDto objDto) {
        PagamentoModel pagamentoModel = modelMapper.map(objDto, PagamentoModel.class);
        pagamentoModel.setId(id);
        pagamentoModel = pagamentoRepository.save(pagamentoModel);
        return modelMapper.map(pagamentoModel, PagamentoDto.class);
    }

    public void delete(Long id) {
        pagamentoRepository.deleteById(id);
    }

}
