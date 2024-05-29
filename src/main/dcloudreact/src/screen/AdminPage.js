import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import NavigationBar from './NavigationBar';
import Footer from './Footer';
import axios from 'axios';

function AdminPage() {
  const navigate = useNavigate();
  const [containerRequests, setContainerRequests] = useState([]);
  const [selectedRequests, setSelectedRequests] = useState({});

  useEffect(() => {
    axios.get('/api/check-auth', { withCredentials: true })
      .then(response => {
        console.log('Response:', response);
        fetchData();
      })
      .catch(error => {
        console.error('Session not valid:', error);
        navigate('/');
      });
  }, [navigate]);

  const fetchData = async () => {
    const response = await fetch('http://localhost:8080/api/allcontainerrequest');
    const data = await response.json();
    setContainerRequests(data);
  };

  const handleCheckChange = (requestId) => {
    setSelectedRequests(prev => ({
      ...prev,
      [requestId]: !prev[requestId]
    }));
  };

  const sendRequestDecision = (action) => {
    const ids = Object.entries(selectedRequests)
                     .filter(([key, value]) => value)
                     .map(([key]) => key);
    console.log(`${action} Selected Requests:`, ids);
    axios.post('http://localhost:8080/api/admin-action', { action, ids })
         .then(response => {
           console.log(`${action} action successful:`, response.data);
           window.location.reload();
         })
         .catch(error => {
           console.error(`${action} action failed:`, error);
         });
  };

  const getStatusStyle = (status) => {
    const baseStyle = {
      textAlign: 'center',
      padding: '10px',
      borderBottom: '1px solid #e0e0e0',
      color: '#616161',
    };

    switch (status) {
      case 'Pending': return { ...baseStyle, color: 'blue' };
      case 'Rejected': return { ...baseStyle, color: 'red' };
      case 'Approved': return { ...baseStyle, color: 'green' };
      default: return baseStyle;
    }
  };

  return (
    <div>
      <NavigationBar />
      <div style={{ height: '10vh' }}></div>
      <main style={styles.container}>
        <h1>Admin Page</h1>
        <p>You can check the announcements on this page. Please check frequently. You can also check the error history so far.</p>

        <div style={styles.tableHeader}>
                <div style={styles.tableHeaderItem}>
                  <input
                    type="radio"
                    name="tableType"
                    value="allowOrDeny"
                    checked={selectedRequests.tableType === 'allowOrDeny'}
                    onChange={() => setSelectedRequests(prev => ({ ...prev, tableType: 'allowOrDeny' }))}
                  />
                   ContainerRequest
                </div>
                <div style={styles.tableHeaderItem}>
                  <input
                    type="radio"
                    name="tableType"
                    value="report"
                    checked={selectedRequests.tableType === 'report'}
                    onChange={() => setSelectedRequests(prev => ({ ...prev, tableType: 'report' }))}
                  />
                    Report
                </div>
              </div>

        {selectedRequests.tableType === 'allowOrDeny' && (
                <>
                  <table style={styles.table}>
                            <thead>
                              <tr>
                                <th style={styles.th}>Select</th>
                                <th style={styles.th}>No</th>
                                <th style={styles.th}>Request Date</th>
                                <th style={styles.th}>User ID</th>
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
                                    <td style={styles.td}>
                                      <input
                                        type="checkbox"
                                        checked={selectedRequests[request.requestId] || false}
                                        onChange={() => handleCheckChange(request.requestId)}
                                      />
                                    </td>
                                    <td style={styles.td}>{request.requestId}</td>
                                    <td style={styles.td}>{formattedDate}</td>
                                    <td style={styles.td}>{request.userId}</td>
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
                          <div style={styles.buttonContainer}>
                            <button style={styles.applyButton} onClick={() => sendRequestDecision('Apply')}>Approved</button>
                            <button style={styles.denyButton} onClick={() => sendRequestDecision('Deny')}>Rejected</button>
                            <button style={styles.pendingButton} onClick={() => sendRequestDecision('Pending')}>Pending</button>
                          </div>
                </>
              )}



    {selectedRequests.tableType === 'report' && (
      <>
        <table style={styles.table}>
                  <thead>
                    <tr>
                      <th style={styles.th}>Select</th>
                      <th style={styles.th}>No</th>
                      <th style={styles.th}>Request Date</th>
                      <th style={styles.th}>User ID</th>
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
                          <td style={styles.td}>
                            <input
                              type="checkbox"
                              checked={selectedRequests[request.requestId] || false}
                              onChange={() => handleCheckChange(request.requestId)}
                            />
                          </td>
                          <td style={styles.td}>{request.requestId}</td>
                          <td style={styles.td}>{formattedDate}</td>
                          <td style={styles.td}>{request.userId}</td>
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
                <div style={styles.buttonContainer}>
                  <button style={styles.applyButton} onClick={() => sendRequestDecision('Apply')}>Approved</button>
                  <button style={styles.denyButton} onClick={() => sendRequestDecision('Deny')}>Rejected</button>
                  <button style={styles.pendingButton} onClick={() => sendRequestDecision('Pending')}>Pending</button>
                </div>
      </>
    )}


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
  buttonContainer: {
    display: 'flex',
    justifyContent: 'space-around',
    width: '100%',
    marginTop: '20px',
  },
  applyButton: {
    padding: '10px 20px',
    backgroundColor: '#4CAF50',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    fontWeight: 'bold',
  },
denyButton: {
    padding: '10px 20px',
    backgroundColor: '#f44336',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    fontWeight: 'bold',
  },
  pendingButton: {
      padding: '10px 20px',
      backgroundColor: '#000000',
      color: 'white',
      border: 'none',
      borderRadius: '5px',
      cursor: 'pointer',
      fontWeight: 'bold',
    },
  tableHeader: {
    display: 'flex',
    justifyContent: 'center',
    marginBottom: '20px',
  },
  tableHeaderItem: {
    marginRight: '20px',
  },
};

export default AdminPage;
