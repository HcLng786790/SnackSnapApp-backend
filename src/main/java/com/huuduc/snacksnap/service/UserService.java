package com.huuduc.snacksnap.service;

import com.huuduc.snacksnap.data.dto.ChangePasswordDTORequest;
import com.huuduc.snacksnap.data.dto.RegisterDTORequest;
import com.huuduc.snacksnap.data.dto.RegisterDTOResponse;
import com.huuduc.snacksnap.data.dto.UserDTOResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public interface UserService {

    UserDTOResponse getByCart(long cartId);

    UserDTOResponse register(RegisterDTORequest registerDTORequest);

    UserDTOResponse getById(long id);

    UserDTOResponse updateById(long id, RegisterDTORequest registerDTORequest, File fileAvatar);

    UserDTOResponse verify(String email, String otp);

    UserDTOResponse changePasswordByToken(ChangePasswordDTORequest changePasswordDTORequest);

    List<UserDTOResponse> getAll();
}
