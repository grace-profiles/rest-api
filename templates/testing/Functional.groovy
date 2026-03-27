@artifact.package@
import spock.lang.Specification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort

import grails.testing.mixin.integration.Integration

@Integration
class @artifact.name@Spec extends Specification {

    @LocalServerPort
    private int port

    @Autowired
    private TestRestTemplate restTemplate

    void "Test the homepage"() {
        when: "The home page is requested"
        String message = this.restTemplate.getForObject("http://localhost:" + port + "/", String)

        then: "The response is correct"
        message.contains('Welcome to Grace')
    }

}
