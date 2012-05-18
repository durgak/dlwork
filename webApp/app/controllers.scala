package controllers

import play._
import play.mvc._

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpGet
import org.apache.http.params.BasicHttpParams
import org.apache.http.util.EntityUtils

import java.io.InputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import org.apache.commons.io.IOUtils
import java.io.StringWriter
import java.io.PrintWriter

//import net.liftweb.json.{Printer, Xml, render}
import net.liftweb.json._
//import net.liftweb.json.JsonAST._

import scala.collection.mutable.HashMap

import java.net.URI

import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import java.util.ArrayList

import scala.util.parsing.json.JSONObject

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonArray
import com.google.gson.JsonPrimitive

import javax.servlet.http.HttpServletResponse


object Application extends Controller {
    
    import views.Application._
    
    def index = {
        html.index("Your Scala application is ready!")
    }
    
    //main method, handles callback, query, and queries requests
    def reconcile: String = {
      var jsonstr = ""
      
      if (params.get("query")!=null){
        val query = params.get("query")
        jsonstr = runQuery(query).toString
      }
      
      /*val resp = new MyCustomResponse()
      resp.setCharacterEncoding("UTF-8")
      resp.setContentType("application/json")
      resp.setStatus(200)*/
      
      //potentially do something with metadata here

      val callback = params.get("callback")
      var jsonp = jsonstr
      if (callback != null){
        val inFile = "metadata.json"
        val j = jsonToJsonObject(inFile).toString
        return callback+"("+j+")"
        //return jsonToJsonObject(inFile)
        //return testObj()
        //jsonp = callback + "("+jsonstr+")"
      }
      
      val queries = params.get("queries")
      if (queries != null){
        val inFile = "queries.json"
        return jsonToJsonObject(inFile).toString
        //return queryObj()
      }
    
      val query = params.get("query")
      if(query == "") {
        return new JsonObject().toString
        //flash += ("error" -> "Oops, please enter a query!")
        //flash.get("error")
      } else {
        val inFile = "query.json"
        return jsonToJsonObject(inFile).toString
        //return queryObj()
        //return runQuery(query)
      }   
    }  
    
    //main single query method
    def runQuery(query: String): JsonObject = {
    
        return queryObj()
      
        var annotatorUrl = "http://rest.bioontology.org/obs/annotator"
 
        val client = new DefaultHttpClient()

        var method = new HttpPost(annotatorUrl)
        
        val params = new ArrayList[BasicNameValuePair]()
        
        params.add(new BasicNameValuePair("apikey", "c63bd0a0-cca6-44bb-8c6d-0500f3d6db69"))
        params.add(new BasicNameValuePair("longestOnly", "true"))
        params.add(new BasicNameValuePair("wholeWordOnly", "true"))
        params.add(new BasicNameValuePair("filterNumber", "true"))
        params.add(new BasicNameValuePair("stopWords", ""))
        params.add(new BasicNameValuePair("withDefaultStopWords", "true"))
        params.add(new BasicNameValuePair("isTopWordsCaseSensitive", "false"))
        params.add(new BasicNameValuePair("mintermSize", "3"))
        params.add(new BasicNameValuePair("scored", "true"))
        params.add(new BasicNameValuePair("withSynonyms", "true"))
        params.add(new BasicNameValuePair("ontologiesToExpand", ""))
        params.add(new BasicNameValuePair("ontologiesToKeepInResult", ""))
        params.add(new BasicNameValuePair("isVirtualOntologyId", "true"))
        params.add(new BasicNameValuePair("semanticTypes", ""))
        params.add(new BasicNameValuePair("levelMax", "0"))
        params.add(new BasicNameValuePair("mappingTypes", "null"))
        params.add(new BasicNameValuePair("format", "xml"))
        params.add(new BasicNameValuePair("textToAnnotate", query))
        
        method.setEntity(new UrlEncodedFormEntity(params))
        
        val response = client.execute(method)

        
        if (response.getStatusLine.getStatusCode != -1) {
          try {
            val entity = response.getEntity().getContent()
            val writer = new StringWriter()
            IOUtils.copy(entity, writer);
            val infile = "result.xml"
            val out = new PrintWriter(infile)
            out.print(writer)  
            out.close
            
            val outfile = xmlToJson(infile)
            val j = jsonToJsonObject(outfile)
            //response.setCharacterEncoding("UTF-8"); 
            //response.setContentType("application/json"); 
            response.setStatusCode(200); 
            //PrintWriter out= response.getWriter(); 
            //out.print(j); 
            return j
            
          } catch {
            case e: Exception => e.printStackTrace
          }
        }
        return new JsonObject()
    }  
    
