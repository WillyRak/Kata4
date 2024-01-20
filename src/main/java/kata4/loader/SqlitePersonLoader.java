package kata4.loader;

import kata4.person.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlitePersonLoader implements PersonLoader{

    private final Connection connection;
    private final static String QueryAll = "SELECT * FROM people";

    public SqlitePersonLoader(Connection connection){
        this.connection = connection;
    }

    public static PersonLoader with(Connection connection){
        return new SqlitePersonLoader(connection);
    }

    @Override
    public List<Person> load() {
        try {
            return loadFromDatabase(queryAll());
        }catch (SQLException e){
            return Collections.emptyList();
        }
    }

    private List<Person> loadFromDatabase(ResultSet resultSet) throws SQLException {
        List<Person> result = new ArrayList<>();
        while(resultSet.next()) {
            result.add(personFrom(resultSet));
        }
        return result;
    }

    private Person personFrom(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getInt("Id"),
                resultSet.getDouble("Height"),
                resultSet.getDouble("Weight")
        );
    }

    private ResultSet queryAll() throws SQLException {
        return connection.createStatement().executeQuery(QueryAll);
    }
}
