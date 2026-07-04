package User;
import java.util.*;
import java.awt.*;
//implemented singleton design pattern

public class UserController {
    
    //static bcs want it to be shared memory
    private static UserController userController = new UserController();
    
    //private constructor to not make it possible to be instantiated
    private UserController(){}
    
    public static UserController getInstance(){
        return userController;
    }
    


}
