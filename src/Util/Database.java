package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Database {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/user";
	
//  Database credentials
   static final String USER = "root";
   static final String PASS = "ghdud159753";
	
	private static Connection connection = null;
	private Statement stmt = null;
	
	public Database() {
		try {
			//STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER); // MySQL 드라이버 로드
			
			//STEP 3: Open a connection		
			connection = (Connection) DriverManager.getConnection(DB_URL, USER ,PASS); // JDBC 연결
			System.out.println("DB 연결 완료");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로드 오류");
		} catch (SQLException e) {
			System.out.println("SQL 실행 오류-Connect");
		}
	}
	
	public boolean AddUserInfo(String id, String pw, String ip, int port)
	{	
		try {
			// Execute a query
			stmt = connection.createStatement();
			String sql = "insert into userinfo values ( '" + id + "', '" + pw + "', '" + ip + "', " + port + ")";
			
			stmt.executeUpdate(sql);
			System.out.println("DB 입력 완료.");
			return true;
		} catch (SQLException e) {
			System.out.println("SQL 실행 오류-Insert");
			return false;
		}
	}
	
	public boolean CheckIsUser(String id, String pw)
	{
		try {
			stmt = connection.createStatement();
						
			String sql = "select * from userInfo where id = '"+id +"' and password = '" + pw +"'";
			ResultSet rs = stmt.executeQuery(sql);
			int count = 0 ;

			 //STEP 5: Extract data from result set		
		      while(rs.next()){
		         //Retrieve by column name
		         String getId = rs.getString("id");
		         String getPw = rs.getString("password");
		         
		         System.out.println( "id : " + getId + " pw : " + getPw);
		         count++;
		      }
		      
		      if( count == 1 )
		    	  return true;
		      else return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return false;
		
	}
	
	public void UpdateDB()
	{
		try {
			 //STEP 4: Execute a query
		      System.out.println("Creating statement...");
		      stmt = connection.createStatement();
		      String sql = "UPDATE Employees set age=30 WHERE id=103";
		      
		      // Let us check if it returns a true Result Set or not.
		      Boolean ret = stmt.execute(sql);
		      System.out.println("Return value is : " + ret.toString() );

		      // Let us update age of the record with ID = 103;
		      int rows = stmt.executeUpdate(sql);
		      System.out.println("Rows impacted : " + rows );

		      // Let us select all the records and display them.
		      sql = "SELECT id, first, last, age FROM Employees";
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      while(rs.next()){
		         //Retrieve by column name
		         int id  = rs.getInt("id");
		         int age = rs.getInt("age");
		         String first = rs.getString("first");
		         String last = rs.getString("last");

		         //Display values
		         System.out.print("ID: " + id);
		         System.out.print(", Age: " + age);
		         System.out.print(", First: " + first);
		         System.out.println(", Last: " + last);
		      }
		      //STEP 6: Clean-up environment
		      rs.close();
		      stmt.close();		

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
