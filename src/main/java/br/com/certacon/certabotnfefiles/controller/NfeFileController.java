package br.com.certacon.certabotnfefiles.controller;

import br.com.certacon.certabotnfefiles.exception.MessageExceptionHandler;
import br.com.certacon.certabotnfefiles.models.NfeFileModel;
import br.com.certacon.certabotnfefiles.repositories.NfeFileRepository;
import br.com.certacon.certabotnfefiles.services.NfeFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("nfeFile")
public class NfeFileController {
    private final NfeFileService fileService;
    private final NfeFileRepository nfeFileRepository;

    public NfeFileController(NfeFileService fileService, NfeFileRepository nfeFileRepository) {
        this.fileService = fileService;
        this.nfeFileRepository = nfeFileRepository;
    }

    @PostMapping
    @Operation(description = "Cria a entidade do Arquivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo criado!", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = NfeFileModel.class))}),
            @ApiResponse(responseCode = "400", description = "Informação inserida esta errada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageExceptionHandler.class))}),
            @ApiResponse(responseCode = "500", description = "Erro no servidor", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageExceptionHandler.class))})
    })
    public ResponseEntity<NfeFileModel> create(@RequestBody NfeFileModel entity) {
        NfeFileModel bot = fileService.saveOrUpdate(entity);
        return ResponseEntity.status(HttpStatus.OK).body(bot);
    }


    @GetMapping
    @Operation(description = "Busca todos Arquivos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivos(s) encontrado(s)!", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = NfeFileModel.class))}),
            @ApiResponse(responseCode = "400", description = "Informação inserida está errada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageExceptionHandler.class))}),
            @ApiResponse(responseCode = "404", description = "Arquivos(s) não encontrado(s)", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageExceptionHandler.class))}),
            @ApiResponse(responseCode = "500", description = "Erro no servidor", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageExceptionHandler.class))})
    })
    public ResponseEntity<List<NfeFileModel>> getAll() {
        try {
            List<NfeFileModel> botList = nfeFileRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(botList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping
    @Operation(description = "Atualiza um Arquivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo atualizado!", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = NfeFileModel.class))}),
            @ApiResponse(responseCode = "400", description = "Informação inserida esta errada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageExceptionHandler.class))}),
            @ApiResponse(responseCode = "404", description = "Arquivo não foi encontrado", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageExceptionHandler.class))}),
            @ApiResponse(responseCode = "500", description = "Erro no servidor", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageExceptionHandler.class))})
    })
    public ResponseEntity<NfeFileModel> update(@RequestBody NfeFileModel entity) throws Exception {
        NfeFileModel response = fileService.saveOrUpdate(entity);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deleta um Arquivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo deletado!", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Informação inserida esta errada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageExceptionHandler.class))}),
            @ApiResponse(responseCode = "404", description = "Arquivo não foi encontrado", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageExceptionHandler.class))}),
            @ApiResponse(responseCode = "500", description = "Erro no servidor", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageExceptionHandler.class))})
    })
    public ResponseEntity delete(@PathVariable(value = "id") UUID id) throws FileNotFoundException {
        boolean entity = fileService.deleteFile(id);
        if (entity == Boolean.FALSE) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquivo não foi encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Deletado com Sucesso");
    }

}
