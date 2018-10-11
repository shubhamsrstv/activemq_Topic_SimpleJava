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
		System.out.println("Enter number of consumers : ");
		int count = sc.nextInt();
		sc.close();
		Connection connection = null;
		try 
		{
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("Testing");   
			for(int i=0;i<count;i++)
			{
				MessageConsumer consumer = session.createConsumer(topic);
				consumer.setMessageListener(new ConsumerMessageListener("consumer"+(i+1)));
			}
			connection.start();    
			
			String payload = "Shubham Srivastava is great!!!";
			Message msg = session.createTextMessage(payload);
			MessageProducer producer = session.createProducer(topic);
			System.out.println("Sending text '" + payload + "'");
			producer.send(msg);
			Thread.sleep(1000);
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
