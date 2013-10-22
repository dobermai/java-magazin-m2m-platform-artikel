package callbacks;

import com.dcsquare.hivemq.spi.callback.CallbackPriority;
import com.dcsquare.hivemq.spi.callback.events.OnPublishReceivedCallback;
import com.dcsquare.hivemq.spi.callback.exception.OnPublishReceivedException;
import com.dcsquare.hivemq.spi.message.PUBLISH;
import com.dcsquare.hivemq.spi.security.ClientData;
import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class PublishReceived implements OnPublishReceivedCallback {


    private static final Logger logger = LoggerFactory.getLogger(PublishReceived.class);

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Could not find MySQL Driver", e);
        }
    }


    @Override
    public void onPublishReceived(final PUBLISH publish, final ClientData clientData) throws OnPublishReceivedException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/m2mplatform?user=root&password=root");

            PreparedStatement statement = connection.prepareStatement("INSERT INTO `Messages` VALUES (NULL, ?, ?, ?);");

            //Unsere ClientID
            statement.setString(1, clientData.getClientId());
            //Der Topic der Message
            statement.setString(2, publish.getTopic());
            //Der Inhalt der Message als UTF-8
            statement.setString(3, new String(publish.getPayload(), Charsets.UTF_8));

            statement.execute();

        } catch (Exception e) {
            logger.error("An error occured", e);
        } finally {
            close(connection);
        }
    }

    private void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Error while closing connection", e);
        }
    }

    @Override
    public int priority() {
        return CallbackPriority.MEDIUM;
    }
}
