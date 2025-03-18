//package com.lms.attendance.model;
//
//import java.sql.Timestamp;
//
//import lombok.Data;
//
//@Data
//public class Post {
//	private Integer postId; // PK
//	private Integer boardId; // 게시판 ID (Foreign Key)
//	private String authorId;
//	private String authorRole;
//	private String author;
//	private String title;
//	private String content;
//	private Timestamp createdAt;
//	private Integer view;
//	private Integer likes;
//	private Timestamp updatedAt;
//	private String likedByUser;
//	private String filePath;     
//}





package com.lms.attendance.model;

import lombok.Data;

@Data
public class Post {
	private int  postId; // PK
	private int  boardId; // 게시판 ID (Foreign Key)
	private String authorId;
	private String authorRole;
	private String author;
	private String title;
	private String content;
	private String createdAt;
	private int  view;
	private int  likes;
	private String updatedAt; //타임스탬프엿던 업데이트날짜를 스트링으로 변경
	private String likedByUser;
	private String filePath;     
}