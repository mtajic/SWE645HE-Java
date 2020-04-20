package HW3;



import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;




@Path("posts")
public class SurveyResource {

	@PersistenceContext EntityManagerFactory emf; 
	EntityManager em; 
	@Resource UserTransaction utx;
	
	/*public SurveyResource() {
		emf = Persistence.createEntityManagerFactory("SWE645HW3Fixed");
    	em = emf.createEntityManager();
		
	}
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "Hello Jersey";
    }*/

	
// This method is called if XML is request
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
    	
    	/*em = emf.createEntityManager(); 
    	try {
    		utx.begin(); 
    		//em.persist(SomeEntity); 
    		//em.merge(AnotherEntity); 
    		em.remove(ThirdEntity); 
    		utx.commit();
    		} 
    	catch (Exception e) { 
    		utx.rollback();
    	}*/
    	emf = Persistence.createEntityManagerFactory("SWE645HW3Fixed");
    	em = emf.createEntityManager();
    	Query q = em.createQuery("SELECT p FROM Post p");
    	List<Post> postList = q.getResultList();
    	Set posts = new HashSet();
    	for(int i=0; i<postList.size(); i++) {
    		
    		posts.add((convertToJSON(postList.get(i))));
    	}
    	
    	JSONObject multiple = new JSONObject();
    	multiple.put("posts",posts);
    	/*Response res = Response
    		      .status(Response.Status.OK)
    		      .entity(multiple)
    		      .type(MediaType.APPLICATION_JSON)
    		      .build();*/
    	
    	System.out.println(multiple.toString());
    	//return posts.toString();
    	
   	 	Response response = Response.status(Status.OK).entity(multiple.toJSONString()).header("Access-Control-Allow-Origin", "*").build();
    	
    	return response;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response findByPk(@PathParam("id")int id) {
        
    	/*em = emf.createEntityManager(); 
    	try {
    		utx.begin(); 
    		//em.persist(SomeEntity); 
    		//em.merge(AnotherEntity); 
    		em.remove(ThirdEntity); 
    		utx.commit();
    		} 
    	catch (Exception e) { 
    		utx.rollback();
    	}*/
    	emf = Persistence.createEntityManagerFactory("SWE645HW3Fixed");
    	em = emf.createEntityManager();
    	Post p = em.find(Post.class, id);
    	
    	//convertToJSON(p);
    	
    	//JSONObject multiple = new JSONObject();
    	//multiple.put("posts",p);
    	return Response
    		      .status(Response.Status.OK)
    		      .entity(convertToJSON(p))
    		      .type(MediaType.APPLICATION_JSON)
    		      .header("Access-Control-Allow-Origin", "*")
    		      .header("Access-Control-Allow-Methods", "GET, PATH, POST, DELETE, PUT, OPTIONS, HEAD")
    		   	  .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
    		      .build();
    	
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //@Consumes("application/json")
    //@Produces(MediaType.APPLICATION_JSON)
    public Response create(String p) {
    	System.out.println("#########################");
    	System.out.println(p);
    	Post value = null;
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			value = mapper.readValue(p, Post.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//System.out.println(value.getClass().toString());
    	emf = Persistence.createEntityManagerFactory("SWE645HW3Fixed");
    	em = emf.createEntityManager();
    	em.getTransaction().begin();
    	em.persist(value);
    	em.getTransaction().commit();
    	//System.out.println(value.getId());
    	JSONObject object = new JSONObject();
    	object.put("message", "Post Added");
    	object.put("postId", value.getId());
   	 	Response response = Response.status(Status.OK).entity(object.toJSONString())
   	 			.header("Access-Control-Allow-Origin", "*").build();
    	
    	return response;
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response edit(@PathParam("id") int id , String p) {
        System.out.println(p);
        System.out.println("##################>>>>>>>>>>>>>>+++++++++++++++");
    	/*em = emf.createEntityManager(); 
    	try {
    		utx.begin(); 
    		//em.persist(SomeEntity); 
    		//em.merge(AnotherEntity); 
    		em.remove(ThirdEntity); 
    		utx.commit();
    		} 
    	catch (Exception e) { 
    		utx.rollback();
    	}*/
    	Post value = null;
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			value = mapper.readValue(p, Post.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	emf = Persistence.createEntityManagerFactory("SWE645HW3Fixed");
    	em = emf.createEntityManager();
    	em.getTransaction().begin();
    	Post pt = em.find(Post.class, id);
    	value.setId(id);
    	pt = value;
    	em.merge(pt);
    	em.getTransaction().commit();
    	
    	JSONObject object = new JSONObject();
    	object.put("message", "Updated Successfully!!!");
   	 	Response response = Response.status(Status.OK).entity(object.toJSONString())
   	 			.header("Access-Control-Allow-Origin", "*")
   	 			.header("Access-Control-Allow-Methods", "GET, PATH, POST, DELETE, PUT, OPTIONS, HEAD")
   	 	.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
   	 	.build();
    	
    	return response;
    	/*JSONObject multiple = new JSONObject();
    	multiple.put("posts",multiple);
    	return Response
    		      .status(Response.Status.OK)
    		      .entity(multiple)
    		      .type(MediaType.APPLICATION_JSON)
    		      .build();
    	*/
    }
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {
    	
    	emf = Persistence.createEntityManagerFactory("SWE645HW3Fixed");
    	em = emf.createEntityManager();
    	Post p = em.find(Post.class, id);
    	System.out.println(p.toString());
    	em.getTransaction().begin();
    	em.remove(p);
    	em.getTransaction().commit();
    	
    	JSONObject object = new JSONObject();
    	object.put("message", "Post Deleted");
   	 	Response response = Response.status(Status.OK).entity(object)
   	 			.header("Access-Control-Allow-Origin", "*")
   	 	.build();
    	
    	return response;
    }

// This method is called if HTML is request
   /* @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html> " + "<title>" + "Hello Jersey" + "</title>"
                + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
    }*/
    
    public String convertToJSON(Post pt) {
    	
    	ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(pt);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return (jsonString);
    }
}
