package models;

import javax.persistence.*;

@Entity
public class User

{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    public String userId;

    @Column(name = "PATIENT_ID")
    public String patientId;

    @Column(name = "PASSWORD")
    public byte[] password;

    @Column(name = "PASSWORD_SALT")
    public byte[] passwordSalt;

    @Column(name = "USER_EMAIL")
    public String userEmail;

    @Column(name = "USER_CELL_PHONE")
    public String cellPhone;




}
