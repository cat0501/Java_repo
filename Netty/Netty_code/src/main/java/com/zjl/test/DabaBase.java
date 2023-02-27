package com.zjl.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DabaBase {
    //private final String url = "jdbc:oracle:thin:@10.11.3.33:1521:evmsc";
    ////服务器地址：，端口号：1521，数据库实例名字：zhyl。
    //private final String username = "ev";
    //private final String pw = "ev";
    //private Connection conn = null;

    private final String url = "jdbc:hive2://10.11.14.32:10000/datacenter";
    private final String username = "root";
    private final String pw = "";
    private Connection conn = null;


    //用户名字和密码是自己建立的。
    public Connection OpenConn() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try {
                conn = DriverManager.getConnection(url, username, pw);
                System.out.println("conn------>" + conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public ResultSet executeQuery(String sql) {
        DabaBase db = new DabaBase();
        ResultSet rs = null;
        Connection con = db.OpenConn();
        try {
            Statement sm = con.createStatement();
            rs = sm.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 获取数据库中所有表的表名，并添加到列表结构中。
    public List getTableNameList(Connection conn) throws SQLException {
        DatabaseMetaData dbmd = conn.getMetaData();
        //访问当前用户ANDATABASE下的所有表
        ResultSet rs = dbmd.getTables("null", "datacenter", null, new String[]{"TABLE"});
        //System.out.println("kkkkkk"+dbmd.getTables("null", "%", "%", new String[] { "TABLE" }));
        List tableNameList = new ArrayList();
        while (rs.next()) {
            tableNameList.add(rs.getString("TABLE_NAME"));
        }
        return tableNameList;
    }

    // 获取数据表中所有列的列名，并添加到列表结构中。
    public List getColumnNameList(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData dbmd = conn.getMetaData();
        ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
        List columnNameList = new ArrayList();
        while (rs.next()) {
            columnNameList.add(rs.getString("COLUMN_NAME"));
        }
        return columnNameList;
    }

    public static void main(String s[]) throws SQLException {
        DabaBase dbConn = new DabaBase();
        Connection conn = dbConn.OpenConn();
        if (conn == null)
            System.out.println("连接失败");
        else
            System.out.println("连接成功");
        try {
            List tableList = dbConn.getTableNameList(conn);//取出当前用户的所有表
            //List tableList = dbConn.getColumnNameList(conn, "LOGIN");//表名称必须是大写的，取出当前表的所有列
            System.out.println(tableList.size());

            for (Object object : tableList) {
                String ss = (String) object;
                System.out.println(ss);
            }
            System.out.println("----------------------------------------");
            List baseArea = dbConn.getColumnNameList(conn, "ods_pack_model_sy");
            for (Object o : baseArea) {
                String ss = (String) o;
                System.out.println(ss);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
