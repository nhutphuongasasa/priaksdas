package project.quanlithuvien.ungdung.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Table(name="loans")
@Entity
@Setter
@Getter
@Builder
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loan_id; 

    @ManyToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private ReaderEntity reader; 

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book; 

    @Column(name="return_date")
    private LocalDate return_date;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loan_date;

    @Column(name="status",nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanStatus status; 

    public enum LoanStatus {
        ĐÃ_TRẢ,
        CHƯA_TRẢ,
    }

}
