package com.daniele.bank.nfeprocessservice;

import com.daniele.bank.nfeprocessservice.enums.Status;
import com.daniele.bank.nfeprocessservice.model.NotaFiscal;
import com.daniele.bank.nfeprocessservice.repository.NfeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

@Component
@Log4j2
public class NfeBatch {

    @Autowired
    private NfeRepository nfeRepository;

    @Value("${file.input.path}")
    private String inputPath;

    @Value("${file.output.path}")
    private String outputPath;

    @Value("${file.error.path}")
    private String errorPath;

    @Scheduled(fixedRate = 120000)
    @Async
    public void main() {
        Set<String> xmlFiles = listFiles();
        List<NotaFiscal> notaFiscalList = new ArrayList<>();

        if (nonNull(xmlFiles)) {
            xmlFiles.forEach(xmlFile -> {
                NotaFiscal nfe = new NotaFiscal();
                nfe.setStatusId(Status.EM_PROCESSAMENTO.getStatusId());
                nfe.setArquivoId(xmlFile);

                NotaFiscal existsNfe = nfeRepository.findByArquivoId(xmlFile);

                try {
                    nfe = convertXmlToObject(xmlFile);

                    if (nonNull(existsNfe)) {
                        buildExistsNfe(existsNfe, nfe);
                        notaFiscalList.add(existsNfe);
                        saveOutputFile(existsNfe);
                    } else {
                        notaFiscalList.add(nfe);
                        saveOutputFile(nfe);
                    }

                    log.info("Saved output file with success: {}", xmlFile);
                } catch (Exception e) {
                    if (nonNull(existsNfe)) {
                        saveErrorFile(existsNfe);
                        notaFiscalList.add(existsNfe);
                    } else {
                        saveErrorFile(nfe);
                        nfe.setArquivoId(xmlFile);
                        notaFiscalList.add(nfe);
                    }

                    log.error("Process error: {}", xmlFile);
                    log.error(e.getMessage() == null ? e.toString() : e.getMessage());
                }
            });

            nfeRepository.saveAll(notaFiscalList);

        }
    }

    private NotaFiscal convertXmlToObject(String file) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(NotaFiscal.class);
        FileReader xmlFileReader = new FileReader(getCompleteFilePath(inputPath, file));
        return (NotaFiscal) context.createUnmarshaller().unmarshal(xmlFileReader);
    }

    private Set<String> listFiles() {
        try (Stream<Path> stream = Files.list(Paths.get(inputPath))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            log.error(e.getMessage() == null ? e.toString() : e.getMessage());
        }

        return null;
    }

    private void saveOutputFile(NotaFiscal nfe) {
        try {
            handleFileSave(nfe.getArquivoId(), outputPath);
            nfe.setStatusId(Status.PROCESSADA.getStatusId());
        } catch (IOException e) {
            log.error(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    private void saveErrorFile(NotaFiscal nfe) {
        try {
            handleFileSave(nfe.getArquivoId(), errorPath);
            nfe.setStatusId(Status.PROCESSADA_COM_ERRO.getStatusId());
        } catch (IOException e) {
            log.error(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    private void handleFileSave(String xmlFile, String path) throws IOException {
        Path copiedXmlFile = Paths.get(getCompleteFilePath(path, xmlFile));
        Path originalXmlFile = Paths.get(getCompleteFilePath(inputPath, xmlFile));

        Files.copy(originalXmlFile, copiedXmlFile, StandardCopyOption.REPLACE_EXISTING);

        deleteFileProcessed(xmlFile);
    }

    private String getCompleteFilePath(String path, String fileName) {
        return path.concat(FileSystems.getDefault().getSeparator()).concat(fileName);
    }

    private void deleteFileProcessed(String file) throws IOException {
        Files.deleteIfExists(Paths.get(getCompleteFilePath(inputPath, file)));
    }

    private void buildExistsNfe(NotaFiscal existsNfe, NotaFiscal nfe) {
        existsNfe.setChave(nfe.getChave());
        existsNfe.setDhRegistro(nfe.getDhRegistro());
        existsNfe.setDuplicatas(nfe.getDuplicatas());
        existsNfe.setNomeDestinatario(nfe.getNomeDestinatario());
        existsNfe.setNomeEmitente(nfe.getNomeEmitente());
        existsNfe.setValorNota(nfe.getValorNota());
        existsNfe.setNumero(nfe.getNumero());
    }
}