    //converts an xml file to a json file, returning output filename
    def xmlToJson(inFile: String):String = {
      val data = xml.XML.loadFile(inFile)
      val str = Printer.pretty(render(net.liftweb.json.Xml.toJson(data)))  
      val outFile = inFile.replace(".xml", ".json")       
      var out_file = new java.io.FileOutputStream(outFile)
      var out_stream = new java.io.PrintStream(out_file)
             
      out_stream.print(str)
      out_stream.close
      return outFile
    }
    
    //converts a json file to a json object
    def jsonToJsonObject(inFile: String) = {
      val lines = scala.io.Source.fromFile(inFile).mkString
      val json = parse(lines)
      
      val parser = new JsonParser()
      val obj = parser.parse(lines)
      obj.getAsJsonObject()
      //return JSONObject(json.asInstanceOf[JObject].values.map(p => Tuple2(p._1.toString, p._2) ))
      //return str.toString()
    }
    
    def metadata(): JsonObject = {
      val jsonObj = new JsonObject()
      jsonObj.addProperty("name", "NCBO Annotator Query")
      jsonObj.addProperty("identifierSpace", "http://localhost:9000/application/reconcile")
      jsonObj.addProperty("schemaSpace", "http://localhost:9000/application/reconcile")
      return jsonObj
    }
    
    //generates a test metadata json object of this form http://standard-reconcile.freebaseapps.com/reconcile
    def testObj(): JsonObject = {
      val jsonObj = new JsonObject()
      val subArray = new JsonArray()
      jsonObj.addProperty("name", "Controlled Vocabulary Reconciliation Service")
      jsonObj.addProperty("identifierSpace", "http://rdf.freebase.com/ns/type.object.mid")
      jsonObj.addProperty("schemaSpace", "http://rdf.freebase.com/ns/type.object.id")
      val subJsonObj1 = new JsonObject()
      subJsonObj1.addProperty("id", "TissueName")
      subJsonObj1.addProperty("name", "TissueName")
      subArray.add(subJsonObj1)
      val subJsonObj2 = new JsonObject()
      subJsonObj2.addProperty("id", "colorname")
      subJsonObj2.addProperty("name", "colorname")
      subArray.add(subJsonObj2)
      val subJsonObj3 = new JsonObject()
      subJsonObj3.addProperty("id", "Tissue")
      subJsonObj3.addProperty("name", "Tissue")
      subArray.add(subJsonObj3)
      val subJsonObj4 = new JsonObject()
      subJsonObj4.addProperty("id", "colors")
      subJsonObj4.addProperty("name", "colors")
      subArray.add(subJsonObj4)
      jsonObj.add("defaultTypes", subArray)
      return jsonObj
    }
    
    //generates a test query json object of this form http://standard-reconcile.freebaseapps.com/reconcile
    def queryObj(): JsonObject = {
      val jsonObj = new JsonObject()
      val subArray = new JsonArray()
      jsonObj.addProperty("id", "colors/Yellow")
      jsonObj.addProperty("name", "Yellow")
      val name = new JsonPrimitive("colors") 
      subArray.add(name)
      jsonObj.add("type", subArray)
      jsonObj.addProperty("score", "100.0")
      jsonObj.addProperty("match", true)
      return jsonObj
    }

}
