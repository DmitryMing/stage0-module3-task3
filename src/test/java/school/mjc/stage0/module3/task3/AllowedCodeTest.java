package school.mjc.stage0.module3.task3;

import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static school.mjc.parser.Asserts.assertNoClassesExceptTopLevel;
import static school.mjc.parser.Asserts.assertNoInitializationBlocks;
import static school.mjc.parser.Util.parse;

public class AllowedCodeTest {

    @Test
    public void verifyThatForbiddenCodeNotUsed() throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get("src/main/java/school/mjc/stage0/module3/task3"))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        CompilationUnit parsed = parse(path);
                        String fileName = path.getFileName().toString();
                        String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
                        verifyFile(parsed, fileNameWithoutExtension);
                    });
        }
    }

    private void verifyFile(CompilationUnit parsed, String fileName) {
        assertNoInitializationBlocks(parsed);
        assertNoClassesExceptTopLevel(parsed, fileName);
    }
}
