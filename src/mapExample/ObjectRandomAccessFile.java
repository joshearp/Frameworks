/*
 * ObjectRandomAccessFile.java
 * This class extends Java's RandomAccessFile class to create a class with methods
 * that can write objects to and read them from random access files.
 * Adapted from a program by Xiaoping Jia in "Object-Oriented Software Development Using Java"
 * p.390, copyright 2003, published by Pearson
 * Modified by F. D'Angelo to:
 * - modify readObject to accept a parameter for the number of the record to read
 * - add updateRecord method
 * - readAllObjects method to read te entire file
 * - document statements with comments
 */

package mapExample;

import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;

public class ObjectRandomAccessFile extends RandomAccessFile {
  private String fileName = null;

  public ObjectRandomAccessFile(String fileNameParm, String accessMode) throws IOException, FileNotFoundException {
    super(fileNameParm, accessMode);
  }

  public int writeObject(int recordNumber, Object obj) throws IOException, Exception {
    int objectSize = 0;

    if (obj != null && obj instanceof Serializable) {
      try {
        ByteArrayOutputStream byteAOS = new ByteArrayOutputStream();
        ObjectOutputStream objOutStrm = new ObjectOutputStream(byteAOS);
        objOutStrm.writeObject(obj); // This writes the obj to byteAOS.
        objectSize = byteAOS.size(); // obtain the number of bytes
        byte[] byteArray = byteAOS.toByteArray(); // convert byteAOS to byte array.
        int totalRecordLength = 4 + objectSize; // 4 is the size of the int that will hold the record length.
        seek(totalRecordLength * recordNumber); // position the read-write pointer at the location where this record will be stored.
        writeInt(objectSize); // write the size of the object to the file
        write(byteArray, 0, objectSize); // write the object to the file
        byteAOS.reset();
        System.out.println("writeObject -- objectSize: " + objectSize + " recordNumber: " + recordNumber
            + " totalRecordLength: " + totalRecordLength + "\n");
        return totalRecordLength;
      }

      catch (IOException ex) {
        throw new IOException(
            "An IOException occurred while writing data to the RandomAccessFile, " + fileName + ": " + ex.getMessage());
      }

      catch (Exception ex) {
        throw new Exception("A generic Exception occurred while writing data to the RandomAccessFile, " + fileName
            + ": " + ex.getMessage());
      }
    }

    else {
      return 0;
    }
  }

  public void readAllObjects(ArrayList<Object> objArrayList) throws IOException, EOFException, Exception {
    //Since an ArrayList is passed by reference, this method can receive an ArrayList and modify it without having to return it.

    int recordNumber = 0;

    while (true) // seldom a good idea, but Java doesn't provide a method to detect EOF, so we keep reading until an EOFException is raised.
    {
      objArrayList.add(readObject(recordNumber));

      recordNumber++;
    }
  }

  public Object readObject(int recordNumber) throws EOFException, IOException, Exception {
    seek(0); // re=position read-write pointer to beginning of file.

    int objectSize = readInt(); // read the first int in the file to get the recordLength that all records will have.

    int totalRecordLength = objectSize + 4; // total length includes both the int size,4, and the objectSize of bytes representing the object.
    byte[] byteArray = new byte[objectSize];
    seek(totalRecordLength * recordNumber); // Position the read-write pointer at the beginning of the desired record.
    readInt(); // read past the recordLength int which will put the read-write pointer at the beginning of the bytes representing the object.
    read(byteArray, 0, objectSize); // read the bytes representing the object into byteArray.

    ObjectInputStream objInStrm = new ObjectInputStream(new ByteArrayInputStream(byteArray)); // use the byteArray to create an ObjectInputStream object.
    Object obj = objInStrm.readObject(); // read the object into obj.

    objInStrm.close();

    return obj;
  }

  public void updateRecord(int numberOfRecordToUpdate, Object replacementObject)
      throws IOException, EOFException, Exception {
    writeObject(numberOfRecordToUpdate, replacementObject); // for simplicity, replace entire object instead of only a field within it.
  }

  public void closeFile() throws IOException {
    close();
  }
}
