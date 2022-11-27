package com.binar.finalproject.BEFlightTicket.dto;

import com.binar.finalproject.BEFlightTicket.model.Roles;
import com.binar.finalproject.BEFlightTicket.model.Users;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class UserRequest {
    @NotEmpty(message = "fullName is required.")
    private String fullName;
    @NotEmpty(message = "email is required.")
    private String email;
    @NotEmpty(message = "password is required.")
    private String password;
    @NotEmpty(message = "telephone is required.")
    private String telephone;
    @NotEmpty(message = "birthDate is required.")
    private LocalDate birthDate;
    @NotEmpty(message = "gender is required.")
    private Boolean gender;
    @NotEmpty(message = "rolesUsers is required.")
    private Integer rolesId;

    public Users toUsers(Roles roles) {
        Users users = new Users();
        users.setFullName(this.fullName);
        users.setEmail(this.email);
        users.setPassword(this.password);
        users.setTelephone(this.telephone);
        users.setBirthDate(this.birthDate);
        users.setGender(this.gender);
        users.setRolesUsers(roles);
        return users;
    }
}
