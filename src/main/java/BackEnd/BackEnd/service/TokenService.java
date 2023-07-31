package BackEnd.BackEnd.service;

import BackEnd.BackEnd.config.ConfigDB;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Service
public class TokenService {

    @Value("${app.token.secret}")
    private String secret;

    @Value("${app.token.issuer}")
    private String issuer;

    public String tokenize(ResultSet user) throws SQLException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        Date expiresAt = calendar.getTime();

        return JWT.create()
                .withIssuer(issuer)
                .withClaim("userId", user.getString("id"))
                .withClaim("role", "user")
                .withExpiresAt(expiresAt)
                .sign(algorithm());
    }

    public String tokenizeRefresh(ResultSet user) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, 7);
        Date expiresAt = calendar.getTime();

        return JWT.create()
                .withIssuer(issuer)
                .withClaim("userId", user.getString("id"))
                .withExpiresAt(expiresAt)
                .sign(algorithm());
    }

    public DecodedJWT verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm())
                    .withIssuer(issuer)
                    .build();

            return verifier.verify(token);

        } catch (Exception e) {
            return null;
        }
    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }

    public Map<String, Object> getUserByToken(){
        Map<String, Object> map = new HashMap<>();
        try(Connection conn = ConfigDB.db()){
            String sql = "select * from users_entity where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.userId());

            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.close();
            if (resultSet.next()){
                map.put("id",resultSet.getString("id"));
                map.put("email",resultSet.getString("email"));
                map.put("phone",resultSet.getString("phone"));
                map.put("firstname",resultSet.getString("firstname"));
                map.put("lastname",resultSet.getString("lastname"));
                map.put("picture",resultSet.getString("picture"));
                map.put("role",resultSet.getString("role"));
                return map;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }


    public String userId() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userId = (String) authentication.getPrincipal();

        return userId;
    }

}
