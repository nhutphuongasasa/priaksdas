package project.quanlithuvien.ungdung.Repository.Impl;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
        LoanEntity loanEntity = loanEntityConverter.toLoanEntity(bookEntity, readerEntity);
        entityManager.merge(loanEntity);
        return "Successful";
    }

    public StringBuilder query(ReaderEntity readerEntity,BookEntity bookEntity){
        StringBuilder sql = new StringBuilder(" select lo.loan_id,lo.book_id,lo.reader_id,lo.loan_date,lo.status from loans lo ");
        sql.append(" inner join books bo on bo.book_id=lo.book_id ");
        sql.append(" inner join readers re on re.reader_id=lo.reader_id ");
        sql.append(" where ");
        sql.append(" lo.book_id = "+bookEntity.getBook_id()+" ");
        sql.append(" and lo.book_id = "+readerEntity.getReader_id()+" ");
        return sql;
    }

    @Override
    public String completeLoan(LoanRequestDTO loanRequestDTO) {
        ReaderEntity readerEntity = readerEntityFind.readerEntityFindByEmail(loanRequestDTO.getEmail());
        if(readerEntity==null){
            return "user does not exist";
        }
        BookEntity bookEntity = bookEntityFind.findByIsbn(loanRequestDTO.getIsbn());
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
                item.setReturn_date(LocalDate.now());
                entityManager.merge(item);
                return "successfull";
            }
        }
        return "Fail! The user has returned all the books";
    }

    public LoanEntity findByLoanId(int book_id,int reader_id){
        String sql = "select * from loans where lo.book_id = "+book_id+" and lo.reader_id = "+reader_id+" ";
        Query query = entityManager.createNativeQuery(sql,LoanEntity.class);
        return (LoanEntity)query.getSingleResult();
    }

    @Override
    public String deleteLoan(String isbn, String email) {
        BookEntity bookEntity = bookEntityFind.findByIsbn(isbn);
        if(bookEntity==null){
            return "No information";
        }
        ReaderEntity readerEntity = readerEntityFind.readerEntityFindByEmail(email);
        if(readerEntity==null){
            return "No information";
        }
        LoanEntity loanEntity = findByLoanId(bookEntity.getBook_id(),readerEntity.getReader_id());
        entityManager.remove(loanEntity);
        return "Successfull";
    }

    @Override
    public String updateLoan(String isbnToupdate, String emailToUpdate, LoanRequestDTO loanRequestDTO) {
        BookEntity bookEntity = bookEntityFind.findByIsbn(isbnToupdate);
        if(bookEntity==null){
            return "No information";
        }
        ReaderEntity readerEntity = readerEntityFind.readerEntityFindByEmail(emailToUpdate);
        if(readerEntity==null){
            return "No information";
        }
        LoanEntity loanEntity = findByLoanId(bookEntity.getBook_id(),readerEntity.getReader_id());
        loanEntity.setBook(bookEntityFind.findByIsbn(loanRequestDTO.getIsbn()));
        loanEntity.setReader(readerEntityFind.readerEntityFindByEmail(loanRequestDTO.getEmail()));
        entityManager.merge(loanEntity);
        return "Successfull";
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
        StringBuilder sql = new StringBuilder("select re.name,re.phone,re.email,bo.title,bo.author,bo.isbn,lo.status,lo.return_date from loans lo ");
        sql.append(joinQuery());
        sql.append(normalQuery(loanRequestDTO));
        sql.append("group by lo.id");
        Query query= entityManager.createNativeQuery(sql.toString(),LoanDTO.class);
        return  query.getResultList();
    }

}
