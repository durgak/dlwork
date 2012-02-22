import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.params.BasicHttpParams
import org.apache.http.util.EntityUtils


import java.sql.Timestamp

class AnnotatorClient { 
	val annotatorUrl = "http://rest.bioontology.org/obs/annotator"
	
    def main(args: Array[String]) {
        try {
            val httpclient = new HttpClient()
            client.getParams().setParameter(
            HttpMethodParams.USER_AGENT,"Annotator Client Example - Annotator")  //Set this string for your application 
            
            PostMethod method = new PostMethod(annotatorUrl)
            
            // Configure the form parameters
            method.addParameter("longestOnly","true")
            method.addParameter("wholeWordOnly","true")
            method.addParameter("filterNumber", "true")
            method.addParameter("stopWords","")
            method.addParameter("withDefaultStopWords","true")
            method.addParameter("isTopWordsCaseSensitive","false")
            method.addParameter("mintermSize","3")
            method.addParameter("scored", "true")
            method.addParameter("withSynonyms","true") 
            method.addParameter("ontologiesToExpand", "")
            method.addParameter("ontologiesToKeepInResult", "") 
            method.addParameter("isVirtualOntologyId", "true") 
            method.addParameter("semanticTypes", "") 
            method.addParameter("levelMax", "0")
            method.addParameter("mappingTypes", "null") //null, Automatic 
            method.addParameter("textToAnnotate", "Melanoma is a malignant tumor of melanocytes which are found predominantly in skin but also in the bowel and the eye")
            method.addParameter("format", "xml") //Options are 'text', 'xml', 'tabDelimited'   
            method.addParameter("apikey", "YourAPIKey")

            // Execute the POST method
            int statusCode = client.executeMethod(method)
            
            if( statusCode != -1 ) {
                try {
                String contents = method.getResponseBodyAsString()
                method.releaseConnection()
                System.out.println(contents)
                }
                catch{
                    case e: Exception => printStackTrace()
                }            }
        }
        catch{
            case e: Exception => printStackTrace()
        }
    }
}
