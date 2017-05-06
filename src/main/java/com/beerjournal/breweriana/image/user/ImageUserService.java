package com.beerjournal.breweriana.image.user;

import com.beerjournal.breweriana.image.persistance.FileRepository;
import com.beerjournal.breweriana.user.persistence.User;
import com.beerjournal.breweriana.user.persistence.UserRepository;
import com.beerjournal.breweriana.utils.FileUtils;
import com.beerjournal.breweriana.utils.SecurityUtils;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import com.mongodb.gridfs.GridFSDBFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.beerjournal.infrastructure.error.ErrorInfo.USER_FORBIDDEN_MODIFICATION;

@Service
@RequiredArgsConstructor
public class ImageUserService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final SecurityUtils securityUtils;
    private final FileUtils fileUtils;

    void saveUserAvatarImage(MultipartFile multipartFile, String userId) throws IOException {
        if (!securityUtils.checkIfAuthorized(userId)) throw new BeerJournalException(USER_FORBIDDEN_MODIFICATION);

        User user = getUserInstance(userId);
        String originalFilename = multipartFile.getOriginalFilename();
        if (!fileUtils.hasImageExtension(originalFilename) || !fileUtils.isImage(multipartFile))
            throw new BeerJournalException(ErrorInfo.UNSUPPORTED_IMAGE_EXTENSION);
        if (user.getAvatarFileId() != null) deleteOldUserAvatarImage(user);

        ObjectId id = fileRepository.saveFile(multipartFile.getInputStream(), originalFilename, multipartFile.getContentType());

        User userToUpdate = User.copyWithChangedAvatar(id.toString(), user);
        userRepository.update(userToUpdate);
    }

    GridFSDBFile loadUserAvatarImage(String userId) {
        return fileRepository
                .loadFileById(getUserInstance(userId).getAvatarFileId())
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.IMAGE_NOT_FOUND));
    }

    void deleteUserAvatarImage(String userId) {
        if (!securityUtils.checkIfAuthorized(userId)) throw new BeerJournalException(USER_FORBIDDEN_MODIFICATION);

        User user = getUserInstance(userId);
        if (user.getAvatarFileId() == null) throw new BeerJournalException(ErrorInfo.IMAGE_NOT_FOUND);

        User userToUpdate = User.copyWithChangedAvatar(null, user);
        userRepository.update(userToUpdate);
        fileRepository.deleteFileById(user.getAvatarFileId());
    }

    private void deleteOldUserAvatarImage(User user) {
        fileRepository.deleteFileById(user.getAvatarFileId());
    }

    private User getUserInstance(String userId) {
        return userRepository
                .findOneById(ServiceUtils.stringToObjectId(userId))
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.USER_NOT_FOUND));
    }
}