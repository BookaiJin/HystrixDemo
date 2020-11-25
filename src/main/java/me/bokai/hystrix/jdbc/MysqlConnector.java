package me.bokai.hystrix.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import me.bokai.hystrix.nopreception.Hystrix;

/**
 * @author bokai
 * @version 10.0
 * Created by bokai on 2020-11-23
 */
public class MysqlConnector implements Runnable {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASS = "123456";
    private static final String SQL = "SELECT * FROM summarySumm";

    @Override
    public void run() {

        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            ResultSet wait = execute(conn, stmt, "SELECT SLEEP(100)");
            while (wait.next()) {
                int result = wait.getRow();
                System.out.println("sleep 1s, result: " + result);
            }
            ResultSet rs = execute(conn, stmt, SQL);

            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String year = rs.getString("year");
                String month = rs.getString("month");
                int count = rs.getInt("count");
                int money = rs.getInt("money");

                // 输出数据
                System.out.print("ID: " + id);
                System.out.print(", 名称: " + name);
                System.out.print(", 年月: " + year + "/" + month);
                System.out.print(", 个数: " + count + "， 金额: " + money);
                System.out.print("\n");
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ignore) {
            }// 什么都不做
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

    @Hystrix
    private ResultSet execute(Connection connection, Statement statement, String SQL) throws Exception {
        return statement.executeQuery(SQL);
    }
}
