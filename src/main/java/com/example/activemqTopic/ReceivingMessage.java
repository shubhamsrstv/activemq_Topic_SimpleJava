package com.example.activemqTopic;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;

public class ReceivingMessage 
{
	public void receiving(int countConsume, Session session, Topic topic) throws JMSException, InterruptedException
	{
	
		for(int i=0;i<countConsume;i++)
		{
			MessageConsumer consumer = session.createConsumer(topic);
			consumer.setMessageListener(new ConsumerMessageListener("consumer"+(i+1)));
		}
	}
}
