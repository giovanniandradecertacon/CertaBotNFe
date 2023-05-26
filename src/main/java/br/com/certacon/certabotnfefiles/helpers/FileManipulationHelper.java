package br.com.certacon.certabotnfefiles.helpers;

import br.com.certacon.certabotnfefiles.utils.ProcessStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;

@Component
public class FileManipulationHelper {

    public ProcessStatus moveFiles(String filePath) throws IOException {
        File sourceFile = new File(filePath);
        File destinyFile = new File(filePath.replace("ORGANIZADOS", "ENVIADOS"));

        Path finalPath = Files.move(sourceFile.toPath(), destinyFile.toPath(), ATOMIC_MOVE);
        if (finalPath != null) {
            return ProcessStatus.MOVED;
        } else {
            throw new RuntimeException("Falha ao mover Arquivos");
        }
    }
}
