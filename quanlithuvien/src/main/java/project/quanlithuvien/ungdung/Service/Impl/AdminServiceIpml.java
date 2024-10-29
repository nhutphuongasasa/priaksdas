package project.quanlithuvien.ungdung.Service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.quanlithuvien.ungdung.Model.AdminEntity;
import project.quanlithuvien.ungdung.Repository.AdminRepository;
import project.quanlithuvien.ungdung.Service.AdminService;
@RequiredArgsConstructor
@Service
public class AdminServiceIpml implements AdminService{
    @Autowired
    private AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public String Login(String userName, String password) {
        Optional<AdminEntity> existingAdmin = adminRepository.findByUserName(userName);
        if(existingAdmin.isEmpty()){
            return "user or password incorrect";
        }
        AdminEntity admin =existingAdmin.get();
        if(!passwordEncoder.matches(password, admin.getPassword())) {
            return "user or password incorrect";
        }
        return "Successful";
    }
    
}
