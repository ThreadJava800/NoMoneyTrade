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


//    @GetMapping("/")
//    public String listUploadedFiles(Model model) throws IOException {
//        model.addAttribute("files", storageService.loadAll().map(
//                        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                                "serveFile", path.getFileName().toString()).build().toUri().toString())
//                .collect(Collectors.toList()));
//
//        return "<html xmlns:th=\"https://www.thymeleaf.org\">\n" +
//                "<body>\n" +
//                "\n" +
//                "<div th:if=\"${message}\">\n" +
//                "  <h2 th:text=\"${message}\"/>\n" +
//                "</div>\n" +
//                "\n" +
//                "<div>\n" +
//                "  <form method=\"POST\" enctype=\"multipart/form-data\" action=\"/\">\n" +
//                "    <table>\n" +
//                "      <tr><td>File to upload:</td><td><input type=\"file\" name=\"file\" /></td></tr>\n" +
//                "      <tr><td></td><td><input type=\"submit\" value=\"Upload\" /></td></tr>\n" +
//                "    </table>\n" +
//                "  </form>\n" +
//                "</div>\n" +
//                "\n" +
//                "<div>\n" +
//                "  <ul>\n" +
//                "    <li th:each=\"file : ${files}\">\n" +
//                "      <a th:href=\"${file}\" th:text=\"${file}\" />\n" +
//                "    </li>\n" +
//                "  </ul>\n" +
//                "</div>\n" +
//                "\n" +
//                "</body>\n" +
//                "</html>";
//    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/api/store_avatar")
    public ResponseEntity<BaseResponse> handleAvatarUpload(@RequestParam("file") MultipartFile file) {
        String fileName = "avatars/" + file.getOriginalFilename();

        storageService.store(file, fileName);

        String jwtCookie = jwtUtils.getCleanJwtCookie().toString();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie).body(new BaseResponse(fileName));
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageException exception) {
        return ResponseEntity.notFound().build();
    }
}
