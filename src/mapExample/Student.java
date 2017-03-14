/*
** Program    : StudentData.java
**
** Purpose    : To provide objects with which to populate the studentDataTableView.
** 				It differs slightly from the Student class by having the major description.
**
** Developer  : F DAngelo
**
*/

package mapExample;

import java.io.Serializable;

public class Student implements Serializable
{

	transient private static final long serialVersionUID = 1L; // keep Java happy
	
	//Declare data members.
	transient public  static final int ACTIVE_RECORD_STATUS = 1;
	transient public  static final int DELETED_RECORD_STATUS = 0;
	
	transient private static final int LASTNAME_LENGTH = 24;
	transient private static final int FIRSTNAME_LENGTH = 16;
	transient private static final int MAJORCODE_LENGTH = 3;
	transient private static final int EMAIL_LENGTH = 32;
	
	private int status;
	private int studentID;
	private String lastName;
	private String firstName;
	private String majorCode;
	private double gradePointAvg ;
	private String email;
	
	public Student()
	{
		this.status = 0;
		this.studentID = 0 ; 
		this.lastName = "" ;
		this.firstName = "" ;
		this.majorCode = "" ;
		this.gradePointAvg = 0.0 ; 
		this.email = "" ;
	}

	// Custom constructor with parameters.
	public Student(int status, int studentID, String lastName, String firstName, 
			String majorCode, double gradePointAvg, String email )
	{
		setStatus(status);
		setStudentID(studentID);
		setLastName(lastName) ;
		setFirstName(firstName) ;
		setMajorCode(majorCode);
		setGradePointAvg(gradePointAvg) ;
		setEmail(email);
	}

	// Define "setter" a.k.a. mutator methods.
	
	public void setStatus( int status ) 
	{
		this.status = status  ;
	}
	
	public void setStudentID( int studentID ) // int studentID ) 
	{
		this.studentID = studentID  ;
	}
	
	public void setLastName( String lastName ) 
	{
		this.lastName = lastName ;
	}
	
	public void setFirstName( String firstName ) 
	{
		this.firstName = firstName ;
	}
	
	public void setMajorCode( String majorCode ) 
	{
		this.majorCode = majorCode ;
	}
	
	public void setGradePointAvg( double gradePointAvg ) 
	{
		this.gradePointAvg = gradePointAvg;
	}
	
	public void setEmail( String email ) 
	{
		this.email = email ;
	}
	
	// Define "getter" a.k.a. accessor methods.
	
	public int getStatus() 
	{
		return status ;
	}
	
	public int getStudentID() 
	{
		return studentID ;
	}
	
	public String getLastName() 
	{
		return lastName ;
	}
	
	public String getFirstName() 
	{
		return firstName ;
	}
	
	public String getMajorCode()  
	{
		return majorCode ;
	}
	
	public double getGradePointAvg() 
	{
		return gradePointAvg ;
	}
	
	public String getEmail()  
	{
		return email;
	}
	
	private String adjustStringLength(String str, int requiredLength)
	{		
		if (str.length() == requiredLength) 
		{
			return str;
		}
		
		else if(str.length() < requiredLength)
		{
			StringBuffer sb = new StringBuffer(str);
			
			while (sb.length() < requiredLength)
			{
				sb.append(" ");
			}
			
			return sb.toString();
		}
		
		else
		{
			return str.substring(0, requiredLength);
		}
	}

	public String toString()
	{
		return	"\n" +
				" Status           : " + status + "\n" +
				" Student ID       : " + studentID + "\n" +
				" Last name        : " + lastName + "\n" +
				" First name       : " + firstName + "\n" +
				" Major code       : " + majorCode + "\n" +
				" GPA              : " + gradePointAvg + "\n" +
				" Email            : " + email + "\n" ;				
	}
	
}

