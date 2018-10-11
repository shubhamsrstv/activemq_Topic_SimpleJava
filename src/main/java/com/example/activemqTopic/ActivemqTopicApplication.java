package com.example.activemqTopic;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URISyntaxException;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

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
			for(int i=0;i<countConsume;i++)
			{
				MessageConsumer consumer = session.createConsumer(topic);
				consumer.setMessageListener(new ConsumerMessageListener("consumer"+(i+1)));
			}
			connection.start();    
			for(int i=0;i<countSend;i++)
			{
				Message msg = session.createTextMessage(payload[i]);
				MessageProducer producer = session.createProducer(topic);
				System.out.println("Sending text '" + payload[i] + "'");
				producer.send(msg);
				Thread.sleep(1000);
			}
			
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
