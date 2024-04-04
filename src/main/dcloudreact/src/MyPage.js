import React from 'react';
import { useNavigate } from 'react-router-dom'; // 페이지 이동을 위해 useNavigate 훅을 import합니다.
import NavigationBar from './NavigationBar'; // NavigationBar 컴포넌트 import

function MyPage() {
  const navigate = useNavigate();

  return  (
         <div>
              <NavigationBar />
              <main style={{padding: '20px'}}>
                <h1>Welcome to the My Page!</h1>
                <p>This is the main area of our application. From here, you can navigate to different sections of our site.</p>
              </main>
         </div>
  );
}

export default MyPage;
