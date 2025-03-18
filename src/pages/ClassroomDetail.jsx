// import { useState, useEffect } from "react";
// import { useParams } from "react-router-dom";
// import { fetchClassDetail } from "../api/classroomApi";
// import { fetchSurveyBoards, createSurveyBoard } from "../api/surveyApi"; // ✅ 설문조사 API 불러오기
// import { useAuth } from "../context/AuthContext";
// import AttendancePage from "../pages/AttendancePage";
// import ManageAttendance from "../pages/ManageAttendancePage";
// import SurveyList from "../pages/SurveyList";
// import SurveyCreate from "../components/SurveyCreate"; 
// import "../styles/ClassroomDetail.css";

// const ClassroomDetail = () => {
//   const { classId } = useParams();
//   const { user } = useAuth();
//   const [classDetail, setClassDetail] = useState(null);
//   const [activeComponent, setActiveComponent] = useState(null);
//   const [boards, setBoards] = useState([]); // ✅ 게시판 목록 (설문조사만 관리)
//   const [showBoardModal, setShowBoardModal] = useState(false); // ✅ 모달 상태
//   const [selectedBoardId, setSelectedBoardId] = useState(null); // ✅ 선택한 게시판
//   const [selectedBoardType, setSelectedBoardType] = useState(null); // ✅ 선택한 게시판 유형

//   useEffect(() => {
//     fetchClassDetail(classId)
//       .then((data) => setClassDetail(data))
//       .catch((error) => console.error("❌ 강의실 정보를 불러오는데 실패했습니다:", error));

//     fetchSurveyBoards(classId) // ✅ 설문조사 게시판 목록 불러오기
//       .then(setBoards)
//       .catch((error) => console.error("❌ 게시판 목록 불러오기 실패:", error));
//   }, [classId]);

//   /** ✅ 게시판 추가 (사용자가 선택한 유형에 따라 API 호출) */
//   const handleCreateBoard = async () => {
//     if (!selectedBoardType) return; // ✅ 선택하지 않았으면 리턴

//     if (selectedBoardType === "survey") {
//       await createSurveyBoard(classId); // ✅ 설문조사 게시판 추가
//       fetchSurveyBoards(classId).then(setBoards); // ✅ 새로 추가된 게시판 반영
//     }

//     setShowBoardModal(false); // ✅ 모달 닫기
//     setSelectedBoardType(null); // ✅ 선택 초기화
//   };

//   const handleSelectBoard = (boardId) => {
//     console.log("📌 설문 게시판 선택됨:", boardId);
  
//     // ✅ 강제 리렌더링을 위해 `selectedBoardId`를 null로 초기화 후 다시 설정
//     setActiveComponent(null);
//     setSelectedBoardId(null);
  
//     setTimeout(() => {
//       setSelectedBoardId(boardId);
//     }, 0); // 0ms 후 다시 설정 → React가 변경을 감지하도록 유도
//   };
  

//   /** ✅ 게시판 선택 시 SurveyList 렌더링 */
//   useEffect(() => {
//     if (selectedBoardId) {
//       console.log("📌 선택한 게시판 ID:", selectedBoardId);
//       setActiveComponent(
//         <SurveyList 
//           key={selectedBoardId}  // ✅ 강제 리렌더링을 위해 key 추가
//           boardId={selectedBoardId} 
//         />
//       );
//     } else {
//       setActiveComponent(null);
//     }
//   }, [selectedBoardId]);
  
//   if (!classDetail) {
//     return <p>클래스 정보를 불러오는 중...</p>;
//   }

//   return (
//     <div className="classroom-detail-wrapper" style={{ position: "relative" }}> {/* ✅ 모달을 포함하기 위해 relative 추가 */}
//       <div className="classroom-detail-container">
//         <h2>{classDetail.className}</h2>
//         <p><strong>교수자:</strong> {classDetail.professorName}</p>
//         <p><strong>이메일:</strong> {classDetail.professorEmail}</p>
//       </div>

//       <div className="classroom-layout">
//         <div className="classroom-menu">
//           {user.role === "student" ? (
//             <button className="menu-button" onClick={() => setActiveComponent(<AttendancePage classId={classId} />)}>
//               출석하기
//             </button>
//           ) : (
//             <button className="menu-button" onClick={() => setActiveComponent(<ManageAttendance classId={classId} />)}>
//               출석 관리
//             </button>
//           )}

//           {/* ✅ 게시판 추가 버튼 (모달 열기)
//           {user.role === "professor" && (
//             <button className="menu-button add-board-btn" onClick={() => setShowBoardModal(true)}>
//               + 게시판 추가
//             </button>
//           )} */}

