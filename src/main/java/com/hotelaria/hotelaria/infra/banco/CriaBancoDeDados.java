package com.hotelaria.hotelaria.infra.banco;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment; // Importante!
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class CriaBancoDeDados implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;
    private final String databaseName; // Agora é 'final' e inicializado no construtor

    // O nome da propriedade que você está usando
    private static final String SCHEMA_PROPERTY = "spring.jpa.properties.hibernate.default_schema";

    // ----------------------------------------------------------------------
    // NOVO CONSTRUTOR
    // ----------------------------------------------------------------------
    public CriaBancoDeDados(DataSource dataSource, Environment env) {
        // Inicializa o JdbcTemplate
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        // Obtém a propriedade do Environment, usando "hotel" como valor padrão
        this.databaseName = env.getProperty(SCHEMA_PROPERTY, "hotel");

        // Log para garantir que você pegou o nome certo
        System.out.println("Configurando banco de dados alvo: " + this.databaseName);
    }
    // ----------------------------------------------------------------------

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createDatabaseIfNotExist();
    }

    // O resto do método createDatabaseIfNotExist() permanece o mesmo.
    private void createDatabaseIfNotExist() {
        // ... sua lógica original ...
        String sql = "SELECT 1 FROM pg_database WHERE datname = ?";
        try {
            // Verifica se o banco de dados existe
            boolean exists = false;
            try {
                Integer count = jdbcTemplate.queryForObject(sql, Integer.class, databaseName);
                if (count != null && count > 0) {
                    exists = true;
                }
            } catch (Exception e) {
                // ...
            }

            if (!exists) {
                try {
                    // Se não existir, tenta criar
                    jdbcTemplate.execute("CREATE DATABASE " + databaseName);
                    System.out.println("Banco de dados '" + databaseName + "' criado com sucesso!");
                } catch (org.springframework.dao.DataAccessException dataAccessException) {
                    // Captura a exceção caso o banco já exista (e ignora)
                    if (dataAccessException.getMessage().contains("already exists")) {
                        System.out.println("Banco de dados '" + databaseName + "' já existe (exceção ignorada).");
                    } else {
                        throw dataAccessException;
                    }
                }
            } else {
                System.out.println("Banco de dados '" + databaseName + "' já existe (verificação inicial).");
            }
        } catch (Exception e) {
            System.err.println("Erro fatal na inicialização do banco de dados: " + e.getMessage());
        }
    }
}