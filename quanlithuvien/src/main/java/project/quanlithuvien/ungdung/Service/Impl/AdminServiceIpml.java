package project.quanlithuvien.ungdung.Service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.quanlithuvien.ungdung.Model.AdminEntity;
import project.quanlithuvien.ungdung.Repository.AdminRepository;
import project.quanlithuvien.ungdung.Service.AdminService;
import project.quanlithuvien.ungdung.Service.LibraryStaffService;
@RequiredArgsConstructor
@Service
public class AdminServiceIpml implements AdminService{
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private LibraryStaffService libraryStaffService;
    
    @Override
    public String Login(String userName, String password) {
        Optional<AdminEntity> existingAdmin = adminRepository.findByUserName(userName);
        if(!existingAdmin.isEmpty()){
            AdminEntity admin =existingAdmin.get();
            System.out.println(passwordEncoder.encode("1234567"));
            if(passwordEncoder.matches(password, admin.getPassword())) {
                return "Admin";
            }
        }
        if(libraryStaffService.Login(userName, password).equals("Successfull")){
            return "Staff";
        }
        return "user or password incorrect";
    }
    
}
