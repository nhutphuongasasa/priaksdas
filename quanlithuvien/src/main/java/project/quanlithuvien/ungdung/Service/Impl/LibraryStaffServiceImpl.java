package project.quanlithuvien.ungdung.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import project.quanlithuvien.ungdung.Builder.StaffSearchBuilder;
import project.quanlithuvien.ungdung.Converter.LibraryStaffDTOConverter;
import project.quanlithuvien.ungdung.Converter.StaffSearchBuilderConverter;
import project.quanlithuvien.ungdung.DTO.LibraryStaffDTO;
import project.quanlithuvien.ungdung.DTO.LibraryStaffRequestDTO;
import project.quanlithuvien.ungdung.Model.LibraryStaffEntity;
import project.quanlithuvien.ungdung.Repository.LibraryStaffRepository;
import project.quanlithuvien.ungdung.Service.LibraryStaffService;
import project.quanlithuvien.ungdung.Utils.LibraryStaffEntityFind;

@Service
public class LibraryStaffServiceImpl implements LibraryStaffService{
    @Autowired
    private LibraryStaffDTOConverter libraryStaffDTOConverter;
    @Autowired
    private LibraryStaffRepository labraryStaffRepository;
    @Autowired
    private StaffSearchBuilderConverter staffSearchBuilderConverter;
    @Autowired
    private LibraryStaffEntityFind libraryStaffEntityFind;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public String addLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO) {
        String result = labraryStaffRepository.addLibraryStaff(libraryStaffRequestDTO);
        return  result;
    }

    @Override
    public String Login(String userName, String password) {
        LibraryStaffEntity existingStaff = libraryStaffEntityFind.findLibraryStaffEntityByUserName(userName);
        if(existingStaff==null){
            return "user or password incorrect";
        }
    
        if(!passwordEncoder.matches(password,existingStaff.getPassword())) {
            return "user or password incorrect";
        }
        return "Successful";
    }

    

    @Override
    public String deleteLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO) {
        String result = labraryStaffRepository.deleteLibraryStaff(libraryStaffRequestDTO);
        return  result;
    }

    @Override
    public List<LibraryStaffDTO> findAllLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO) {
        StaffSearchBuilder staffSearchBuilder = staffSearchBuilderConverter.toStaffSearchBuilder(libraryStaffRequestDTO);
        List<LibraryStaffEntity> libraryStaffEntities = labraryStaffRepository.findAllLibraryStaff(staffSearchBuilder);
        List<LibraryStaffDTO> result = new ArrayList<>();
        for(LibraryStaffEntity item : libraryStaffEntities){
            result.add(libraryStaffDTOConverter.toLibraryStaffDTO(item));
        }
        return result;
    }

    @Override
    public String updateLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO,String emailToUpdate) {
        String result = labraryStaffRepository.updateLibraryStaff(libraryStaffRequestDTO,emailToUpdate);
        return  result;
    }
    
}
