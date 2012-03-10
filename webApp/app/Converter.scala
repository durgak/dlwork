import net.liftweb.json.{Printer, Xml, render}
import net.liftweb.json.JsonAST._

object Converter{
    def convert (file: String) = {
    
    val data = xml.XML.loadFile(file)
    //def meth(xml: NodeSeq): JValue = Xml.toJson
    //val f = (xml: scala.xml.NodeSeq) => Xml.toJson(xml):JValue
    val str = Printer.pretty(render(Xml.toJson(data)))   
     
    var out_file = new java.io.FileOutputStream("inJSON.json")
    var out_stream = new java.io.PrintStream(out_file)
     
    out_stream.print(str)
    out_stream.close
    
    }

}
