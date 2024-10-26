package project.quanlithuvien.ungdung.Converter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import project.quanlithuvien.ungdung.Builder.BookSearchBuilder;
import project.quanlithuvien.ungdung.Utils.MapUtils;
@Component
public class BookSearchBuilderConverter {
    public BookSearchBuilder toBookSearchBuilder(Map<String,String> param,List<String> categories){
        BookSearchBuilder bookSearchBuilder = BookSearchBuilder.builder()
                            .title(MapUtils.getObject(param,"title",String.class))
                            .author(MapUtils.getObject(param,"author",String.class))
                            .isbn(MapUtils.getObject(param,"isbn",String.class))
                            .publisher(MapUtils.getObject(param,"publisher",String.class))
                            .publicationYearTo(MapUtils.getObject(param,"publicationYearTo",Integer.class))
                            .publicationYearFrom(MapUtils.getObject(param,"publicationYearFrom",Integer.class))
                            .category(categories)
                            .build();
        return bookSearchBuilder;
    }
}
