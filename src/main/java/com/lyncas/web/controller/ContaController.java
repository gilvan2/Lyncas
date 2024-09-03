package com.lyncas.web.controller;

import com.lyncas.domain.entity.Conta;
import com.lyncas.domain.entity.Situacao;
import com.lyncas.domain.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<Conta> create(@RequestBody Conta conta) {
        Conta savedConta = contaService.save(conta);
        return new ResponseEntity<>(savedConta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conta> update(@PathVariable Long id, @RequestBody Conta conta) {
        if (!contaService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        conta.setId(id);
        Conta updatedConta = contaService.save(conta);
        return new ResponseEntity<>(updatedConta, HttpStatus.OK);
    }

    @PatchMapping("/{id}/situacao")
    public ResponseEntity<Conta> updateSituacao(@PathVariable Long id, @RequestParam String situacao) {
        Optional<Conta> contaOptional = contaService.findById(id);
        if (contaOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Situacao novaSituacao;
        try {
            novaSituacao = Situacao.valueOf(situacao.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Conta conta = contaOptional.get();
        conta.setSituacao(novaSituacao);
        contaService.save(conta);
        return new ResponseEntity<>(conta, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Conta>> list(Pageable pageable,
                                            @RequestParam(required = false) String descricao,
                                            @RequestParam(required = false) String dataVencimento) {
        // Implementar filtros aqui
        Page<Conta> contas = contaService.findAll(pageable);
        return new ResponseEntity<>(contas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> getById(@PathVariable Long id) {
        Optional<Conta> conta = contaService.findById(id);
        if (conta.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(conta.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!contaService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        contaService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
