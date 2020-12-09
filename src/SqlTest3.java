import java.sql.*;
import java.util.Scanner;
import static java.lang.System.out;

public class SqlTest3
{
    static Connection conn = null;
    static Statement stmt = null;
    static String SQL = null;
    static ResultSet rs = null;

    public static void main(String[] args) throws SQLException
    {
        try
        {
            Scanner scan = new Scanner(System.in);
            System.out.println("SQL Programming Test");

            try {
                Class.forName("org.postgresql.Driver");
                String url = "jdbc:postgresql://localhost:5432/";
                String id = "postgres";
                String pw = "1234";
                conn = DriverManager.getConnection(url, id, pw);    // getCo... : 계정정보 url, id, pw
                stmt = conn.createStatement();
                out.println("데이터베이스 db에 성공적으로 접속했습니다1");

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("예상치 못한 오류가 발생하였습니다.");
            }


            //Query 1 시작
            System.out.println("Recursive test 1");

            stmt.executeUpdate("with recursive " +
                    "Ancestor(a,d) as (select parent as a, child as d from ParentOf " +
                    "union " +
                    "select Ancestor.a , ParentOf.child as d  " +
                    "from Ancestor, ParentOf " +
                    "where Ancestor.d = ParentOf.parent)");
            rs = stmt.executeQuery("select a from Ancestor where d ='Mary' ");
            while (rs.next())
            {
                out.println(rs.getString("a"));
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();



            //Query 2 실행
            System.out.println("Recursive test 2");

            SQL = "With recursive Route(orig, dest, total) as (select orig, dest, cost as total from Flight union select R.orig, F.dest, cost+total AS total from ROUTE r, fLIGHT f WHERE r.DEST = f.orig)";
            stmt.executeUpdate(SQL);

            rs = stmt.executeQuery("select * from Route where orig = 'A' and dest = 'B'");
            while (rs.next())
            {
                out.println(rs.getString("orig"));
                out.println(rs.getString("dest"));
                out.println(rs.getInt("total"));
            }



            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();



            //Query 3번
            System.out.println("Recursive test 3");
// Recursive query 3 실행하고 결과는 적절한 Print문을 이용해 Display
            SQL = "with recursive ToB(orig, total) as (select orig, cost as total from Flight where dest = 'B'\n" +
                    "\t\t\t\t\t\t\t\t   union\n" +
                    "\t\t\t\t\t\t\t\t   select F.orig, cost+total as total\n" +
                    "\t\t\t\t\t\t\t\t   from Flight F, ToB TB\n" +
                    "\t\t\t\t\t\t\t\t   where F.dest = TB.orig)\n";

            stmt.executeUpdate(SQL);

            rs = stmt.executeQuery("select min(total) from ToB where orig = 'A'");




            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            //Query 1번 실행
            System.out.println("OLAP test 1");
            stmt.executeUpdate("select storeID, itemID, custID, sum(price)\n" +
                    "from Sales\n" +
                    "group by cube(storeID, itemID, custID);");

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            System.out.println("OLAP test 2");
// OLAP query 2 실행하고 결과는 적절한 Print문을 이용해 Display
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            System.out.println("OLAP test 3");
// OLAP query 3 실행하고 결과는 적절한 Print문을 이용해 Display
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
        } catch(Exception ex)
        {
            throw ex;
        }
    }
}