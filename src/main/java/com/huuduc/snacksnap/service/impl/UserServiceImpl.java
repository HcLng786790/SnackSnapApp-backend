package com.huuduc.snacksnap.service.impl;

import com.huuduc.snacksnap.data.dto.ChangePasswordDTORequest;
import com.huuduc.snacksnap.data.dto.RegisterDTORequest;
import com.huuduc.snacksnap.data.dto.RegisterDTOResponse;
import com.huuduc.snacksnap.data.dto.UserDTOResponse;
import com.huuduc.snacksnap.data.entity.Cart;
import com.huuduc.snacksnap.data.entity.User;
import com.huuduc.snacksnap.data.mapper.UserMapper;
import com.huuduc.snacksnap.enu.ERole;
import com.huuduc.snacksnap.enu.OtpUitls;
import com.huuduc.snacksnap.exception.ConflicExeception;
import com.huuduc.snacksnap.exception.NotFoundException;
import com.huuduc.snacksnap.exception.NullException;
import com.huuduc.snacksnap.exception.ValidationException;
import com.huuduc.snacksnap.repository.CartRepository;
import com.huuduc.snacksnap.repository.RoleRepository;
import com.huuduc.snacksnap.repository.UserReopsitory;
import com.huuduc.snacksnap.service.FileService;
import com.huuduc.snacksnap.service.MailService;
import com.huuduc.snacksnap.service.UserService;
import jakarta.mail.MessagingException;
import org.apache.http.MessageConstraintException;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserReopsitory userReopsitory;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FileService fileService;

    @Autowired
    private OtpUitls otpUitls;

    @Autowired
    private MailService mailService;

    @Override
    public UserDTOResponse getByCart(long cartId) {

        Cart findCart = this.cartRepository.findById(cartId).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Cart id",cartId))
        );

        User findUser = this.userReopsitory.findByCartId(cartId);

        return toDTO(findUser);

    }

    @Override
    public UserDTOResponse register(RegisterDTORequest registerDTORequest) {

        String otp = otpUitls.generateOtp();

        try {
            mailService.sendOtpEmail(registerDTORequest.getEmail(),otp);

        }catch (Exception e){
            throw new RuntimeException("Unbale, please try again");
        }

        User user = this.userMapper.toEntity(registerDTORequest);

        if(this.userReopsitory.existsByEmail(user.getEmail())){
            throw new ConflicExeception(Collections.singletonMap("email",user.getEmail()));
        }

        if(ObjectUtils.isEmpty(registerDTORequest.getEmail())){
            throw new NullException(Collections.singletonMap("Email",null));
        }

        user.setRole(
                this.roleRepository.findByName(ERole.User.toString()).orElseThrow(
                        ()-> new NotFoundException(Collections.singletonMap("Role",ERole.User.toString()))
                )
        );

        user.setPassword(this.passwordEncoder.encode(registerDTORequest.getPassword()));

        user.setActive(false);
        user.setOtp(otp);
        Cart newCart = new Cart();
        newCart.setUser(user);

        cartRepository.save(newCart);

        user.setCart(newCart);
        userReopsitory.save(user);


//        return this.userMapper.toDTO(user);
        return toDTO(user);
    }

    @Override
    public UserDTOResponse getById(long id) {

        User findUser = this.userReopsitory.findById(id).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("User id",id))
        );

        return toDTO(findUser);

    }

    @Override
    public UserDTOResponse updateById(long id, RegisterDTORequest registerDTORequest, File fileAvatar) {

        User findUser = this.userReopsitory.findById(id).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("User id",id))
        );

        if(!ObjectUtils.isEmpty(registerDTORequest.getEmail())){
            if(!registerDTORequest.getEmail().equals(findUser.getEmail())){
                if (this.userReopsitory.existsByEmail(registerDTORequest.getEmail())){
                    throw new ConflicExeception(Collections.singletonMap("Email",registerDTORequest.getEmail()));
                }else{
                    findUser.setEmail(registerDTORequest.getEmail());
                }
            }
        }
        if(!ObjectUtils.isEmpty(registerDTORequest.getName())){
            findUser.setName(registerDTORequest.getName());
        }
        if(!ObjectUtils.isEmpty(registerDTORequest.getPhoneNumber())){
            findUser.setPhoneNumber(registerDTORequest.getPhoneNumber());
        }

        //upload file to google drive
        if(fileAvatar!=null){

            String avatarUrl = this.fileService.uploadFileToDrive(fileAvatar);

            if(avatarUrl==null){
                throw new NotFoundException(Collections.singletonMap("File:",null));
            }

            findUser.setImg(avatarUrl);
        }

        this.userReopsitory.save(findUser);

        return toDTO(findUser);
    }

    @Override
    public UserDTOResponse verify(String email, String otp) {

        User findUser = this.userReopsitory.findByEmail(email).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Email user",email))
        );

        if(findUser.getOtp().equals(otp)){
            findUser.setActive(true);
            this.userReopsitory.save(findUser);
        }else{
            throw new NotFoundException(Collections.singletonMap("otp",otp));
        }

        return toDTO(findUser);
    }

    @Override
    public UserDTOResponse changePasswordByToken(ChangePasswordDTORequest changePasswordDTORequest) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userReopsitory.findByEmail(email).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("User email",email))
        );

        String oldPassword = user.getPassword();
        String newPassword = changePasswordDTORequest.getNewPassword();

        // Check newPassword is similar to oldPassword
        if (checkValidOldPassword(oldPassword, newPassword)){
            throw new ConflicExeception(Collections.singletonMap("passowrd",newPassword));
        }

        // Check confirm old password similar to old password
        if (checkValidOldPassword(oldPassword, changePasswordDTORequest.getOldPassword())) {

            user.setPassword(passwordEncoder.encode(newPassword));

            this.userReopsitory.save(user);

        } else {
            throw new ConflicExeception(Collections.singletonMap("passowrd",newPassword));
        }

        return toDTO(user);
    }

    @Override
    public List<UserDTOResponse> getAll() {

        List<User> users = this.userReopsitory.findByRoleId(2);

        List<UserDTOResponse> userDTOResponses = users.stream().map(
                u-> toDTO(u)
        ).toList();

        return userDTOResponses;

    }

    public boolean checkValidOldPassword(String oldPass, String confirmPass) {
        if (confirmPass.isEmpty()) {
            throw new ValidationException(Collections.singletonMap("confirm password ", confirmPass));
        }
        return passwordEncoder.matches(confirmPass, oldPass);
    }

    public UserDTOResponse toDTO(User user){
        UserDTOResponse userDTOResponse = new UserDTOResponse();
        userDTOResponse.setId(user.getId());
        userDTOResponse.setEmail(user.getEmail());
        userDTOResponse.setCartId(user.getCart().getId());
        userDTOResponse.setName(user.getName());
        userDTOResponse.setPhoneNumber(user.getPhoneNumber());
        userDTOResponse.setImg(user.getImg());

        return userDTOResponse;
    }
}
