package com.example.activemqTopic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URISyntaxException;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import javax.jms.Topic;

@SpringBootApplication
public class ActivemqTopicApplication
{
	public static void main(String[] args) throws URISyntaxException, Exception 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number of messages to send : ");
		int countSend = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter number of consumers : ");
		int countConsume = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the message(s) to send : ");
		String[] payload = new String[countSend];
		for(int i=0;i<countSend;i++)
		{
			payload[i] = sc.nextLine();
		}
		sc.close();
		Connection connection = null;
		try 
		{
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("Testing");
			
			ReceivingMessage receiving = new ReceivingMessage();
			receiving.receiving(countConsume, session, topic);
		
			connection.start();
		
			SendingMessage sending = new SendingMessage();
			sending.sending(countSend, payload, session, topic);
		
			session.close();
		}
		finally
		{
			if (connection != null) 
			{
				connection.close();
			}
		}
		
	}
}
