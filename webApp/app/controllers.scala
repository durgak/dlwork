package controllers

import play._
import play.mvc._

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.params.BasicHttpParams
import org.apache.http.util.EntityUtils

object Application extends Controller {
    
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
        val annotatorUrl = "http://www.themoviedb.org/"
        
        val client = new DefaultHttpClient
        //client.getParams.setParameter(HttpClientParams.USER_AGENT, "Annotator Client Example")

        val method = new HttpPost(annotatorUrl)
        
        /*
        method.addParameter("longestOnly", "true")
        method.addParameter("wholeWordOnly", "true")
        method.addParameter("filterNumber", "true")
        method.addParameter("stopWords", "")
        method.addParameter("withDefaultStopWords", "true")
        method.addParameter("isTopWordsCaseSensitive", "false")
        method.addParameter("mintermSize", "3")
        method.addParameter("scored", "true")
        method.addParameter("withSynonyms", "true")
        method.addParameter("ontologiesToExpand", "")
        method.addParameter("ontologiesToKeepInResult", "")
        method.addParameter("isVirtualOntologyId", "true")
        method.addParameter("semanticTypes", "")
        method.addParameter("levelMax", "0")
        method.addParameter("mappingTypes", "null")
        method.addParameter("textToAnnotate", query)
        method.addParameter("format", "xml")
        method.addParameter("apikey", "4e24de590e8231cbbb317195bad993d9")
        */
        
        val params = new BasicHttpParams
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
        params.setParameter("apikey", "4e24de590e8231cbbb317195bad993d9")
        
        method.setParams(params)
        
        return method.getURI.toString // just returns annotatorUrl -- are params getting set?  look for tutorial on httpclient (try it out somewhere else where i don't need an api key)
        
        val response = client.execute(method)
        return response.toString  
        // saying it's forbidden.  why?  
        // 1.  maybe i set the params incorrectly
        // 2.  maybe my api key doesn't give me sufficient privileges
        // 3.  maybe i have the wrong annotatorUrl
        
        if (response.getStatusLine.getStatusCode != -1) {
          try {
            val entity = method.getEntity
            return entity.toString
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
