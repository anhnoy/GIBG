package BackEnd.BackEnd.controller;

import BackEnd.BackEnd.config.ConfigDB;
import BackEnd.BackEnd.service.TokenService;
import BackEnd.BackEnd.exception.BaseException;
import BackEnd.BackEnd.exception.UsersException;
import BackEnd.BackEnd.model.Response;
import BackEnd.BackEnd.model.UploadFileReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/file")
public class FileController {

    UploadFileReq url = new UploadFileReq();
    public static String uploadDirectory = System.getProperty("user.dir");

    @Autowired
    private TokenService tokenService;


    @PostMapping("/image/user-profile")
    public Object uploadProfilePicture(@RequestParam("fileName") MultipartFile file) throws IOException {
        Map<String, Object> userByToken = tokenService.getUserByToken();
        System.out.println(file.getOriginalFilename());
        String dir = new UploadFileReq().getDirUserProfile();
        String timeStamp = new UploadFileReq().getTimeStamp();
        String imgName = new UploadFileReq().getImgName();

        if (file == null) {
            //throw error
            throw UsersException.userAlreadyExists();
        }
        if (file.getSize() > 1048576 * 5) {
//            throw error
            throw UsersException.userAlreadyExists();
        }
        String contentType = file.getContentType();
        if (contentType == null) {
            //throw  error
            throw UsersException.userAlreadyExists();
        }

        StringBuilder fileNames = new StringBuilder();

        Path fileNameAndPath = Paths.get(uploadDirectory + dir, imgName);
        fileNames.append(file.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            byte[] bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Object, Object> img = new HashMap<>();
        img.put("url", "http://localhost:8080" + dir + imgName);
        img.put("name", imgName);
        try (Connection conn = ConfigDB.db()) {
            String sql_update = "UPDATE users_entity set picture = '" + imgName + "', update_date = CURRENT_TIMESTAMP where id = '" + userByToken.get("id") + "'";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql_update);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        return new Response().success("yeah");
        return new Response().ok("upload success", "img", img);

    }


    @GetMapping("/userProfile")
    public ResponseEntity<Object> getUserProfile() throws BaseException {
        Map<String, Object> userByToken = tokenService.getUserByToken();
        try (Connection conn = ConfigDB.db()) {
            String user_picture;
            String sql = "select * from users_entity where id = '" + userByToken.get("id") + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return ResponseEntity.ok(new Response().ok("success","picture", (url.getHost() + url.getDirUserProfile() + resultSet.getString("picture"))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        user.setPicture(url.getHost() + url.getDirUserProfile() + user.getPicture())
        return (ResponseEntity<Object>) ResponseEntity.notFound();
    }

}