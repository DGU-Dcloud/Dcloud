import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import NavigationBar from './NavigationBar';
import Footer from './Footer';
import axios from 'axios';

function AdminPage() {
  const navigate = useNavigate();
  const [posts, setPosts] = useState([]);
  const [filteredPosts, setFilteredPosts] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [postsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState('');
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
        <h1>Allow or deny</h1>
        <p>You can check the announcements on this page. Please check frequently. You can also check the error history so far.</p>

        <h1>Container Request Status</h1>
                <table style={styles.table}>
                  <thead>
                    <tr>

                      <th style={styles.th}>Request Date</th>
                      <th style={styles.th}>Student ID</th>
                      <th style={styles.th}>Department</th>
                      <th style={styles.th}>Environment</th>
                      <th style={styles.th}>GPU Model</th>
                      <th style={styles.th}>Expected Expire Date</th>
                      <th style={styles.th}>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {containerRequests.map((request, index) => {
                      const date = new Date(request.createdAt);
                        const formattedDate = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;
                        return (
                        <tr key={index}>
                          <td style={styles.td}>{formattedDate}</td>
                          <td style={styles.td}>{request.studentId}</td>
                          <td style={styles.td}>{request.department}</td>
                          <td style={styles.td}>{request.environment}</td>
                          <td style={styles.td}>{request.gpuModel}</td>
                          <td style={styles.td}>{request.expectedExpirationDate}</td>
                          <td style={getStatusStyle(request.status)}>{request.status}</td>
                        </tr>
                      );
                    })}
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
  searchBar: {
    padding: '10px',
    marginBottom: '20px',
    width: '300px',
    borderRadius: '5px',
    border: '1px solid #ccc'
  },
  pagination: {
    marginTop: '20px'
  },
  pageButton: {
    padding: '8px 16px',
    margin: '0 5px',
    borderRadius: '5px',
    cursor: 'pointer',
    background: '#f0f0f0',
    border: 'none'
    },

};

export default AdminPage;
