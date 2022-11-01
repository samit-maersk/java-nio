package net.java.chapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Nio2PathTest {
    @TempDir
    Path tempDir;

    /**
     * https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html
     *
     * https://docs.oracle.com/javase/tutorial/essential/io/pathOps.html
     */
    @Test
    @DisplayName("Path")
    void testOne() throws Exception {
        String str = "/home/a/b/c/d.txt";
        Path p1 = Paths.get(str);

        assertEquals(Path.of("/home/a/b/c"), p1.getParent());
        assertEquals(5,p1.getNameCount());
        assertEquals(Path.of("d.txt"),p1.getFileName());
        assertEquals(str, p1.toString());

        assertEquals(Path.of("/"), p1.getRoot());

        assertEquals(Path.of("home"),p1.getName(0));
        assertEquals(Path.of("a"),p1.getName(1));

        //merge two path
        Path p2 = Path.of("/home/a");
        assertEquals(Path.of("/home/a/b"),p2.resolve("b"));

        assertEquals(Path.of("/home/a/b/c/d.txt"),p1.toAbsolutePath());
        assertTrue(p1.isAbsolute());

        assertEquals(Path.of("/home/a/b/c/d.txt"),p1.normalize());
        assertEquals(Path.of("a/b"),p1.subpath(1,3));

        assertEquals(URI.create("file:///home/a/b/c/d.txt"),p1.toUri());
        assertEquals(0,p1.compareTo(Path.of("/home/a/b/c/d.txt")));

        assertTrue(p1.endsWith("d.txt"));
        assertTrue(p1.startsWith("/home"));

        Path p3 = Paths.get(new URI("file:///home/directory"));
        assertEquals(Path.of("/home/directory"),p3.toAbsolutePath());

        //and there are many more api available under Path ...
    }

    @Test
    @DisplayName("FileSystems")
    void testTwo() {
        Path path = tempDir.resolve("a");
        FileSystem fileSystem = path.getFileSystem();
        assertEquals("/",fileSystem.getSeparator());

        // If we execute in Windows it would be "\"
        //assertEquals("\",fileSystem.getSeparator());

        assertFalse(fileSystem.isReadOnly());
    }
}
