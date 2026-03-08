package org.example.user;

import java.util.List;

public class UserMain {
    public static void main(String[] args) throws Exception{
      String url = "jdbc:mysql://localhost:3306/recipehub?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
      String user = "recipehub_user";
      String password = "user";

      UserDao dao = new JdbcUserDao(url, user, password);
      UserService service = new UserService(dao);

      int id =service.addUser("test");
      System.out.println(id);

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
