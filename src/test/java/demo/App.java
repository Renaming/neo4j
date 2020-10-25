package demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Values;

import com.opensymphony.xwork2.ActionContext;


public class App {

	public App() {
		// TODO Auto-generated constructor stub
	}
	 public static void main(String[] args) {
	        Driver driver=GraphDatabase.driver("bolt://localhost:7687",AuthTokens.basic("neo4j", "123456"));
	        try(Session session=driver.session()){
	           // try(Transaction transaction=session.beginTransaction()){
	           //     transaction.run("create(n:A1{NAME:{NAME},TITLE:{TITLE}})", Values.parameters("NAME","james","TITLE","King"));
	           //     transaction.success();
	          //  }
	        	//MATCH (u:Steam)-[:contain]->(opt:Point)
	        	//where u.content='在长8厘米 宽6厘米的长方形内画一个最大的圆 求圆的周长和面积'
	        	//RETURN opt	        
	        	List<String> list =new ArrayList<String>();
	            try(Transaction tx=session.beginTransaction()) {
	                StatementResult result=tx.run("match(a:Steam)-[:contain]->(opt:Point) WHERE a.content = {NAME} RETURN opt.point as Point", Values.parameters("NAME","在长8厘米 宽6厘米的长方形内画一个最大的圆 求圆的周长和面积"));
	                while (result.hasNext()) {
	                    Record record=result.next();
	                    String Pas=record.get("Point").asString();
	                    list.add(Pas);
	                    System.out.println(String.format("%s",record.get("Point").asString()));
	                }	           
	       		 }
	                ActionContext ctx=ActionContext.getContext();
	        		ctx.getSession().put("AllPointResultSet", list);
	          } 
	        driver.close(); 
	    }	     
}
