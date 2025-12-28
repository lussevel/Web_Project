package com.collegefeedback.repository;

import com.collegefeedback.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    // Dashboard
    long countByStatus(String status);
    long countByCategory(String category);
    List<Feedback> findAllByOrderByCreatedAtDesc();

    // Feedback list filters
    @Query("SELECT f FROM Feedback f WHERE " +
            "(:category IS NULL OR f.category = :category) AND " +
            "(:status IS NULL OR f.status = :status) AND " +
            "(:date IS NULL OR DATE(f.createdAt) = :date)")
    List<Feedback> filterFeedback(@Param("category") String category,
                                  @Param("status") String status,
                                  @Param("date") LocalDate date);

    // Analytics
    @Query("SELECT SUM(f.votes) FROM Feedback f")
    Long sumVotes();

    @Query("""
        SELECT CONCAT(f.title, ' (', f.votes, ' votes)')
        FROM Feedback f
        ORDER BY f.votes DESC
    """)
    List<String> findMostVotedTitles();

    @Query("""
    SELECT f FROM Feedback f
    ORDER BY f.votes DESC, f.createdAt DESC
""")
    List<Feedback> findAllOrderByVotesDescCreatedAtDesc();

}
