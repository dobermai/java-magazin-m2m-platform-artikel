package de.dobermai.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Path("/messages")
@Produces(value = MediaType.APPLICATION_JSON)
public class Webservice {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @GET
    public List<Message> getAllMessages() {
        try {
            PreparedStatement statement = createStatement("select * from `Messages`");

            ResultSet resultSet = statement.executeQuery();

            return createMessagesFromResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Message>();
    }

    private PreparedStatement createStatement(String sql) throws SQLException {
        Connection connect = DriverManager
                .getConnection("jdbc:mysql://localhost:8889/m2mplatform?"
                        + "user=root&password=root");

        return connect.prepareStatement(sql);
    }

    @GET
    @Path("/{topic}")
    public List<Message> getMessagesForTopic(@PathParam("topic") String topic) {
        try {

            final String finalTopic = topic.replace("+", "/");

            PreparedStatement statement = createStatement("select * from `Messages` m where m.topic = ?");

            statement.setString(1, finalTopic);
            ResultSet resultSet = statement.executeQuery();

            return createMessagesFromResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Message>();
    }

    private List<Message> createMessagesFromResultSet(ResultSet resultSet) throws SQLException {
        final List<Message> messages = new ArrayList<Message>();

        while (resultSet.next()) {

            final Message message = new Message();
            message.setClientId(resultSet.getString("client"));
            message.setTopic(resultSet.getString("topic"));
            message.setPayload(resultSet.getString("payload"));
            messages.add(message);
        }

        return messages;
    }
}
