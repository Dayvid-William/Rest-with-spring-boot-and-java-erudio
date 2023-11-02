package br.com.dayvid.apirestdocker.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder({"id", "firstName", "lastName", "address", "gender"})
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @Mapping("id")//diz o nome que referencia nmo model no model
    private Long Key;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
    public PersonVO() {}

    public Long getKey() {
        return Key;
    }

    public void setKey(Long key) {
        Key = key;
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
        if (!super.equals(o)) return false;

        PersonVO personVO = (PersonVO) o;

        if (!Objects.equals(Key, personVO.Key)) return false;
        if (!Objects.equals(firstName, personVO.firstName)) return false;
        if (!Objects.equals(lastName, personVO.lastName)) return false;
        if (!Objects.equals(address, personVO.address)) return false;
        return Objects.equals(gender, personVO.gender);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (Key != null ? Key.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }
}
