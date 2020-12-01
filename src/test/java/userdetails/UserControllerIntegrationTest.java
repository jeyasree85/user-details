package userdetails;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import userdetails.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserDetailsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/users",
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
    }
    @Test
    public void testUserId() {
        User user = restTemplate.getForObject(getRootUrl() + "/users/5", User.class);
        assertNotNull(user);
    }
    @Test
    public void testCreateUser() {
        User user = new User();
        user.setFullName("admin");
        user.setPassword("root");
        user.setEmail("admin@gmail.com");
        user.setJobTitle("administrator");
        user.setDepartment("software");
        user.setPhoneNumber("1234567890");
        ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateUser() {
        int id = 1;
        User user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        user.setFullName("admin1");
        restTemplate.put(getRootUrl() + "/users/" + id, user);

        User updatedEmployee = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        assertNotNull(updatedEmployee);
    }

    @Test
    public void testDeleteUser() {
        int id = 2;
        User user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        assertNotNull(user);

        restTemplate.delete(getRootUrl() + "/users/" + id);

        try {
            user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
