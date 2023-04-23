/**
 * Created by Nadun Kawishika
 * date :
 * time :
 * project name :IntelliJ IDEA
 */
package lk.hnkm.rohanarenting.utill;

import lk.hnkm.rohanarenting.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CruidUtil {
    public static <T>T execute(String sql, Object...args) throws SQLException {
       PreparedStatement preparedStatement= DBConnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
           preparedStatement.setObject(i+1,args[i]);
        }
        if(sql.startsWith("SELECT")||sql.startsWith("select")){
           return (T) preparedStatement.executeQuery();
        }else {
           return (T)(Boolean)( 0 < preparedStatement.executeUpdate());
        }
    }
}
