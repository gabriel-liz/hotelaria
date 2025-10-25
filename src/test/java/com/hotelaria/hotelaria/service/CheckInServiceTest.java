package com.hotelaria.hotelaria.service;

import com.hotelaria.hotelaria.domain.exception.NegocioException;
import com.hotelaria.hotelaria.domain.model.CheckIn;
import com.hotelaria.hotelaria.domain.model.Hospede;
import com.hotelaria.hotelaria.domain.model.Pagamento;
import com.hotelaria.hotelaria.domain.repository.CheckInRepository;
import com.hotelaria.hotelaria.domain.repository.HospedeRepository;
import com.hotelaria.hotelaria.domain.repository.PagamentoRepository;
import com.hotelaria.hotelaria.domain.service.CalculoHospedagemService;
import com.hotelaria.hotelaria.domain.service.CheckInService;
import com.hotelaria.hotelaria.domain.service.HospedeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckInServiceTest {

    @InjectMocks
    private CheckInService checkInService;

    @Mock
    private CheckInRepository checkInRepository;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private CalculoHospedagemService calculoHospedagemService;
    @Mock
    private HospedeRepository hospedeRepository;
    @Mock
    private HospedeService hospedeService;
    private CheckIn checkInMock;
    private Hospede hospedeMock;

    @BeforeEach
    void setUp() {
        hospedeMock = new Hospede();
        hospedeMock.setId(1L);
        hospedeMock.setNome("Hospede Teste");

        checkInMock = new CheckIn();
        checkInMock.setId(10L);
        checkInMock.setHospede(hospedeMock);
        checkInMock.setDataSaida(null);
    }

    @Test
    @DisplayName("Deve realizar check-out, calcular valor e criar pagamento")
    void checkOut_DeveFinalizarCheckInECriarPagamento() {
        BigDecimal valorCalculado = new BigDecimal("150.00");
        when(checkInRepository.findById(10L)).thenReturn(Optional.of(checkInMock));

        when(calculoHospedagemService.calcularValor(any(CheckIn.class))).thenReturn(valorCalculado);

        checkInService.checkOut(10L);

        assertThat(checkInMock.getDataSaida()).isNotNull();

        ArgumentCaptor<Pagamento> pagamentoCaptor = ArgumentCaptor.forClass(Pagamento.class);
        verify(pagamentoRepository).save(pagamentoCaptor.capture());

        Pagamento pagamentoSalvo = pagamentoCaptor.getValue();
        assertThat(pagamentoSalvo.getValorPago()).isEqualTo(valorCalculado);
        assertThat(pagamentoSalvo.getHospede()).isEqualTo(hospedeMock);
        assertThat(pagamentoSalvo.getCheckIn()).isEqualTo(checkInMock);

        verify(checkInRepository).save(checkInMock);
    }

    @Test
    @DisplayName("Deve lançar NegocioException ao tentar fazer check-out duplicado")
    void checkOut_DeveLancarExcecao_QuandoCheckOutJaRealizado() {

        checkInMock.setDataSaida(OffsetDateTime.now().minusDays(1));
        when(checkInRepository.findById(10L)).thenReturn(Optional.of(checkInMock));

        NegocioException exception = assertThrows(
                NegocioException.class,
                () -> checkInService.checkOut(10L)
        );

        assertThat(exception.getMessage()).contains("O Check-out já foi finalizado.");

        verify(pagamentoRepository, never()).save(any());
        verify(checkInRepository, never()).save(any());
    }
}