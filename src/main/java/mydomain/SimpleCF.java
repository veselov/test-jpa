package mydomain;

import org.h2.jdbcx.JdbcDataSource;
import org.postgresql.jdbc2.optional.SimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SimpleCF extends SimpleDataSource {

    public SimpleCF() {

        /*
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        */

        // setURL("jdbc:postgresql://localhost/datanucleus");

        /*
        NOTE! This must be tested against Postgres, and not H2.
        H2 doesn't preclude changing transaction isolation in the middle of a transaction.
         */

        setServerName("localhost");
        setDatabaseName("datanucleus");
        setUser("datanucleus");
        setPassword("datanucleus");
    }

    /**
     * Modify the connection, though without malice.
     * Connections returned from a data source are not guaranteed to be pristine.
     * @param c connection to modify
     * @throws SQLException if an oopsie happened.
     */
    private void doStuffTo(Connection c) throws SQLException {
        c.setAutoCommit(false);
        c.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        Statement s = c.createStatement();
        // if you comment this line - the test will pass.
        s.executeQuery("select 1");
        s.close();
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection c = super.getConnection();
        doStuffTo(c);
        return c;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection c = super.getConnection(username, password);
        doStuffTo(c);
        return c;
    }
}
