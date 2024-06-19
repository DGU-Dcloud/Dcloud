import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function NavigationBar() {
  const navigate = useNavigate();
  const [userRole, setUserRole] = useState('');

  useEffect(() => {
    // 로그인 상태 확인 후 사용자의 권한을 설정합니다.
    // 예시 API는 수정해야 할 수 있습니다.
    axios.get('/api/user-role', { withCredentials: true })
      .then(response => {
        setUserRole(response.data.roleId === 1 ? 'manager' : '');
      })
      .catch(error => {
        console.log('Error fetching user role:', error);
      });

  }, []);

  const handleLogout = () => {
    console.log("Logging out...");
    axios.post('/api/logout', {}, { withCredentials: true })
      .then(response => {
        navigate('/');
      })
      .catch(error => {
        console.error('Logout failed:', error);
      });
  };

  const headerStyle = {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '10px 20px',
    background: '#FEB309',
    color: 'white',
    position: 'fixed',
    top: 0,
    left: 0,
    right: 0,
    zIndex: 1000,
    height: '7vh',
    borderRadius: '0 0 10px 10px',
  };

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

  return (
    <>
      <header style={headerStyle}>
        <img src="/dcloudlogo.png" onClick={() => navigate('/mainpage')} alt="Logo" style={{height: '65px', cursor: 'pointer'}} />
        <nav>
          <ul style={{listStyle: 'none', display: 'flex', gap: '20px', margin: 0, padding: 0}}>
            <li><button style={buttonStyle} onClick={() => navigate('/containerrequestform')}>Container Request</button></li>
            <li><button style={buttonStyle} onClick={() => navigate('/forum')}>Forum</button></li>
            <li><button style={buttonStyle} onClick={() => navigate('/containerlookup')}>Container Lookup</button></li>
            <li><button style={buttonStyle} onClick={() => navigate('/reporterrors')}>Report Errors or Contact Us</button></li>
            <li><button style={buttonStyle} onClick={() => navigate('/mypage')}>My Page</button></li>
            {userRole === 'manager' && <li><button style={buttonStyle} onClick={() => navigate('/admin')}>Admin</button></li>}
          </ul>
        </nav>
        <img src="/dcloudlogo.png" style={{visibility: 'hidden', height: '65px'}} alt="Hidden Logo" />
      </header>
    </>
  );
}

export default NavigationBar;
