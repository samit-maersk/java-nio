### [java.io, java.nio & nio.2](https://docs.oracle.com/javase/8/docs/technotes/guides/io/index.html)

### I/O Streams
  - **Byte Streams**
  >Programs use byte streams to perform input and output of 8-bit bytes. All byte stream classes are descended from InputStream and OutputStream.
  ```
  public static void main(String[] args) throws IOException {

        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream("xanadu.txt");
            out = new FileOutputStream("outagain.txt");
            int c;

            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
  ```
  
  - **Character Streams**
  > The Java platform stores character values using Unicode conventions. Character stream I/O automatically translates this internal format to and from the local character set
  ```
  public static void main(String[] args) throws IOException {

        FileReader inputStream = null;
        FileWriter outputStream = null;

        try {
            inputStream = new FileReader("xanadu.txt");
            outputStream = new FileWriter("characteroutput.txt");

            int c;
            while ((c = inputStream.read()) != -1) {
                outputStream.write(c);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
  ```
  - **Buffered Streams**
  > Most of the examples we've seen so far use unbuffered I/O. This means each read or write request is handled directly by the underlying OS. This can make a program much less efficient, since each such request often triggers disk access, network activity, or some other operation that is relatively expensive.To reduce this kind of overhead, the Java platform implements buffered I/O streams. Buffered input streams read data from a memory area known as a buffer; the native input API is called only when the buffer is empty. Similarly, buffered output streams write data to a buffer, and the native output API is called only when the buffer is full.
    ```
    inputStream = new BufferedReader(new FileReader("xanadu.txt"));
    outputStream = new BufferedWriter(new FileWriter("characteroutput.txt"));
    ```
  - **Scanning and Formatting**
    > Programming I/O often involves translating to and from the neatly formatted data humans like to work with
    - Scanning
    ```
        public static void main(String[] args) throws IOException {

        Scanner s = null;

        try {
            s = new Scanner(new BufferedReader(new FileReader("xanadu.txt")));

            while (s.hasNext()) {
                System.out.println(s.next());
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
    ```
    - Formatting
    > Stream objects that implement formatting are instances of either PrintWriter, a character stream class, or PrintStream, a byte stream class.
    ```
    public static void main(String[] args) {
        int i = 2;
        double r = Math.sqrt(i);
        
        System.out.print("The square root of ");
        System.out.print(i);
        System.out.print(" is ");
        System.out.print(r);
        System.out.println(".");

        i = 5;
        r = Math.sqrt(i);
        System.out.println("The square root of " + i + " is " + r + ".");
    }
    ```
  - I/O from the Command Line
    - Data Streams 
    >Data streams support binary I/O of primitive data type values (boolean, char, byte, short, int, long, float, and double) as well as String values. All data streams implement either the DataInput interface or the DataOutput interface. This section focuses on the most widely-used implementations of these interfaces, DataInputStream and DataOutputStream.
    - Object Streams
    >Just as data streams support I/O of primitive data types, object streams support I/O of objects. Most, but not all, standard classes support serialization of their objects. Those that do implement the marker interface Serializable.
    




### [Junit 5.x](https://junit.org/junit5/docs/current/user-guide/) 
- [Maven Dependency](https://junit.org/junit5/docs/current/user-guide/#running-tests-build-maven)
- [assertions](https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions)