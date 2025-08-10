package util;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


public class ResourceUtil {
    private final ConnectionFactory connection;
    private final Queue queue;
    
    public ResourceUtil(ConnectionFactory connection, Queue queue) {
        this.connection = connection;
        this.queue = queue;
    }
    
    public Response query(String query, String className) {
        try {
            return JMSUtil.getWithQuery(connection, queue, className, query);
        } catch (JMSException ex) {
            return Response.status(404, "JMS ERROR").build();
        }
    }
    
    
    public Response get(String className) {
        try {
            String query = "SELECT k FROM "+ className +" k";
            return JMSUtil.getWithQuery(connection, queue, className, query);
        } catch (JMSException ex) {
            return Response.status(404, "JMS ERROR").build();
        }
    }
    
    public Response getByField(String className, String fieldName, String value) {
        try {
            String query;
            if (value.matches("-?\\d+(\\.\\d+)?")) {
                query = "SELECT k FROM " + className + " k WHERE k." + fieldName + " = " + value;
            } else {
                query = "SELECT k FROM " + className + " k WHERE k." + fieldName + " LIKE '%" + value + "%'";
            }

            return JMSUtil.getWithQuery(connection, queue, className, query);
        } catch (JMSException ex) {
            return Response.status(404, "JMS ERROR").build();
        }
    }
    
    public Response update(String idStr,String classPath,UriInfo uriInfo) {
        try {
            return JMSUtil.updateEntity(connection, queue, classPath, idStr, uriInfo);
        } catch (JMSException ex) {
            return Response.status(404, "Failed to update " + classPath +".").build();
        }
    }
    
    public Response flushQueue() {
        JMSContext context = connection.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        int num = 0;
        while ((consumer.receiveNoWait()) != null) 
            num++;
        consumer.close();
        if(num > 0)
            return Response.ok("FLUSHED " + num + " MESSAGES").build(); 
        else
            return Response.ok("NO PENDING MESSAGES").build();    
    }
    
    public Response delete(String classPath, String idStr) {
        try {
            return JMSUtil.delete(connection, queue, classPath, idStr);
        } catch (JMSException ex) {
            return Response.status(404, "Failed to delete "+ classPath +".").build();
        }
    }
}
