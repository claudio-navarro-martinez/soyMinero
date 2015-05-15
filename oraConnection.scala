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
  val callString = "{ call DBMS_LOGMNR.START_LOGMNR(STARTTIME => ’15-MAY-2015 00:00:00′, ENDTIME => SYSDATE, OPTIONS => DBMS_LOGMNR.DICT_FROM_ONLINE_CATALOG + DBMS_LOGMNR.CONTINUOUS_MINE ) }" 
  val callableStatement = connection prepareCall callString 
  
  callableStatement execute 
 
 // Gets the returned value of the output parameter as an Object. 
  val resultObject = callableStatement getObject 3 // or eg getFloat 
    
 // Scala casting - returned Object to ResultSet. 
  var procRs: ResultSet = resultObject match { 
      case r: ResultSet => r; 
      case _ => null 
  } 
    
  while (procRs next) { 
         println(procRs getInt 1) 
  }    
  if (procRs != null) procRs.close 
  if (callableStatement != null) callableStatement.close 
  if (rs != null) 
      rs.close()
}  
