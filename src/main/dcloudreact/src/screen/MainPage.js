import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom'; // 페이지 이동을 위해 useNavigate 훅을 import합니다.
import NavigationBar from './NavigationBar'; // NavigationBar 컴포넌트 import
import Footer from './Footer'; // 경로 확인 필요
import axios from 'axios'; // Axios import
function MainPage() {
  const navigate = useNavigate();
  const [servers, setServers] = useState([]); // DB에서 가져온 컨테이너 데이터를 저장할 상태
    const [loading, setLoading] = useState(true); // 로딩 상태 관리
//  useEffect(() => {
//      // 데이터를 불러오는 함수
//      const fetchData = async () => {
//        const response = await fetch('http://localhost:8080/api/servers');
//        const data = await response.json();
//        setServers(data);
//      };
//
//      fetchData().catch(console.error);
//    }, []);

useEffect(() => {
    // 세션 검증
    axios.get('/api/check-auth', { withCredentials: true })
      .then(response => {
        // 세션이 유효한 경우에만 서버 데이터 로딩
        console.log('Response:', response);
        fetchData();
      })
      .catch(error => {
        // 세션이 유효하지 않은 경우 로그인 페이지로 리디렉션
        console.error('Session not valid:', error);
        navigate('/');
      });
  }, [navigate]);

  const fetchData = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/servers');
      const data = await response.json();
      setServers(data);
      setLoading(false);
    } catch (error) {
      console.error('Error loading data:', error);
    }
  };

  if (loading) {
    return <div>Loading...</div>; // 로딩 중 화면 표시
  }
  return (
    <div>
      <NavigationBar />
      <div style={{ height: '10vh' }}></div>
      <main style={styles.container}>
        <h1>Welcome to Dcloud!</h1>
        <p>This is the main area of our application. From here, you can navigate to different sections of our site.</p>

        <h1>Server Spec Table</h1>
        <table style={styles.table}>
          <thead>
            <tr>
              <th style={styles.th}>Server Name</th>
              <th style={styles.th}>CPU</th>
              <th style={styles.th}>CPU Count</th>
              <th style={styles.th}>GPU</th>
              <th style={styles.th}>GPU Count</th>
              <th style={styles.th}>RAM (GB)</th>
              <th style={styles.th}>SSD (GB)</th>
            </tr>
          </thead>
          <tbody>
            {servers.map((server, index) => (
              <tr key={index}>
                <td style={styles.td}>{server.serverName}</td>
                <td style={styles.td}>{server.cpuName}</td>
                <td style={styles.td}>{server.cpuCnt}</td>
                <td style={styles.td}>{server.gpuName}</td>
                <td style={styles.td}>{server.gpuCnt}</td>
                <td style={styles.td}>{server.ramGB}</td>
                <td style={styles.td}>{server.ssdGB}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </main>
      <Footer />
    </div>
  );
}



const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    margin: '0 auto',
    maxWidth: '1200px',
    width: '100%',
    padding: '20px',
    paddingBottom: '10vh',
  },
  table: {
    borderCollapse: 'collapse',
    width: '80%', // 테이블의 너비를 조정
    marginTop: '20px',
    marginBottom: '20px',
    boxShadow: '0 4px 8px 0 rgba(0,0,0,0.2)', // 그림자 효과 추가
    textAlign: 'center',
    border: '1px solid #e0e0e0', // 경계선 스타일 조정
  },
  th: {
    backgroundColor: '#f5f5f5', // 헤더 배경색
    color: '#333', // 헤더 글자색
    fontWeight: 'bold',
    padding: '12px 10px', // 헤더 패딩 조정
    borderBottom: '2px solid #e0e0e0', // 하단 경계선 스타일 조정
  },
  td: {
    textAlign: 'center',
    padding: '10px', // 데이터 셀 패딩 조정
    borderBottom: '1px solid #e0e0e0', // 셀 하단 경계선 스타일 조정
    color: '#616161', // 글자색 조정
  }
}


export default MainPage;
