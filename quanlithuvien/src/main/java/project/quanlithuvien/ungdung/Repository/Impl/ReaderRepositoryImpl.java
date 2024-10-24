package project.quanlithuvien.ungdung.Repository.Impl;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import project.quanlithuvien.ungdung.Model.ReaderEntity;
import project.quanlithuvien.ungdung.Repository.ReaderRepository;
@Repository
@Transactional

public class ReaderRepositoryImpl implements  ReaderRepository{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public String addReader(ReaderEntity readerEntity) {
        ReaderEntity readerEntity_1 = entityManager.find(ReaderEntity.class,readerEntity.getPhone());
        if(readerEntity_1 !=null){
            return "phone number already exists";
        }
        ReaderEntity readerEntity_2 = entityManager.find(ReaderEntity.class,readerEntity.getEmail());
        if(readerEntity_2 != null){
            return "email already exists";
        }
        entityManager.persist(readerEntity);
        return "Successfull";
    }

    @Override
    public String deleteReader(ReaderEntity readerEntity) {//yeu cau nguoi dung phai nhap du name email phone
        ReaderEntity readerEntityCopy = entityManager.find(ReaderEntity.class,readerEntity.getEmail());
        if(readerEntityCopy==null){
            return "user does not exist";
        }
        readerEntityCopy.setActive("deactivated");
        entityManager.merge(readerEntityCopy);
        return "Successfull";
    }

    @Override
    public String updateReader(ReaderEntity readerEntity) {//yeu cau nguoi dung phai nhap du name email phone
        ReaderEntity readerEntityCopy = entityManager.find(ReaderEntity.class,readerEntity.getEmail());
        if(readerEntityCopy==null){
            return "user account does not exist";
        }
        entityManager.merge(readerEntityCopy);
        return "Successfull";
    }

    public StringBuilder query(ReaderEntity readerEntity){
        StringBuilder ss= new StringBuilder(" where 1=1 ");
		try {
			Field[] fields = ReaderEntity.class.getDeclaredFields();
			for(Field x : fields) {
				x.setAccessible(true);
				String fieldName = x.getName();
				if(!fieldName.equals("registration_date")) {
					Object value =x.get(readerEntity);
					if(value!=null) {
						ss.append(" and b."+x.getName()+" like "+"'%"+value+"%' ");
					}
				}
			}
            if(readerEntity.getRegistration_date()!=null){
                ss.append(" and re ="+readerEntity.getRegistration_date()+" ");
            }
        } catch(IllegalAccessException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
		}
		return ss;
    }

    @Override
    public List<ReaderEntity> findAllReader(ReaderEntity readerEntity) {
        StringBuilder sql = new StringBuilder("select * from readers re ");
        sql.append(query(readerEntity));
        Query query= entityManager.createNativeQuery(sql.toString(),ReaderEntity.class);
        return query.getResultList();
    }
    
}
