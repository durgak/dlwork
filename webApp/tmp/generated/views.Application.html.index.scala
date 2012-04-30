
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
    <form action="""")+_display_(/*4.20*/action(controllers.Application.reconcile))+format.raw/*4.61*/("""" method="GET">
        <input type="text" name="query" /> 
        <input type="submit" value="Search NCBO" />
    </form>
""")})+format.raw/*8.2*/("""
""")}
                            } catch {
                                case e:TemplateExecutionError => throw e
                                case e => throw Reporter.toHumanException(e)
                            }
                        }

                    }

                
                /*
                    -- GENERATED --
                    DATE: Wed Apr 25 15:55:18 PDT 2012
                    SOURCE: /app/views/Application/index.scala.html
                    HASH: e207ed3abd2170e0ff9c7811d3c4a9b98f14ef5f
                    MATRIX: 329->1|450->15|478->18|506->38|526->40|572->60|633->101|785->226
                    LINES: 10->1|14->1|16->3|16->3|16->3|17->4|17->4|21->8
                    -- GENERATED --
                */
            
