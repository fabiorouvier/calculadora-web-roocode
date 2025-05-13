package com.frouvier.backend.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.frouvier.backend.BackendApplication;

/**
 * Teste para verificar se o contexto da aplicação é carregado corretamente.
 * Este teste garante que a configuração básica do Spring Boot está funcionando.
 */
@SpringBootTest
class BackendApplicationTest {

    /**
     * Teste que verifica se o contexto da aplicação é carregado com sucesso.
     * Se o contexto for carregado sem erros, o teste passa.
     */
    @Test
    void contextLoads() {
        // Este teste verifica apenas se o contexto do Spring é carregado corretamente
        // Não é necessário adicionar assertions aqui
    }
    
    /**
     * Teste para verificar se a classe principal pode ser instanciada.
     * Este é um teste simples para garantir que a classe principal não tem problemas de inicialização.
     */
    @Test
    void applicationCanBeInstantiated() {
        // Arrange & Act
        BackendApplication application = new BackendApplication();
        
        // Assert - se não houver exceção, o teste passa
        // Não é necessário adicionar assertions aqui
    }
}