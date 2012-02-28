
                    package views.Application.html

                    import play.templates._
                    import play.templates.TemplateMagic._
                    import views.html._

                    object queryNCBO extends BaseScalaTemplate[Html,Format[Html]](HtmlFormat) {

                        def apply():Html = {
                            try {
                                _display_ {

}
                            } catch {
                                case e:TemplateExecutionError => throw e
                                case e => throw Reporter.toHumanException(e)
                            }
                        }

                    }

                
                /*
                    -- GENERATED --
                    DATE: Fri Feb 24 18:27:51 PST 2012
                    SOURCE: /app/views/Application/queryNCBO.scala.html
                    HASH: da39a3ee5e6b4b0d3255bfef95601890afd80709
                    MATRIX: 
                    LINES: 
                    -- GENERATED --
                */
            
