package com.teste.banco.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teste.banco.dto.ClienteDTO;
import com.teste.banco.dto.ContaDTO;
import com.teste.banco.exception.ContaNotFoundException;
import com.teste.banco.mapper.ClienteMapper;
import com.teste.banco.mapper.ContaMapper;
import com.teste.banco.model.Cliente;
import com.teste.banco.model.Conta;
import com.teste.banco.repository.ContaRepository;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteService clienteService;

    public ContaService(ContaRepository contaRepository, ClienteService clienteService) {
        this.contaRepository = contaRepository;
        this.clienteService = clienteService;
    }

    public ContaDTO findById(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ContaNotFoundException("Conta não encontrada com ID: " + id));
        return ContaMapper.toDTO(conta);
    }

    public List<ContaDTO> findAll() {
        List<Conta> contas = contaRepository.findAll();
        if (contas.isEmpty()) {
            throw new ContaNotFoundException("Nenhuma Conta encontrada");
        }
        return contas.stream()
                .map(ContaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ContaDTO criarConta1(Long idCliente, Long numeroConta) {        
        try {
        validarParametros(idCliente, numeroConta);
        Cliente cliente = obterCliente(idCliente);
        Conta conta = newConta(numeroConta,cliente);
            contaRepository.save(conta);
            return ContaMapper.toDTO(conta);
        } catch (Exception e) {
            throw new ContaNotFoundException(e.getMessage());
        }
    }
    
    private Conta newConta(Long numeroConta, Cliente cliente) {
        Conta conta = new Conta();
        conta.setContaNumero(numeroConta);
        cliente.addConta(conta);
        return conta;
    }

    private Cliente obterCliente(Long idCliente) {
        return clienteService.findById(idCliente);
    }

    private void validarParametros(Long idCliente, Long numeroConta) {
        if (numeroConta == null || numeroConta == 0L) {
            throw new IllegalArgumentException("Número da conta inválido");
        }
        validarNumeroContaExiste(numeroConta);
        if (idCliente == null || idCliente == 0L) {
            throw new IllegalArgumentException("Número do cliente inválido");
        }
    }
    
    private void validarNumeroContaExiste(Long numeroConta){
        Conta conta = contaRepository.findByContaNumero(numeroConta);
        if(conta != null) {
            throw new IllegalArgumentException("Já Existe uma Conta Cadastrado com este Número: "+numeroConta+", associe na o cliente á Conta");
        }
    }

    public ContaDTO findByNumero(Long numero) {
        Conta conta = contaRepository.findByContaNumero(numero);
        if (conta == null) {
            throw new ContaNotFoundException("Conta não encontrada com número: " + numero);
        }
        return ContaMapper.toDTO(conta);
    }

    public ClienteDTO getClientIdConta(Long contaId){
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new ContaNotFoundException("Conta não encontrada com ID: " + contaId));
        if (conta.getClientes().isEmpty()){
            throw new IllegalArgumentException("Não existe cliente associado a Conta de ID: "+ contaId);
        }       
        return ClienteMapper.toDTO(conta.getClientes().get(0));
    }
}