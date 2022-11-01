package net.java.chapter;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.DELETE_ON_CLOSE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    @DisplayName("Copy and Move")
    void copyAndMoving(@TempDir Path source, @TempDir Path target) throws Exception {
        Path sourceFile = source.resolve("test.txt");
        Files.write(sourceFile, "Hello World".getBytes(StandardCharsets.UTF_8));

        Path targetFile = Files.copy(sourceFile, target.resolve("test.txt"));

        assertNotEquals(sourceFile, targetFile);
        assertEquals("Hello World", Files.readString(targetFile));
    }

    @Test
    @DisplayName("Read & Write")
    void testTwo() throws Exception {
        Path path = tempDir.resolve("file.txt");

        //write
        String str = "This is write file Example";
        Path writtenFilePath = Files.write(path, str.getBytes(StandardCharsets.UTF_8));

        //read
        byte[] bs = Files.readAllBytes(path);
        List<String> strings = Files.readAllLines(path);

        assertEquals(str, new String(bs));
        assertEquals(List.of("This is write file Example"), strings);
    }

    /**
     * For more around async File I/O
     * https://www.baeldung.com/java-nio2-async-file-channel
     */
    @Test
    @DisplayName("Async File Read & Write")
    void testThree() throws Exception {
        Path filePath = tempDir.resolve("async-file.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(
                filePath, READ, WRITE, CREATE, DELETE_ON_CLOSE);
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //Write
        //we can write to a file in two ways. Using Future and using CompletionHandler.
        buffer.put("Hello World".getBytes());
        buffer.flip();
        Future<Integer> writeOperation = fileChannel.write(buffer, 0);
        buffer.clear();
        //run other code as operation continues in background
        writeOperation.get();

        //Read
        //all asynchronous operations in NIO2, reading a file's contents can be done in two ways.
        // Using Future and using CompletionHandler.
        // In each case, we use the read API of the returned channel
        Future<Integer> readOperation = fileChannel.read(buffer, 0);
        // run other code as operation continues in background
        readOperation.get();
        String fileContent = new String(buffer.array()).trim();
        buffer.clear();
        assertEquals(fileContent, "Hello World");
    }

    @Test
    @DisplayName("Folder")
    void testFour() throws Exception {
        /*
		Path path1 = Paths.get("D:/a");
		Path path2 = Paths.get("/home/a/java18");
		Path path3 = Paths.get(Path.of(new URI("file://home/a/java18/Files"));
        */
        Path path1 = tempDir.resolve("a");
        Path path2 = tempDir.resolve("a/b");
        Path path3 = tempDir.resolve("a/b/java18");
        Path createdDir1 = Files.createDirectory(path1);
        Path createdDir2 = Files.createDirectory(path2);
        Path createdDir3 = Files.createDirectory(path3);

        assertEquals(path1.toAbsolutePath(), createdDir1);
        assertEquals(path2.toAbsolutePath(), createdDir2);
        assertEquals(path3.toAbsolutePath(), createdDir3);
    }

    @Test
    @DisplayName("Files.walk - FileVisit")
    void testFive() throws Exception {
        //"" or "." uses internally the system parameter "user.dir" value to determine the current directory
        Path path = Paths.get("");
        //list all files from a directory, including all levels of sub-directories (default)
        Stream<Path> walk = Files.walk(path);
        walk.filter(Files::isRegularFile).collect(Collectors.toList()).stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("Files.walk - Only Read File")
    void testFive_1() throws Exception {
        //"" or "." uses internally the system parameter "user.dir" value to determine the current directory
        Path path = Paths.get("");
        //If we want to list files from the root directory only, put maxDepth = 1 or 5 or 10 (level of depth you want to go)
        Stream<Path> walk = Files.walk(path,10);
        walk.filter(Files::isRegularFile).collect(Collectors.toList()).stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("Files.walk - Only Read Folder")
    void testFive_2() throws Exception {
        //"" or "." uses internally the system parameter "user.dir" value to determine the current directory
        Path path = Paths.get("");
        //If we want to list files from the root directory only, put maxDepth = 1 or 5 or 10 (level of depth you want to go)
        Stream<Path> walk = Files.walk(path,10);
        walk.filter(Files::isDirectory).collect(Collectors.toList()).stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("Files.walk - Filter by File extension")
    void testFive_3() throws Exception {
        //"" or "." uses internally the system parameter "user.dir" value to determine the current directory
        Path path = Paths.get("");
        //If we want to list files from the root directory only, put maxDepth = 1 or 5 or 10 (level of depth you want to go)
        Stream<Path> walk = Files.walk(path,10);
        walk
                .filter(Files::isRegularFile)
                .filter(p -> p.getFileName().toString().endsWith(".java"))
                //.filter(p -> p.getFileName().toString().equalsIgnoreCase(fileName)) //Find by File Name
                .collect(Collectors.toList()).stream().forEach(System.out::println);

        //Modify file last modify time
        /*
        walk
            .filter(Files::isReadable)      // read permission
            .filter(Files::isRegularFile)   // file only
            .forEach(p -> {
                    // set last modified time
                    Files.setLastModifiedTime(p, FileTime.from(instant));
                    });
         */
    }

    @Test
    @DisplayName("FilesWatcher")
    //@Disabled
    void testSix() throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(".");

        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println(
                        "Event kind:" + event.kind()
                                + ". File affected: " + event.context() + ".");
            }
            key.reset();
        }
    }
}
