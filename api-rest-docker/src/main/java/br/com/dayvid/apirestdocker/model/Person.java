package br.com.dayvid.apirestdocker.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity // cria uma connecção com o banco através da anotation @Entity do jpa
@Table(name = "person") // indica a que tabela do banco de dados esse objeto se referência
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id //já não permite o id ser nulo memso sem usar a anotation nullable = False
    @GeneratedValue(strategy = GenerationType.IDENTITY) //torna o id auto incremental adicionando 1 a cada novo id
    private Long id;

    @Column(name = "first_name", nullable = false, length = 80) //funciona para referenciar qual coluna do banco esse atributo representa (só e nescessario caso o nome seja diferente)
    private String firstName;

    @Column(name = "Last_name", nullable = false, length = 80 )// length define o tamanho da cadeia de caracteres
    private String lastName;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 6)
    private String gender;

    public Person() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!Objects.equals(id, person.id)) return false;
        if (!Objects.equals(firstName, person.firstName)) return false;
        if (!Objects.equals(lastName, person.lastName)) return false;
        if (!Objects.equals(address, person.address)) return false;
        return Objects.equals(gender, person.gender);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }
}
