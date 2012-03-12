package controllers

import play._
import play.mvc._

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.params.BasicHttpParams
import org.apache.http.util.EntityUtils
import java.io.InputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import org.apache.commons.io.IOUtils
import java.io.StringWriter
import java.io.PrintWriter


import org.apache.http.Header
import org.apache.http.client.methods.HttpGet

import net.liftweb.json.{Printer, Xml, render}
//import net.liftweb.json.{render => renderJson}
import net.liftweb.json.JsonAST._

//import net.sf.json.JSON;
//import net.sf.json.xml.XMLSerializer;



//import scala.io._
//import java.io._

//import play.api.libs.json._
//import Json._


object Application extends Controller {
    
    import views.Application._
    
    def convertStreamToString(is: InputStream): String = {
        val reader = new BufferedReader(new InputStreamReader(is));
        val sb = new StringBuilder();
     
        var line : String = null;
        try {
        
          while ({line = reader.readLine(); line != null}) {
          //while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
          }
        } catch {
          case e: IOException => e.printStackTrace();
        } finally {
          try {
            is.close();
          } catch {
            case e: IOException => e.printStackTrace();
          }
        }
     
        sb.toString();
      }
    
    
    def index = {
        html.index("Your Scala application is ready!")
    }
    
    def queryNCBO: String = {
      val query = params.get("query")
      if(query == "") {
        flash += ("error" -> "Oops, please enter a query!")
        //Action(index)
        ""
      } else {
        
        val annotatorUrl = "http://rest.bioontology.org/bioportal/ontologies?apikey=3784d3e4-7674-4f7a-b2c0-ae2f54e4d561"
        
        val client = new DefaultHttpClient

        var method = new HttpGet(annotatorUrl)
        
        val params = new BasicHttpParams
        /*
        params.setParameter("longestOnly", "true")
        params.setParameter("wholeWordOnly", "true")
        params.setParameter("filterNumber", "true")
        params.setParameter("stopWords", "")
        params.setParameter("withDefaultStopWords", "true")
        params.setParameter("isTopWordsCaseSensitive", "false")
        params.setParameter("mintermSize", "3")
        params.setParameter("scored", "true")
        params.setParameter("withSynonyms", "true")
        params.setParameter("ontologiesToExpand", "")
        params.setParameter("ontologiesToKeepInResult", "")
        params.setParameter("isVirtualOntologyId", "true")
        params.setParameter("semanticTypes", "")
        params.setParameter("levelMax", "0")
        params.setParameter("mappingTypes", "null")
        params.setParameter("textToAnnotate", query)
        params.setParameter("format", "xml")
        */
        
        params.setParameter("wholeWordOnly", "true")
        params.setParameter("withDefaultStopWords", "true")
        params.setParameter("isVirtualOntologyId", "true")
        params.setParameter("semanticTypes", "")
        params.setParameter("levelMax", "0")
        params.setParameter("mappingTypes", "")
        params.setParameter("textToAnnotate", query)
  
        //params.setParameter("apikey", "3784d3e4-7674-4f7a-b2c0-ae2f54e4d561")
       
        
        method.setParams(params)
        
        val response = client.execute(method)

        
        if (response.getStatusLine.getStatusCode != -1) {
          try {
            val entity = response.getEntity().getContent()
            val writer = new StringWriter()
            IOUtils.copy(entity, writer);
            val out = new PrintWriter("inXML.xml")
            out.print(writer)  
            out.close
            val data = xml.XML.loadFile("inXML.xml")
            //val f = (xml: scala.xml.NodeSeq) => Xml.toJson(xml):JValue
            //val str = Printer.pretty(render(Xml.toJson(data))) 
            val str = Printer.pretty(render(net.liftweb.json.Xml.toJson(data)))  
             
            var out_file = new java.io.FileOutputStream("inJSON.json")
            var out_stream = new java.io.PrintStream(out_file)
             
            out_stream.print(str)
            out_stream.close
            return "HI"
            //return xmlToJson(entity.toString).toString
            ////val xmlSerializer = new XMLSerializer(); 
            
            ////val json = xmlSerializer.read( writer.toString );  
            //System.out.println( json.toString(2) );
            //var out_stream = new java.io.PrintStream(out_file)
            ////return json.toString
 
            //out_stream.print(str)
            //out_stream.close
            ////val inXml = writer.toString();
            //val jsonObject = XML.toJSONObject(inXml)
            //return jsonObject.toString
            //val data = xml.XML.loadFile("inXML.xml")
            //return data.toString
            //val str = Printer.pretty(render(Xml.toJson(writer )))   
 
            //var out_file = new java.io.FileOutputStream("inJSON.json")
            //var out_stream = new java.io.PrintStream(out_file)
 
            //out_stream.print(str)
            //out_stream.close
            //return writer.toString();
            
            //return convertStreamToString(entity)
            //EntityUtils.consume(entity)
            //method.releaseConnection
            //return contents
          } catch {
            case e: Exception => e.printStackTrace
          }
        }
       
 

        
        
        "hi"
        //html.query(params.get("query"))
      }
      
      
    }
}
