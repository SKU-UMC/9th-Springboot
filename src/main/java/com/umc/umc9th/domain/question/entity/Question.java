package com.umc.umc9th.domain.question.entity;

import com.umc.umc9th.domain.user.entity.User;
import com.umc.umc9th.domain.question.questionComment.entity.QuestionComment;
import com.umc.umc9th.domain.question.questionImage.entity.QuestionImage;
import com.umc.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "question")
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(name = "question_title", nullable = false, length = 30)
    private String questionTitle;

    @Lob
    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false, length = 30)
    private QuestionType questionType;

    @Column(name = "is_answered", nullable = false)
    private Boolean isAnswered;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<QuestionComment> questionComments = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<QuestionImage> questionImages = new ArrayList<>();
}