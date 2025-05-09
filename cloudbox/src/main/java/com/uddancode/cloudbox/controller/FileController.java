package com.uddancode.cloudbox.controller;

import com.uddancode.cloudbox.entity.FileEntity;
import com.uddancode.cloudbox.services.FileServiceStorage;
import org.apache.coyote.Response;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {

    private  final FileServiceStorage fileServiceStorage;

    public FileController(FileServiceStorage fileServiceStorage) {
        this.fileServiceStorage = fileServiceStorage;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file,
                                             @RequestParam(value = "parentFolderId", required = false) Long parentFolderId)
    {
        try{
            String response = fileServiceStorage.saveFile(file,parentFolderId);
            return  ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("File Uploaded Failed!!");
        }
    }

    @GetMapping("/download/{id}")
    public  ResponseEntity<Resource> downloadFile(@PathVariable Long id){
        try{
            //mysql meta data
            FileEntity fileEntity= fileServiceStorage.getFileById(id);
            Path path = Paths.get(fileEntity.getPath());
            Resource resource= new UrlResource(path.toUri());
            return  ResponseEntity.ok().header("content disposition", "attachment ;  filename=\""+fileEntity.getName()+"\"")
                    .body(resource);
        } catch (Exception e) {
            return  ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileEntity>> listfiles(
            @RequestParam(value = "parentFolderId", required = false) Long parentFolderId)
    {
        List<FileEntity> files;
        if(parentFolderId==null){
            files = fileServiceStorage.getFilesInFolder(null);
        }
        else{
            files=fileServiceStorage.getFilesInFolder(parentFolderId);
        }
        return ResponseEntity.ok(files);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id){
        try {
            //will take the path from mysql
            FileEntity fileEntity = fileServiceStorage.getFileById(id);

            //will make the path and delete from disk
            Path path = Paths.get(fileEntity.getPath());
            Files.deleteIfExists(path);
            fileServiceStorage.deleteById(id);
            return  ResponseEntity.ok("File Deleted successfully");
        } catch (Exception e) {
            return  ResponseEntity.status(500).body("Failed to delete file");
        }
    }
}
