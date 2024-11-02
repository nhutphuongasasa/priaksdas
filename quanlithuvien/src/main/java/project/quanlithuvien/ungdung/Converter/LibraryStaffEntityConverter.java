package project.quanlithuvien.ungdung.Converter;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import project.quanlithuvien.ungdung.DTO.LibraryStaffRequestDTO;
import project.quanlithuvien.ungdung.Model.LibraryStaffEntity;
import project.quanlithuvien.ungdung.Model.RoleEntity;
import project.quanlithuvien.ungdung.Service.RoleService;
@Component
@RequiredArgsConstructor
public class LibraryStaffEntityConverter {
    @Autowired
	private ModelMapper modelMapper;
    @Autowired
    private RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    public LibraryStaffEntity toLibraryStaffEntity(LibraryStaffRequestDTO libraryStaffRequestDTO){
        LibraryStaffEntity libraryStaffEntity = modelMapper.map(libraryStaffRequestDTO,LibraryStaffEntity.class);
        libraryStaffEntity.setStatus("active");
        if(libraryStaffRequestDTO.getPassword()!=null&&!libraryStaffRequestDTO.getPassword().trim().equals("")){
            String encodedPassword = passwordEncoder.encode(libraryStaffRequestDTO.getPassword());
            libraryStaffEntity.setPassword(encodedPassword);
        }
        RoleEntity roleEntity = roleService.findByName("staff").get();
        List<LibraryStaffEntity> tmp =roleEntity.getLibrary_staff();
        tmp.add(libraryStaffEntity);
        libraryStaffEntity.setRole(roleEntity);
        return libraryStaffEntity;
    }
}
