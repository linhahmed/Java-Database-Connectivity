package eg.edu.alexu.csd.oop.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class ConnectionImp implements Connection {
	private StatementImp statement;
	private boolean isClosed;
	private Logger log;

	public ConnectionImp(String url, String path) {
		isClosed = false;
		log = Logger.getInstance();
	}

	@Override
	public void close() {
		isClosed = true;
		statement = null;

	}

	@Override
	public Statement createStatement() {
		try {
			if (isClosed) {
				throw new SQLException("Statement is closed ..Can't do operations");
			}
			log.log.info("creating Statement.");
			statement = new StatementImp(this);

		} catch (Exception e) {
			log.log.info("Error in creating Statement");

		}

		return statement;
	}

	@Override
	public Statement createStatement(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Statement createStatement(int arg0, int arg1, int arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void abort(Executor arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearWarnings() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void commit() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Array createArrayOf(String arg0, Object[] arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Blob createBlob() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clob createClob() {
		throw new UnsupportedOperationException();
	}

	@Override
	public NClob createNClob() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML createSQLXML() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Struct createStruct(String arg0, Object[] arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getAutoCommit() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCatalog() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Properties getClientInfo() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getClientInfo(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getHoldability() {
		throw new UnsupportedOperationException();
	}

	@Override
	public DatabaseMetaData getMetaData() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getNetworkTimeout() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSchema() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getTransactionIsolation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Class<?>> getTypeMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLWarning getWarnings() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isClosed() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isReadOnly() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isValid(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nativeSQL(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CallableStatement prepareCall(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CallableStatement prepareCall(String arg0, int arg1, int arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int[] arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, String[] arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void releaseSavepoint(Savepoint arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void rollback() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void rollback(Savepoint arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAutoCommit(boolean arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCatalog(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setClientInfo(Properties arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setClientInfo(String arg0, String arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHoldability(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setNetworkTimeout(Executor arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setReadOnly(boolean arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Savepoint setSavepoint() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Savepoint setSavepoint(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSchema(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTransactionIsolation(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> arg0) {
		throw new UnsupportedOperationException();
	}

}
