## [java.io, java.nio & nio.2](https://docs.oracle.com/javase/8/docs/technotes/guides/io/index.html)

### java.io
- Was introduce in java 1.0
- blocking I/O
- api are:
  - Byte Stream
  ```
  FileInputStream in = new FileInputStream("xanadu.txt");
  FileOutputStream out = new FileOutputStream("outagain.txt");
  int c;
  while ((c = in.read()) != -1) {
    out.write(c);
  }
  ```
  - Character Stream
  ```
  FileReader inputStream = new FileReader("xanadu.txt");
  FileWriter outputStream = new FileWriter("characteroutput.txt");
  int c;
  while ((c = inputStream.read()) != -1) {
    outputStream.write(c);
  }
  ```
  - Buffer Stream
  ```
  inputStream = new BufferedReader(new FileReader("xanadu.txt"));
  outputStream = new BufferedWriter(new FileWriter("characteroutput.txt"));
  ```
  - Scanning & Formatting
  ```
  new Scanner(new BufferedReader(new FileReader("xanadu.txt")));
  ```

## java.nio 
- Was introduce in java 1.4
- non-blocking I/O

## java.nio (nio.2)
- java.nio 2.0 was introduced with java 1.7
- NIO.2 is also know as JSR 203
- async approach to non-blocking I/O
- In old versions, you could use the java.io.File to handle files. But there were problems with symbolic link, attributes, performance. The Java 1.4 introduced the NIO API, but it was not enough to solve the problems. The version 7 brings the NIO.2 API such a solution to help in the support to manipulate files and directories. 
- Instead to use java.io.File, now you can use java.nio.file package (Path, Paths, and Files).
- Some of the most usful api are:
  - Path (interface)
  - Paths (class)
  - Files (class)
  - AsynchronousFileChannel
  - AsynchronousSocketChanel
  - WatchService (Interface)
  - FileVisitor (Interface)
  - PathMatcher (Functional Interface)









### [Junit 5.x](https://junit.org/junit5/docs/current/user-guide/) 
- [Maven Dependency](https://junit.org/junit5/docs/current/user-guide/#running-tests-build-maven)
- [assertions](https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions)