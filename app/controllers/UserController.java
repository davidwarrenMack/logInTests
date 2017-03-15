package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Password;
import models.User;
import play.Logger;

import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Call;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.MILLIS;
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
            jpaApi.em().persist(user);
            System.out.println("password hash: " + hash);
            System.out.println("password salt: " + salt);

            return ok(toJson("refresh DB for updated values"));
        }
        else
            {
                return redirect(route);
            }
    }
}
