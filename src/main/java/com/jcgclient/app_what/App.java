package com.jcgclient.app_what;

import java.net.UnknownHostException;

import com.jcg.client.app_what.sidetrain.User;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

/**
 * Hello world!
 *
 */
// My Kavka  History
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "This client connects to local Mongo DB " );

        User user = createUser();
		DBObject doc = createDBObject(user);
        // or
        try 
        {
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB("bzippererjavadb");
			
			DBCollection col = db.getCollection("users");
			/*
		    //create user
			WriteResult result = col.insert(doc);
			System.out.println(result.getUpsertedId());
			System.out.println(result.getN());
			System.out.println(result.isUpdateOfExisting());
			System.out.println(result.getLastConcern());
				*/
			//read example
			
			// The new User is a DBObject Wrapped instance
			DBObject query = BasicDBObjectBuilder.start().add("_id", user.getId()).get();
			user.setName("Benjamin Zipperer ");
			user.setRole("SW Engineer");
			doc = createDBObject(user);
			WriteResult nresult = col.update(query, doc); 
			
			
			DBCursor cursor = col.find(query);
			while(cursor.hasNext()){
					System.out.println(cursor.next());
				}
				/*
			//update example
			user.setName("Pankaj Kumar");
			doc = createDBObject(user);
			result = col.update(query, doc);
			System.out.println(result.getUpsertedId());
			System.out.println(result.getN());
			System.out.println(result.isUpdateOfExisting());
			System.out.println(result.getLastConcern());
				
			//delete example
			result = col.remove(query);
			System.out.println(result.getUpsertedId());
			System.out.println(result.getN());
			System.out.println(result.isUpdateOfExisting());
			System.out.println(result.getLastConcern());
			*/	
			//close resources
			mongoClient.close();
			
		} 
        catch(MongoException e)
        {
        	e.printStackTrace();
        }
        catch (UnknownHostException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // should use this always

        // or, to connect to a replica set, with auto-discovery of the primary
        /*MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27017),
                                      new ServerAddress("localhost", 27018),
                                      new ServerAddress("localhost", 27019)));
       */
      
    }
    private static DBObject createDBObject(User user) 
    {
		BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
								
		docBuilder.append("_id", user.getId());
		docBuilder.append("name", user.getName());
		docBuilder.append("role", user.getRole());
		docBuilder.append("isEmployee", user.isEmployee());
		return docBuilder.get();
	}

	private static User createUser() {
		User u = new User();
		u.setId(2);
		u.setName("Pankaj");
		u.setEmployee(true);
		u.setRole("CEO");
		return u;
	}
}
