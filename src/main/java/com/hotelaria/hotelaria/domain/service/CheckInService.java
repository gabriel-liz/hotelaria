package com.hotelaria.hotelaria.domain.service;

import com.hotelaria.hotelaria.domain.exception.CheckInNaoEncontradoException;
import com.hotelaria.hotelaria.domain.exception.NegocioException;
import com.hotelaria.hotelaria.domain.model.CheckIn;
import com.hotelaria.hotelaria.domain.model.Hospede;
import com.hotelaria.hotelaria.domain.model.Pagamento;
import com.hotelaria.hotelaria.domain.repository.CheckInRepository;
import com.hotelaria.hotelaria.domain.repository.HospedeRepository;
import com.hotelaria.hotelaria.domain.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Service
public class CheckInService {

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private HospedeRepository hospedeRepository;

    @Autowired
    private HospedeService hospedeService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private CalculoHospedagemService calculoHospedagemService;

    @Transactional
    public CheckIn salvar(CheckIn checkIn) {
        Hospede hospede = checkIn.getHospede();
        String nome = hospede.getNome();
        String documento = hospede.getDocumento();
        String telefone = hospede.getTelefone();
        boolean dadosInformados = StringUtils.hasLength(nome) ||
                StringUtils.hasLength(documento) ||
                StringUtils.hasLength(telefone);
        if (!dadosInformados) {
            throw new NegocioException("É obrigatório informar o Nome, Documento ou Telefone do hóspede para realizar o check-in.");
        }
        Hospede hospedeFinal = obterOuCriarHospede(nome, documento, telefone);
        checkIn.setHospede(hospedeFinal);
        checkIn.checkIn();
        return checkInRepository.save(checkIn);
    }

    private Hospede obterOuCriarHospede(String nome, String documento, String telefone) {
        Hospede hospedeEncontrado = Stream.<Supplier<Hospede>>of(
                        () -> buscarPorCampo(documento, hospedeRepository::findByDocumento),
                        () -> buscarPorCampo(telefone, hospedeRepository::findByTelefone),
                        () -> buscarPorNome(nome)
                )
                .map(Supplier::get)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        if (hospedeEncontrado != null) {
            return hospedeEncontrado;
        }
        boolean possuiTodosDados = StringUtils.hasLength(nome)
                && StringUtils.hasLength(documento)
                && StringUtils.hasLength(telefone);
        if (!possuiTodosDados) {
            throw new NegocioException("Hóspede não encontrado com os critérios fornecidos. Informe Nome, Documento e Telefone para novo cadastro.");
        }
        Hospede novoHospede = new Hospede();
        novoHospede.setNome(nome);
        novoHospede.setDocumento(documento);
        novoHospede.setTelefone(telefone);
        return hospedeService.salvar(novoHospede);
    }

    private Hospede buscarPorCampo(String valor, Function<String, Optional<Hospede>> funcaoBusca) {
        if (!StringUtils.hasLength(valor)) {
            return null;
        }
        return funcaoBusca.apply(valor).orElse(null);
    }

    private Hospede buscarPorNome(String nome) {
        if (!StringUtils.hasLength(nome)) {
            return null;
        }
        List<Hospede> encontrados = hospedeRepository.findByNomeContainingIgnoreCase(nome);
        if (encontrados.size() == 1) {
            return encontrados.get(0);
        } else if (encontrados.size() > 1) {
            throw new NegocioException("Múltiplos hóspedes encontrados pelo nome. Por favor, forneça o Documento ou Telefone.");
        }
        return null;
    }

    public CheckIn buscarOuFalhar(Long checkInId) {
        return checkInRepository.findById(checkInId)
                .orElseThrow(() -> new CheckInNaoEncontradoException(checkInId));
    }

    @Transactional
    public void checkOut(Long checkInId) {
        CheckIn checkIn = buscarOuFalhar(checkInId);
        if (checkIn.getDataSaida() != null) {
            throw new NegocioException(
                    String.format("O Check-out já foi finalizado."));
        }
        checkIn.checkOut();
        BigDecimal valorDevido = calculoHospedagemService.calcularValor(checkIn);
        Pagamento pagamento = checkIn.gerarPagamento(valorDevido);
        pagamentoRepository.save(pagamento);
        checkInRepository.save(checkIn);
    }
}
