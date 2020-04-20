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
	

	
// This method is called if XML is request
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
    	
    	
    	emf = Persistence.createEntityManagerFactory("SWE645HW3Fixed");
    	em = emf.createEntityManager();
    	Query q = em.createQuery("SELECT p FROM Post p");
    	List<Post> postList = q.getResultList();
    	Set posts = new HashSet();
    	
    	//converting the Post objects to JSON using a hashset.
    	for(int i=0; i<postList.size(); i++) {
    		
    		posts.add((convertToJSON(postList.get(i))));
    	}
    	
    	JSONObject multiple = new JSONObject();
    	multiple.put("posts",posts);

    	System.out.println(multiple.toString());
    	//return posts.toString();
    	
   	 	Response response = Response.status(Status.OK).entity(multiple.toJSONString()).header("Access-Control-Allow-Origin", "*").build();
    	
    	return response;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response findByPk(@PathParam("id")int id) {
        

    	emf = Persistence.createEntityManagerFactory("SWE645HW3Fixed");
    	em = emf.createEntityManager();
    	Post p = em.find(Post.class, id);
    	

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
    public Response create(String p) {
    	System.out.println("#########################");
    	System.out.println(p);
    	
    	//convert the JSON string to a Post object
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
    	
    	//Add the newly created Post to DB
    	em.persist(value);
    	em.getTransaction().commit();
    	//System.out.println(value.getId());
    	
    	
    	//return a JSON response back telling the user that the new post has been created
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
    	
    	//find the post object from DB and apply the changes.
    	Post pt = em.find(Post.class, id);
    	value.setId(id);
    	pt = value;
    	em.merge(pt);
    	em.getTransaction().commit();
    	
    	// send a JSON respond back telling the user that the update was successful
    	JSONObject object = new JSONObject();
    	object.put("message", "Updated Successfully!!!");
   	 	Response response = Response.status(Status.OK).entity(object.toJSONString())
   	 			.header("Access-Control-Allow-Origin", "*")
   	 			.header("Access-Control-Allow-Methods", "GET, PATH, POST, DELETE, PUT, OPTIONS, HEAD")
   	 	.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
   	 	.build();
    	
    	return response;
    	
    }
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {
    	
    	emf = Persistence.createEntityManagerFactory("SWE645HW3Fixed");
    	em = emf.createEntityManager();
    	
    	//Find the post object
    	Post p = em.find(Post.class, id);
    	System.out.println(p.toString());
    	em.getTransaction().begin();
    	em.remove(p);	//remove from DB
    	em.getTransaction().commit();
    	
    	//Return a JSON Response telling the user that delete was successful
    	JSONObject object = new JSONObject();
    	object.put("message", "Post Deleted");
   	 	Response response = Response.status(Status.OK).entity(object)
   	 			.header("Access-Control-Allow-Origin", "*")
   	 	.build();
    	
    	return response;
    }

    
    //Method to convert a Post object to JSON
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
