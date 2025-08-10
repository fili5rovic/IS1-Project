package server.resources;

import entities.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import util.JMSUtil;

@Path("")
public class JavaEE8Resource {
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory connection;
    
    @Resource(lookup="queue1")
    private Queue queue1;
   
    @Resource(lookup="queue2")
    private Queue queue2;
    
    @Resource(lookup="queue3")
    private Queue queue3;
    
    @GET
    public Response ping() {
        return Response.ok("ping").build();
    }
    
    public Queue getQueueForClass(String className) {
        for(String s : new String[]{"Korisnik","Mesto"}) {
            if(className.equals(s))
                return queue1;
        }
        for(String s: new String[]{"AudioSnimak","AudioKategorijaPK","AudioKategorija","Kategorija"}) {
            if(className.equals(s))
                return queue2;
        }
        return queue3;
    }
    
    @GET
    @Path("test/{id}")
    public Response getKorisnici(@PathParam("id") int id) {
        try {
            String className = "Korisnik";
            String query = "SELECT k FROM Korisnik k WHERE k.idk = " + id;

            return JMSUtil.getWithQuery(connection, queue1, className, query);
        } catch (JMSException ex) {
            System.out.println("JMS Exception!");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the request.")
                    .build();
        }
    }
    
    @GET
    @Path("AudioKategorija/all")
    public Response getAudioKategorija() {
        try {
            String className = "Korisnik";
            String query = "SELECT a.ida, k.idk FROM AudioSnimak a JOIN a.kategorijaList k";

            return JMSUtil.getWithQuery(connection, queue2, className, query);
        } catch (JMSException ex) {
            System.out.println("JMS Exception!");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the request.")
                    .build();
        }
    }
    
    @GET
    @Path("{className}/getAll")
    public Response getAll(@PathParam("className") String className) {
        System.out.println(className);
        Queue queue = getQueueForClass(className);
        try {
            JMSContext context = connection.createContext();
            JMSProducer producer = context.createProducer();
            
            TextMessage msg = context.createTextMessage("getAll");
            msg.setStringProperty("MESSAGE_TYPE", "REQUEST");
            producer.send(queue, msg);
            
            TextMessage nameMsg = context.createTextMessage(className);
            nameMsg.setStringProperty("MESSAGE_TYPE", "REQUEST");
            producer.send(queue, nameMsg);
            
            JMSConsumer consumer = context.createConsumer(queue, "MESSAGE_TYPE = 'RESPONSE'");
            
            Message response = consumer.receive();
            if(!(response instanceof TextMessage))
                return Response.status(400, "Should only receive text message requests from client").build();
            
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
        } catch (JMSException ex) {
            System.out.println("JMS EXCEPTION");
        }
        
        return Response.status(400, "Couldn't find").build();
    }
    
    @POST
    @Path("Korisnik/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(Korisnik k) {
        try {
            return JMSUtil.createEntity(k,connection, queue1);
        } catch (JMSException ex) {
            return Response.status(404, "Failed to create Korisnik.").build();
        }
    }
    
    @POST
    @Path("Mesto/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPlace(Mesto m) {
        try {
            return JMSUtil.createEntity(m,connection, queue1);
        } catch (JMSException ex) {
            return Response.status(404, "Failed to create Mesto.").build();
        }
    }
    
    @POST
    @Path("Kategorija/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCategory(Kategorija k) {
        try {
            return JMSUtil.createEntity(k,connection, queue2);
        } catch (JMSException ex) {
            return Response.status(404, "Failed to create Kategorija.").build();
        }
    }
    
    @POST
    @Path("AudioSnimak/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAudio(AudioSnimak a) {
        try {
            return JMSUtil.createEntity(a,connection, queue2);
        } catch (JMSException ex) {
            return Response.status(404, "Failed to create AudioSnimak.").build();
        }
    }
    
