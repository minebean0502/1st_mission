package com.example.board;

import com.example.board.entity.Article;
import com.example.board.entity.Comment;
import com.example.board.repo.ArticleRepository;
import com.example.board.repo.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    public void createComment(String context,
                       String password,
                       Long articleNumId) {
        // 주어진 정보로 새로운 Comment 객체를 만든다
        Comment comment = new Comment();
        comment.setContext(context);
        comment.setPassword(password);
        // (일치시킬) 게시글을 찾는다
        Optional<Article> optionalArticle
                = articleRepository.findById(articleNumId);
        // 댓글에 게시글의 id를 할당한다
        comment.setArticleNum(optionalArticle.orElse(null));
        // repository의 save 메서드를 호출한다.
        commentRepository.save(comment);
    }
    public boolean deleteComment(
            Long id, // comment의 PK
            String password // 수정할 때 일치해야 할 password
    ) {
        // comment의 id로 객체 찾기
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();

            // 비밀번호 일치 여부 확인
            if (comment.getPassword().equals(password)) {
                // 비밀번호가 일치하면 댓글 삭제
                commentRepository.delete(comment);
                return true;
            }
        }
        return false; // 아닐 경우 삭제 실패 false 반환
    }
    // 특정 게시글에 연결된 모든 댓글들을 조회하는 메서드
    public List<Comment> getCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticleNumId(articleId);
    }
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }
}
