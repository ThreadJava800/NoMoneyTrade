package com.example.nomoneytrade.controller;

import com.example.nomoneytrade.imageStorage.StorageException;
import com.example.nomoneytrade.imageStorage.StorageService;
import com.example.nomoneytrade.payload.responses.BaseResponse;
import com.example.nomoneytrade.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    @Autowired
    JwtUtils jwtUtils;

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/api/put_image")
    @ResponseBody
    public ResponseEntity<BaseResponse> putFile(@RequestParam("file") MultipartFile file) {
        storageService.store(file, file.getOriginalFilename());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getOriginalFilename() + "\"").body(new BaseResponse("Test"));
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageException exception) {
        return ResponseEntity.notFound().build();
    }
}
