package project.quanlithuvien.ungdung.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.quanlithuvien.ungdung.Converter.LibraryStaffDTOConverter;
import project.quanlithuvien.ungdung.DTO.LibraryStaffDTO;
import project.quanlithuvien.ungdung.DTO.LibraryStaffRequestDTO;
import project.quanlithuvien.ungdung.Model.LibraryStaffEntity;
import project.quanlithuvien.ungdung.Repository.LibraryStaffRepository;
import project.quanlithuvien.ungdung.Service.LibraryStaffService;

@Service
public class LibraryStaffServiceImpl implements LibraryStaffService{
    @Autowired
    private LibraryStaffDTOConverter libraryStaffDTOConverter;
    @Autowired
    private LibraryStaffRepository labraryStaffRepository;
    @Override
    public String addLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO) {
        String result = labraryStaffRepository.addLibraryStaff(libraryStaffRequestDTO);
        return  result;
    }

    @Override
    public String deleteLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO) {
        String result = labraryStaffRepository.deleteLibraryStaff(libraryStaffRequestDTO);
        return  result;
    }

    @Override
    public List<LibraryStaffDTO> findAllLibraryStaff() {
        List<LibraryStaffEntity> libraryStaffEntities = labraryStaffRepository.findAllLibraryStaff();
        List<LibraryStaffDTO> result = new ArrayList<>();
        for(LibraryStaffEntity item : libraryStaffEntities){
            result.add(libraryStaffDTOConverter.toLibraryStaffDTO(item));
        }
        return result;
    }

    @Override
    public String updateLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO) {
        String result = labraryStaffRepository.updateLibraryStaff(libraryStaffRequestDTO);
        return  result;
    }
    
}
