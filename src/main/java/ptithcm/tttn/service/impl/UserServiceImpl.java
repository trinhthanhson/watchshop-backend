package ptithcm.tttn.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ptithcm.tttn.config.JwtTokenProvider;
import ptithcm.tttn.entity.Customer;
import ptithcm.tttn.entity.Role;
import ptithcm.tttn.entity.Staff;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.repository.CustomerRepo;
import ptithcm.tttn.repository.RoleRepo;
import ptithcm.tttn.repository.StaffRepo;
import ptithcm.tttn.repository.UserRepo;
import ptithcm.tttn.request.SignUpRequest;
import ptithcm.tttn.service.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepo userRepo;
    private final StaffRepo staffRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepo customerRepo;


    public UserServiceImpl(JwtTokenProvider jwtTokenProvider, UserRepo userRepo, StaffRepo staffRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder, CustomerRepo customerRepo) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepo = userRepo;
        this.staffRepo = staffRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.customerRepo = customerRepo;
    }

    @Override
    @Transactional
    public User createUser(SignUpRequest rq) throws Exception {
        User user = new User();
        Role role = roleRepo.findByName(rq.getRole_name());
        user.setCreated_at(LocalDateTime.now());
        user.setStatus("Active");
        user.setUpdated_at(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(rq.getPassword()));
        user.setRole_id(role.getRole_id());
        user.setUsername(rq.getUsername());
        User saveUser = userRepo.save(user);
        if (saveUser != null) {
            Customer customer = new Customer();
            customer.setCreated_at(LocalDateTime.now());
            customer.setEmail(rq.getEmail());
            customer.setUser_id(saveUser.getUser_id());
            customer.setFirst_name(rq.getFirstname());
            customer.setLast_name(rq.getLastname());
            customer.setUpdated_at(LocalDateTime.now());
            Customer saveCustomer = customerRepo.save(customer);
        } else {
            throw new Exception("Create user fail");
        }
        return saveUser;
    }

    @Override
    public User findByUsername(String username) throws Exception {
        User findUsername = userRepo.findByUsername(username);
        if (findUsername != null) {
            return findUsername;
        } else {
            throw new Exception("Not found user by username " + username);
        }

    }

    @Override
    @Transactional
    public User signIn(User user) {
        return userRepo.save(user);
    }

    @Override
    public User findUserByJwt(String jwt) throws Exception {
        String username = jwtTokenProvider.getUsernameFromJwtToken(jwt);
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new Exception("user not exist with username " + username);
        }
        return user;
    }

    @Override
    public User findById(Long id) throws Exception {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new Exception("Not found User by id " + id);
    }

    @Override
    public List<User> findAll() throws Exception {
        List<User> findAll = userRepo.findAll();
        if (findAll != null) {
            return findAll;
        }
        throw new Exception("User is empty");
    }

    @Override
    @Transactional
    public User updateStatus(Long id, String status, String jwt) throws Exception {
        User user = findUserByJwt(jwt);
        Staff staff = staffRepo.findByUserId(user.getUser_id());
        User update = findById(id);
        User save = new User();
        if(update != null && (user.getRole_user().getRole_name().equals("MANAGER") || user.getRole_user().getRole_name().equals("STAFF")) ){
            update.setStatus(status);
            update.setUpdated_by(staff.getStaff_id());
            save = userRepo.save(update);
        }else{
            throw new Exception("Can't update user of staff");
        }
        return user;
    }
}
