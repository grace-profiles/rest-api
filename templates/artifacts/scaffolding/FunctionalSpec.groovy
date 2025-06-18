<%=packageName ? "package ${packageName}" : ''%>

import spock.lang.Specification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore

@Integration
class ${className}FunctionalSpec extends Specification {

    @LocalServerPort
    private int port

    @Autowired
    private TestRestTemplate restTemplate

    @OnceBefore
    void init() {
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    String getResourcePath() {
        assert false, "TODO: provide the path to your resource. Example: \"\${baseUrl}/books\""
    }

    Map getValidJson() {
        assert false, "TODO: provide valid JSON"
    }

    Map getInvalidJson() {
        assert false, "TODO: provide invalid JSON"
    }

    void "Test the index action"() {
        when:"The index action is requested"
        ResponseEntity<List> response = this.restTemplate.getForEntity(resourcePath, List.class)

        then:"The response is correct"
        response.statusCode == HttpStatus.OK
        response.body == []
    }

    @Rollback
    void "Test the save action correctly persists an instance"() {
        when:"The save action is executed with no content"
        ResponseEntity<${className}> response = this.restTemplate.postForEntity(resourcePath, [:], ${className})

        then:"The response is correct"
        response.statusCode == HttpStatus.UNPROCESSABLE_ENTITY

        when:"The save action is executed with invalid data"
        response = this.restTemplate.postForEntity(resourcePath, invalidJson, ${className})

        then:"The response is correct"
        response.statusCode == HttpStatus.UNPROCESSABLE_ENTITY

        when:"The save action is executed with valid data"
        response = this.restTemplate.postForEntity(resourcePath, validJson, ${className})

        then:"The response is correct"
        response.statusCode == HttpStatus.CREATED
        response.body
        ${className}.count() == 1

        cleanup:
        def id = response.body.id
        def path = "\${resourcePath}/\${id}"
        this.restTemplate.delete(path)
        response = this.restTemplate.getForEntity(path, ${className})
        assert response.statusCode == HttpStatus.NOT_FOUND
    }

    @Rollback
    void "Test the update action correctly updates an instance"() {
        when:"The save action is executed with valid data"
        ResponseEntity<${className}> response = this.restTemplate.postForEntity(resourcePath, validJson, ${className})

        then:"The response is correct"
        response.statusCode == HttpStatus.CREATED
        response.body

        when: "The update action is called with valid data"
        String path = "\${resourcePath}/\${response.body.id}"
        this.restTemplate.put(path, validJson)
        response = this.restTemplate.getForEntity(path, ${className})

        then:"The response is correct"
        response.statusCode == HttpStatus.OK
        response.body

        cleanup:
        this.restTemplate.delete(path)
        response = this.restTemplate.getForEntity(path, ${className})
        assert response.statusCode == HttpStatus.NOT_FOUND
    }

    @Rollback
    void "Test the show action correctly renders an instance"() {
        when:"The save action is executed with valid data"
        ResponseEntity<${className}> response = this.restTemplate.postForEntity(resourcePath, validJson, ${className})

        then:"The response is correct"
        response.statusCode == HttpStatus.CREATED
        response.body.id

        when:"When the show action is called to retrieve a resource"
        def id = response.body.id
        String path = "\${resourcePath}/\${id}"
        response = this.restTemplate.getForEntity(path, ${className})

        then:"The response is correct"
        response.statusCode == HttpStatus.OK
        response.body.id == id

        cleanup:
        this.restTemplate.delete(path)
    }

    @Rollback
    void "Test the delete action correctly deletes an instance"() {
        when:"The save action is executed with valid data"
        ResponseEntity<${className}> response = this.restTemplate.postForEntity(resourcePath, validJson, ${className})

        then:"The response is correct"
        response.statusCode == HttpStatus.CREATED
        response.body.id

        when:"When the delete action is executed on an unknown instance"
        def id = response.body.id
        def path = "\${resourcePath}/99999"
        response = this.restTemplate.exchange(path, HttpMethod.DELETE, null, ${className})

        then:"The response is correct"
        response.statusCode == HttpStatus.NOT_FOUND

        when:"When the delete action is executed on an existing instance"
        path = "\${resourcePath}/\${id}"
        response = this.restTemplate.exchange(path, HttpMethod.DELETE, null, ${className})

        then:"The response is correct"
        response.statusCode == HttpStatus.NO_CONTENT
        !${className}.get(id)
    }
}
