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
  if (rs != null) 
      rs.close()
}  
