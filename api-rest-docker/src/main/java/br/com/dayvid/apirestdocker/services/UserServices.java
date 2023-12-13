package br.com.dayvid.apirestdocker.services;

import br.com.dayvid.apirestdocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

@Service //Serve para o spring boot veja esse como objeto que sera injetado em run time em outras classes da aplicação
public class UserServices implements UserDetailsService {
    private Logger logger = Logger.getLogger(UserServices.class.getName());

    @Autowired //injeta via propriedade
    UserRepository repository;

    public UserServices(UserRepository repository) { //quando se injeta pelo construtor significa que esse atributo e required
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("Finding one user by name!" + username + "!");
        var user = repository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }

    }
}
