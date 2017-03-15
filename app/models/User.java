package models;

import javax.persistence.*;

@Entity
public class User

{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    public int userId;

    @Column(name = "PATIENT_ID")
    public String patientId;

    @Column(name = "EMAIL")
    public String userEmail;

    @Column(name = "PASSWORD")
    public byte[] password;

    @Column(name = "CELL_PHONE")
    public String cellPhone;

    @Column(name = "PASSWORD_SALT")
    public byte[] passwordSalt;



}
