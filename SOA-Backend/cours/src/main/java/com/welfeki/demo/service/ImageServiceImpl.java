package com.welfeki.demo.service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.welfeki.demo.entities.Cours;
    import com.welfeki.demo.repos.CoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.welfeki.demo.entities.Image;
import com.welfeki.demo.repos.ImageRepository;


@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    ImageRepository imageRepository;
    @Autowired
    CoursService coursService;
    @Autowired
    CoursRepository coursRepository;

    @Override
    public Image uplaodImage(MultipartFile file) throws IOException {
        // Save the image to the database
        Image image = imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(file.getBytes())
                .build());
        return image;
    }

    @Override
    public Image getImageDetails(Long id) throws IOException{
        final Optional<Image> dbImage = imageRepository. findById (id);

        return Image.builder()
                .idImage(dbImage.get().getIdImage())
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .image(dbImage.get().getImage()).build() ;
    }

    @Override
    public ResponseEntity<byte[]> getImage(Long id) throws IOException{
        final Optional<Image> dbImage = imageRepository. findById (id);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(dbImage.get().getImage());
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
    @Override
    public Image uplaodImageCours(MultipartFile file,Long idCours)
            throws IOException {
        Cours c = new Cours();
        c.setId(idCours);
        return imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(file.getBytes())
                .cours(c).build() );
    }
    @Override
    public List<Image> getImagesParCours(Long prodId) {
        Cours p = coursRepository.findById(prodId).get();
        return p.getImages();
    }

}