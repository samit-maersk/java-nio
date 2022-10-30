package net.java.chapter;

import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WhatWeHaveInPath {
    //https://docs.oracle.com/javase/tutorial/essential/io/pathOps.html
    /**
    File I/O (Featuring NIO.2)
    What Is a Path? (And Other File System Facts)
    The Path Class
     **/
    public static void aboutPath(String[] args) {
        //Create Path
        Path p1 = Paths.get("/tmp/foo");
        Path p2 = Paths.get(args[0]);
        Path p3 = Paths.get(URI.create("file:///Users/joe/FileTest.java"));

        Path p4 = FileSystems.getDefault().getPath("/users/sally");
        Path p5 = Paths.get(System.getProperty("user.home"),"logs", "foo.log");

        //Retrieving Information about a Path
        //Path path = Paths.get("C:\\home\\joe\\foo");
        Path path = Paths.get("/home/joe/foo"); // "C:\\home\\joe\\foo"
        System.out.format("toString: %s%n", path.toString());
        System.out.format("getFileName: %s%n", path.getFileName());
        System.out.format("getName(0): %s%n", path.getName(0));
        System.out.format("getNameCount: %d%n", path.getNameCount());
        System.out.format("subpath(0,2): %s%n", path.subpath(0,2));
        System.out.format("getParent: %s%n", path.getParent());
        System.out.format("getRoot: %s%n", path.getRoot());

        //Converting Path
        Path p6 = Paths.get("/home/logfile");
        // Result is file:///home/logfile
        System.out.format("%s%n", p6.toUri());

        //Joining two paths
        // Solaris
        Path p7 = Paths.get("/home/joe/foo");
        // Result is /home/joe/foo/bar
        System.out.format("%s%n", p7.resolve("bar"));

    }

    /**
    Path Operations
    File Operations
    Checking a File or Directory
    Deleting a File or Directory
    Copying a File or Directory
    Moving a File or Directory
    Managing Metadata (File and File Store Attributes)
    Reading, Writing, and Creating Files
    Random Access Files
    Creating and Reading Directories
    Links, Symbolic or Otherwise
    Walking the File Tree
    Finding Files
    Watching a Directory for Changes
    Other Useful Methods
    Legacy File I/O Code
     **/

    public static void main(String[] args) {

    }
}
