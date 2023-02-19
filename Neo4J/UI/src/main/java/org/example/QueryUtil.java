package org.example;


import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryUtil implements AutoCloseable{

    private final Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( Global.dbUser, Global.dbPassword ) );


    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public int query2(String date)
    {
        try ( Session session = driver.session() )
        {
            Result result = session.run( "MATCH(test:Test{date:\""+date+"\"})\n" +
                    " \n" +
                    "WHERE test.positive = true\n" +
                    " \n" +
                    "RETURN COUNT(test)" );
            Record record = result.next();
            Value val = record.values().get(0);
            return val.asInt();
        }
    }

    public ArrayList<String> query1(String id)
    {
        ArrayList<String> resultList = new ArrayList<>();
        try ( Session session = driver.session() )
        {
            Result result = session.run( "MATCH (person: Person{personId: "+id+"}) - [v: VISITED] -> (place: Place) <- [i: VISITED] - (placeContact: Person)\n" +
                    "WHERE (time(i.time) - duration({hours: 2})) <= time(v.time) AND\n" +
                    "time(v.time) <= (time(i.time) + duration({hours: 2}))\n" +
                    "RETURN placeContact as contact\n" +
                    "union\n" +
                    "MATCH (person: Person{personId: "+id+"}) - [r: STREET_CONTACT] - (streetContact: Person)\n" +
                    "RETURN streetContact as contact\n" +
                    "Union\n" +
                    "match (person: Person{personId: "+id+"}) - [a: RELATIVE] - (relativeContact: Person)\n" +
                    "RETURN relativeContact as contact" );
            while(result.hasNext()){
                Record record = result.next();
                Map<String, Object> ddd = record.values().get(0).asMap();
                String stringToCompare = ddd.get("personId").toString()+" "+ddd.get("firstName")+" "+ddd.get("lastName");
                if(!resultList.contains(stringToCompare)){
                    resultList.add(stringToCompare);
                }
            }
        }
        return resultList;
    }


    public ArrayList<String> query3()
    {
        ArrayList<String> resultList = new ArrayList<>();
        try ( Session session = driver.session() )
        {
            Result result = session.run( "MATCH(vaccinatedPositive: Person{vaccinated: True}) -[r1:TEST]- (test1:Test{positive:true})\n" +
                    "OPTIONAL MATCH (totalPositive: Person{vaccinated: True})\n" +
                    "RETURN count(DISTINCT vaccinatedPositive) as Positive, count(DISTINCT totalPositive) as Total, " +
                    "tofloat((count(DISTINCT vaccinatedPositive)*1.0)/(count(DISTINCT totalPositive))) as percentage\n" +
                    " \n" +
                    "union\n" +
                    " \n" +
                    "MATCH(notVaccinatedPositive: Person{vaccinated: False}) -[r2:TEST]- (test2:Test{positive:true})\n" +
                    "OPTIONAL MATCH (totalPositive: Person{vaccinated: False})\n" +
                    "RETURN count(DISTINCT notVaccinatedPositive) as Positive, count(DISTINCT totalPositive) as Total, tofloat((count(DISTINCT notVaccinatedPositive)*1.0)/(count(DISTINCT totalPositive))) as percentage" );
            while(result.hasNext()){
                Record record = result.next();
                resultList.add(record.values().get(0).toString());resultList.add(record.values().get(1).toString());resultList.add(record.values().get(2).toString());
            }
        }
        return resultList;
    }

    public ArrayList<String> query4()
    {
        ArrayList<String> resultList = new ArrayList<>();
        try ( Session session = driver.session() )
        {
            Result result = session.run( "MATCH (p:Person)-[s: RELATIVE]-(positive1: Person)-[t: TEST]- (positiveTest1: Test{positive:true})\n" +
                    "MATCH (positiveTest2:Test{positive:false})-[t2: TEST]-(person1: Person)-[s1: RELATIVE]-(positive3:Person)-[t1:TEST]-(positiveTest3:Test{positive:true})\n" +
                    "WHERE NOT (p)-[:TEST]->(:Test)\n" +
                    "RETURN (COUNT (DISTINCT person1)+ COUNT (DISTINCT p)) as quarantined\n" +
                    "union\n" +
                    " \n" +
                    "MATCH (p:Person)-[s: STREET_CONTACT]-(positive1: Person)-[t: TEST]- (positiveTest1: Test{positive:true})\n" +
                    "MATCH (positiveTest2:Test{positive:false})-[t2: TEST]-(person1: Person)-[s1: STREET_CONTACT]-(positive3:Person)-[t1:TEST]-(positiveTest3:Test{positive:true})\n" +
                    "WHERE NOT (p)-[:TEST]->(:Test)\n" +
                    "RETURN (COUNT (DISTINCT person1)+ COUNT (DISTINCT p)) as quarantined\n" +
                    " \n" +
                    "union\n" +
                    " \n" +
                    "MATCH (p:Person)-[s: VISITED]-> (place:Place) <-[v:VISITED]-(positive1: Person)-[t: TEST]- (positiveTest1: Test{positive:true})\n" +
                    "MATCH (positiveTest2:Test{positive:false})-[t2: TEST]-(person1: Person)-[s1: VISITED]-> (place:Place) <-[v1:VISITED]-(positive3:Person) -[t1:TEST]- (positiveTest3:Test{positive:true})\n" +
                    "WHERE NOT (p)-[:TEST]->(:Test)\n" +
                    "RETURN (COUNT (DISTINCT person1)+ COUNT (DISTINCT p)) as quarantined\n" );
            while(result.hasNext()){
                Record record = result.next();
                resultList.add(record.values().get(0).toString());
            }
        }
        return resultList;
    }

    public ArrayList<String> query5()
    {
        ArrayList<String> resultList = new ArrayList<>();
        try ( Session session = driver.session() )
        {
            Result result = session.run( "MATCH (person: Person) - [r: STREET_CONTACT] - (streetContact: Person)\n" +
                    "OPTIONAL MATCH (person: Person) - [a: RELATIVE] - (relativeContact: Person)\n" +
                    "OPTIONAL MATCH (person: Person) - [v: VISITED] -> (place: Place) <- [i: VISITED] - (placeContact: Person)\n" +
                    "WHERE (time(i.time) - duration({hours: 2})) <= time(v.time) AND\n" +
                    "time(v.time) <= (time(i.time) + duration({hours: 2}))\n" +
                    " \n" +
                    "with person, toFloat(count(distinct relativeContact) + count(distinct streetContact) + count(distinct placeContact)) as totalContact order by totalContact DESC LIMIT 10\n" +
                    " \n" +
                    "RETURN COLLECT ( person), totalContact" );
            while(result.hasNext()){
                Record record = result.next();
                List<Object> aaa=record.values().get(0).asList();
                for (Object obj :aaa) {
                    Node bbb = (Node)obj;
                    Map<String, Object> props = bbb.asMap();
                    resultList.add(props.get("firstName")+" "+props.get("lastName"));
                }
            }
        }
        return resultList;
    }


    public ArrayList<String> query6()
    {
        ArrayList<String> resultList = new ArrayList<>();
        try ( Session session = driver.session() )
        {
            Result result = session.run( "MATCH (test1: Test{positive: FALSE})<-[:TEST]-(person: Person)-[:TEST]->(test2: Test{positive: TRUE}),\n" +
                    "(person)-[v: VISITED]->(place:Place)\n" +
                    "WHERE NOT(test1.testId = test2.testId) AND date(test1.date) < date(test2.date)\n" +
                    "AND date(test1.date) <= date(v.date) AND date(v.date) <= date(test2.date)\n" +
                    "RETURN DISTINCT person" );
            while(result.hasNext()){
                Record record = result.next();
                Map<String, Object> aaa = record.values().get(0).asMap();
                resultList.add(aaa.get("personId")+" "+aaa.get("firstName")+" "+aaa.get("lastName"));
            }
        }
        return resultList;
    }

    public ArrayList<String> command1(String id)
    {
        ArrayList<String> resultList = new ArrayList<>();
        try ( Session session = driver.session() )
        {
            Result result = session.run( "match(n:Person {personId:"+id+"}) RETURN n" );
            while(result.hasNext()){
                Record record = result.next();
                Map<String, Object> aaa = record.values().get(0).asMap();
                resultList.add(aaa.get("firstName")+" "+aaa.get("lastName"));
            }
            Result result1 = session.run( "match(n:Person {personId:"+id+"}) detach delete n" );
        }
        return resultList;
    }

    public ArrayList<String> command2(String id)
    {
        ArrayList<String> resultList = new ArrayList<>();
        try ( Session session = driver.session() )
        {
            Result result = session.run( "match(n:Person {personId:"+id+"}) RETURN n" );
            while(result.hasNext()){
                Record record = result.next();
                Map<String, Object> aaa = record.values().get(0).asMap();
                resultList.add(aaa.get("firstName")+" "+aaa.get("lastName"));
            }
            Result result1 = session.run( "MATCH (p:Person{personId: 1})\n" +
                    "SET p.vaccinated = TRUE\n" +
                    "RETURN p" );
        }
        return resultList;
    }

    public boolean command3(String id,String firstName, String lastName)
    {
        ArrayList<String> resultList = new ArrayList<>();
        try ( Session session = driver.session() )
        {
            String query = "match(n:Person {personId:"+id+"}) RETURN n";
            Result result = session.run( "match(n:Person {personId:"+id+"}) RETURN n" );
            while(result.hasNext()){
                Record record = result.next();
                Map<String, Object> aaa = record.values().get(0).asMap();
                resultList.add(aaa.get("firstName")+" "+aaa.get("lastName"));
            }
            if(resultList.size()>0){
                return false;
            }
            Result result1 = session.run( "CREATE (p:Person {personId: toInteger("+id+"), firstName: \""+firstName+"\", \n" +
                    "lastName: \""+lastName+"\",\n" +
                    "vaccinated: toBoolean(\"false\")});" );
        }
        return true;
    }
}
