
package server.resources;

import entities.AudioSnimak;
import entities.Kategorija;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import util.JMSUtil;
import util.ResourceUtil;

@Path("AudioResource")
public class AudioResource {
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory connection;
    
    @Resource(lookup="queue2")
    private Queue queue;
    
    private ResourceUtil resourceUtil;
    
    @PostConstruct
    public void init() {
        this.resourceUtil = new ResourceUtil(connection, queue);
    }
    
    
    @GET
    @Path("AudioSnimak/{id}/Kategorija")
    public Response getCategoryForAudio(@PathParam("id") int id) {
        String query = "SELECT k FROM AudioSnimak a JOIN a.kategorijaList k WHERE a.ida = " + id; 
        return resourceUtil.query(query, "Kategorija");
    }
    
    @GET
    @Path("AudioKategorija")
    public Response getAudioKategorija() {
        String query = "SELECT a.ida, k.idk FROM AudioSnimak a JOIN a.kategorijaList k";
        return resourceUtil.query(query, "AudioSnimak");
    }
    
    @POST
    @Path("AudioKategorija/link/{idk}/{ida}")
    public Response createAudioKategorija(@PathParam("idk") int idk, @PathParam("ida") int ida) {
        try {
            JMSContext context = connection.createContext();
            JMSProducer producer = context.createProducer();
            
            TextMessage msg = context.createTextMessage("audioKategorija");
            msg.setStringProperty("MESSAGE_TYPE", "REQUEST");
            producer.send(queue, msg);
            
            TextMessage idkMsg = context.createTextMessage(idk + "");
            idkMsg.setStringProperty("MESSAGE_TYPE", "REQUEST");
            producer.send(queue, idkMsg);
            
            TextMessage idaMsg = context.createTextMessage(ida + "");
            idaMsg.setStringProperty("MESSAGE_TYPE", "REQUEST");
            producer.send(queue, idaMsg);
            
            JMSConsumer consumer = context.createConsumer(queue, "MESSAGE_TYPE = 'RESPONSE'");
            
            Message response = consumer.receive();
            if(!(response instanceof StreamMessage)) 
                return Response.status(400, "[createAudioKategorija] Should receive stream message now").build();
            
            StreamMessage responseStream = (StreamMessage) response;
            boolean created = responseStream.readBoolean();
            if(created) {
                return Response.status(200, "Created AudioKategorija successfully.").build();
            } else {
                return Response.status(400, "Failed at creating AudioKategorija.").build();
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @POST
    @Path("AudioSnimak/JSON")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(AudioSnimak k) {
        try {
            return JMSUtil.createEntity(k,connection, queue);
        } catch (JMSException ex) {
            return Response.status(404, "Failed to create Korisnik.").build();
        }
    }
    
    @POST
    @Path("Kategorija/JSON")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPlace(Kategorija m) {
        try {
            return JMSUtil.createEntity(m,connection, queue);
        } catch (JMSException ex) {
             return Response.status(404, "Failed to create Mesto.").build();
        }
    }
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Generic methods">
    
    @GET
    @Path("{className}")
    public Response get(@PathParam("className") String className) {
        return resourceUtil.get(className);
    }
    
    @GET
    @Path("{className}/{fieldName}/{value}")
    public Response getByField(@PathParam("className") String className, @PathParam("fieldName") String fieldName, @PathParam("value") String value) {
        return resourceUtil.getByField(className, fieldName, value);
    }
    
    @PUT
    @Path("{classPath}/{id}")
    public Response update(@PathParam("id") String idStr,@PathParam("classPath") String classPath, @Context UriInfo uriInfo) {
        return resourceUtil.update(idStr, classPath, uriInfo);
    }
    
    @DELETE
    @Path("{classPath}/{id}")
    public Response delete(@PathParam("classPath") String classPath, @PathParam("id") String idStr) {
        return resourceUtil.delete(classPath, idStr);
    }
    
    @GET
    @Path("flush")
    public Response flushQueue() {
        return resourceUtil.flushQueue();
    }
    //</editor-fold>
    
}
