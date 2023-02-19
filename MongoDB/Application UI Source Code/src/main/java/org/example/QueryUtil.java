package org.example;


import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import javax.print.Doc;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static com.mongodb.client.model.Filters.*;
import static org.apache.http.client.utils.DateUtils.GMT;

public class QueryUtil implements AutoCloseable{

    //string to connect to the correct database
    MongoClientURI uri = new MongoClientURI("mongodb+srv://root:password123456*qwerty@cluster0.k5ok7.mongodb.net/test?authSource=admin&replicaSet=atlas-z7a47q-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");
    //creates a client associated with the string above
    MongoClient mongoClient = new MongoClient(uri);
    //connects to the correct database in the client
    MongoDatabase database = mongoClient.getDatabase("CovidCertificationData");
    //selects the correct collection
    MongoCollection<Document> collection = database.getCollection("Certificates");

    private final Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "Global.dbUser", "A" ) );


    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public int query2()
    {
        long count = 0;
        LocalDate nowDate = LocalDate.now().minusMonths(9);
        Bson query = gte("vaccination.somministration_date", nowDate);
        count = collection.countDocuments(query);
        return (int) count;
    }

    public int query1(String id)
    {
        long count = 0;
        LocalDate nowDate = LocalDate.now();
        LocalDate vaccineMinimalDate = nowDate.minusMonths(9);
        LocalDate testMinimalDate = nowDate.minusDays(3);
        Bson query1 = gte("vaccination.somministration_date", vaccineMinimalDate);
        Bson query21 = gte("tests.date", testMinimalDate);
        Bson query22 = eq("tests.result", "false");
        Bson query2 = and(query21,query22);
        Bson resultAccepted = or(query1,query2);
        Bson query = and(eq("person.person_id",Integer.parseInt(id)),resultAccepted);
        count = collection.countDocuments(query);
        return (int) count;
    }


    public int query3(String dateString)
    {
        long count = 0;
        LocalDate localDate = LocalDate.parse(dateString);
        Bson query = eq("vaccination.somministration_date", localDate);
        count = collection.countDocuments(query);
        return (int) count;
    }

    public ArrayList<String> query4(String id)
    {
        ArrayList<String> resultList = new ArrayList<>();

        MongoCursor<Document> cursor = collection.find(eq("vaccination.doctors.doctor_id", Integer.parseInt(id))).iterator();
        try {
            while (cursor.hasNext()) {
                //System.out.println(cursor.next().toJson());
                Document a = cursor.next();
                Document person = a.get("person", Document.class);
                String name = person.getString("name");
                String surname = person.getString("surname");
                resultList.add(name + " " + surname);
            }
        } finally {
            cursor.close();
        }
        return resultList;
    }

    public ArrayList<String> query5(String id)
    {
        ArrayList<String> resultList = new ArrayList<>();

        Bson req1 = eq("vaccination.authorized_center_id", id);
        Bson req2 = eq("vaccination.brand","Johnson & Johnson Janssen");
        MongoCursor<Document> cursor = collection.find(and(req1,req2)).iterator();
        try {
            while (cursor.hasNext()) {
                Document a = cursor.next();
                Document person = a.get("person", Document.class);
                String name = person.getString("name");
                String surname = person.getString("surname");
                resultList.add(name + " " + surname);
            }
        } finally {
            cursor.close();
        }
        return resultList;
    }


    public ArrayList<String> query6(String id)
    {
        ArrayList<String> resultList = new ArrayList<>();

        MongoCursor<Document> cursor = collection.find(eq("vaccination.authorized_center_id", id)).iterator();
        try {
            while (cursor.hasNext()) {
                Document a = cursor.next();
                Document person = a.get("person", Document.class);
                String name = person.getString("name");
                String surname = person.getString("surname");
                resultList.add(name + " " + surname);
            }
        } finally {
            cursor.close();
        }
        return resultList;
    }

    public int query7(String size)
    {
        long count = 0;
        Bson query = size("vaccination", Integer.parseInt(size));
        count = collection.countDocuments(query);
        return (int) count;
    }

    public boolean command1(String person, String test, String center, String type, String date, String doctor, String nurse, String result)
    {
        LocalDate localDate = LocalDate.parse(date);
        Document listItem = new Document().append("test_id",test).append("authorized_center_id",center)
                .append("type",type).append("date",localDate).append("doctors",Arrays.asList(doctor))
                .append("nurses",Arrays.asList(nurse)).append("result",result);
        collection.updateOne(eq("person.person_id", person), Updates.addToSet("tests", listItem));
        Bson searchQuery = eq("person.person_id", Integer.parseInt(person));
        collection.updateOne(searchQuery, Updates.addToSet("tests",listItem));
        return true;
    }

    public boolean command2(String firstName, String lastName)
    {
        Bson query = and(eq("person.name",firstName),eq("person.surname",lastName));
        collection.deleteOne(query);
        return true;
    }

    public boolean command3(String id,String phoneNumber)
    {
        Bson searchQuery = eq("person.person_id", Integer.parseInt(id));
        collection.updateOne(searchQuery, new Document("$set", new Document("emergency_contact.phone_number", phoneNumber)));
        return true;
    }
}
