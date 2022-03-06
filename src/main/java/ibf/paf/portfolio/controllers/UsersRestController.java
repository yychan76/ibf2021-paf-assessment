package ibf.paf.portfolio.controllers;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ibf.paf.portfolio.models.CreateUserForm;
import ibf.paf.portfolio.models.UserProfile;
import ibf.paf.portfolio.services.UserService;

@RestController
@RequestMapping(path = "/api/users", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UsersRestController {
    private static final Logger LOG = Logger.getLogger(UsersRestController.class.getName());
    private JsonMapper jsonMapper = new JsonMapper();

    @Autowired
    private UserService userSvc;

    @GetMapping
    public ResponseEntity<String> getUsers() {
        List<UserProfile> users = userSvc.getAllUsers();
        LOG.info(() -> "getUsers() returned: " + users.toString());
        try {
            return ResponseEntity.ok(jsonMapper.writeValueAsString(users));
        } catch (IOException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("{uId}")
    public ResponseEntity<String> getUserById(@PathVariable int uId) throws JsonProcessingException {
        Optional<UserProfile> opt = userSvc.getUserById(uId);
        if (opt.isPresent()) {
            LOG.info(() -> "getUserById() returned: " + opt.get().toString());
            return ResponseEntity.ok(jsonMapper.writeValueAsString(opt.get()));
        } else {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().build().toUri();
            return ResponseEntity.notFound().location(location).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postCreateUser(@RequestBody CreateUserForm createUserForm)
            throws JsonProcessingException {
        LOG.info(() -> "createUserForm: " + createUserForm.toString());
        try {
            int uId = userSvc.addUser(createUserForm);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(uId).toUri();
            return ResponseEntity.created(location).body(jsonMapper
                    .writeValueAsString(Collections.singletonMap("response", "User: %d created".formatted(uId))));
        } catch (SQLException | DuplicateKeyException e) {
            return ResponseEntity.unprocessableEntity()
                    .body(jsonMapper.writeValueAsString(Collections.singletonMap("error", e.getMessage())));
        }

    }

    @PutMapping(path = "{uId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUser(@PathVariable int uId, @RequestBody UserProfile userProfile)
            throws JsonProcessingException {
        LOG.info(() -> "updateUser: " + uId + " " + userProfile.toString());
        boolean updated = userSvc.updateUser(uId, userProfile);
        if (updated) {
            return ResponseEntity.ok().body(jsonMapper
                    .writeValueAsString((Collections.singletonMap("response", "User: %d updated".formatted(uId)))));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonMapper
                    .writeValueAsString(Collections.singletonMap("error", "User: %d update fail".formatted(uId))));
        }
    }

    @DeleteMapping("{uId}")
    public ResponseEntity<String> deleteUser(@PathVariable int uId) throws JsonProcessingException {
        LOG.info(() -> "deleteUser: " + uId);
        boolean deleted = userSvc.deleteUser(uId);
        if (deleted) {
            return ResponseEntity.ok(jsonMapper
                    .writeValueAsString(Collections.singletonMap("response", "User: %d deleted".formatted(uId))));
        } else {
            return ResponseEntity.internalServerError().body(jsonMapper
                    .writeValueAsString(Collections.singletonMap("error", "User: %d delete fail".formatted(uId))));
        }
    }
}
