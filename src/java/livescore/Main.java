/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package livescore;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 *
 * @author tleku
 */
public class Main {

    @Resource(mappedName = "jms/SimpleJMSTopic")
    private static Topic simpleJMSTopic;

    @Resource(mappedName = "jms/ConnectionFactory")
    private static ConnectionFactory connectionFactory;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection connection = null;
        Destination dest = simpleJMSTopic;
        
        try {
            connection = connectionFactory.createConnection();
            
            Session  session = connection.createSession(
                    false,
                    Session.AUTO_ACKNOWLEDGE
            );
            MessageProducer producer = session.createProducer(dest);
            TextMessage message = session.createTextMessage();
            Scanner sc = new Scanner(System.in);
            System.out.println("To end program, Type q or Q, then <return>");
            while (true) {
                System.out.print("Enter Live Score: ");
                String score = sc.nextLine();
                if ((score.equals("Q")) || (score.equals("q"))) {
                    producer.send(session.createMessage());
                    break;
                }
                else {
                    message.setText("Enter Live Score " + score);
                    producer.send(message);
                }
       
            }
        }
        catch (JMSException e) {
            System.err.println("Exception occurred: " + e.toString());
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                }
            }
        }
    }
    
}
