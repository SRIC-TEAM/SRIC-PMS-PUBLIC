package com.spring.springboot.user.service;

import com.spring.springboot.user.pojo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService
{
    List<User> getUserList();
    List<User> getUserMessageList(UserManageListRequestPojo userManageListRequest);
    int getUserMessageCount();
    boolean updateUsernameByUuid(User user);
    boolean updatePasswordByUuid(User user);
    SignInResponsePojo signIn(User user);
    UserMessageResponsePojo getUserByUuid(String uuid);
    int getCodeByMail(String mail);
    int signUp(SignUpRequestPojo signUpRequest);
    int getUserStatusByUuid(String uuid);
    boolean updateStatusByUuid(User user);
    User getUserByUsername(String username);
    int editUserProfile(User user);
    int uploadAvatar(MultipartFile file,String uuid);
    List<User> getUserListByKeyWord(String keyWord);
}
