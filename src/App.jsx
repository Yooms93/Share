import { BrowserRouter as Router, Routes, Route, useLocation  } from "react-router-dom";
import { useEffect } from "react";
import { AuthProvider } from "./context/AuthContext";  // ✅ import 확인!
import Header from "./components/Header";
import Footer from "./components/Footer";
import ClassroomList from "./components/ClassroomList";
import Dashboard from "./components/Dashboard";
import AttendancePage from "./pages/AttendancePage";
import ManageAttendancePage from "./pages/ManageAttendancePage";
import ClassSettings from "./pages/ClassSettings"; 
import SurveyList from "./pages/SurveyList";
import SurveyCreate from "./components/SurveyCreate";
import SurveyDetail from "./components/SurveyDetail";
import BoardPage from "./pages/BoardPage";
import AddPostPage from "./components/AddPostPage";
import MyPage from "./pages/MyPage";
import MyProfile from "./components/MyProfile";
import ProfessorStatus from "./pages/ProfessorStatus";
import MyPost from "./components/MyPost";
import ClassroomDetail from "./pages/ClassroomDetail";
import MyPostDetail from "./components/MyPostDetail";
import MyAttendance from "./components/MyAttendance";

function TitleUpdater() {
  const location = useLocation(); // 현재 경로 감지

  useEffect(() => {
    switch (location.pathname) {
      case "/":
        document.title = "출결관리 시스템";
        break;
      case "/classroom/:classId":
        document.title = "강의실 상세보기";
        break;
      case "/classroom/:classId/attendance":
        document.title = "출석하기";
        break;
      case "/classroom/:classId/manage-attendance":
        document.title = "출결 관리 페이지";
        break;
      case "/classroom/:classId/settings":
        document.title = "강의실 설정";
        break;
      default:
        document.title = "출결관리 시스템";
    }
  }, [location]); // location 변경될 때마다 실행
  return null; // 아무것도 렌더링하지 않음
}
  
function App() {
  const studentId = 1; // 예제용, 실제 로그인 정보에서 가져와야 함
  return (
    <AuthProvider> {/* ✅ 여기서 Provider 감싸기 */}
      <Router>
        <TitleUpdater />
        <Header />
        <main>
        <Routes>
          <Route path="/" element={<ClassroomList />} />
          {/* <Route path="/classroom/:classId/attendance" element={<AttendancePage />} /> */}
          <Route path="/classroom/:classId/manage-attendance" element={<ManageAttendancePage />} />

          <Route path="/classroom/:classId/surveys" element={<SurveyList />} />
          <Route path="/survey/create" element={<SurveyCreate />} />
          <Route path="/survey/:surveyId" element={<SurveyDetail />} />

          {/* <Route path="/classroom/:classId/boards" element={<BoardPage />} /> */}
          <Route path="/classroom/:classId/boards" element={<ClassroomDetail />} />
          <Route path="/classroom/:classId/boards/addpost/:boardId" element={<AddPostPage />} /> {/* 게시글 추가 페이지 */}

          <Route path="/classroom/:classId/settings" element={<ClassSettings />} />
          <Route path="/admin/professors" element={<ProfessorStatus />} />

          <Route path="/mypage" element={<MyPage />}>
            <Route path="/mypage/myprofile" element={<MyProfile />} />
            <Route path="/mypage/mypost" element={<MyPost />} />
            <Route path="/mypage/post/:postId" element={<MyPostDetail />} /> {/* 🔹 상세 페이지 추가 */}
            <Route path="/mypage/myattendance" element={<MyAttendance />} />
          </Route>
        </Routes>
        </main>
        <Footer />
      </Router>
    </AuthProvider>
  );
}

export default App;