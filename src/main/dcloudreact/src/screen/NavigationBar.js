import React from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function NavigationBar() {
  // 내비게이션 바의 코드는 그대로 유지합니다.
  const navigate = useNavigate();

//  const handleLogout = () => {
//    console.log("Logging out...");
//    navigate('/');
//  };
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

  const hoverEffect = (e) => {
    e.target.style.background = '#777';
  };

  const resetEffect = (e) => {
    e.target.style.background = '#555';
  };

  return (
    <>
      <header style={headerStyle}>
        <img src="/dcloudlogo.png" onClick={() => navigate('/mainpage')} alt="Logo" style={{height: '65px', cursor: 'pointer'}} />
        <nav>

          <ul style={{listStyle: 'none', display: 'flex', gap: '20px', margin: 0, padding: 0}}>
            <li><button style={buttonStyle} onMouseEnter={hoverEffect} onMouseLeave={resetEffect} onClick={() => navigate('/containerrequestform')}>Container Request</button></li>
            <li><button style={buttonStyle} onMouseEnter={hoverEffect} onMouseLeave={resetEffect} onClick={() => navigate('/forum')}>Forum</button></li>
            <li><button style={buttonStyle} onMouseEnter={hoverEffect} onMouseLeave={resetEffect} onClick={() => navigate('/containerlookup')}>Container Lookup</button></li>
            <li><button style={buttonStyle} onMouseEnter={hoverEffect} onMouseLeave={resetEffect} onClick={() => navigate('/reporterrors')}>Report Errors or Contact Us</button></li>
            <li><button style={buttonStyle} onMouseEnter={hoverEffect} onMouseLeave={resetEffect} onClick={() => navigate('/mypage')}>My Page</button></li>
          </ul>


        </nav>
        <img src="/dcloudlogo.png" onClick={() => navigate('/mainpage')} alt="Logo" style={{visibility: 'hidden',height: '65px', cursor: 'pointer'}} />
      </header>

    </>
  );
}


export default NavigationBar;