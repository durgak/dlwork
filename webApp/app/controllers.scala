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

import net.liftweb.json.{Printer, Xml, render}
import net.liftweb.json.JsonAST._

import scala.collection.mutable.HashMap

import java.net.URI

import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import java.util.ArrayList


object Application extends Controller {
    
    import views.Application._
    
    def index = {
        html.index("Your Scala application is ready!")
    }
    
    def xmlToJson(inFile: String) = {
      val data = xml.XML.loadFile(inFile)
      val str = Printer.pretty(render(net.liftweb.json.Xml.toJson(data)))  
      val outFile = inFile.replace(".xml", ".json")       
      var out_file = new java.io.FileOutputStream(outFile)
      var out_stream = new java.io.PrintStream(out_file)
             
      out_stream.print(str)
      out_stream.close
    }
    
    def queryNCBO: String = {
      val query = params.get("query")
      if(query == "") {
        flash += ("error" -> "Oops, please enter a query!")
        flash.get("error")
      } else {
        
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
            
            xmlToJson(infile)
            
            return "completed"
          } catch {
            case e: Exception => e.printStackTrace
          }
        }
        "not completed"
      }   
    }
}
