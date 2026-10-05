package fit.lythithuy.util;

import javax.sql.DataSource;
import java.sql.Connection;

public class DBUtil {
    private DataSource dataSource;
    public DBUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public Connection getConnection(){
        Connection conn;
        try {
            conn = dataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}
