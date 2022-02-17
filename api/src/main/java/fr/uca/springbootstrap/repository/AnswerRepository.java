package fr.uca.springbootstrap.repository;

import fr.uca.springbootstrap.models.Resource.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Transactional
    List<Answer> findByQuestionId(Long questionId);
    @Transactional
    List<Answer> findByUserId(Long userId);
}
