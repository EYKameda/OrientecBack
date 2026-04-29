package com.teste.banco.service;

import com.teste.banco.dto.FuncionarioDTO;
import com.teste.banco.dto.LoginRequest;
import com.teste.banco.dto.LoginResponse;
import com.teste.banco.model.Funcionario;
import com.teste.banco.repository.FuncionarioRepository;
import com.teste.banco.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuditService auditService;

    /**
     * Realiza login
     */
    public LoginResponse login(LoginRequest request, HttpServletRequest httpRequest) throws Exception {
        Funcionario funcionario = funcionarioRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new Exception("Funcionário não encontrado"));

        // Verificar se está ativo
        if (!funcionario.getAtivo()) {
            throw new Exception("Funcionário inativo");
        }

        // Validar senha
        if (!passwordEncoder.matches(request.getSenha(), funcionario.getSenha())) {
            throw new Exception("Senha incorreta");
        }

        // Log login
        auditService.logLogin(funcionario.getLogin(), httpRequest);

        // Gerar token
        String token = jwtUtil.generateToken(funcionario.getLogin());

        // Preparar resposta
        FuncionarioDTO dto = converterParaDTO(funcionario);
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setExpiresIn(jwtUtil.getExpirationTime() / 1000); // em segundos
        response.setFuncionario(dto);

        return response;
    }

    /**
     * Converte entidade para DTO
     */
    private FuncionarioDTO converterParaDTO(Funcionario funcionario) {
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setCodigo(funcionario.getCodigo());
        dto.setLogin(funcionario.getLogin());
        dto.setNome(funcionario.getNome());
        dto.setApelido(funcionario.getApelido());
        dto.setNivel(funcionario.getNivel());
        dto.setFuncao(funcionario.getFuncao());
        dto.setSede(funcionario.getSede());
        dto.setCartao(funcionario.getCartao());
        dto.setAtivo(funcionario.getAtivo());
        dto.setModuloBackup(funcionario.getModuloBackup());
        dto.setModuloCadastros(funcionario.getModuloCadastros());
        dto.setModuloCaixa(funcionario.getModuloCaixa());
        dto.setModuloEstoque(funcionario.getModuloEstoque());
        dto.setModuloFinanceiro(funcionario.getModuloFinanceiro());
        dto.setModuloGerencial(funcionario.getModuloGerencial());
        dto.setModuloOs(funcionario.getModuloOs());
        dto.setModuloPonto(funcionario.getModuloPonto());
        dto.setModuloTabelas(funcionario.getModuloTabelas());
        dto.setModuloVendas(funcionario.getModuloVendas());
        return dto;
    }
}
