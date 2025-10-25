package com.hotelaria.hotelaria.service;

import com.hotelaria.hotelaria.domain.exception.HospedeNaoEncontradoException;
import com.hotelaria.hotelaria.domain.exception.NegocioException;
import com.hotelaria.hotelaria.domain.model.Hospede;
import com.hotelaria.hotelaria.domain.repository.HospedeRepository;
import com.hotelaria.hotelaria.domain.service.HospedeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HospedeServiceTest {

    @InjectMocks
    private HospedeService hospedeService;

    @Mock
    private HospedeRepository hospedeRepository;

    private Hospede hospedeMock;

    @BeforeEach
    void setUp() {
        hospedeMock = new Hospede();
        hospedeMock.setId(1L);
        hospedeMock.setNome("João da Silva");
    }

    @Test
    @DisplayName("Deve retornar Hospede quando ID existe")
    void buscarOuFalhar_DeveRetornarHospede_QuandoIdExiste() {
        when(hospedeRepository.findById(1L)).thenReturn(Optional.of(hospedeMock));
        Hospede hospedeEncontrado = hospedeService.buscarOuFalhar(1L);
        assertThat(hospedeEncontrado).isNotNull();
        assertThat(hospedeEncontrado.getId()).isEqualTo(1L);
        assertThat(hospedeEncontrado.getNome()).isEqualTo("João da Silva");
    }

    @Test
    @DisplayName("Deve lançar HospedeNaoEncontradoException quando ID não existe")
    void buscarOuFalhar_DeveLancarExcecao_QuandoIdNaoExiste() {
        when(hospedeRepository.findById(99L)).thenReturn(Optional.empty());
        HospedeNaoEncontradoException exception = assertThrows(
                HospedeNaoEncontradoException.class,
                () -> hospedeService.buscarOuFalhar(99L)
        );
        assertThat(exception.getMessage()).contains("Não existe cadastro de hóspede com código 99");
    }

    @Test
    @DisplayName("Deve excluir hospede com sucesso quando ID existe")
    void excluir_DeveDeletarHospede_QuandoIdExiste() {
        when(hospedeRepository.findById(1L)).thenReturn(Optional.of(hospedeMock));
        hospedeService.excluir(1L);
        verify(hospedeRepository, times(1)).deleteById(1L);
        verify(hospedeRepository, times(1)).flush();
    }

    @Test
    @DisplayName("Deve lançar HospedeNaoEncontradoException ao tentar excluir ID inexistente")
    void excluir_DeveLancarExcecao_QuandoIdNaoExiste() {
        when(hospedeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(
                HospedeNaoEncontradoException.class,
                () -> hospedeService.excluir(99L)
        );
        verify(hospedeRepository, never()).deleteById(anyLong());
        verify(hospedeRepository, never()).flush();
    }

    @Test
    @DisplayName("Deve lançar NegocioException ao excluir hospede com dependências")
    void excluir_DeveLancarNegocioException_QuandoHospedeTemDependencias() {
        when(hospedeRepository.findById(1L)).thenReturn(Optional.of(hospedeMock));
        doThrow(DataIntegrityViolationException.class).when(hospedeRepository).deleteById(1L);
        NegocioException exception = assertThrows(
                NegocioException.class,
                () -> hospedeService.excluir(1L)
        );
        assertThat(exception.getMessage()).contains("Hóspede com código 1 não pode ser removido");
    }
}