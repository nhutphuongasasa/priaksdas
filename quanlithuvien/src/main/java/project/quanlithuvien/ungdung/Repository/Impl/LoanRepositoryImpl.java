package project.quanlithuvien.ungdung.Repository.Impl;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import project.quanlithuvien.ungdung.Converter.LoanEntityConverter;
import project.quanlithuvien.ungdung.DTO.LoanRequestDTO;
import project.quanlithuvien.ungdung.Model.BookEntity;
import project.quanlithuvien.ungdung.Model.LoanEntity;
import project.quanlithuvien.ungdung.Model.ReaderEntity;
import project.quanlithuvien.ungdung.Repository.LoanRepository;
import project.quanlithuvien.ungdung.Utils.BookEntityFind;
import project.quanlithuvien.ungdung.Utils.ReaderEntityFind;

@Repository
@Transactional
public class LoanRepositoryImpl implements LoanRepository{
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private LoanEntityConverter loanEntityConverter;
    @Autowired
    private ReaderEntityFind readerEntityFind;
    @Autowired
    private BookEntityFind bookEntityFind;
    @Override
    public String addLoan(LoanRequestDTO loanRequestDTO) {//buoc nguoi dung nhap email phone isbn
        ReaderEntity readerEntity = readerEntityFind.readerEntityFindByEmail(loanRequestDTO.getEmail());
        if(readerEntity==null){
            return "user does not exist";
        }
        BookEntity bookEntity = bookEntityFind.findByIsbn(loanRequestDTO.getIsbn());
        if(bookEntity==null){
            return "book does not exits";
        }
        if(loanRequestDTO.getQuantity()>bookEntity.getAvailable_quantity()){
            return "Not enough books available";
        }
        else{
            bookEntity.setAvailable_quantity(bookEntity.getAvailable_quantity()-loanRequestDTO.getQuantity());
        }
        LoanEntity loanEntity = loanEntityConverter.toLoanEntity(bookEntity, readerEntity,loanRequestDTO.getQuantity());
        entityManager.merge(loanEntity);
        return "Successful";
    }

    @Override
    public String completeLoan(List<Integer> loan_id) {//lam table co nut tra sach 
        for(Integer item : loan_id){
            LoanEntity loanEntity = entityManager.find(LoanEntity.class, item);
            if(loanEntity.getStatus().equals("CHƯA_TRẢ")){
                loanEntity.setStatus("ĐÃ_TRẢ");
                entityManager.merge(loanEntity);
            }
        }
        return "Successfull";
    }

    @Override
    public String deleteLoan(List<Integer> loan_id) {
        for(Integer item : loan_id){
            LoanEntity loanEntity = entityManager.find(LoanEntity.class, item);
            if(loanEntity.getStatus().equals("ĐÃ_TRẢ")){
                entityManager.remove(loanEntity);
            }
        }
        return "Successfull";
    }

    public StringBuilder joinQuery(){
        StringBuilder s = new StringBuilder(" ");
        s.append(" inner join books bo on bo.book_id=lo.book_id ");
        s.append(" inner join readers re on re.reader_id=lo.reader_id ");
        return s;
    }

    public StringBuilder normalQuery(LoanRequestDTO loanRequestDTO){
        StringBuilder ss= new StringBuilder(" where 1=1 ");
		try {
			Field[] fields = LoanRequestDTO.class.getDeclaredFields();
			for(Field x : fields) {
				x.setAccessible(true);
				String fieldName = x.getName();
				if(fieldName.equals("name")||fieldName.equals("phone")||fieldName.equals("email")) {
					Object value =x.get(loanRequestDTO);
					if(value!=null) {
						ss.append(" and re."+x.getName()+" like "+"'%"+value+"%' ");
					}
				}
                else if(fieldName.equals("title")||fieldName.equals("author")||fieldName.equals("isbn")) {
					Object value =x.get(loanRequestDTO);
					if(value!=null) {
						ss.append(" and bo."+x.getName()+" like "+"'%"+value+"%' ");
					}
				}
                else if(fieldName.equals("quantity")){
                    Object value =x.get(loanRequestDTO);
					if(value!=null) {
						ss.append(" and lo."+x.getName()+" = "+value+" ");
					}
                }
			} 

        } catch(IllegalAccessException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
		}
		return ss;
    }

    @Override
    public List<LoanEntity> findAllLoan(LoanRequestDTO loanRequestDTO) {
        StringBuilder sql = new StringBuilder("select lo.book_id,lo.reader_id, lo.status,lo.loan_date,lo.return_date,lo.quantity,lo.loan_id from loans lo ");
        sql.append(joinQuery());
        sql.append(normalQuery(loanRequestDTO));
        sql.append("group by lo.loan_id");
        Query query= entityManager.createNativeQuery(sql.toString(),LoanEntity.class);
        return  query.getResultList();
    }

}
