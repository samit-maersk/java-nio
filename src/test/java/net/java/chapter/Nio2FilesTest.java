package net.java.chapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Nio2FilesTest {

    @TempDir
    //@TempDir(cleanup = CleanupMode.ON_SUCCESS)
    Path tempDir;

    @Test
    @DisplayName("Files & File Attribute")
    void testOne() throws Exception {
        Path path = tempDir.resolve("test.txt");
        assertFalse(Files.exists(path));
        assertTrue(Files.notExists(path));

        //create a file
        Path testTxt = Files.createFile(path);
        assertTrue(Files.exists(testTxt));
        assertTrue(Files.isRegularFile(testTxt));
        assertFalse(Files.isExecutable(testTxt));
        assertTrue(Files.isReadable(testTxt));
        assertFalse(Files.isHidden(testTxt));

        //Read File Attribute
        PosixFileAttributes attr = Files.readAttributes(path,PosixFileAttributes.class);
        Set<PosixFilePermission> permissions = attr.permissions();
        System.out.println(permissions); //[OWNER_READ, OTHERS_READ, OWNER_WRITE, GROUP_READ]

        //Create File with attribute
        FileAttribute<Set<PosixFilePermission>> rwx = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx"));
        Path path1 = tempDir.resolve("test1.txt");
        Path file1 = Files.createFile(path1, rwx);

        assertTrue(Files.exists(file1));
        assertTrue(Files.isRegularFile(file1));
        assertTrue(Files.isExecutable(file1));
        assertTrue(Files.isReadable(file1));
        assertFalse(Files.isHidden(file1));

        PosixFileAttributes attr1 = Files.readAttributes(path1,PosixFileAttributes.class);
        Set<PosixFilePermission> permissions1 = attr1.permissions();
        System.out.println(permissions1); //[OWNER_READ, OTHERS_EXECUTE, OWNER_EXECUTE, GROUP_EXECUTE, OTHERS_READ, OWNER_WRITE, GROUP_READ]
    }
    @Test
    @DisplayName("File Read & Write")
    void testTwo(){

    }

    @Test
    @DisplayName("Async File Read & Write")
    void testThree(){

    }

    @Test
    @DisplayName("Folder")
    void testFour(){

    }

    @Test
    @DisplayName("Files.walk - FileVisit")
    void testFive(){

    }

    @Test
    @DisplayName("FilesWatcher")
    void testSix(){

    }
    
}
