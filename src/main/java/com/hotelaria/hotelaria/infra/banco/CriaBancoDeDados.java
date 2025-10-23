package com.hotelaria.hotelaria.infra.banco;

import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

@Configuration
public class CriaBancoDeDados {

    private final JdbcTemplate jdbcTemplate;
    private final String schemaName;
    private static final String SCHEMA_PROPERTY = "spring.jpa.properties.hibernate.default_schema";

    public CriaBancoDeDados(DataSource dataSource, Environment env) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.schemaName = env.getProperty(SCHEMA_PROPERTY, "hotel");
        System.out.println("Configuração de Schema Alvo: " + this.schemaName);
    }

    // REMOVA: public void run(ApplicationArguments args) throws Exception {...}

    @Bean
    public EntityManagerFactoryBuilderCustomizer customizeEntityManagerFactoryBuilder() {
        // Esta função garante que createSchemaIfNotExist() seja chamado antes
        // da construção da EntityManagerFactory (onde o ddl-auto é executado).
        createSchemaIfNotExist();

        return (builder) -> {
            // Não é necessário adicionar mais configurações, a chamada acima basta.
        };
    }

    private void createSchemaIfNotExist() {
        System.out.println("Iniciando verificação/criação do Schema: " + this.schemaName);
        String sql = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = ?";

        try {
            boolean exists = false;
            try {
                jdbcTemplate.queryForObject(sql, String.class, schemaName);
                exists = true;
            } catch (EmptyResultDataAccessException e) {
                // Schema não encontrado, vamos criar
            }

            if (!exists) {
                try {
                    jdbcTemplate.execute("CREATE SCHEMA " + schemaName);
                    System.out.println("Schema '" + schemaName + "' criado com sucesso! ✅");
                } catch (DataAccessException dataAccessException) {
                    if (dataAccessException.getMessage() != null && dataAccessException.getMessage().contains("already exists")) {
                        System.out.println("Schema '" + schemaName + "' já existe (exceção ignorada).");
                    } else {
                        throw dataAccessException;
                    }
                }
            } else {
                System.out.println("Schema '" + schemaName + "' já existe (verificação inicial).");
            }
        } catch (Exception e) {
            System.err.println("Erro fatal na inicialização do schema: " + e.getMessage());
            throw new RuntimeException("Falha ao configurar o schema PostgreSQL.", e);
        }
    }
}