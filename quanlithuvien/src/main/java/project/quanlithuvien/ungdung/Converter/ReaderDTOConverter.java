package project.quanlithuvien.ungdung.Converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.quanlithuvien.ungdung.DTO.ReaderDTO;
import project.quanlithuvien.ungdung.Model.ReaderEntity;
@Component
public class ReaderDTOConverter {
    @Autowired
	private ModelMapper modelMapper;
    public ReaderDTO toReaderDTO(ReaderEntity readerEntity){
        ReaderDTO readerDTO = modelMapper.map(readerEntity,ReaderDTO.class);
        return readerDTO;
    }
}
