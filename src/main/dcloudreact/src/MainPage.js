import React from 'react';
import { useNavigate } from 'react-router-dom'; // 페이지 이동을 위해 useNavigate 훅을 import합니다.
import NavigationBar from './NavigationBar'; // NavigationBar 컴포넌트 import
import Footer from './Footer'; // 경로 확인 필요
function MainPage() {
  const navigate = useNavigate();

  return  (
         <div>
                       <NavigationBar/>
                       <div style={{ height: '10vh' }}></div>
                       <main style={styles.container}>
                         <h1>Welcome to the Main Page!</h1>
                         <p>This is the main area of our application. From here, you can navigate to different sections of our site.</p>
                       </main>
                       <Footer/>
                  </div>
  );
}
const styles = {
  container: {
    display: 'flex', // Flexbox 레이아웃 사용
    flexDirection: 'column', // 아이템들을 세로 방향으로 배열
    alignItems: 'center', // 가로 축에서 중앙에 정렬
    justifyContent: 'center', // 세로 축에서 중앙에 정렬 (필요한 경우)
    margin: '0 auto', // 자동 마진을 사용하여 좌우 중앙에 배치
    maxWidth: '1000px', // 최대 너비를 800px로 설정
    width: '100%',
    padding: '20px',
    paddingBottom: '10vh',
  },
}
export default MainPage;
