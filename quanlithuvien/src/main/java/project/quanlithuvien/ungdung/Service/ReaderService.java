package project.quanlithuvien.ungdung.Service;

import java.util.List;

import project.quanlithuvien.ungdung.DTO.ReaderDTO;
import project.quanlithuvien.ungdung.DTO.ReaderRequestDTO;

public interface  ReaderService {
    String addReader(ReaderRequestDTO readerRequestDTO);
    String updateReader(ReaderRequestDTO readerRequestDTO,String emailToUpdate);
    String deleteReader(ReaderRequestDTO readerRequestDTO);
    List<ReaderDTO> findAllReader(ReaderRequestDTO readerRequestDTO);
}
