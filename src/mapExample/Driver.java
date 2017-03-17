/*
 * Driver.java
 * This program creates objects of the Student class and writes them to class ObjectRandomAccessFile which is derived from
 * RandomAccessFile for the purpose of handling objects. Then this program reads objects back from ObjectRandomAccessFile
 * and stores in HashMaps. Last, it iterates through the HashMaps and displays their contents.
 * F. D'Angelo
 */
package mapExample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.EOFException;
import java.io.FileNotFoundException;

public class Driver {
  final static String FILE_NAME    = "c:/data/StudentFile.dat"; // may need to be different on your system.
  static int          recordNumber = -1;

  public static void main(String[] args) {
    final String ACCESS_MODE = "rw"; // read-write -- the file can be written to as well as read from.
    ObjectRandomAccessFile orafObject = null;
    Map<Integer, Student> idKeyedMap = new HashMap<Integer, Student>();
    Map<String, Student> emailKeyedMap = new HashMap<String, Student>();

    try {
      orafObject = new ObjectRandomAccessFile(FILE_NAME, ACCESS_MODE);
    } catch (FileNotFoundException e) {
      System.out.println("A FileNotFoundException occurred while opening the RandomAccessFile, " + FILE_NAME);
    } catch (IOException e) {
      System.out.println("An IOException occurred while opening the RandomAccessFile.");
    }

    System.out.println("\n************** Begin Writing Data **************\n");
    Student studentObj = null; // int status, int studentID, String lastName, String firstName, String majorCode, double gradePointAvg, String email
    
    studentObj = new Student(Student.ACTIVE_RECORD_STATUS, 1, "anderson", "andrew", "csc", 0.0,
        "aanderson@university.edu");
    writeStudent(studentObj, orafObject);

    studentObj = new Student(Student.ACTIVE_RECORD_STATUS, 2, "bierce", "bill", "mis", 0.0, "bbierce@university.edu");
    writeStudent(studentObj, orafObject);

    studentObj = new Student(Student.ACTIVE_RECORD_STATUS, 3, "castenada", "carol", "psy", 0.0,
        "ccastenada@university.edu");
    writeStudent(studentObj, orafObject);

    studentObj = new Student(Student.ACTIVE_RECORD_STATUS, 4, "durazo", "deborah", "psy", 0.0,
        "ddurazo@university.edu");
    writeStudent(studentObj, orafObject);
    
    studentObj = new Student(Student.ACTIVE_RECORD_STATUS, 5, "edwards", "elaine", "mis", 0.0,
        "eedwards@university.edu");
    writeStudent(studentObj, orafObject);
    
    studentObj = new Student(Student.ACTIVE_RECORD_STATUS, 6, "fernandez", "franciso", "csc", 0.0,
        "ffernandez@university.edu");
    writeStudent(studentObj, orafObject);
    
    System.out.println("\n************** Read File and add Student objects to the HashMaps **************\n");
    try {
      loadMaps(orafObject, idKeyedMap, emailKeyedMap);
    } catch (EOFException e) {
      System.out.println("An EOFException occurred while reading data from the RandomAccessFile -- " + e.toString());
    } catch (IOException e) {
      System.out.println("An IOException occurred while reading data from the RandomAccessFile -- " + e.toString());
    } catch (Exception e) {
      System.out
          .println("A generic Exception occurred while reading data from the RandomAccessFile -- " + e.toString());
    }
    
    System.out.println("\n************** Displaying contents of idKeyedMap HashMap **************\n");
    Set<Map.Entry<Integer, Student>> idKeyedEntrySet = idKeyedMap.entrySet(); // represents	a set of all records in the Map.
    for (Map.Entry<Integer, Student> entry : idKeyedEntrySet) // for each entry in idKeyedEntrySet
    {
      System.out.println("\n" + "key: " + entry.getKey() + "\n" + entry.getValue());
    }
    
    System.out.println("\n************** Displaying contents of emailKeyedMap HashMap **************\n");
    Set<Map.Entry<String, Student>> emailKeyedEntrySet = emailKeyedMap.entrySet(); // represents a set of all records in the Map.
    for (Map.Entry<String, Student> entry : emailKeyedEntrySet) // for each entry in idKeyedEntrySet
    {
      System.out.println("\n" + "key: " + entry.getKey() + "\n" + entry.getValue());
    }
    try {
      orafObject.closeFile();
    } catch (IOException e) {
      System.out.println("An IOException occurred while attempting to close the RandomAccessFile -- " + e.getMessage());
    }
  }
  
  

  private static void writeStudent(Student studentObj, ObjectRandomAccessFile orafObject) {
    try {
      recordNumber++;
      orafObject.writeObject(recordNumber, studentObj);
    } catch (IOException e) {
      System.out.println("An IOException occurred while writing data from the RandomAccessFile, " + FILE_NAME + " -- "
          + e.getMessage());
    } catch (Exception e) {
      System.out.println("A generic Exception occurred while writing data from the RandomAccessFile, " + FILE_NAME
          + " -- " + e.getMessage());
    }
  }

  private static void loadMaps(ObjectRandomAccessFile oraFile, Map<Integer, Student> idMap,
      Map<String, Student> emailMap) throws EOFException, IOException, Exception {
    Student inputStudentObj = null;
    int recordNumber = -1;
    while (true) // not a great idea, but I can't think of a better way to read all the records in a RandomAccessFile.
    {
      ++recordNumber;
      System.out.println("\n1. loadMaps: recordNumber " + recordNumber);
      inputStudentObj = (Student) oraFile.readObject(recordNumber);
      System.out.println("\n2. loadMaps: " + recordNumber + " " + inputStudentObj);
      idMap.put(inputStudentObj.getStudentID(), inputStudentObj); // add record to ID keyed Map.
      emailMap.put(inputStudentObj.getEmail(), inputStudentObj); // add record to email keyed Map.
    }
  }
}
