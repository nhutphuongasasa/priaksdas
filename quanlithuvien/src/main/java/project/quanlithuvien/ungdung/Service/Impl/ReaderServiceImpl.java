package project.quanlithuvien.ungdung.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.quanlithuvien.ungdung.Converter.ReaderDTOConverter;
import project.quanlithuvien.ungdung.Converter.ReaderEntityConverter;
import project.quanlithuvien.ungdung.DTO.ReaderDTO;
import project.quanlithuvien.ungdung.DTO.ReaderRequestDTO;
import project.quanlithuvien.ungdung.Model.ReaderEntity;
import project.quanlithuvien.ungdung.Repository.ReaderRepository;
import project.quanlithuvien.ungdung.Service.ReaderService;
@Service
public class ReaderServiceImpl implements  ReaderService{
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private ReaderEntityConverter readerEntityConverter;
    @Autowired
    private ReaderDTOConverter readerDTOConverter;
    @Override
    public String addReader(ReaderRequestDTO readerRequestDTO){
        ReaderEntity readerEntity = readerEntityConverter.toReaderEntity(readerRequestDTO);
        String result = readerRepository.addReader(readerEntity);
        return result;
    }
    
    
    @Override
    public String deleteReader(ReaderRequestDTO readerRequestDTO) {
        ReaderEntity readerEntity = readerEntityConverter.toReaderEntity(readerRequestDTO);
        String result = readerRepository.deleteReader(readerEntity);
        return result;
    }

    @Override
    public String updateReader(ReaderRequestDTO readerRequestDTO,String emailToUpdate) {
        ReaderEntity readerEntity = readerEntityConverter.toReaderEntity(readerRequestDTO);
        String result = readerRepository.updateReader(readerEntity,emailToUpdate);
        return result;
    }

    @Override
    public List<ReaderDTO> findAllReader(ReaderRequestDTO readerRequestDTO) {
        ReaderEntity readerEntity = readerEntityConverter.toReaderEntity(readerRequestDTO);
        List<ReaderEntity> readerEntities = readerRepository.findAllReader(readerEntity);
        List<ReaderDTO> result = new ArrayList<>();
        for(ReaderEntity item : readerEntities){
            result.add(readerDTOConverter.toReaderDTO(item));
        }
        return result;
    }

}
