
                    package views.Application.html

                    import play.templates._
                    import play.templates.TemplateMagic._
                    import views.html._

                    object query extends BaseScalaTemplate[Html,Format[Html]](HtmlFormat) {

                        def apply/*1.2*/(query:String):Html = {
                            try {
                                _display_ {

format.raw/*1.16*/("""

""")+_display_(/*3.2*/main(title = "Query Results")/*3.31*/ {format.raw/*3.33*/("""
	<h1>Query text: """)+_display_(/*4.19*/(query ?: "None"))+format.raw/*4.36*/("""</h1>
	<a href="""")+_display_(/*5.12*/action(controllers.Application.index))+format.raw/*5.49*/("""">Back to search home</a>
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
                    SOURCE: /app/views/Application/query.scala.html
                    HASH: c818561d188cfa826d0c84ef56db330454b139c9
                    MATRIX: 329->1|450->15|478->18|515->47|535->49|580->68|617->85|660->102|717->139
                    LINES: 10->1|14->1|16->3|16->3|16->3|17->4|17->4|18->5|18->5
                    -- GENERATED --
                */
            
