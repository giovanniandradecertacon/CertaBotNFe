package br.com.certacon.certabotnfefiles.controller;

import br.com.certacon.certabotnfefiles.dto.ProcessFileDto;
import br.com.certacon.certabotnfefiles.exception.MessageExceptionHandler;
import br.com.certacon.certabotnfefiles.models.ProcessFileModel;
import br.com.certacon.certabotnfefiles.repositories.ProcessFileRepository;
import br.com.certacon.certabotnfefiles.services.ProcessNfeFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("processFile")
public class ProcessFileController {

    private final ProcessNfeFileService processNfeFileService;
    private final ProcessFileRepository processFileRepository;

    public ProcessFileController(ProcessNfeFileService processNfeFileService, ProcessFileRepository processFileRepository) {
        this.processNfeFileService = processNfeFileService;
        this.processFileRepository = processFileRepository;
    }

    @PostMapping
    @Operation(description = "Cria a entidade do Processo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processo criado!", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProcessFileModel.class))}),
            @ApiResponse(responseCode = "400", description = "Informação inserida esta errada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageExceptionHandler.class))}),
            @ApiResponse(responseCode = "500", description = "Erro no servidor", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageExceptionHandler.class))})
    })
    public ResponseEntity<ProcessFileModel> create(@RequestBody ProcessFileDto entityDTO) {
        ProcessFileModel bot = processNfeFileService.createFile(entityDTO);
        return ResponseEntity.status(HttpStatus.OK).body(bot);
    }


    @GetMapping
    @Operation(description = "Busca todos Processos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processo(s) encontrado(s)!", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProcessFileModel.class))}),
            @ApiResponse(responseCode = "400", description = "Informação inserida está errada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageExceptionHandler.class))}),
            @ApiResponse(responseCode = "404", description = "Processo(s) não encontrado(s)", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageExceptionHandler.class))}),
            @ApiResponse(responseCode = "500", description = "Erro no servidor", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageExceptionHandler.class))})
    })
    public ResponseEntity<List<ProcessFileModel>> getAll() {
        try {
            List<ProcessFileModel> botList = processFileRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(botList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping
    @Operation(description = "Atualiza um Processo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processo atualizado!", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProcessFileModel.class))}),
            @ApiResponse(responseCode = "400", description = "Informação inserida esta errada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageExceptionHandler.class))}),
            @ApiResponse(responseCode = "404", description = "Processo não foi encontrado", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageExceptionHandler.class))}),
            @ApiResponse(responseCode = "500", description = "Erro no servidor", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageExceptionHandler.class))})
    })
    public ResponseEntity<ProcessFileModel> update(@RequestBody ProcessFileDto entityDTO) throws Exception {
        ProcessFileModel response = processNfeFileService.updateFile(entityDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deleta um Processo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processo deletado!", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Informação inserida esta errada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageExceptionHandler.class))}),
            @ApiResponse(responseCode = "404", description = "Processo não foi encontrado", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageExceptionHandler.class))}),
            @ApiResponse(responseCode = "500", description = "Erro no servidor", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageExceptionHandler.class))})
    })
    public ResponseEntity delete(@PathVariable(value = "id") UUID id) {
        boolean entity = processNfeFileService.deleteFile(id);
        if (entity == Boolean.FALSE) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Processo não foi encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Deletado com Sucesso");
    }

}
