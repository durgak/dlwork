
                    package views.Application.html

                    import play.templates._
                    import play.templates.TemplateMagic._
                    import views.html._

                    object index extends BaseScalaTemplate[Html,Format[Html]](HtmlFormat) {

                        def apply/*1.2*/(title:String):Html = {
                            try {
                                _display_ {

format.raw/*1.16*/("""

""")+_display_(/*3.2*/main(title = "Home")/*3.22*/ {format.raw/*3.24*/("""
    <form action="""")+_display_(/*4.20*/action(controllers.Application.queryNCBO))+format.raw/*4.61*/("""" method="GET">
        <input type="text" name="query" /> 
        <input type="submit" value="Search NCBO" />
    </form>
""")})}
                            } catch {
                                case e:TemplateExecutionError => throw e
                                case e => throw Reporter.toHumanException(e)
                            }
                        }

                    }

                
                /*
                    -- GENERATED --
                    DATE: Fri Mar 02 14:49:45 PST 2012
                    SOURCE: /app/views/Application/index.scala.html
                    HASH: 14ffb7e46c0816ada3fa031d919f7036c9264c4c
                    MATRIX: 329->1|450->15|478->18|506->38|526->40|572->60|633->101
                    LINES: 10->1|14->1|16->3|16->3|16->3|17->4|17->4
                    -- GENERATED --
                */
            
