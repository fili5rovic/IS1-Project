package util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class JMSUtil {
    
    public static Response getWithQuery(ConnectionFactory connection, Queue queue, String className, String query) throws JMSException {
        JMSContext context = connection.createContext();
        JMSProducer producer = context.createProducer();

        TextMessage msg = context.createTextMessage("getQueried");
        msg.setStringProperty("MESSAGE_TYPE", "REQUEST");
        producer.send(queue, msg);
        
        TextMessage nameMsg = context.createTextMessage(className);
        nameMsg.setStringProperty("MESSAGE_TYPE", "REQUEST");
        producer.send(queue, nameMsg);
        
        TextMessage queryMsg = context.createTextMessage(query);
        queryMsg.setStringProperty("MESSAGE_TYPE", "REQUEST");
        producer.send(queue, queryMsg);
        
        JMSConsumer consumer = context.createConsumer(queue, "MESSAGE_TYPE = 'RESPONSE'");
            
        Message response = consumer.receive();
        if(!(response instanceof TextMessage))
            return Response.status(400, "Should receive the number of objects now.").build();

        TextMessage txtNumObj = (TextMessage) response;

        int numberOfObjects = Integer.parseInt(txtNumObj.getText());

        Object[] objects = new Object[numberOfObjects];

        for (int i = 0; i < numberOfObjects; i++) {
            Message received = consumer.receive();

            if(!(received instanceof ObjectMessage)) 
                return Response.status(400, "Should receive object message after receiving number of objects being sent").build();

            ObjectMessage objMsg = (ObjectMessage) received;
            Serializable object = objMsg.getObject();
            objects[i] = object;
        }
        
        return Response.ok(objects).build();
    }
    
    public static Response createEntity(Serializable entity,ConnectionFactory connection, Queue queue) throws JMSException {
        JMSContext context = connection.createContext();
        JMSProducer producer = context.createProducer();

        TextMessage msg = context.createTextMessage("create");
        msg.setStringProperty("MESSAGE_TYPE", "REQUEST");

        producer.send(queue, msg);

        ObjectMessage objMsg = context.createObjectMessage(entity);
        objMsg.setStringProperty("MESSAGE_TYPE", "REQUEST");
        producer.send(queue, objMsg);

        JMSConsumer consumer = context.createConsumer(queue, "MESSAGE_TYPE = 'RESPONSE'");

        Message response = consumer.receive();
        if(!(response instanceof TextMessage)) 
             return Response.status(400, "[createUser] Should receive text message (number of objects being sent)").build();

        if(!((TextMessage)response).getText().equals("1")) 
             return Response.status(400, "[createUser] Should only send one object").build();

        response = consumer.receive();

        if(!(response instanceof ObjectMessage))
            return Response.status(400, "[createUser] Should only receive object message requests from client").build();

        ObjectMessage ret = (ObjectMessage) response;
        return Response.ok(ret.getObject()).build();

    }
    
    public static Response updateEntity(ConnectionFactory connection, Queue queue, String className, String idStr, UriInfo uriInfo) throws JMSException {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        JMSContext context = connection.createContext();
        JMSProducer producer = context.createProducer();

        TextMessage msg = context.createTextMessage("update");
        msg.setStringProperty("MESSAGE_TYPE", "REQUEST");
        producer.send(queue, msg);

        TextMessage classMsg = context.createTextMessage(className);
        classMsg.setStringProperty("MESSAGE_TYPE", "REQUEST");
        producer.send(queue, classMsg);

        TextMessage idMsg = context.createTextMessage(idStr);
        idMsg.setStringProperty("MESSAGE_TYPE", "REQUEST");
        producer.send(queue, idMsg);

        HashMap<String, List<String>> paramsMap = new HashMap<>(queryParams);
        ObjectMessage paramsMsg = context.createObjectMessage((Serializable) paramsMap);
        paramsMsg.setStringProperty("MESSAGE_TYPE", "REQUEST");
        producer.send(queue, paramsMsg);

        JMSConsumer consumer = context.createConsumer(queue, "MESSAGE_TYPE = 'RESPONSE'");

        Message response = consumer.receive();
        if(!(response instanceof TextMessage)) 
             return Response.status(400, "[updateUser] Should receive text message (number of objects being sent)").build();

        if(!((TextMessage)response).getText().equals("1")) 
             return Response.status(400, "[updateUser] The number of objects being sent should be 1, but is instead: " + ((TextMessage)response).getText()).build();


        response = consumer.receive();

        if(!(response instanceof ObjectMessage))
            return Response.status(400, "[updateUser] Should only receive object message requests from client").build();

        ObjectMessage ret = (ObjectMessage) response;
        
        return Response.ok(ret.getObject()).build();
    }
    
    public static Response delete(ConnectionFactory connection, Queue queue, String className, String idStr) throws JMSException {
        JMSContext context = connection.createContext();
        JMSProducer producer = context.createProducer();

        TextMessage msg = context.createTextMessage("delete");
        msg.setStringProperty("MESSAGE_TYPE", "REQUEST");
        producer.send(queue, msg);

        TextMessage classMsg = context.createTextMessage(className);
        classMsg.setStringProperty("MESSAGE_TYPE", "REQUEST");
        producer.send(queue, classMsg);

        TextMessage idMsg = context.createTextMessage(idStr);
        idMsg.setStringProperty("MESSAGE_TYPE", "REQUEST");
        producer.send(queue, idMsg);

        JMSConsumer consumer = context.createConsumer(queue, "MESSAGE_TYPE = 'RESPONSE'");

        Message response = consumer.receive();
        if(!(response instanceof StreamMessage)) 
            return Response.status(400, "[delete] Should receive stream message now").build();

        StreamMessage responseStream = (StreamMessage) response;
        boolean deleted = responseStream.readBoolean();

        if(deleted) 
            return Response.ok("[SUCCESS] Deleted " + className + " with ID " + idStr).build();
        else
            return Response.status(200, "Couldn't find "+ className + ", id=" + idStr).build();
    }
}
