package net.java.chapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class Nio2 {
    public static void main(String[] args) {
        try {
            // Create
            String str = "/home/a/b/c/d.txt";
            Path p1 = Paths.get(str);
            Path p2 = FileSystems.getDefault().getPath(str);
            Path p3 = FileSystems.getFileSystem(new URI("http://ex.com")).getPath("","");
            Path p4 = Paths.get(new URI("file:///home/directory"));
            Path psub = p1.subpath(1,3); // a/b

            // Join
            Path p5 = Paths.get("/home/a");
            Path p6 = Paths.get("b");
            Path p7 = p5.resolve(p6); // /home/a/b

            // Create Directory
            Files.createDirectory(p7);
            Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr--r--");
            FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions.asFileAttribute(permissions);
            Files.createDirectory(p7,fileAttributes);

            // Working with Legacy (new method - toPath)
            File file = new File("/home/img.png");
            Path pFile = file.toPath();

            // Use
            Paths.get(new URI("https://samitkumarpatel.github.io")).toUri();
            Paths.get("/home/../test").getParent().normalize().toAbsolutePath();

            // View - Methods
            pFile.toString();    // /home/img.png
            pFile.getNameCount(); // 2
            for(int x=0;x<pFile.getNameCount();x++){
                pFile.getName(x); // 0: home; 1: img.png
            }


            // Access - Methods
            pFile.getFileName(); // img.png
            pFile.getParent();   // /home
            pFile.getRoot();     // /

            // Check - Methods
            pFile.isAbsolute();    // true
            pFile.toAbsolutePath();// /home/img.png
            Paths.get(".././d.txt").toRealPath(); // /home/a/b/c/d.txt

            // Helper Class
            Files.exists(pFile);
            Files.isSameFile(p1, pFile);

            // Copy - file or directory
            Files.copy(p1,p7);
            Files.copy(new FileInputStream("test.txt"), p1);
            Files.copy(p1, new FileOutputStream("test.txt"));

            // move
            Files.move(pFile, Paths.get("/home/image.png")); // rename
            Files.move(pFile, Paths.get("/home/a/image.png")); // move

            // Remove
            Files.delete(p1); // throw an exception is file not exist
            Files.deleteIfExists(p1); // return false if file not exist

            // read and write

        } catch (Exception e) {

        }

    }
}
