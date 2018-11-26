package com.demo.storage;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;


@Component
@Profile("prod")
public class S3FileSystemStorageService implements StorageService{


    private final Path rootLocation;

    @Autowired
    public S3FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Value("${s3}")
    public String bktname;

    @Override
    public String store(MultipartFile file,String user) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename())+"_"+user;
        try {
            if (file.isEmpty()) {
                System.out.println();
                throw new StorageException("Failed to store empty file " + filename);


            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }


        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .build();

        String name = file.getOriginalFilename();
        File file_temp = new File(rootLocation.toString()+"/"+filename);

        System.out.println("debug" + file_temp.getAbsolutePath());

        s3client.putObject(
                bktname,
                filename,
                file_temp
        );

        file_temp.delete();

        String fileLink =  s3client.getUrl(bktname, filename).toExternalForm();



        return fileLink;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {


        return null;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public Boolean deleteFile(String filePath) {

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .build();

        System.out.println("debug s3" + filePath);
        String[] resourceKey = filePath.split("/");
        String s3Key = resourceKey[resourceKey.length-1];
     //   System.out.println("resource key "+resourceKey[resourceKey.length-1]);

        try {
            s3client.deleteObject(bktname, s3Key);
            return true;
        } catch (Exception e) {
            return false;
        }


    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
