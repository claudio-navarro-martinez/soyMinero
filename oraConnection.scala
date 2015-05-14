import java.sql.Connection
import java.sql.ResultSet
 
import oracle.jdbc.pool.OracleDataSource

object soyMinero {
  def main(args: Array[String]) {
      if (args.length != 4) {
        println("Args: username pwd hostname servicename")
        System.exit(1)
     }
     
  val ods = new OracleDataSource()
  ods.setUser(args(0))
  ods.setPassword(args(1))
  ods.setURL("jdbc:oracle:thin:@" + args(2)+":1521/"+args(3))
  val con = ods.getConnection()
  
  val s1 = con.createStatement()
  s1.setFetchSize(1000)
  val rs = s1.executeQuery("Select /*fetchsize=" + s1.getFetchSize() + " */ * " + "from customers where rownum<= " + rows)
  while (rs.next()) {
         val c1 = rs.getString(1)
         val c2 = rs.getString(2)
  }
  rs.close()
}  
