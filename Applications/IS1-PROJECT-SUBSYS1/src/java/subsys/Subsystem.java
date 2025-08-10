package subsys;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public abstract class Subsystem {
    protected ConnectionFactory connection;
    protected Queue queue;
    
    protected JMSContext context;
    protected JMSProducer producer;
    protected JMSConsumer consumer;
    
    protected JPAManager jpaManager;
    
    public abstract void init();
    
    protected void init(String queueName) {
        try {
            Context ctx = getContext();
            
            connection = (ConnectionFactory) ctx.lookup("jms/__defaultConnectionFactory");
            queue = (Queue) ctx.lookup(queueName);
            
            context = connection.createContext();
            producer = context.createProducer();
            consumer = context.createConsumer(queue, "MESSAGE_TYPE = 'REQUEST'");
            
            jpaManager = new JPAManager();
            
            System.out.println("Subsystem initialized.");
        } catch (NamingException e) {
            System.err.println("Error initializing JMS resources: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void listen() throws JMSException {
        discardPreviousRequests();
        while(true) {
            System.out.println("Waiting for server request...");
            Message request = consumer.receive();
            
            if(!(request instanceof TextMessage)) {
                throw new JMSException("[JMS_ERROR] Subsystem should only receive text message requests.");
            }
            
            TextMessage codeMsg = (TextMessage) request;
            
            handleMessageCodeCalls(codeMsg.getText());
        }
        
    }
    
    protected abstract void handleMessageCodeCalls(String codeName) throws JMSException;
    
    private void discardPreviousRequests() {
        Message msg;
        while ((msg = consumer.receiveNoWait()) != null) {  // 🔹 Receive and discard
            System.out.println("Flushed message: " + msg);
        }
    }
    
    protected void sendAllObjects(List<Serializable> objects) throws JMSException {
        TextMessage numOfObjectsBeingSent = context.createTextMessage(objects.size() + "");
        numOfObjectsBeingSent.setStringProperty("MESSAGE_TYPE", "RESPONSE");

        producer.send(queue, numOfObjectsBeingSent);

        for(Serializable s : objects) {
            ObjectMessage msg = context.createObjectMessage(s);
            msg.setStringProperty("MESSAGE_TYPE", "RESPONSE");

            producer.send(queue, msg);
        }
        System.out.println("[SUCCESS] Number of objects sent: " + numOfObjectsBeingSent.getText());
    }
    
    protected void handleGetQueried() {
        try {
            System.out.println("Waiting for entity name...");
            Message msg = consumer.receive();
            if(!(msg instanceof TextMessage)) {
                System.out.println("[ERROR] Expecting TextMessage (entity name)");
                return;
            }
            TextMessage txtMsg = (TextMessage) msg;
            String className = txtMsg.getText();
            
            System.out.println("Waiting for query...");
            Message queryMsg = consumer.receive();
            if(!(queryMsg instanceof TextMessage)) {
                System.out.println("[ERROR] Expecting TextMessage (query)");
                return;
            }
            TextMessage queryTxt = (TextMessage) queryMsg;
            String query = queryTxt.getText();
            
            sendAllObjects(jpaManager.get(className, query));
        } catch (JMSException ex) {
            System.out.println("JMS EXCEPTION"); 
       }
    }
    
    protected void handleGetAll() {
        try {
            System.out.println("Waiting for entity name...");
            Message msg = consumer.receive();
            if(!(msg instanceof TextMessage)) {
                System.out.println("[ERROR] Expecting TextMessage (entity name)");
                return;
            }
            TextMessage txtMsg = (TextMessage) msg;
            String entityName = txtMsg.getText();
            sendAllObjects(jpaManager.getAll(entityName));
        } catch (JMSException ex) {
            System.out.println("JMS EXCEPTION"); 
       }
        
    }
    
    protected void handleCreation() {
        try {
            System.out.println("Waiting for server object...");
            Message request = consumer.receive();
            if(!(request instanceof ObjectMessage)) {
                System.out.println("[ERROR] Should be object message from server");
                return;
            }
            ObjectMessage objMsg = (ObjectMessage) request;
            Serializable k = objMsg.getObject();
            sendAllObjects(jpaManager.persistObject(k));
        } catch (JMSException ex) {
            System.out.println("[JMS_EXCEPTION]");
        }
        
    }
    
    protected void handleUpdate() {
        try {
            System.out.println("Waiting for class name...");
            Message request = consumer.receive();
            
            if(!(request instanceof TextMessage)) {
                System.out.println("[ERROR] Expecting TextMessage (class name)");
                return;
            }
            TextMessage classNameMsg = (TextMessage) request;
            String className = classNameMsg.getText();
            
            request = consumer.receive();
            
            if(!(request instanceof TextMessage)) {
                System.out.println("[ERROR] Expecting TextMessage (id)");
                return;
            }
            TextMessage idMsg = (TextMessage) request;
            int id = Integer.parseInt(idMsg.getText());
            
            request = consumer.receive();
            if(!(request instanceof ObjectMessage)) {
                System.out.println("[ERROR] Expecting ObjectMessage [HashMap]");
                return;
            }
            ObjectMessage objMsg = (ObjectMessage) request;
            if(!(objMsg.getObject() instanceof HashMap)) {
                System.out.println("[ERROR] Expecting HashMap object.");
            }

            HashMap<String,List<String>> params = (HashMap<String,List<String>>) objMsg.getObject();
            
            sendAllObjects(jpaManager.update(className, id, params));
            
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    protected void handleDelete() {
        try {
            System.out.println("Waiting for class name...");
            Message request = consumer.receive();
            
            if(!(request instanceof TextMessage)) {
                System.out.println("[ERROR] Expecting TextMessage (class name)");
                return;
            }
            TextMessage classNameMsg = (TextMessage) request;
            String className = classNameMsg.getText();
            
            System.out.println("Waiting for id...");
            request = consumer.receive();
            
            if(!(request instanceof TextMessage)) {
                System.out.println("[ERROR] Expecting TextMessage (id)");
                return;
            }
            TextMessage idMsg = (TextMessage) request;
            int id = Integer.parseInt(idMsg.getText());
            
            boolean deleted = jpaManager.delete(className, id);
            StreamMessage retMsg = context.createStreamMessage();
            retMsg.writeBoolean(deleted);
            retMsg.setStringProperty("MESSAGE_TYPE", "RESPONSE");
            producer.send(queue, retMsg);
            System.out.println("Sent back: " + deleted);
            
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void wrongCode() throws JMSException {
        TextMessage numOfObjectsBeingSent = context.createTextMessage("0");
        numOfObjectsBeingSent.setStringProperty("MESSAGE_TYPE", "RESPONSE");

        producer.send(queue, numOfObjectsBeingSent);
        System.out.println("[BAD_CODE] Number of objects sent: 0");
    }
    
    
    
    protected static Context getContext() {
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, 
                         "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty(Context.URL_PKG_PREFIXES, 
                         "com.sun.enterprise.naming");

        try {
            Context ctx = new InitialContext(props);
            return ctx;
        } catch (NamingException ex) {
            System.out.println("Naming exception");
            return null;
        }
    }
    

}
