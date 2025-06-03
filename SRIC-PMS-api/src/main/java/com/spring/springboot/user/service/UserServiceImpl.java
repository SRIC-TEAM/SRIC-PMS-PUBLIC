package com.spring.springboot.user.service;

import com.spring.springboot.tools.MailService;
import com.spring.springboot.user.mapper.UserMapper;
import com.spring.springboot.user.pojo.*;
import com.spring.springboot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * - - - - - - - - - -
 * user 服务类
 * - - - - - - - - - -
 */

@Service
public class UserServiceImpl implements UserService
{
    @Value("${filesPath}") //获取application.properties中的全局变量
    private String filesPath;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MailService mailService;

    /**
     * @author SRIC
     *
     * 获取 user 数据列表
     */
    @Override
    public List<User> getUserList()
    {
        return userMapper.getUserList();
    }

    /**
     * @author SRIC
     *
     * 获取用户信息列表
     * 将用户信息封装为 uuid UID 名称 邮箱 时间
     * 将邮箱内容隐藏
     */
    @Override
    public List<User> getUserMessageList(UserManageListRequestPojo userManageListRequest)
    {
        userManageListRequest.setLimitSize(20); //一页获取20条数据
        userManageListRequest.setLimitBefore((userManageListRequest.getPage() - 1) * 20); //当前获取数据之前的空缺 （如第一页空缺0条 第二页空缺20条）
        List<User> userList = userMapper.getUserManageList(userManageListRequest);
        for(int i = 0;i < userList.size();i++)
        {
            userList.get(i).setPassword("");
            String mail = userList.get(i).getMail();
            int length = mail.length();
            String markEmail = "";
            markEmail += mail.substring(0,2);
            markEmail += "****";
            markEmail += mail.substring(length - 12,length);
            userList.get(i).setMail(markEmail);
        }
        return userList;
    }

    /**
     * @author SRIC
     *
     * 获取用户信息列表数量
     */
    @Override
    public int getUserMessageCount()
    {
        return userMapper.getUserManageCount();
    }

    /**
     * @author SRIC
     *
     * 根据 uuid 修改 username
     * 若修改数据条数不为 0 则修改成功
     */
    @Override
    public boolean updateUsernameByUuid(User user)
    {
        int updateNum = userMapper.updateUsernameByUuid(user.getUuid(), user.getUsername());
        return updateNum > 0;
    }

    /**
     * @author SRIC
     *
     * 根据 uuid 修改 password
     * 若修改数据条数不为 0 则修改成功
     */
    @Override
    public boolean updatePasswordByUuid(User user)
    {
        int updateNum = userMapper.updatePasswordByUuid(user.getUuid(), user.getPassword());
        return updateNum > 0;
    }

    /**
     * @author SRIC
     *
     * 登录
     * 根据用户名获取用户信息 并进行密码匹配
     * 返回状态码 200成功 404无此用户 400密码错误
     */
    @Override
    public SignInResponsePojo signIn(User user)
    {
        User userSignIn = userMapper.getUserByUsername(user.getUsername()); //通过用户名获取用户信息
        if(userSignIn == null)
        {
            userSignIn = userMapper.getUserByMail(user.getUsername()); //用户可能使用邮箱登录
        }
        SignInResponsePojo signInResponse = new SignInResponsePojo();
        if(userSignIn == null)
        {
            signInResponse.setCode(404); //查无此用户
        }
        else
        {
            if(userSignIn.getPassword().equals(user.getPassword())) //用户名密码匹配
            {
                signInResponse.setCode(200);
                signInResponse.setUuid(userSignIn.getUuid());
                signInResponse.setUid(userSignIn.getUid());
                signInResponse.setStatus(userSignIn.getStatus());
            }
            else
            {
                signInResponse.setCode(400); //密码错误
            }
        }
        return signInResponse;
    }

    /**
     * @author SRIC
     *
     * 根据 uuid 获取 用户信息
     * 仅获取部分展示信息
     */
    @Override
    public UserMessageResponsePojo getUserByUuid(String uuid)
    {
        User user = userMapper.getUserByUuid(uuid);
        UserMessageResponsePojo userMessage = new UserMessageResponsePojo();
        userMessage.setUid(user.getUid());
        userMessage.setUsername(user.getUsername());
        userMessage.setAvatar(user.getAvatar());
        userMessage.setMark(user.getMark());
        return userMessage;
    }

    /**
     * @author SRIC
     *
     * 获取验证码
     * 检查邮箱是否被注册
     * 若未被注册
     * 生成一个随机的六位数验证码
     * 并且在 redis 中添加一条记录 包含 (邮箱,验证码)
     * 并且调用邮件服务类 向目标邮箱发送一封包含验证码的邮件
     */
    @Override
    public int getCodeByMail(String mail)
    {
        User user = userMapper.getUserByMail(mail);
        if(user != null) //邮箱已被注册
        {
            return 400;
        }
        String code = String.valueOf(new Random().nextInt(900000) + 100000); //生成六位数验证码
        redisTemplate.opsForValue().set(mail, code, 10, TimeUnit.MINUTES); //在redis中添加记录 保持十分钟
        System.out.println("邮箱 : " + mail);
        System.out.println("验证码 : " + code);
        String subject = "SRIC-FMS: E-mail verification";
        String text = "Here is your verification code:\n\n" + code + "\n\n\n\n\n" + "SRIC-FMS team";
        mailService.sendEmail(mail,subject,text);
        return 200;
    }

    /**
     * @author SRIC
     *
     * 注册
     * 查找用户名是否已经被注册
     * 若未被注册
     * 从 redis 中通过邮箱查找验证码并且与用户输入的验证码进行匹配
     * 返回状态码 200成功 400已被注册 401验证码错误
     */
    @Override
    public int signUp(SignUpRequestPojo signUpRequest)
    {
        // 定义正则表达式，验证用户名是否仅包含数字、字母、下划线和短横线
        String usernamePattern = "^[a-zA-Z0-9_-]+$";
        if(!signUpRequest.getUsername().matches(usernamePattern))
        {
            return 500; //用户名含有非法字符
        }
        User userByUsername = userMapper.getUserByUsername(signUpRequest.getUsername()); //通过输入的用户名查找是否有用户
        if(userByUsername != null) //用户名已被注册
        {
            return 400;
        }
        String code = redisTemplate.opsForValue().get(signUpRequest.getMail()); //通过邮箱获取验证码
        if(code.equals(signUpRequest.getCode())) //验证码正确
        {
            userMapper.insertUser(signUpRequest);
            return 200;
        }
        return 401; //验证码错误
    }

    /**
     * @author SRIC
     *
     * 根据 uuid 获取 用户权限信息
     */
    @Override
    public int getUserStatusByUuid(String uuid)
    {
        User user = userMapper.getUserByUuid(uuid);
        return user.getStatus();
    }

    /**
     * @author SRIC
     *
     * 根据 uuid 修改 status 权限
     */
    @Override
    public boolean updateStatusByUuid(User user)
    {
        int updateNum = userMapper.updateStatusByUuid(user);
        return updateNum > 0;
    }

    /**
     * @author SRIC
     *
     * 根据 username 获取 用户信息
     */
    @Override
    public User getUserByUsername(String username)
    {
        return userMapper.getUserByUsername(username);
    }

    /**
     * @author SRIC
     *
     * 根据 uuid 修改 用户信息
     * 修改 username 和 mark
     */
    @Override
    public int editUserProfile(User user)
    {
        // 定义正则表达式，验证用户名是否仅包含数字、字母、下划线和短横线
        String usernamePattern = "^[a-zA-Z0-9_-]+$";
        if(!user.getUsername().matches(usernamePattern))
        {
            return 500; //用户名含有非法字符
        }
        User userByUsername = userMapper.checkUserByUsername(user); //检查是否有重复用户名
        if(userByUsername != null) //用户名已被注册
        {
            return 400;
        }
        if(userMapper.updateUserProfileByUuid(user) > 0) //成功
        {
            return 200;
        }
        else //数据处理失败
        {
            return 401;
        }
    }

    /**
     * @author SRIC
     *
     * 更新头像
     * 根据传入的头像文件和用户信息更新用户头像
     */
    @Override
    public int uploadAvatar(MultipartFile file, String uuid)
    {
        //检查文件是否为空
        if(file.isEmpty())
        {
            return 400;
        }

        String originalFilename = file.getOriginalFilename(); //获取文件名称

        //获取文件后缀名称
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains("."))
        {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        //设置文件名称 uuid + 文件属性后缀
        String avatarFileName = uuid + fileExtension;

        //创建文件绝对路径对象
        File saveFile = new File(filesPath + "/userAvatar/" + avatarFileName);

        //保存文件
        try
        {
            if(saveFile.exists()) //文件已存在
            {
                System.out.println("查询文件：" + avatarFileName + " 已存在 将被替换");
            }
            else
            {
                userMapper.updateAvatarByUuid(uuid,avatarFileName); //若未曾经修改过头像 则在数据库修改用户头像信息
            }
            file.transferTo(saveFile);
            System.out.println("保存文件：" + avatarFileName + " 成功");

            return 200;
        }
        catch (IOException e)
        {
            System.out.println("保存文件：" + avatarFileName + " 失败");
            System.out.println(e);
            return 400;
        }
    }

    /**
     * @author SRIC
     *
     * 根据传入的 keyWord 获取一个最多 20 条的用户列表
     * 对 username 和 mail 进行匹配
     */
    @Override
    public List<User> getUserListByKeyWord(String keyWord)
    {
        return userMapper.getUserListByKeyWord(keyWord);
    }
}
