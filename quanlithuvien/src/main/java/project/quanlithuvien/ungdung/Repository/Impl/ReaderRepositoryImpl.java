package project.quanlithuvien.ungdung.Repository.Impl;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import project.quanlithuvien.ungdung.Model.ReaderEntity;
import project.quanlithuvien.ungdung.Repository.ReaderRepository;
import project.quanlithuvien.ungdung.Utils.ReaderEntityFind;
@Repository
@Transactional

public class ReaderRepositoryImpl implements  ReaderRepository{
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ReaderEntityFind readerEnriryFind;
    @Override
    public String addReader(ReaderEntity readerEntity) {//yeu cau nguoi dung nhap tat ca
        ReaderEntity readerEntity_1 = readerEnriryFind.readerEntityFindByEmail(readerEntity.getEmail());
        if(readerEntity_1!=null&&readerEntity_1.getPhone().equals(readerEntity.getPhone())){
            if(readerEntity_1.getStatus().equals("Inactive")){
                readerEntity_1.setStatus("Active");
                entityManager.merge(readerEntity_1);
                return  "Successful";
            }else{
                return "Fail ! User Member is Active.";
            }
        }
        if(readerEntity_1 !=null){
            return "email already exists";
        }
        ReaderEntity readerEntity_2 = readerEnriryFind.readerEntityFindByPhone(readerEntity.getPhone());
        System.out.println(readerEntity_2);
        if(readerEntity_2 != null){
            return "phone number already exists";
        }
        readerEntity.setStatus("Active");
        entityManager.persist(readerEntity);
        return "Successfull";
    }

    @Override
    public String deleteReader(String emailToDelete) {//yeu cau nguoi dung phai nhap du nhat thiet phai co email
        ReaderEntity readerEntityCopy = readerEnriryFind.readerEntityFindByEmail(emailToDelete);
        if(readerEntityCopy==null){
            return "user does not exist";
        }
        readerEntityCopy.setStatus("Inactive");
        entityManager.merge(readerEntityCopy);
        return "Successfull";
    }

    @Override
    public String updateReader(ReaderEntity readerEntity,String emailToUpdate) {//yeu cau nguoi dung phai nhap du name email phone
        ReaderEntity readerEntityCopy = readerEnriryFind.readerEntityFindByEmail(emailToUpdate);
        if(readerEntityCopy==null){
            return "user account does not exist";
        }
        ReaderEntity ReaderEntity_1 = readerEnriryFind.readerEntityFindByEmail(readerEntity.getEmail());
        if(!readerEntity.getEmail().equals(emailToUpdate)&&ReaderEntity_1!=null){
            return "email already exists";
        }
        ReaderEntity ReaderEntity_2 = readerEnriryFind.readerEntityFindByPhone(readerEntity.getPhone());
        if(!readerEntity.getPhone().equals(readerEntityCopy.getPhone())&&ReaderEntity_2!=null){
            return "phone already exists";
        }
        readerEntity.setReader_id(readerEntityCopy.getReader_id());
        readerEntity.setStatus(readerEntityCopy.getStatus());
        readerEntity.setRegistration_date(readerEntityCopy.getRegistration_date());
        entityManager.merge(readerEntity);
        return "Successfull";
    }

    public StringBuilder query(ReaderEntity readerEntity){
        StringBuilder ss= new StringBuilder(" where 1=1 ");
		try {
			Field[] fields = ReaderEntity.class.getDeclaredFields();
			for(Field x : fields) {
				x.setAccessible(true);
				String fieldName = x.getName();
				if(!fieldName.equals("registration_date")&&!fieldName.equals("status")&&!fieldName.equals("loans")) {
					Object value =x.get(readerEntity);
					if(value!=null) {
						ss.append(" and re."+x.getName()+" like "+"'%"+value+"%' ");
					}
				}
			}
        } catch(IllegalAccessException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
		}
		return ss;
    }

    @Override
    public List<ReaderEntity> findAllReader(ReaderEntity readerEntity) {//co the nhap hoac bo bat ki field nao
        StringBuilder sql = new StringBuilder("select * from readers re ");
        sql.append(query(readerEntity));
        Query query= entityManager.createNativeQuery(sql.toString(),ReaderEntity.class);
        return query.getResultList();
    }
    
}
