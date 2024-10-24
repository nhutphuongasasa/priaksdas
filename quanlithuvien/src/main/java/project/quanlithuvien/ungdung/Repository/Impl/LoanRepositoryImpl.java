package project.quanlithuvien.ungdung.Repository.Impl;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import project.quanlithuvien.ungdung.Converter.LoanEntityConverter;
import project.quanlithuvien.ungdung.DTO.LoanDTO;
import project.quanlithuvien.ungdung.DTO.LoanRequestDTO;
import project.quanlithuvien.ungdung.Model.BookEntity;
import project.quanlithuvien.ungdung.Model.LoanEntity;
import project.quanlithuvien.ungdung.Model.LoanEntity.LoanStatus;
import project.quanlithuvien.ungdung.Model.ReaderEntity;
import project.quanlithuvien.ungdung.Repository.LoanRepository;

@Repository
@Transactional
public class LoanRepositoryImpl implements LoanRepository{
    @PersistenceContext
    private EntityManager entityManager;
    private LoanEntityConverter loanEntityConverter;
    @Override
    public String addLoan(LoanRequestDTO loanRequestDTO) {
        ReaderEntity readerEntity = entityManager.find(ReaderEntity.class,loanRequestDTO.getEmail());
        if(readerEntity==null){
            return "user does not exist";
        }
        BookEntity bookEntity = entityManager.find(BookEntity.class, loanRequestDTO.getIsbn());
        if(bookEntity==null){
            return "book does not exits";
        }
        LoanEntity loanEntity = loanEntityConverter.toLoanEntity(bookEntity, readerEntity);
        entityManager.persist(loanEntity);
        return "Successful";
    }

    public StringBuilder query(ReaderEntity readerEntity,BookEntity bookEntity){
        StringBuilder sql = new StringBuilder(" select * from loans lo where ");
        sql.append(" lo.book_id = "+bookEntity.getBook_id()+" ");
        sql.append(" and lo.book_id = "+readerEntity.getReader_id()+" ");
        return sql;
    }

    @Override
    public String completeLoan(LoanRequestDTO loanRequestDTO) {
        ReaderEntity readerEntity = entityManager.find(ReaderEntity.class,loanRequestDTO.getEmail());
        if(readerEntity==null){
            return "user does not exist";
        }
        BookEntity bookEntity = entityManager.find(BookEntity.class, loanRequestDTO.getIsbn());
        if(bookEntity==null){
            return "book does not exits";
        }
        StringBuilder sql = new StringBuilder();
        sql.append(query(readerEntity, bookEntity));
        Query query= entityManager.createNativeQuery(sql.toString(),LoanEntity.class);
        List<LoanEntity> loanEntities = query.getResultList();
        if(loanEntities == null){
            return "Fail! The user has not borrowed this book";
        }
        for(LoanEntity item : loanEntities){
            if(item.getStatus() == LoanStatus.CHƯA_TRẢ){
                item.setStatus(LoanStatus.ĐÃ_TRẢ);
                entityManager.merge(item);
                return "successfull";
            }
        }
        return "Fail! The user has returned all the books";
    }


    public StringBuilder joinQuery(){
        StringBuilder s = new StringBuilder(" ");
        s.append("inner join books bo on bo.book_id=lo.book_id");
        s.append("inner join readers re on re.reader_id=lo.reader_id");
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
			} 
        } catch(IllegalAccessException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
		}
		return ss;
    }

    @Override
    public List<LoanDTO> findAllLoan(LoanRequestDTO loanRequestDTO) {
        StringBuilder sql = new StringBuilder("select * from loans lo ");
        sql.append(joinQuery());
        sql.append(normalQuery(loanRequestDTO));
        sql.append(" and lo.status like '%CHƯA_TRẢ%' ");
        sql.append("group by lo.id");
        Query query= entityManager.createNativeQuery(sql.toString(),LoanDTO.class);
        return  query.getResultList();
    }
    
}
