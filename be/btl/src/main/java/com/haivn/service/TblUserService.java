package com.haivn.service;

import com.haivn.common_api.Role;
import com.haivn.common_api.TblUser;
import com.haivn.dto.TblUserDto;
import com.haivn.handler.Utils;
import com.haivn.mapper.TblUserMapper;
import com.haivn.repository.RoleRepository;
import com.haivn.repository.TblUserRepository;
import com.llq.springfilter.boot.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class TblUserService {
    private final TblUserRepository repository;
    private final RoleRepository roleRepository;
    private final TblUserMapper tblUserMapper;

    private PasswordEncoder bcryptEncoder;
    @Autowired
    private JavaMailSender javaMailSender;



    @Autowired
    private UserRoleService userRoleService;

    public TblUserService(TblUserRepository repository,RoleRepository roleRepository, TblUserMapper tblUserMapper,PasswordEncoder bcryptEncoder) {
        this.repository = repository;
        this.tblUserMapper = tblUserMapper;
        this.roleRepository = roleRepository;
        this.bcryptEncoder = bcryptEncoder;
    }



    public TblUserDto save(TblUserDto tblUserDto) {
        tblUserDto.setPassword(bcryptEncoder.encode(tblUserDto.getPassword()));
        TblUser entity = tblUserMapper.toEntity(tblUserDto);
        return tblUserMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public TblUserDto findById(Long id) {
        return tblUserMapper.toDto(repository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Item Not Found! ID: " + id)
        ));
    }

    public Page<TblUserDto> findByCondition(@Filter Specification<TblUser> spec, Pageable pageable) {
        Page<TblUser> entityPage = repository.findAll(spec, pageable);
        List<TblUser> entities = entityPage.getContent();
        return new PageImpl<>(tblUserMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public TblUserDto update(TblUserDto tblUserDto, Long id) {
        TblUserDto data = findById(id);
        TblUser entity = tblUserMapper.toEntity(tblUserDto);
        BeanUtils.copyProperties(entity, data, Utils.getNullPropertyNames(entity));
        return save(tblUserMapper.toDto(entity));
    }

    public TblUserDto khoaTaiKhoan(Long id) {
        Optional<TblUser> tblUser = repository.findById(id);
        tblUser.get().setStatus(0);
        TblUser tblUser1 = repository.save(tblUser.get());
        return tblUserMapper.toDto(tblUser1);
    }

    public TblUserDto moTaiKhoan(Long id) {
        Optional<TblUser> tblUser = repository.findById(id);
        tblUser.get().setStatus(1);
        TblUser tblUser1 = repository.save(tblUser.get());
        return tblUserMapper.toDto(tblUser1);
    }

    public TblUserDto findByUserName(String userName){
        TblUser entity = repository.findByUsername(userName);
        TblUserDto tblUserDto = tblUserMapper.toDto(entity);
        return tblUserDto;
    }

//    public TblUserDto findByEmailAndPassword(String email, String password){
//        TblUser entity = repository.findByEmailAndPassword(email, password);
//        TblUserDto tblUserDto = tblUserMapper.toDto(entity);
//        return tblUserDto;
//    }

//    public TblUserDto findByNewPass(String newPass){
//        TblUser entity = repository.findByNewPass(newPass);
//        TblUserDto tblUserDto = tblUserMapper.toDto(entity);
//        return tblUserDto;
//    }

    public TblUserDto findByVerificationCode(String verificationCode){
        //TblUser user = repository.findByVerificationCode(verificationCode);
        TblUser user = null;
        TblUserDto tblUserDto = tblUserMapper.toDto(user);
        return tblUserDto;
    }

//    public void register(TblUserDto userDto, String siteURL)
//            throws UnsupportedEncodingException, MessagingException {
//        String password = userDto.getPassword();
//        String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
//        userDto.setPassword(encodedPassword);
//
//        String randomCode = RandomString.make(64);
//        userDto.setVerificationCode(randomCode);
//        userDto.setStatus((short) 0);
//        userDto.setCreatedDate(Utilities.getTimestamp());
//        userDto.setModifiedDate(Utilities.getTimestamp());
//
//        TblUser user = tblUserMapper.toEntity(userDto);
//
//        // Lưu tài khoản vào cơ sở dữ liệu và lấy ra id của tk đó
//        Long userId = repository.save(user).getId();
//
//        // Lưu id của tài khoản vừa thêm cùng với các role_id tương ứng vào bảng user_role
//        for (Long roleId:userDto.getRoleId()) {
//            UserRoleDto userRoleDto = new UserRoleDto();
//            userRoleDto.setUserId(userId);
//            userRoleDto.setRoleId(roleId);
//            userRoleService.save(userRoleDto);
//        }
//
//        String verifyURL = siteURL + "/api/register/verify?code=" + user.getVerificationCode();
//
//        EmailDto emailDto = new EmailDto();
//        emailDto.setTitle("Xác minh tài khoản");
//        emailDto.setMailTo(user.getEmail());
//        emailDto.getModel().put("fullName", user.getName());
//        emailDto.getModel().put("userName", user.getEmail());
//        emailDto.getModel().put("passWord", password);
//        emailDto.getModel().put("verifyURL", verifyURL);
//        emailDto.setTemplateName("reg-success.ftl");
//
//        emailService.sendMimeMail(emailDto);
//
////        sendVerificationEmail(user, password, siteURL);
//    }

//    public void forgetPass(TblUserDto user, String siteURL){
//        String randomCode = RandomString.make(8);
//        user.setVerificationCode(randomCode);
//
//        this.update(user, user.getId());
//
//        EmailDto emailDto = new EmailDto();
//        emailDto.setTitle("Xác nhận thay đổi mật khẩu");
//        emailDto.setMailTo(user.getEmail());
//        emailDto.getModel().put("userResetPasswordLink", siteURL + "/change-pass?code=" + randomCode);
//        emailDto.setTemplateName("reset-link.ftl");
//
//        emailService.sendMimeMail(emailDto);
//    }

    private void sendVerificationEmail(TblUser user, String password, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "ngapham06061985@gmail.com";
        String senderName = "HaiVN";
        String subject = "Vui lòng xác minh đăng ký của bạn";
        String content = "Chào [[name]],<br>"
                + "Bạn đã đăng ký thành công tài khoản vào hệ thống của chúng tôi.<br>"
                + "Thông tin tài khoản:<br>"
                + "<b>Email:</b> [[email]]<br>"
                + "<b>Mật khẩu:</b> [[password]]<br>"
                + "<div>Click vào link bên dưới để xác minh tài khoản:</div>"
                + "<h4><a href=\"[[URL]]\" target=\"_self\">Xác minh</a></h4>"
                + "Thank you,<br>"
                + "HaiVN";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFullName());
        content = content.replace("[[email]]", user.getEmail());
        content = content.replace("[[password]]", password);
        String verifyURL = siteURL + "/api/register/verify?code=" + user.getRole_id();

        content = content.replace("[[URL]]", verifyURL);

//        content = thymeleafService.getContent(user);

        helper.setText(content, true);

        javaMailSender.send(message);

    }

    public boolean verify(String verificationCode) {
        //TblUser user = repository.findByVerificationCode(verificationCode);
        TblUser user = null;
        if (user == null) {
            return false;
        } else {
//            user.setVerificationCode(null);
//            user.setStatus((short) 1);
            repository.save(user);
            return true;
        }
    }

    /**
     * Phương thức gửi thông báo khóa tài khoản về mail
     * @param userDto
     * @return
     */
//    public boolean blockAccount(TblUserDto userDto){
//        try{
//            EmailDto emailDto = new EmailDto();
//            emailDto.setTitle("Thông báo khóa tài khoản");
//            emailDto.setMailTo(userDto.getEmail());
//            emailDto.getModel().put("fullName", userDto.getName());
//            emailDto.getModel().put("reason", userDto.getReason());
//            emailDto.setTemplateName("block-account.ftl");
//
//            emailService.sendMimeMail(emailDto);
//
//            return true;
//        }
//        catch (Exception e)
//        {
//            return false;
//        }
//    }
}