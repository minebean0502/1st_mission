-- INSERT INTO board (id, name) VALUES (1, '전체 게시판');
-- INSERT INTO board (id, name) VALUES (2, '자유 게시판');
-- INSERT INTO board (id, name) VALUES (3, '개발 게시판');
-- INSERT INTO board (id, name) VALUES (4, '일상 게시판');
-- INSERT INTO board (id, name) VALUES (5, '사건사고 게시판');

INSERT INTO comment(article_num_id, id, context, password)
VALUES
    (1, 1, '첫번째 댓글', '1234'),
    (1, 2, '두번째 댓글', '1234'),
    (2, 3, '두번째 게시글에서는 첫번째 댓글이지만, 전체에서는 세번째임', '1234'),
    (2, 4, '4번째 댓글', '1234'),
    (2, 6, '5번째 댓글이 아니라 6번째 댓글인 이유는 5번째를 삭제했기 때문에', '1234');

INSERT INTO article(classification_id, id, content, password, title)
VALUES
    (1,1,'첫번째 게시글의 내용임','1234','첫번째 게시글'),
    (1,2,'내용','1234','두번째 게시글'),
    (1,3,'ㅇㅇ','1234','세번째 게시글'),
    (2,4,'자유','1234','자유게시판 게시글'),
    (3,5,'ㅁㄴㅇㄹㄹㄹ','1234','다섯번째 게시글'),
    (3,6,'힝','1234','개발은 어려워'),
    (4,7,'ㅇㅅㅇ','1234','ㅇㅅ'),
    (1,8,'','1234','애는 내용을 안써볼게요');