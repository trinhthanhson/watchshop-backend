package ptithcm.tttn.service;

import ptithcm.tttn.entity.User;
import ptithcm.tttn.request.SignUpRequest;

import java.util.List;

public interface UserService {

    User createUser(SignUpRequest rq) throws Exception;
    User findByUsername(String username) throws Exception;
    User signIn(User user);
    User findUserByJwt(String jwt) throws Exception;
    User findById(Long id) throws Exception;
    List<User> findAll() throws Exception;
    public User updateStatus(Long id, String status, String jwt) throws Exception;
}
