@artifact.package@
import grails.converters.*
import grails.rest.*

class @artifact.name@Controller extends RestfulController<@artifact.name@> {
    static responseFormats = ['json', 'xml']

    @artifact.name@Controller() {
        super(@artifact.name@)
    }
}
