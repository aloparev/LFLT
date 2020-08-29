package home.lflt.security;

import home.lflt.model.User;
import home.lflt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * return user or throw an error
 */
@Service
public class DetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    @Autowired
    public DetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user != null) return user;

        throw new UsernameNotFoundException(
                "User '" + username + "' not found!"
        );
    }
}
