package project.quanlithuvien.ungdung.Repository;

import java.util.List;

import project.quanlithuvien.ungdung.Model.ReaderEntity;

public interface  ReaderRepository {
    String addReader(ReaderEntity readerEntity);
    String updateReader(ReaderEntity readerEntity,String emailToUpdate);
    String deleteReader(String emailToDelete);
    List<ReaderEntity> findAllReader(ReaderEntity readerEntity);
}
