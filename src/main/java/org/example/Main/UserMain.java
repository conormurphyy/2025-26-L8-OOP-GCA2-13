package org.example.Main;

import org.example.dao.UserDao;
import org.example.dao.jdbc.JdbcUserDao;
import org.example.domain.User;
import org.example.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserMain {
    public static void main(String[] args) throws Exception{
      String url = "jdbc:mysql://localhost:3306/taskhub?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
      String user = "root";
      String password = "";

      UserDao dao = new JdbcUserDao(url, user, password);
      UserService service = new UserService(dao);

      //User created = service.addUser(new User(100011, "test", "user", 3.0));
      //System.out.println(created);


        System.out.println("find by id");
       Optional<User> uTest = service.findById(2);
       System.out.println(uTest);
        for(User u :service.list())
        {
            System.out.println(u);
        }
        service.updateRating(1, 5);
        for(User u :service.list())
        {
            System.out.println(u);
        }
        service.updateType(1, "admin");
        for(User u :service.list())
        {
            System.out.println(u);
        }
        service.delete(1);
        for(User u :service.list())
        {
            System.out.println(u);
        }



        System.out.println("testing predicate");
        List <User> nonTests =UserService.filter(service.list(),u ->"test".equalsIgnoreCase(u.getUsername()));
        for(User u :nonTests)
        {
            System.out.println(u);
        }

        System.out.println("Testing predicate 2");
        List <User> admins =UserService.filter(service.list(),u ->"admin".equalsIgnoreCase(u.getUserType()));
        for(User u :admins)
        {
            System.out.println(u);
        }


    }
}
