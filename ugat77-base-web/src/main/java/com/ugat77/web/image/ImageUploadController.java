package com.ugat77.web.image;

import com.ugat77.utils.ResultUtils;
import com.ugat77.utils.ResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class ImageUploadController {
    //Get uploading path

    @Value("${web.uploadpath}")
    private String webUploadpath;

    @RequestMapping("/uploadImage")
    public ResultVo uploadImage(@RequestParam("file")MultipartFile file){
        //return path after success uploading
        String Url = null;
        //Get file name
        String fileName = file.getOriginalFilename();
        //get extension .png
        String fileExtensionName = fileName.substring(fileName.indexOf("."));

        //generate new name
        String newName = UUID.randomUUID().toString() + fileExtensionName;
        String path = webUploadpath;
        File fileDir = new File(path);
        if (!fileDir.exists()){
            //set permission
            fileDir.setWritable(true);
            fileDir.mkdir();
        }

        File targetFile = new File(path, newName);
        try {
            file.transferTo(targetFile);
            Url = "/" + targetFile.getName();
        }catch (Exception e){
            return  null;
        }
        return ResultUtils.success("Success","/images" + Url);
    }

}
