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


import org.apache.http.Header
import org.apache.http.client.methods.HttpGet

object Application extends Controller {
    
    import views.Application._
    
    def convertStreamToString(is: InputStream): String = {
        val reader = new BufferedReader(new InputStreamReader(is));
        val sb = new StringBuilder();
     
        var line : String = null;
        try {
          while ((line = reader.readLine()) != null) {
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
    
    import views.Application._
    
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

 
        //return method.getURI.toString // just returns annotatorUrl 
        
        val response = client.execute(method)
        //return response.toString 
        //return client.getResponseBody() 
        // saying it's forbidden.  why?  
        // 1.  maybe i set the params incorrectly
        // 2.  maybe my api key doesn't give me sufficient privileges
        // 3.  maybe i have the wrong annotatorUrl
        
        if (response.getStatusLine.getStatusCode != -1) {
          try {
            val entity = response.getEntity().getContent()
            val writer = new StringWriter()
            IOUtils.copy(entity, writer);
            return writer.toString();
            
            
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
