package BackEnd.BackEnd.service;

import BackEnd.BackEnd.config.ConfigDB;
import BackEnd.BackEnd.exception.BaseException;
import BackEnd.BackEnd.exception.UsersException;
import BackEnd.BackEnd.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    private TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public UsersService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String generatedID () {
        String generatedString = (UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                + UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                ).substring(0, 36);
        return generatedString;
    }

    public Object registerUser(Map<String,Object> req) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        try(Connection conn = ConfigDB.db()){
            String phone = req.get("phone").toString();
            String email = req.get("email").toString();
            if (phone.isBlank()){
                throw UsersException.phoneBlank();
            }
            if (email.isBlank()){
                throw UsersException.emailBlank();
            }
            System.out.println(phone);
            System.out.println(email);
            System.out.println(req.get("firstname").toString());
            System.out.println(req.get("lastname").toString());
            System.out.println(req.get("password").toString());
            String sql = "SELECT * FROM users_entity WHERE phone = '"+ phone +"' or email = '"+ email +"'";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            boolean next = rs.next();
            if (next){
                throw  UsersException.userAlreadyExists();
            }
            String sql_insert = "INSERT INTO users_entity (id,create_date,email,firstname,lastname,password,phone,role,update_date)" +
                    "VALUES('"+ this.generatedID() +"',CURRENT_TIMESTAMP,'"+ email +"','"+ req.get("firstname").toString() +"','"+ req.get("lastname").toString() +"','"+ passwordEncoder.encode(req.get("password").toString()) +"'" +
                    ",'"+ phone +"', 1,CURRENT_TIMESTAMP)";
            Statement st_insert = conn.createStatement();
            st_insert.executeUpdate(sql_insert);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Response().success("success");
    }

    public Object loginUser(Map<String,Object> req) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String token = "";
        try(Connection conn = ConfigDB.db()){
            String phone = req.get("user").toString();
            String password = req.get("password").toString();
            if (phone.isBlank()){
                throw UsersException.phoneBlank();
            }
            String sql = "SELECT * FROM users_entity WHERE phone = '"+ phone +"' or email = '"+ phone +"'";
            System.out.println(sql);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            boolean next = rs.next();
            System.out.println(next);
            if (next){
                token = tokenService.tokenize(rs);
                if (!mathpassword(password, rs.getString("password"))){
                    throw UsersException.passwordNotmatch();
                }
            }else {
                throw UsersException.loginFail();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Response().ok("Ok","token",token);
    }

    public Object userProfile() throws BaseException {
//        Map<String, Object> map = new HashMap<>();
        Map<String, Object> userByToken = tokenService.getUserByToken();

        return new Response().ok("Ok","value",userByToken);
    }

    public Object editProfile(Map<String,Object> req) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> user = tokenService.getUserByToken();
        try(Connection conn = ConfigDB.db()){
            String email = req.get("email").toString();
            String phone = req.get("phone").toString();
            String firstname = req.get("firstname").toString();
            String lastname = req.get("lastname").toString();

            if (email.isBlank()){
                throw UsersException.emailBlank();
            }
            if (phone.isBlank()){
                throw UsersException.phoneBlank();
            }
            if (firstname.isBlank()){
                throw UsersException.firstnameBlank();
            }
            if (lastname.isBlank()){
                throw UsersException.lastnameBlank();
            }
            String sql_update = "UPDATE users_entity set email = '"+ email +"',phone = '"+ phone +"',firstname = '"+ firstname +"',lastname = '"+ lastname +"',update_date = CURRENT_TIMESTAMP where id = '"+ user.get("id") +"'";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql_update);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Response().success("success");
    }

    public boolean mathpassword(String rawpassword, String endcodepassword){
        return passwordEncoder.matches(rawpassword,endcodepassword);
    }
}