//           {/* ✅ 설문조사 게시판 목록 */}
//           {boards.length > 0 ? (
//             boards.map((board) => (
//               <button
//                 key={board.boardId}
//                 className="menu-button"
//                 onClick={() => handleSelectBoard(board.boardId)} // ✅ 항상 리렌더링
//               >
//                 설문조사
//               </button>
//             ))
//           ) : (
//             <p>📌 아직 설문조사 게시판이 없습니다. 생성해 주세요.</p>
//           )}
//         </div>

//         {/* ✅ 오른쪽 컴포넌트 렌더링 */}
//         <div className="classroom-content">
//           {activeComponent || <p>📌 설문조사 게시판을 선택하세요.</p>}
//         </div>
//       </div>

//       {/* ✅ 게시판 추가 모달 (창처럼 띄우기) */}
//       {showBoardModal && (
//         <div className="modal-overlay" onClick={() => setShowBoardModal(false)}>
//           <div className="modal-window" onClick={(e) => e.stopPropagation()}>
//             <h3>게시판 추가</h3>
//             <p>추가할 게시판 유형을 선택하세요.</p>
            
//             <div className="modal-buttons">
//               <button onClick={() => setSelectedBoardType("survey")}>설문조사 게시판</button>
//               <button disabled>일반 게시판 (준비 중)</button> {/* 나중에 추가 */}
//               <button disabled>투표 게시판 (준비 중)</button> {/* 나중에 추가 */}
//             </div>

//             {selectedBoardType && (
//               <div className="modal-footer">
//                 <p>선택된 게시판 유형: <strong>{selectedBoardType === "survey" ? "설문조사 게시판" : selectedBoardType}</strong></p>
//                 <button onClick={handleCreateBoard}>추가</button>
//               </div>
//             )}

//             <button className="modal-close" onClick={() => setShowBoardModal(false)}>취소</button>
//           </div>
//         </div>
//       )}
//     </div>
//   );
// };

// export default ClassroomDetail;






import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import { fetchClassDetail } from "../api/classroomApi";
import { fetchBoardsByClassId, createBoard, deleteBoardByBoardId } from "../api/boardsApi";
import { fetchSurveyBoards } from "../api/surveyApi"; // 설문조사 게시판 관련 API
import { useAuth } from "../context/AuthContext";
import AttendancePage from "../pages/AttendancePage";
import ManageAttendance from "../pages/ManageAttendancePage";
import Post from "../components/PostList"; // 일반 게시물 목록 컴포넌트
import SurveyList from "../pages/SurveyList"; // 설문조사 게시판 목록 컴포넌트
import DeleteBoardModal from "../components/DeleteBoardModal";
import AddBoardModal from "../components/AddBoardModal";
import "../styles/ClassroomDetail.css";
import "../styles/post.css";

