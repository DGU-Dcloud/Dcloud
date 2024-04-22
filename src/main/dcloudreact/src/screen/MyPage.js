import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import NavigationBar from './NavigationBar';
import Footer from './Footer';
import axios from 'axios'; // Axios import

function MyPage() {
  const navigate = useNavigate();

  // Sample user information and container request data
  const [userInfo, setUserInfo] = useState({});
  const [containerRequests, setContainerRequests] = useState([]);

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
              const response = await fetch('http://localhost:8080/api/yourinfo', {
                credentials: 'include'
              });
              const data = await response.json();
              setUserInfo(data.userInfo);
              if (data.containerRequests) { // containerRequests가 있는지 확인
                setContainerRequests(data.containerRequests);
              } else {
                console.log('No containerRequests found:', data);
              }
            } catch (error) {
              console.error('Error fetching data:', error);
            }
          };


  const handleLogout = () => {
      console.log("Logging out...");
      axios.post('/api/logout', {}, { withCredentials: true }) // 서버에 로그아웃 요청
        .then(response => {
          // 로그아웃 성공 후 홈페이지로 리디렉션
          navigate('/');
        })
        .catch(error => {
          console.error('Logout failed:', error);
        });
    };
     const hoverEffect = (e) => {
        e.target.style.background = '#777';
      };

      const resetEffect = (e) => {
        e.target.style.background = '#555';
      };

  return (
    <div>
      <NavigationBar />
      <div style={{ height: '10vh' }}></div>
      <main style={styles.container}>
        <h1>Your Info</h1>
                <table style={styles.table}>
                  <tbody>
                    <tr>
                      <th style={styles.thRow}>Name</th>
                      <td style={styles.tdRow}>{userInfo.userName}</td>
                    </tr>
                    <tr>
                      <th style={styles.thRow}>ID</th>
                      <td style={styles.tdRow}>{userInfo.userID}</td>
                    </tr>
                    <tr>
                      <th style={styles.thRow}>Birth Date</th>
                      <td style={styles.tdRow}>{userInfo.birthDate}</td>
                    </tr>
                    <tr>
                      <th style={styles.thRow}>Email</th>
                      <td style={styles.tdRow}>{userInfo.email}</td>
                    </tr>
                  </tbody>
                </table>
        <div style={{ height: '3vh' }}></div>
        <h1>Container Request Status</h1>
        <table style={styles.table}>
          <thead>
            <tr>
              <th style={styles.th}>Request Date</th>
              <th style={styles.th}>Status</th>
            </tr>
          </thead>
          <tbody>
            {containerRequests.map((request, index) => {
              const date = new Date(request.createdAt);
              const formattedDate = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
              return (
                <tr key={index}>
                  <td style={styles.td}>{formattedDate}</td>
                  <td style={styles.td}>{request.status}</td>
                </tr>
              );
            })}
          </tbody>

        </table>
 <div style={{ height: '3vh' }}></div>
        <h1>Your Active Container</h1>
                <table style={styles.table}>
                  <thead>
                    <tr>
                      <th style={styles.th}>No</th>
                      <th style={styles.th}>Server Name</th>
                      <th style={styles.th}>SSH Command</th>
                      <th style={styles.th}>Jupyter URL</th>
                      <th style={styles.th}>Due Date</th>
                    </tr>
                  </thead>
                  <tbody>
                    {containerRequests.map((request, index) => (
                      <tr key={index}>
                        <td style={styles.td}>1</td>
                        <td style={styles.td}>LAB2</td>
                        <td style={styles.td}>ssh mingyun@210.94.179.18 -p 9100</td>
                        <td style={styles.td}>http://210.94.179.18:9101</td>
                        <td style={styles.td}>24.05.31</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
      </main>
      <div style={containerStyle}>
      <button onClick={handleLogout} style={{...buttonStyle, background: '#444'}} onMouseEnter={hoverEffect} onMouseLeave={resetEffect}>Log out</button>
      </div>
      <div style={{ height: '10vh' }}></div>
      <Footer />
    </div>
  );
}
const buttonStyle = {
    padding: '8px 16px',
    background: '#555',
    color: 'white',
    border: 'none',
    borderRadius: '20px',
    cursor: 'pointer',
    transition: 'all 0.2s ease-in-out',
    fontWeight: 'bold',
    margin: '0 5px',


  };
  const containerStyle = {
      display: 'flex',
      justifyContent: 'center', // 가로 방향으로 중앙 정렬
      alignItems: 'center', // 세로 방향으로 중앙 정렬
    };
const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    margin: '0 auto',
    maxWidth: '1000px',
    width: '100%',
    padding: '20px',
    paddingBottom: '10vh',
  },
  infoTable: {
    width: '50%',  // Setting the width of the info table
    marginBottom: '20px',
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
  },
  table: {
    borderCollapse: 'collapse',
    width: '80%',
    marginTop: '20px',
    marginBottom: '20px',
    boxShadow: '0 4px 8px 0 rgba(0,0,0,0.2)',
    textAlign: 'center',
    border: '1px solid #e0e0e0',
  },
  th: {
    backgroundColor: '#f5f5f5',
    color: '#333',
    fontWeight: 'bold',
    padding: '12px 10px',
    borderBottom: '2px solid #e0e0e0',
  },
  td: {
    textAlign: 'center',
    padding: '10px',
    borderBottom: '1px solid #e0e0e0',
    color: '#616161',
  },

  thRow: {
      backgroundColor: '#f5f5f5',
      color: '#333',
      fontWeight: 'bold',
      padding: '12px 10px',
      borderRight: '2px solid #e0e0e0',
    },
    tdRow: {
      textAlign: 'center',
      padding: '10px',
      borderRight: '1px solid #e0e0e0',
      color: '#616161',
    }


}

export default MyPage;
