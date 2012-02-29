package controllers

import play._
import play.mvc._

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.params.BasicHttpParams
import org.apache.http.util.EntityUtils


import org.apache.http.Header
import org.apache.http.client.methods.HttpGet

object Application extends Controller {
    
    import views.Application._
    
    def index: String = {
      val query = params.get("query")
      if(query == "") {
        flash += ("error" -> "Oops, please enter a query!")
        //Action(index)
        ""
      } else {
        //var annotatorUrl = "http://api.themoviedb.org/2.1/Movie.browse/en-US/xml/4e24de590e8231cbbb317195bad993d9?"
        
        val annotatorUrl = "http://rest.bioontology.org/bioportal/ontologies?apikey=3784d3e4-7674-4f7a-b2c0-ae2f54e4d561"
        
        val client = new DefaultHttpClient
        //client.getParams.setParameter(HttpClientParams.USER_AGENT, "Annotator Client Example")
        
        //client.getParams().setParameter(

        var method = new HttpGet(annotatorUrl)
        
        val params = new BasicHttpParams
        
        /*
        params.setParameter("order_by", "title")
        params.setParameter("order", "des")
        params.setParameter("genres", "18")
        params.setParameter("min_votes", "5")
        params.setParameter("page", "1")
        params.setParameter("per_page", "10")
        */
        
        //val params = new BasicHttpParams
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
        //params.setParameter("apikey", "3784d3e4-7674-4f7a-b2c0-ae2f54e4d561")
        

        //params.setParameter("apikey", "4e24de590e8231cbbb317195bad993d9")
        
        //return params.getParameter("APIKEY").toString
        
        method.setParams(params)
        
        //return method.getParams().getParameter("page").toString
        
        //method = new HttpGet("http://api.themoviedb.org/2.1/Movie.browse/en-US/xml/4e24de590e8231cbbb317195bad993d9?order_by=rating&order=desc&genres=18&min_votes=5&page=1&per_page=10")
        
        //return method.getRequestLine().toString
        
        
        //return method.getURI.toURL.toString // just returns annotatorUrl -- are params getting set?  look for tutorial on httpclient (try it out somewhere else where i don't need an api key)
        
        val response = client.execute(method)
        //return method.getURI.toString
        return response.toString  
        // saying it's forbidden.  why?  
        // 1.  maybe i set the params incorrectly
        // 2.  maybe my api key doesn't give me sufficient privileges
        // 3.  maybe i have the wrong annotatorUrl
        
        if (response.getStatusLine.getStatusCode != -1) {
          try {
            //val entity = method.getEntity
            //return entity.toString
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
