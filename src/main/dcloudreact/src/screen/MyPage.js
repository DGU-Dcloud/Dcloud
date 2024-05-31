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
    const [activeContainers, setActiveContainers] = useState([]);
    const [reports, setReports] = useState([]);
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
              const response = await fetch('/api/yourinfo', {
                credentials: 'include'
              });
              const data = await response.json();
              setUserInfo(data.userInfo);
              console.log('Response:',data);
              if (data.containerRequests) { // containerRequests가 있는지 확인
                setContainerRequests(data.containerRequests);
              } else {
                console.log('No containerRequests found:', data);
              }

              if (data.activeContainers) { // activeContainer가 있는지 확인
                  setActiveContainers(data.activeContainers);
                } else {
                  console.log('No activeContainers found:', data);
                }

              if (data.reports) { // report있는지 확인
                  setReports(data.reports);
                } else {
                  console.log('No reports found:', data);
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

     const getStatusStyle = (status) => {
         const baseStyle = {
           textAlign: 'center',
           padding: '10px',
           borderBottom: '1px solid #e0e0e0',
           color: '#616161', // Default color, overridden below
         };

         switch (status) {
           case 'Pending':
             return { ...baseStyle, color: 'blue' };
           case 'Rejected':
             return { ...baseStyle, color: 'red' };
           case 'Approved':
             return { ...baseStyle, color: 'green' };
           default:
             return baseStyle;
         }
       };

return (
    <div>
      <NavigationBar />
      <div style={{ height: '10vh' }}></div>
      <main style={styles.container}>
        {/* User Info */}
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

        {/* Container Request Status */}
        <h1>Container Request Status</h1>
        <table style={styles.table}>
          <thead>
            <tr>
              <th style={styles.th}>Request Date</th>
              <th style={styles.th}>Student ID</th>
              <th style={styles.th}>Environment</th>
              <th style={styles.th}>GPU Model</th>
              <th style={styles.th}>Status</th>
            </tr>
          </thead>
          <tbody>
            {containerRequests.map((request, index) => (
              <tr key={index}>
                <td style={styles.td}>{new Date(request.createdAt).toLocaleString()}</td>
                <td style={styles.td}>{request.studentId}</td>
                <td style={styles.td}>{request.environment}</td>
                <td style={styles.td}>{request.gpuModel}</td>
                <td style={getStatusStyle(request.status)}>{request.status}</td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* Active Containers */}
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
            {activeContainers.map((container, index) => (
              <tr key={index}>
                <td style={styles.td}>{index + 1}</td>
                <td style={styles.td}>{container.serverName}</td>
                <td style={styles.td}>{container.sshPort}</td>
                <td style={styles.td}>{container.jupyterPort}</td>
                <td style={styles.td}>{container.deletedAt}</td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* Reports */}
        <h1>Your Report</h1>
        <table style={styles.table}>
          <thead>
            <tr>
              <th style={styles.th}>Requirement</th>
              <th style={styles.th}>SSH Port</th>
              <th style={styles.th}>Report Date</th>
            </tr>
          </thead>
          <tbody>
            {reports.map((report, index) => (
                          <tr key={index}>
                            <td style={styles.td}>{report.category}</td>
                            <td style={styles.td}>{report.sshPort}</td>
                            <td style={styles.td}>{new Date(report.createdAt).toLocaleDateString()}</td>
                          </tr>
                        ))}
          </tbody>
        </table>

      </main>
      <div style={containerStyle}>
        <button onClick={handleLogout} style={buttonStyle}>Log out</button>
      </div>
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
    marginBottom: '200px',


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
