package com.mycode.myExpenseTracker.controller;

import com.mycode.myExpenseTracker.exceptions.InvalidFileException;
import com.mycode.myExpenseTracker.model.LoginAccount;
import com.mycode.myExpenseTracker.service.FileService;
import com.mycode.myExpenseTracker.service.LoginAccountService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private LoginAccountService loginAccountService;

    @Value("${project.profile_img}")
    private String path;

    @PostMapping("/upload/{username}")
    public ResponseEntity<?> uploadFileHandler(@PathVariable String username, @RequestBody MultipartFile file
    ) throws IOException, InvalidFileException {
        if (file == null) {
            throw new InvalidFileException("Image file should not be null.");
        }
        String uploadedFilePath = fileService.uploadFile(path, file);
        LoginAccount loginAccount = loginAccountService.getByUsername(username);
        loginAccount.setImageUrl(uploadedFilePath);
        loginAccountService.save(loginAccount);
        return ResponseEntity.ok().body("File uploaded: " + uploadedFilePath);
    }

    @GetMapping("/{fileName}")
    public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {

        InputStream resourceFile = fileService.getResourceFile(path, fileName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resourceFile, response.getOutputStream());
    }

}
