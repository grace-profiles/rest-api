@artifact.package@
import spock.lang.Specification

import grails.testing.services.ServiceUnitTest

class @artifact.name@ServiceSpec extends Specification implements ServiceUnitTest<@artifact.name@Service> {

    def setup() {
    }

    def cleanup() {
    }

    void 'test something'() {
        expect: 'fix me'
            true == false
    }

}
