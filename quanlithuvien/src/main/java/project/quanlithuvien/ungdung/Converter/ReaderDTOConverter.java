package project.quanlithuvien.ungdung.Converter;

import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ReaderDTO readerDTO = modelMapper.map(readerEntity,ReaderDTO.class);
        readerDTO.setRegistration_date(readerEntity.getRegistration_date().format(formatter));
        return readerDTO;
    }
}
