package br.com.dayvid.apirestdocker.repositories;


import br.com.dayvid.apirestdocker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

        @Query("SELECT u FROM User WHERE u.userName =:userName") //ligaguem jpql lida com objeto não com a tabela em sí
        User findByUsername(@Param("userName") String userName);

}

