import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import NavigationBar from './NavigationBar';
import Footer from './Footer';

function MyPage() {
  const navigate = useNavigate();

  // Sample user information and container request data
  const [userInfo, setUserInfo] = useState({
    name: "홍길동",
    userId: "gildong123",
    birthDate: "1990.01.01",
    email: "gildong@example.com",
  });

  const [containerRequests, setContainerRequests] = useState([
    { date: "23.04.01", status: "승인대기" },
    { date: "23.03.25", status: "승인완료" },
    { date: "23.03.20", status: "반려" },
  ]);

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
              <td style={styles.tdRow}>{userInfo.name}</td>
            </tr>
            <tr>
              <th style={styles.thRow}>ID</th>
              <td style={styles.tdRow}>{userInfo.userId}</td>
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
            {containerRequests.map((request, index) => (
              <tr key={index}>
                <td style={styles.td}>{request.date}</td>
                <td style={styles.td}>{request.status}</td>
              </tr>
            ))}
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
