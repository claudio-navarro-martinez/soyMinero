import scala.language.postfixOps 

import java.sql.{Connection, DriverManager, ResultSet} 
import oracle.jdbc.OracleTypes 
 

object oracleClientLogMiner {
  def main(args: Array[String]) {
      if (args.length != 4) {
        println("Args: username pwd hostname servicename")
        System.exit(1)
     }
     
  val oracleDataSource = new OracleDataSource()
  oracleDataSource setUser(args(0))
  oracleDataSource setPassword(args(1))
  oracleDataSource setURL("jdbc:oracle:thin:@" + args(2)+":1521/"+args(3))
  val connection = oracleDataSource getConnection
  
  val stament1 = con createStatement
  stament1 setFetchSize 10
  val rs = s1 executeQuery "Select /*fetchsize=" + s1.getFetchSize() + " */ * " + "from customers where rownum<= " + rows
  while (rs next()) {
         val c1 = rs.getString(1)
         val c2 = rs.getString(2)
  }
  
 /** Stored procedure */ 
    
 // ? indicates parameter - in, out and cursor. 
  val callString = "{ call DBMS_LOGMNR.START_LOGMNR(STARTTIME => ’19-MAR-2013 14:02:14′, ENDTIME => SYSDATE, OPTIONS => DBMS_LOGMNR.DICT_FROM_ONLINE_CATALOG + DBMS_LOGMNR.CONTINUOUS_MINE ) }" 
  val callableStatement = connection prepareCall callString 
78       callableStatement.setInt(1, 7) // Sets first parameter to 7 
79       callableStatement.setInt(2, 9) // Sets second parameter to 9 
80       // Sets third parameter to output as cursor. 
81       callableStatement registerOutParameter(3, OracleTypes.CURSOR) // or eg FLOAT 
82    
83       callableStatement execute 
84       // Gets the returned value of the output parameter as an Object. 
85       val resultObject = callableStatement getObject 3 // or eg getFloat 
86    
87       // Scala casting - returned Object to ResultSet. 
88       var procRs: ResultSet = resultObject match { 
89         case r: ResultSet => r; 
90         case _ => null 
91       } 
92    
93       while (procRs next) { 
94         println(procRs getInt 1) 
95       } 
96    
97       if (procRs != null) procRs.close 
98       if (callableStatement != null) callableStatement.close 
  if (rs != null) 
      rs.close()
}  
