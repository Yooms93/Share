//package com.lms.attendance.repository;
//
//import java.util.List;
//
//
//import org.apache.ibatis.annotations.Delete;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.Update;
//
//import com.lms.attendance.model.Post;
//
//@Mapper
//public interface PostMapper {
//
//    @Insert("""
//        INSERT INTO Post (post_id, board_id, author_id, author_role, author, title, content)
//        VALUES (#{postId}, #{boardId}, #{authorId}, #{authorRole}, #{author}, #{title}, #{content})
//    """)
//    void createPost(Post newPost);
//    
//    @Delete("DELETE FROM Post WHERE post_id = #{postId}")
//    void deletePostByPostId(int postId);
// 
//    //---------------게시글 수정 기능
//    @Update("""
//    	    UPDATE Post 
//    	    SET title = #{title}, content = #{content}, author = #{author}
//    	    WHERE post_id = #{postId}
//    """)
//    void updatePost(
//    	@Param("postId") int postId,
//    	@Param("title") String title,
//    	@Param("content") String content,
//    	@Param("author") String author
//    );
//    
//    //조건에 맞는 게시글 가져오기
//    @Select("SELECT title, created_at, author_role, author FROM Post WHERE board_id = #{boardId};")
//    List<Post> getAllPosts(int boardId);
//    
//    // title로 게시글 검색
//    @Select("SELECT * FROM Post WHERE title LIKE CONCAT('%', #{title}, '%')")
//    List<Post> searchPostsByTitle(String title);
//}









package com.lms.attendance.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lms.attendance.model.Post;

@Mapper
public interface PostMapper {

    @Insert("""
        INSERT INTO Post (post_id, board_id, author_id, author_role, author, title, content)
        VALUES (#{postId}, #{boardId}, #{authorId}, #{authorRole}, #{author}, #{title}, #{content})
    """)
    @Results({
        @Result(property = "postId", column = "post_id"),
        @Result(property = "title", column = "board_id"),
        @Result(property = "authorId", column = "author_id"),
        @Result(property = "authorRole", column = "author_role"),
        @Result(property = "author", column = "author")
    })
    void createPost(Post newPost);
    
    @Delete("DELETE FROM Post WHERE post_id = #{postId}")
    void deletePostByPostId(int postId);
 
    //---------------게시글 수정 기능
    @Update("""
    	    UPDATE Post 
    	    SET title = #{title}, content = #{content}, author = #{author}
    	    WHERE post_id = #{postId}
    	""")
    	void updatePost(
    		@Param("postId") int postId,
    	    @Param("title") String title,
    	    @Param("content") String content,
    	    @Param("author") String author
    	);
    
  //모든 게시글 가져오기
    @Select("SELECT post_id, title, DATE_FORMAT(created_at, '%Y-%m-%d %H:%i:%s') AS created_at, author_id, author_role, author, view FROM Post WHERE board_id = #{boardId};")
    @Results({
        @Result(property = "postId", column = "post_id"),
        @Result(property = "title", column = "title"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "authorRole", column = "author_role"),
        @Result(property = "authorId", column = "author_id"),
        @Result(property = "author", column = "author")
    })
    List<Post> getAllPosts(int boardId);
    
    // 특정 사용자의 게시글 가져오기
    @Select("""
    	    SELECT post_id,
    	           title,
    	           DATE_FORMAT(created_at, '%Y-%m-%d %H:%i:%s') AS created_at,
    	           author_id,
    	           author_role,
    	           author,
    	           view
    	      FROM Post
    	     WHERE author_id = #{userId}
    	""")
    	@Results({
    	    @Result(column = "post_id", property = "postId"),
    	    @Result(column = "title", property = "title"),
    	    @Result(column = "created_at", property = "createdAt"),
    	    @Result(column = "author_id", property = "authorId"),
    	    @Result(column = "author_role", property = "authorRole"),
    	    @Result(column = "author", property = "author"),
    	    @Result(column = "view", property = "view")
    	})
    	List<Post> getUsersAllPosts(@Param("userId") int userId);

    // id별 게시글 가져오기    
    @Select("SELECT post_id, title, DATE_FORMAT(created_at, '%Y-%m-%d %H:%i:%s') AS created_at, author_id, content, author_role, author, view FROM Post WHERE post_id = #{postId};")
    @Results({
        @Result(property = "postId", column = "post_id"),
        @Result(property = "title", column = "title"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "authorRole", column = "author_role"),
        @Result(property = "authorId", column = "author_id"),
        @Result(property = "author", column = "author")
    })
    List<Post> getPostById(int postId);

    
    // title로 게시글 검색
    @Select("SELECT * FROM Post WHERE title LIKE CONCAT('%', #{title}, '%')")
    List<Post> searchPostsByTitle(String title);
}
    