package models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Patient

{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "PATIENT_ID")
    public String patientId;

    @Column(name = "FIRST_NAME")
    public String firstName;

    @Column(name = "LAST_NAME")
    public String lastName;

    @Column(name = "DOB")
    public LocalDate dob;

    @Column(name = "GENDER")
    public String gender;

    @Column(name = "ADDRESS")
    public String address;

    @Column(name = "CITY")
    public String city;

    @Column(name = "STATE")
    public String state;

    @Column(name = "CELL_PHONE")
    public String cellPhone;

    @Column(name = "ZIP")
    public String zip;

    @Column(name = "EMAIL")
    public String email;




}