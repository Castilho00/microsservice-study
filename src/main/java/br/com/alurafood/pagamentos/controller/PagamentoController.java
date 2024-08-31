package br.com.alurafood.pagamentos.controller;

import br.com.alurafood.pagamentos.dto.PagamentoDto;
import br.com.alurafood.pagamentos.service.PagamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/pagamento")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @GetMapping
    public Page<PagamentoDto> findAll(@PageableDefault(size = 10) Pageable pageable) {
        return pagamentoService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> findById(@PathVariable @NotNull Long id) {
        PagamentoDto pagamentoDto = pagamentoService.findById(id);
        return ResponseEntity.ok(pagamentoDto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> save(@RequestBody @Valid PagamentoDto dto, UriComponentsBuilder uriComponentsBuilder) {
        PagamentoDto pagamentoDto = pagamentoService.create(dto);
        URI endereco = uriComponentsBuilder.path("/pagamentos/{id}").buildAndExpand(pagamentoDto.getId()).toUri();
        return ResponseEntity.created(endereco).body(pagamentoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDto> update(@PathVariable @Valid Long id, @RequestBody @Valid PagamentoDto pagamentoDto) {
        PagamentoDto atualizado = pagamentoService.update(id, pagamentoDto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDto> delete(@PathVariable @NotNull Long id) {
        pagamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmPayment(@PathVariable Long id) {
        pagamentoService.confirmPayment(id);
    }

}
