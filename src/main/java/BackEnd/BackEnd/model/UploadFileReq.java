package BackEnd.BackEnd.model;


import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class UploadFileReq {


    private String dirUserProfile = "/uploads/images/Userprofile/";
    private String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    private String imgName = timeStamp + ".png";
    private String host = "http://localhost:8080";

}
