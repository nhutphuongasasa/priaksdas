package project.quanlithuvien.ungdung.Converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.quanlithuvien.ungdung.DTO.ReaderRequestDTO;
import project.quanlithuvien.ungdung.Model.ReaderEntity;
@Component
public class ReaderEntityConverter {
    @Autowired
	private ModelMapper modelMapper;
    public ReaderEntity toReaderEntity(ReaderRequestDTO readerRequestDTO){
        ReaderEntity readerEntity = modelMapper.map(readerRequestDTO,ReaderEntity.class);
        System.out.println(readerEntity.getStatus()+readerEntity.getRegistration_date());
        return readerEntity;
    }
}

    