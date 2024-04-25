package com.huuduc.snacksnap.service;

import com.huuduc.snacksnap.data.dto.AuthenDTORequest;
import com.huuduc.snacksnap.data.dto.AuthenDTOResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenService {

    AuthenDTOResponse login(AuthenDTORequest authenDTORequest);
}
