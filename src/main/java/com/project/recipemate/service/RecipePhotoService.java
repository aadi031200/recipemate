package com.project.recipemate.service;

import com.project.recipemate.dtos.RecipePhotoResponseDTO;
import com.project.recipemate.dtos.RecipeResponseDTO;
import com.project.recipemate.entities.Recipe;
import com.project.recipemate.entities.RecipePhoto;
import com.project.recipemate.entities.User;
import com.project.recipemate.repository.RecipePhotoRepository;
import com.project.recipemate.repository.RecipeRepository;
import com.project.recipemate.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipePhotoService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RecipePhotoRepository recipePhotoRepository;
    private final FileStorageService fileStorageService;

    public RecipePhotoService (RecipeRepository recipeRepository,
                               UserRepository userRepository,
                               RecipePhotoRepository recipePhotoRepository,
                               FileStorageService fileStorageService) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.recipePhotoRepository = recipePhotoRepository;
        this.fileStorageService = fileStorageService;
    }

    public RecipePhotoResponseDTO uploadPhoto(Long userId, Long  recipeId, MultipartFile file,String caption) throws IOException {
            User user=userRepository.findById(userId)
                    .orElseThrow(()->new RuntimeException("User not found"+userId));

            Recipe recipe=recipeRepository.findById(recipeId)
                    .orElseThrow(()->new RuntimeException("Recipe not found"+recipeId));

            String path=fileStorageService.uploadFile(file);

        RecipePhoto photo=new RecipePhoto();
        photo.setUser(user);
        photo.setRecipe(recipe);
        photo.setCaption(caption);
        photo.setImageUrl(path);
        photo.setTakenAt(Instant.now());

        photo=recipePhotoRepository.save(photo);
        return mapToDTO(photo);
    }

    public List<RecipePhotoResponseDTO> getPhotosForUser(Long userId) {
        return recipePhotoRepository.findByUserUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private RecipePhotoResponseDTO mapToDTO(RecipePhoto photo) {
        RecipePhotoResponseDTO dto=new RecipePhotoResponseDTO();
        dto.setPhoto_id(photo.getPhoto_id());
        dto.setImageUrl(photo.getImageUrl());
        dto.setCaption(photo.getCaption());
        dto.setTakenAt(photo.getTakenAt());

        return dto;
    }
}
