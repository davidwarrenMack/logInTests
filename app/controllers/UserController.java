package controllers;

import models.Password;
import models.User;
import models.Patient;

import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Call;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static models.Password.getNewSalt;
import static play.libs.Json.toJson;

public class UserController extends Controller
{
    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public UserController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }


    public Result showUser()
    {
        return ok(views.html.user.render());
    }
    public Result showAddPassword()
    {
        return ok(views.html.addpasswordandsalt.render());
    }

    @Transactional
    public Result addPasswordAndSalt()
    {
        Call route = routes.UserController.addPasswordAndSalt();

        DynamicForm dynaForm = formFactory.form().bindFromRequest();

        String userEmail = dynaForm.get("userEmail");
        String password = dynaForm.get("password");
        String cellPhone = dynaForm.get ("cellPhone");
        String cellPhone1 = "1" + cellPhone;




        byte hash[] = null;
        byte salt[] = null;

        try
        {
            //Get a nice random salt
            salt = getNewSalt();
            System.out.println("test" + salt);
            //hash the password
            hash = Password.hashPassword(password.toCharArray(), salt);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        List<User> users = (List<User>)jpaApi.em().
                createQuery("select u from User u where u.userEmail = :userEmail", User.class).
                setParameter("userEmail", userEmail).getResultList();
        System.out.println("email and password " + userEmail + " " + password);

        if (users.size() == 1)
        {
            User user = users.get(0);
            user.password = hash;
            user.passwordSalt = salt;
            user.cellPhone = cellPhone1;
            System.out.println(cellPhone1);
            jpaApi.em().persist(user);
            System.out.println("password hash: " + hash);
            System.out.println("password salt: " + salt);

            return ok(toJson("refresh DB for updated values"));
        }
        else if (users.size() == 0)
            {
                //String cellPhone1 = "1" + cellPhone;
                System.out.println(cellPhone1);

                User user = new User();
                user.password = hash;
                user.passwordSalt = salt;
                user.cellPhone = cellPhone1;
                user.userEmail = userEmail;
                jpaApi.em().persist(user);
                System.out.println("new password hash: " + hash);
                System.out.println("new password salt: " + salt);

                return ok(toJson("refresh DB for updated values"));
            }
        else
            {
                return redirect(route);
            }
    }

    public Result showAddUser()
    {
        return ok(views.html.createnewuser.render());
    }

    @Transactional
    public Result postAddUser()
    {
        DynamicForm dynaForm = formFactory.form().bindFromRequest();

        Patient patient = new Patient();

        String firstName = dynaForm.get("firstName");
        String lastName = dynaForm.get("lastName");
        String dob = dynaForm.get("dob");
        String gender = dynaForm.get("gender");
        String address = dynaForm.get("address");
        String city = dynaForm.get("city");
        String state = dynaForm.get("state");
        String cellPhone = dynaForm.get("cellPhone");
        String zip = dynaForm.get("zip");
        String email = dynaForm.get("email");
        System.out.println("dob: " + dob);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.US);
        LocalDate dob1 = LocalDate.parse(dob, formatter);
        System.out.println("dob1: " + dob1);

        patient.firstName = firstName;
        patient.lastName = lastName;
        patient.dob = dob1;
        patient.gender = gender;
        patient.address = address;
        patient.city = city;
        patient.state = state;
        patient.cellPhone = cellPhone;
        patient.zip = zip;
        patient.email = email;


        jpaApi.em().persist(patient);

        //return ok(toJson("addeduser: " + firstName + " " + lastName + " " + dob + " " +
        //gender + " " + address + " " + city + " " + state + " " + cellPhone + " " + zip + " " + email));
        return redirect(routes.UserController.showAddPassword());
    }
}
