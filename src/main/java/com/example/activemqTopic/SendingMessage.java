package com.example.activemqTopic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

public class SendingMessage
{
	public void sending(int countSend, String[] payload, Session session, Topic topic) throws JMSException, InterruptedException
	{
		for(int i=0;i<countSend;i++)
		{
			Message msg = session.createTextMessage(payload[i]);
			MessageProducer producer = session.createProducer(topic);
			System.out.println("Sending text '" + payload[i] + "'");
			producer.send(msg);
			Thread.sleep(1000);
		}
}
}
