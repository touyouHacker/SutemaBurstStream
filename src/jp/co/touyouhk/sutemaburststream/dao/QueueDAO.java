package jp.co.touyouhk.sutemaburststream.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.touyouhk.sutemaburststream.Constant;
import jp.co.touyouhk.util.UniversalUtil;

public class QueueDAO {

	private String queueTableHeader = "queue_";
	private String tableName;
	private Connection con = null;

	public QueueDAO(String itaName) {
		// create a database connection
		try {
			con = DriverManager.getConnection("jdbc:sqlite:" + "sutemaBurstStream_Queue.sqlite");

			tableName = queueTableHeader + itaName;

			tableCheck();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public QueueDAO(String itaName, String inputSqliteFileName) {
		// create a database connection
		try {
			con = DriverManager.getConnection("jdbc:sqlite:" + inputSqliteFileName);

			tableName = queueTableHeader + itaName;

			tableCheck();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void closeHandler() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * 指定された板のテーブルがあるかないか探してなければ作成
	 * @param itaName
	 * @throws SQLException
	 */
	private void tableCheck() throws SQLException {
		//http://stackoverflow.com/questions/2942788/check-if-table-exists
		Statement statement = con.createStatement();
		DatabaseMetaData dbm = con.getMetaData();
		//check if "employee" table is there
		ResultSet tables = dbm.getTables(null, null, tableName, null);
		if (tables.next()) {
			// Table exists
		}
		else {
			// Table does not exist
			statement.executeUpdate("create table " + tableName
					+ " (id PRIMARY KEY,title,match_key,ins_date,update_date,note)");
		}

		statement.close();
	}

	/**
	 * スレッドIDをキーにすでにDB登録したか検索する
	 * @param itaName
	 * @param threadId
	 */
	public boolean findById(int threadId) {

		Statement stmt;
		ResultSet rs;

		try {
			stmt = con.createStatement();
			String query = "SELECT * FROM " + tableName + " WHERE id = " + threadId;
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				return true;
			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 処理したスレをDBに登録する
	 * @param itaName
	 * @param threadId
	 * @param title
	 * @param keyword
	 */
	public void regist(int threadId, String title, String keyword) {
		try {
			String query = "INSERT INTO " + tableName;
			query += " (id,title,match_key,ins_date,update_date,note) VALUES (?,?,?,?,?,?) ";

			PreparedStatement stmt = con.prepareStatement(query);

			stmt.setInt(1, threadId);
			stmt.setString(2, title);
			stmt.setString(3, keyword);
			stmt.setString(4,UniversalUtil.nowDate());
			stmt.setString(5,UniversalUtil.nowDate());
			stmt.setString(6, Constant.SOFTWARE_NAME);

			stmt.executeUpdate();

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

}


