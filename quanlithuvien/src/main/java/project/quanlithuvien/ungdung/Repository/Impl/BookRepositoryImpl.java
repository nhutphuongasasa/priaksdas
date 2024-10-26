package project.quanlithuvien.ungdung.Repository.Impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import project.quanlithuvien.ungdung.Builder.BookSearchBuilder;
import project.quanlithuvien.ungdung.Converter.BookEntityConverter;
import project.quanlithuvien.ungdung.DTO.BookRequestDTO;
import project.quanlithuvien.ungdung.Model.BookEntity;
import project.quanlithuvien.ungdung.Model.CategoryEntity;
import project.quanlithuvien.ungdung.Repository.BookRepository;
import project.quanlithuvien.ungdung.Utils.BookEntityFind;
import project.quanlithuvien.ungdung.Utils.CategoryEntityFind;
@Transactional
@Repository
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CategoryEntityFind categoryEntityFind;
    @Autowired
    private BookEntityFind bookEntityFind;
    @Autowired
    private BookEntityConverter bookEntityConverter;
    @Override
    //buoc nguoi dung nhap day du
    public String addBook(BookEntity bookEntity,List<CategoryEntity> CategoryEntities) {
        try {
            BookEntity bookEntityCopy = bookEntityFind.findByIsbn(bookEntity.getIsbn());
            if(bookEntityCopy != null){
                return "Failed ! sach da ton tai";
            }
            //them book vao category
            List<CategoryEntity> CategoryEntities2=new ArrayList<>(); 
            Iterator<CategoryEntity> iterator = CategoryEntities.iterator();
            while (iterator.hasNext()) {
                CategoryEntity x = iterator.next();
                CategoryEntity item = categoryEntityFind.findAllByName(x.getName());
                if(item == null){
                    // entityManager.persist(x);
                    x.getBooks().add(bookEntity);
                    CategoryEntities2.add(x);
                }
                else{
                    boolean check =true;
                    for(BookEntity y : item.getBooks()){
                        if(y.getIsbn().equals(bookEntity.getIsbn())){
                            check=false;
                        }
                    }
                    if(check){//neu sach chua co trong category
                        System.out.println("kkkkkkkkkkkkkkk");
                        item.getBooks().add(bookEntity);
                    }
                    CategoryEntities2.add(item);
                }
            }

            //kiem tra doi tuong da ton tai hay chua de them book vao table
            if(bookEntityCopy == null){
                bookEntity.setCategories(CategoryEntities2);
                entityManager.merge(bookEntity);
            }
            return "Successful";
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "false";
        }
    }
    //buoc nguoi dung nhap isbn
    @Override
    public String updateBook(String isbnToUpdate,BookRequestDTO bookRequestDTO,List<CategoryEntity> CategoryEntities ) {
        try {
            BookEntity bookEntityCopy = bookEntityFind.findByIsbn(isbnToUpdate);
            if(bookEntityCopy == null){
                return "Failed ! sach chua ton tai";
            }
            
            BookEntity bookEntity = bookEntityFind.findByIsbn(bookRequestDTO.getIsbn());
            if(!bookRequestDTO.getIsbn().equals(isbnToUpdate)&&bookEntity!=null){
                return "Failed ! isbn already exist";
            }
            //them book vao category
            List<CategoryEntity> CategoryEntities2=new ArrayList<>(); 
            Iterator<CategoryEntity> iterator = CategoryEntities.iterator();
            while (iterator.hasNext()) {
                CategoryEntity x = iterator.next();
                CategoryEntity item = categoryEntityFind.findAllByName(x.getName());
                if(item == null){
                    x.getBooks().add(bookEntityCopy);
                    CategoryEntities2.add(x);
                }
                else{
                    boolean check =true;//nhung category chua co book trong do
                    for(BookEntity y : item.getBooks()){
                        if(y.getIsbn().equals(isbnToUpdate)){
                            check=false;
                        }
                    }
                    if(check){//neu sach chua co trong category
                        item.getBooks().add(bookEntityCopy);
                    }
                    CategoryEntities2.add(item);
                }
            }
            bookEntityCopy = bookEntityConverter.toBookEntity(bookRequestDTO, bookEntityCopy);
            bookEntityCopy.setCategories(CategoryEntities2);
            entityManager.merge(bookEntityCopy);
            return "Successful";
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "false";
        }
    }
    //buoc nguoi dung nhap isbn
    @Override
    public String deleteBook(String isbn) {
        if (isbn == null) {
            return "ISBN không hợp lệ.";
        }
    
        BookEntity existingBook = bookEntityFind.findByIsbn(isbn);
        if (existingBook == null) {
            return "Sách không tồn tại.";
        }
        //loai bo toan bo quan he giua book va category
        try {
            for(CategoryEntity item : existingBook.getCategories()){
                item.getBooks().remove(existingBook);
            }
            existingBook.getCategories().clear();
            entityManager.remove(existingBook); 
            return "Successful";
        } catch (Exception e) {
            return "Failed";
        }
    }

    public  StringBuilder joinQuery(BookSearchBuilder bookSearchBuilder){
        StringBuilder s =new StringBuilder("");
        s.append(" inner join bookcategories bc on bc.book_id=b.book_id ");
        s.append(" inner join categories ca on ca.category_id=bc.category_id ");
        return s;
    }

    public StringBuilder normalQuery(BookSearchBuilder bookSearchBuilder){
        StringBuilder ss= new StringBuilder(" where 1=1 ");
		try {
			Field[] fields = BookSearchBuilder.class.getDeclaredFields();
			for(Field x : fields) {
				x.setAccessible(true);
				String fieldName = x.getName();
				if(!fieldName.startsWith("publication")&&!fieldName.equals("category")) {
					Object value =x.get(bookSearchBuilder);
					if(value!=null) {
						if(x.getType().getName().equals("java.lang.Long")) {
							ss.append(" and b."+x.getName()+"="+value+" ");
						}
						else {
							ss.append(" and b."+x.getName()+" like "+"'%"+value+"%' ");
						}
					}
				}
			}
            if(bookSearchBuilder.getCategory()!=null){
                for(String item : bookSearchBuilder.getCategory()){
                    ss.append(" and ca.name like '%"+item+"%' ");
                }
                
            }
        } catch(IllegalAccessException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
		}
		return ss;
    }

    public StringBuilder whereSpecial(BookSearchBuilder bookSearchBuilder){
        StringBuilder sss = new StringBuilder("");
        Integer PubliscationYearFrom= bookSearchBuilder.getPublicationYearFrom();
        if(PubliscationYearFrom != null){
            sss.append(" and b.publication_year >="+PubliscationYearFrom+" ");
        }
        Integer PubliscationYearTo= bookSearchBuilder.getPublicationYearTo();
        if(PubliscationYearTo != null){
            sss.append(" and b.publication_year <="+PubliscationYearTo+" ");
        }
        return sss;
    }

    @Override
    public List<BookEntity> findAllBook(BookSearchBuilder bookSearchBuilder) {
        StringBuilder sql = new StringBuilder("SELECT b.book_id, b.title, b.author, b.publisher,b.publication_year,b.isbn,b.quantity,b.available_quantity FROM books b ");
        sql.append(joinQuery(bookSearchBuilder));
        sql.append(normalQuery(bookSearchBuilder));
        sql.append(whereSpecial(bookSearchBuilder));
        sql.append(" group by b.book_id");
        System.out.println(sql);
        Query query= entityManager.createNativeQuery(sql.toString(),BookEntity.class);
        return query.getResultList();
    }
    
}
