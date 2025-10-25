package com.hotelaria.hotelaria.domain.repository;

import com.hotelaria.hotelaria.domain.model.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospedeRepository extends JpaRepository<Hospede, Long> {

    @Query("SELECT DISTINCT h FROM Hospede h " +
            "JOIN h.checkins ci " +
            "WHERE h NOT IN (" +
            "SELECT ciAtivo.hospede FROM CheckIn ciAtivo WHERE ciAtivo.dataSaida IS NULL" +
            ")")
    List<Hospede> findHospedesAtualmenteForaDoHotel();

    @Query("SELECT h FROM Hospede h WHERE h IN " +
            "(SELECT ci.hospede FROM CheckIn ci WHERE ci.dataSaida IS NULL)")
    List<Hospede> findHospedesNoHotel();


    @Query("SELECT h FROM Hospede h " +
            "WHERE (:nome IS NULL OR LOWER(h.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
            "OR (:documento IS NULL OR h.documento = :documento) " +
            "OR (:telefone IS NULL OR h.telefone = :telefone)")
    List<Hospede> findHospedesByDadosCheckIn(
            @Param("nome") String nome,
            @Param("documento") String documento,
            @Param("telefone") String telefone
    );

    Optional<Hospede> findByDocumento(String documento);

    Optional<Hospede> findByTelefone(String telefone);

    List<Hospede> findByNomeContainingIgnoreCase(String nome);

}