    @POST
    @Path("AudioKategorija/create/{idk}/{ida}")
    public Response createAudioKategorija(@PathParam("idk") int idk, @PathParam("ida") int ida) {
        Queue queue = queue2;

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
    @Path("Paket/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPaket(Paket p) {
        try {
            return JMSUtil.createEntity(p,connection, queue3);
        } catch (JMSException ex) {
            return Response.status(404, "Failed to create Paket.").build();
        }
    }
    
    @POST
    @Path("Pretplata/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPretplata(Pretplata p) {
        try {
            return JMSUtil.createEntity(p,connection, queue3);
        } catch (JMSException ex) {
            return Response.status(404, "Failed to create Pretplata.").build();
        }
    }
    
    @POST
    @Path("Slusanje/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSlusanje(Slusanje s) {
        try {
            return JMSUtil.createEntity(s,connection, queue3);
        } catch (JMSException ex) {
            return Response.status(404, "Failed to create Slusanje.").build();
        }
    }
    
    @POST
    @Path("Ocena/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOcena(Ocena o) {
        try {
            return JMSUtil.createEntity(o,connection, queue3);
        } catch (JMSException ex) {
            return Response.status(404, "Failed to create Ocena.").build();
        }
    }
    
    
    
    @POST
    @Path("Omiljeni/create/{idk}/{ida}")
    public Response createFavorite(@PathParam("idk") int idk, @PathParam("ida") int ida) {
        Queue queue = queue3;

        try {
            JMSContext context = connection.createContext();
            JMSProducer producer = context.createProducer();
            
            TextMessage msg = context.createTextMessage("favorite");
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
                return Response.status(400, "[deleteUser] Should receive stream message now").build();
            
            StreamMessage responseStream = (StreamMessage) response;
            boolean created = responseStream.readBoolean();
            if(created) {
                return Response.status(200, "Created favorite successfully.").build();
            } else {
                return Response.status(400, "Failed at creating favorite.").build();
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @GET
    @Path("Kategorija/get/{id}")
    public Response getCategoryForAudio(@PathParam("id") int id) {
        try {
            String className = "Kategorija";
            String query = "SELECT k FROM AudioSnimak a JOIN a.kategorijaList k WHERE a.ida = " + id; 

            return JMSUtil.getWithQuery(connection, queue2, className, query);
        } catch (JMSException ex) {
            System.out.println("JMS Exception!");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the request.")
                    .build();
        }
    }
    
    @GET
    @Path("Pretplata/get/{id}")
    public Response getSubscriptionsForUser(@PathParam("id") int id) {
        try {
            String className = "Pretplata";
            String query = "SELECT p FROM Pretplata p WHERE p.idk.idk = " + id;

            return JMSUtil.getWithQuery(connection, queue3, className, query);
        } catch (JMSException ex) {
            System.out.println("JMS Exception!");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the request.")
                    .build();
        }
    }
    
    @GET
    @Path("Slusanje/get/{id}")
    public Response getListeningsForAudio(@PathParam("id") int id) {
        try {
            String className = "Slusanje";
            String query = "SELECT s FROM Slusanje s WHERE s.slusanjePK.idAudio = " + id;

            return JMSUtil.getWithQuery(connection, queue3, className, query);
        } catch (JMSException ex) {
            System.out.println("JMS Exception!");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the request.")
                    .build();
        }
    }
    
    @GET
    @Path("Ocena/get/{id}")
    public Response getRatingsForAudio(@PathParam("id") int id) {
        try {
            String className = "Ocena";
            String query = "SELECT o FROM Ocena o WHERE o.audioSnimak.ida = " + id;

            return JMSUtil.getWithQuery(connection, queue3, className, query);
        } catch (JMSException ex) {
            System.out.println("JMS Exception!");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the request.")
                    .build();
        }
    }
    
    @GET
    @Path("Omiljeni/get/{id}")
    public Response getFavoritesForUser(@PathParam("id") int id) {
        try {
            String className = "AudioSnimak";
            String query = "SELECT a FROM AudioSnimak a JOIN a.korisnikList k WHERE k.idk = " + id;

            return JMSUtil.getWithQuery(connection, queue3, className, query);
        } catch (JMSException ex) {
            System.out.println("JMS Exception!");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the request.")
                    .build();
        }
    }

    @PUT
    @Path("update/{className}/{id}")
    public Response updateEntity(@PathParam("className") String className, @PathParam("id") String idStr, @Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        Queue queue = getQueueForClass(className);
        try {
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
                 return Response.status(400, "[updateUser] Should only send one object").build();
            
            
            response = consumer.receive();

            if(!(response instanceof ObjectMessage))
                return Response.status(400, "[updateUser] Should only receive object message requests from client").build();

            ObjectMessage ret = (ObjectMessage) response;
            return Response.ok(ret.getObject()).build();
            
        } catch (JMSException ex) {
            System.out.println("JMS EXCEPTION!");
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @DELETE
    @Path("{className}/delete/{id}")
    public Response delete(@PathParam("className") String className, @PathParam("id") String idStr) {
        Queue queue = getQueueForClass(className);
        try {
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
                return Response.status(400, "[deleteUser] Should receive stream message now").build();
            
            StreamMessage responseStream = (StreamMessage) response;
            boolean deleted = responseStream.readBoolean();
            
            if(deleted) 
                return Response.ok("[SUCCESS] Deleted " + className + " with ID " + idStr).build();
            else
                return Response.status(200, "Couldn't find "+ className + ", id=" + idStr).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @GET
    @Path("flush")
    public Response flushQueue() {
        JMSContext context = connection.createContext();
        JMSConsumer consumer1 = context.createConsumer(queue1);
        JMSConsumer consumer2 = context.createConsumer(queue2);
        JMSConsumer consumer3 = context.createConsumer(queue3);
        int num = 0;
        while ((consumer1.receiveNoWait()) != null || (consumer2.receiveNoWait() != null) || (consumer3.receiveNoWait() != null)) {
            num++;
        }
        consumer1.close();
        consumer2.close();
        consumer3.close();
        if(num > 0)
            return Response.ok("FLUSHED " + num + " MESSAGES").build(); 
        else
            return Response.ok("NO PENDING MESSAGES").build();    
        
    }


    
}
