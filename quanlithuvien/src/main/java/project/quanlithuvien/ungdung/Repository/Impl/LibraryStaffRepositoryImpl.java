package project.quanlithuvien.ungdung.Repository.Impl;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import project.quanlithuvien.ungdung.Builder.StaffSearchBuilder;
import project.quanlithuvien.ungdung.Converter.LibraryStaffEntityConverter;
import project.quanlithuvien.ungdung.DTO.LibraryStaffRequestDTO;
import project.quanlithuvien.ungdung.Model.LibraryStaffEntity;
import project.quanlithuvien.ungdung.Repository.LibraryStaffRepository;
import project.quanlithuvien.ungdung.Utils.LibraryStaffEntityFind;
@Repository
@Transactional
public class LibraryStaffRepositoryImpl implements LibraryStaffRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private LibraryStaffEntityConverter libraryStaffEntityConverter;
    @Autowired
    private LibraryStaffEntityFind libraryStaffEntityFind;

    @Override
    public String addLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO) {//admin nhap het
        LibraryStaffEntity libraryStaffEntity1 = libraryStaffEntityFind.findLibraryStaffEntityByEmail(libraryStaffRequestDTO.getEmail());
        if(libraryStaffEntity1!=null&&libraryStaffEntity1.getPhone().equals(libraryStaffRequestDTO.getPhone())){
            if(libraryStaffEntity1.getStatus().equals("Inactive")){
                libraryStaffEntity1.setStatus("Active");
                entityManager.merge(libraryStaffEntity1);
                return  "Success! The staff member is back to work";
            }else{
                return "Fail ! Staff Member is Active.";
            }
        }
        if(libraryStaffEntity1!=null){
            return "email already exists";
        }
        LibraryStaffEntity libraryStaffEntity2 = libraryStaffEntityFind.findLibraryStaffEntityByPhone(libraryStaffRequestDTO.getPhone());
        if(libraryStaffEntity2!=null){
            return "phone already exists";
        }
        LibraryStaffEntity libraryStaffEntity3 = libraryStaffEntityFind.findLibraryStaffEntityByUserName(libraryStaffRequestDTO.getUser_name());
        if(libraryStaffEntity3!=null){
            return "user name already exist";
        }
        LibraryStaffEntity libraryStaffEntityCopy = libraryStaffEntityConverter.toLibraryStaffEntity(libraryStaffRequestDTO);
        libraryStaffEntityCopy.setStatus("Active");
        entityManager.merge(libraryStaffEntityCopy);
        return  "Successfull";
    }

    @Override
    public String deleteLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO) {//buoc nguoi dung nhap email va phone do email va phone unique
        LibraryStaffEntity libraryStaffEntity = libraryStaffEntityFind.findLibraryStaffEntityByEmail(libraryStaffRequestDTO.getEmail());
        if(libraryStaffEntity==null){
            return "Staff does not exists";
        }
        libraryStaffEntity.setStatus("Inactive");
        entityManager.merge(libraryStaffEntity);
        return  "Successfull";
    }

    @Override
    public String updateLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO,String emailToUpdate) {//nhap het cho thay doi tat ca ke ca phone va email
        LibraryStaffEntity libraryStaffEntity = libraryStaffEntityFind.findLibraryStaffEntityByEmail(emailToUpdate);
        if(libraryStaffEntity==null){
            return "Staff does not exists";
        }
        LibraryStaffEntity libraryStaffEntity1 = libraryStaffEntityFind.findLibraryStaffEntityByEmail(libraryStaffRequestDTO.getEmail());
        if(!libraryStaffRequestDTO.getEmail().equals(emailToUpdate)&&libraryStaffEntity1!=null){
            return "email already exists";
        }
        LibraryStaffEntity libraryStaffEntity2 = libraryStaffEntityFind.findLibraryStaffEntityByPhone(libraryStaffRequestDTO.getPhone());
        if(libraryStaffRequestDTO.getPhone().equals(libraryStaffEntity.getPhone())&&libraryStaffEntity2!=null){
            return "phone already exists";
        }
        LibraryStaffEntity libraryStaffEntityCopy = libraryStaffEntityConverter.toLibraryStaffEntity(libraryStaffRequestDTO);
        libraryStaffEntityCopy.setStaff_id(libraryStaffEntity.getStaff_id());
        libraryStaffEntityCopy.setStatus(libraryStaffEntity.getStatus());
        libraryStaffEntityCopy.setHireDate(libraryStaffEntity.getHireDate());
        entityManager.merge(libraryStaffEntityCopy);
        return  "Successfull";
    }

    public StringBuilder normalQuery(StaffSearchBuilder staffSearchBuilder){
        StringBuilder ss= new StringBuilder("");
		try {
			Field[] fields = StaffSearchBuilder.class.getDeclaredFields();
			for(Field x : fields) {
				x.setAccessible(true);
				String fieldName = x.getName();
				if(!fieldName.equals("hire_date")) {
					Object value =x.get(staffSearchBuilder);
					if(value!=null){
                        ss.append(" and li."+x.getName()+" like '"+value+"' ");
                    }
				}
			}
        ss.append(" and li.hire_date = '"+staffSearchBuilder.getHire_date()+"' ");
        } catch(IllegalAccessException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
		}
		return ss;
    }

    @Override
    public List<LibraryStaffEntity> findAllLibraryStaff(StaffSearchBuilder staffSearchBuilder) {//co the nhap hoac khong bat ki field nao
        StringBuilder sql = new StringBuilder("select * from library_staff li where li.status like 'active' ");
        sql.append(normalQuery(staffSearchBuilder));
        System.out.println(sql);
        Query query = entityManager.createNativeQuery(sql.toString(), LibraryStaffEntity.class);
        return query.getResultList();
    }
    
}
