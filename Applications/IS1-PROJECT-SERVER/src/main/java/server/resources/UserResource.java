package server.resources;

import entities.Korisnik;
import entities.Mesto;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
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

@Path("UserResource")
public class UserResource {
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory connection;
    
    @Resource(lookup="queue1")
    private Queue queue;
    
    private ResourceUtil resourceUtil;
    
    @PostConstruct
    public void init() {
        this.resourceUtil = new ResourceUtil(connection, queue);
    }
    
    @POST
    @Path("Korisnik/JSON")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(Korisnik k) {
        try {
            return JMSUtil.createEntity(k,connection, queue);
        } catch (JMSException ex) {
            return Response.status(404, "Failed to create Korisnik.").build();
        }
    }
    
    @POST
    @Path("Mesto/JSON")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPlace(Mesto m) {
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
