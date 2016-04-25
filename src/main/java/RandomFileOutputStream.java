import java.io.*;

/**
 * A positionable file output stream.
 * <p>
 * Threading Design : [x] Single Threaded  [ ] Threadsafe  [ ] Immutable  [ ] Isolated
 */

public class RandomFileOutputStream
        extends OutputStream
{

// *****************************************************************************
// INSTANCE PROPERTIES
// *****************************************************************************

    protected RandomAccessFile              randomFile;                             // the random file to write to
    protected boolean                       sync;                                   // whether to synchronize every write

// *****************************************************************************
// INSTANCE CONSTRUCTION/INITIALIZATON/FINALIZATION, OPEN/CLOSE
// *****************************************************************************

    public RandomFileOutputStream(String fnm) throws IOException {
        this(fnm,false);
    }

    public RandomFileOutputStream(String fnm, boolean syn) throws IOException {
        this(new File(fnm),syn);
    }

    public RandomFileOutputStream(File fil) throws IOException {
        this(fil,false);
    }

    public RandomFileOutputStream(File fil, boolean syn) throws IOException {
        super();

        File                                par;                                    // parent file

        fil=fil.getAbsoluteFile();
        if((par=fil.getParentFile())!=null) { createDir(par); }
        randomFile=new RandomAccessFile(fil,"rw");
        sync=syn;
    }

// *****************************************************************************
// INSTANCE METHODS - OUTPUT STREAM IMPLEMENTATION
// *****************************************************************************

    public void write(int val) throws IOException {
        randomFile.write(val);
        if(sync) { randomFile.getFD().sync(); }
    }

    public void write(byte[] val) throws IOException {
        randomFile.write(val);
        if(sync) { randomFile.getFD().sync(); }
    }

    public void write(byte[] val, int off, int len) throws IOException {
        randomFile.write(val,off,len);
        if(sync) { randomFile.getFD().sync(); }
    }

    public void flush() throws IOException {
        if(sync) { randomFile.getFD().sync(); }
    }

    public void close() throws IOException {
        randomFile.close();
    }

// *****************************************************************************
// INSTANCE METHODS - RANDOM ACCESS EXTENSIONS
// *****************************************************************************

    public long getFilePointer() throws IOException {
        return randomFile.getFilePointer();
    }

    public void setFilePointer(long pos) throws IOException {
        randomFile.seek(pos);
    }

    public long getFileSize() throws IOException {
        return randomFile.length();
    }

    public void setFileSize(long len) throws IOException {
        randomFile.setLength(len);
    }

    public FileDescriptor getFD() throws IOException {
        return randomFile.getFD();
    }

    private static void createDir(File dir) {
        if (dir.isDirectory() && dir.exists()) return;

        if (!dir.mkdirs()) {
            throw new RuntimeException("Fail creating directory " + dir);
        }
    }

} // END PUBLIC CLASS