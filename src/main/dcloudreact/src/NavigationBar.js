import React from 'react';
import { useNavigate } from 'react-router-dom';

function NavigationBar() {
  const navigate = useNavigate();

  const handleLogout = () => {
    console.log("Logging out...");
    navigate('/');
  };

  const headerStyle = {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '10px 20px',
    background: '#FEB309', // 다크 그레이 색상으로 변경
    color: 'white',
    position: 'fixed',
    top: 0,
    left: 0,
    right: 0,
    zIndex: 1000,
    borderRadius: '0 0 10px 10px', // 하단 둥근 모서리 추가
  };

  const buttonStyle = {
    padding: '8px 16px',
    background: '#555', // 버튼 배경 색상 변경
    color: 'white',
    border: 'none',
    borderRadius: '20px', // 더 둥근 모서리
    cursor: 'pointer',
    transition: 'all 0.2s ease-in-out',
    fontWeight: 'bold',
    margin: '0 5px',
  };

  // 호버 효과를 더 단순화
  const hoverEffect = (e) => {
    e.target.style.background = '#777'; // 호버 시 색상 변경
  };

  const resetEffect = (e) => {
    e.target.style.background = '#555'; // 기본 색상 복원
  };

  return (
    <header style={headerStyle}>
      <img src="/dcloudlogo.png" onClick={() => navigate('/mainpage')} alt="Logo" style={{height: '65px', cursor: 'pointer'}} />
      <nav>
        <ul style={{listStyle: 'none', display: 'flex', gap: '20px', margin: 0, padding: 0}}>
          <li><button style={buttonStyle} onMouseEnter={hoverEffect} onMouseLeave={resetEffect} onClick={() => navigate('/containerrequestform')}>Container Request</button></li>
          <li><button style={buttonStyle} onMouseEnter={hoverEffect} onMouseLeave={resetEffect} onClick={() => navigate('/containerInquiry')}>Container Inquiry</button></li>
          <li><button style={buttonStyle} onMouseEnter={hoverEffect} onMouseLeave={resetEffect} onClick={() => navigate('/report')}>Report Errors or Contact Us</button></li>
          <li><button style={buttonStyle} onMouseEnter={hoverEffect} onMouseLeave={resetEffect} onClick={() => navigate('/mypage')}>My Page</button></li>
        </ul>
      </nav>
      <button onClick={handleLogout} style={{...buttonStyle, background: '#444'}} onMouseEnter={hoverEffect} onMouseLeave={resetEffect}>Log out</button>
    </header>
  );
}

export default NavigationBar;
