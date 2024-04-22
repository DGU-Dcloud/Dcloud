import React from 'react';
import { useNavigate } from 'react-router-dom';

function Footer() {
  return (
    <footer style={styles.footer}>
      <p>Developed by <strong>PARK MIN GYUN, LEE JEE YEON</strong></p>
      <p>Contact: griffinland9@naver.com, dlwldus2427@gmail.com </p>
    </footer>
  );
}

// Footer 스타일 정의
const styles = {
  footer: {
    background: '#f2f2f2', // 옅은 회색 배경
    color: '#333', // 글자 색상
    textAlign: 'center', // 텍스트 중앙 정렬
    padding: '10px 0', // 상하 패딩
    position: 'fixed', // 고정 위치
    left: 0,
    bottom: 0,

    width: '100%', // 너비 100%
    fontSize: '14px', // 폰트 크기
  }
};

export default Footer;

