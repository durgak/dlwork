import net.liftweb.json.{Printer, Xml, render}
import net.liftweb.json.JsonAST._

object Test{
    def main (args: Array[String]) = {
    
    val data = xml.XML.loadFile("inXML.xml")
    //def meth(xml: NodeSeq): JValue = Xml.toJson
    //val f = (xml: scala.xml.NodeSeq) => Xml.toJson(xml):JValue
    val str = Printer.pretty(render(net.liftweb.json.Xml.toJson(data)))   
     
    var out_file = new java.io.FileOutputStream("inJSON.json")
    var out_stream = new java.io.PrintStream(out_file)
     
    out_stream.print(str)
    out_stream.close
    
    }

}