const ClassroomDetail = () => {
  const { classId } = useParams();
  // const [selectedBoardId, setSelectedBoardId] = useState(null);
  const { user } = useAuth();
  const [classDetail, setClassDetail] = useState(null);
  const [activeComponent, setActiveComponent] = useState(null);
  const [selectedMenu, setSelectedMenu] = useState(null); // "post" 또는 "survey"
  const [boards, setBoards] = useState([]); // 일반 게시판 (board) 목록
  const [boardId, setBoardId] = useState(null);
  const [posts, setPosts] = useState([]); // 게시글 목록 상태
  const [selectedPost, setSelectedPost] = useState(null); // 선택된 게시글 상태

  const [surveyBoards, setSurveyBoards] = useState([]); // 설문조사 게시판 (survey_board) 목록
  const [showBoardModal, setShowBoardModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  
  const handleBoardClick = (id) => {
    setBoardId(id);
    setSelectedMenu("post");
  };
  // 강의실 정보 불러오기
  useEffect(() => {
    fetchClassDetail(classId)
      .then((data) => setClassDetail(data))
      .catch((error) => console.error("❌ 강의실 정보 불러오기 오류:", error));
    
    // // 일반 게시판 불러오기 (board)
    fetchBoardsByClassId(classId)
      .then((data) => setBoards(data))
      .catch((error) => console.error("❌ 일반 게시판 불러오기 오류:", error));
    
    // 설문조사 게시판 불러오기 (survey_board)
    fetchSurveyBoards(classId)
      .then((data) => setSurveyBoards(data))
      .catch((error) => console.error("❌ 설문조사 게시판 불러오기 오류:", error));
  }, [classId]);

  // console.log("useParams() 결과:", { classId, boardId });

  // 좌측 메뉴에서 선택한 항목에 따라 activeComponent 업데이트
  useEffect(() => {
    if (!selectedMenu) return;
    switch (selectedMenu) {
      case "post":
        setActiveComponent(boardId ? <Post boardId={boardId} /> : <p>게시판을 선택해주세요.</p>);
        break;
      case "survey":
        setActiveComponent(<SurveyList classId={classId} boards={surveyBoards} />);
        break;
      case "attendance":
        setActiveComponent(user.role === "student" ? <AttendancePage classId={classId} /> : <ManageAttendance classId={classId} />);
        break;
      default:
        setActiveComponent(null);
    }
  }, [selectedMenu, classId, boardId, surveyBoards, user]);

  if (!classDetail) return <p>클래스 정보를 불러오는 중...</p>;
  return (
    <div className="classroom-detail-wrapper" style={{ position: "relative" }}>
      <div className="classroom-detail-container">
        <h2>{classDetail.className}</h2>
        <p><strong>교수자:</strong> {classDetail.professorName}</p>
        <p><strong>이메일:</strong> {classDetail.professorEmail}</p>
      </div>
      <div className="classroom-layout">
        {/* 좌측 메뉴 */}
        <div className="classroom-menu">
          {/* 메뉴 버튼: 출석/출석 관리 */}
          {user.role === "student" && (
  <button className="menu-button" onClick={() => setSelectedMenu("attendance")}>
    출석하기
  </button>
)}

{user.role === "professor" && (
  <button className="menu-button" onClick={() => setSelectedMenu("attendance")}>
    출석 관리
  </button>
)}
          {boards.map((board) => (
            <button key={board.boardId} className="menu-button" onClick={() => handleBoardClick(board.boardId)}>
              {board.boardName}
            </button>
          ))}
          {/* 메뉴 버튼: 일반 게시판
          <button className="menu-button" onClick={() => setSelectedMenu("post")}>
            게시판
          </button> */}
          {/* 메뉴 버튼: 설문조사 게시판 */}
          <button className="menu-button" onClick={() => setSelectedMenu("survey")}>
            설문조사
          </button>
          {/* 추가적인 메뉴 버튼 */}
          <Link to="/"><button className="menu-button btn btn-danger">메인으로</button></Link>
         
         
         
          {/* 교수 계정: 게시판 추가/삭제 모달 버튼 */}
          {user.role === "professor" && (
            <div className="button-group">
              <button className="menu-button" onClick={() => setShowBoardModal(true)}>
                + 게시판 추가
              </button>
              <button className="menu-button" onClick={() => setShowDeleteModal(true)}>
                게시판 삭제
              </button>
            </div>
          )}



          {/* 게시판 추가 모달 */}
          {showBoardModal && (
            <AddBoardModal
              onClose={() => setShowBoardModal(false)}
              onAddBoardModal={(boardData) => {
                // boardData에 따라 일반 게시판 또는 설문조사 게시판 생성
                if (boardData.type === "survey") {
                  // survey_board 생성
                  createSurveyBoard(classId, boardData)
                    .then(() => fetchSurveyBoards(classId))
                    .then(setSurveyBoards)
                    .catch((error) => console.error("❌ 설문조사 게시판 추가 오류:", error));
                } else {
                  // 일반 board 생성
                  createBoard({ ...boardData, classId })
                    .then(() => fetchBoardsByClassId(classId))
                    .then(setBoards)
                    .catch((error) => console.error("❌ 일반 게시판 추가 오류:", error));
                }
                setShowBoardModal(false);
              }}
            />
          )}
          {/* 게시판 삭제 모달 */}
          {showDeleteModal && (
            <DeleteBoardModal
              onClose={() => setShowDeleteModal(false)}
              onDeleteBoardModal={(deletedBoardId) => {
                // 설문조사 게시판 삭제 조건: surveyBoards 배열에 해당 boardId가 있으면
                if (surveyBoards.find((board) => board.boardId === deletedBoardId)) {
                  deleteBoardByBoardId(deletedBoardId)
                    .then(() => fetchSurveyBoards(classId))
                    .then(setSurveyBoards)
                    .catch((error) => console.error("❌ 설문조사 게시판 삭제 오류:", error));
                } else {
                  // 그렇지 않으면 일반 게시판 삭제
                  deleteBoardByBoardId(deletedBoardId)
                    .then(() => fetchBoardsByClassId(classId))
                    .then(setBoards)
                    .catch((error) => console.error("❌ 일반 게시판 삭제 오류:", error));
                }
                setShowDeleteModal(false);
              }}
              boards={boards.concat(surveyBoards)} // 둘 다 포함하여 삭제 대상 목록 표시
            />
          )}
        </div>
        {/* 우측 콘텐츠 영역 */}
        <div className="classroom-content">
          {activeComponent || <p>메뉴에서 항목을 선택해주세요.</p>}
        </div>
      </div>
    </div>
  );
};
export default ClassroomDetail;