import React from 'react';
import { useNavigate } from 'react-router-dom';
import NavigationBar from './NavigationBar'; // 경로 확인 필요
import Footer from './Footer'; // 경로 확인 필요

function ContainerRequestForm() {
  const navigate = useNavigate();
   const tableData = [
      ['', 'Image 1', 'Image 2', 'Image 3'],
      ['Cuda Version', '11.2', '11.8', '-'],
      ['Tensorflow Version', '2.8.2', '2.12.0', '-'],
      ['OS', 'Ubuntu 20.04', 'Ubuntu 20.04', 'Ubuntu 20.04']
    ];

    const hoverEffect = (e) => {
        e.target.style.background = '#777';
      };
      const resetEffect = (e) => {
        e.target.style.background = '#555';
      };

  return (
    <div>
        <NavigationBar/>
        <div style={{ height: '10vh' }}></div>
        <main style={styles.container}>
      <h1 style={styles.heading}>Container Request Form</h1>
            <form style={styles.form}>
              <div style={styles.formGroup}>
                <p style={styles.label}>Choose a Container Image</p>
                <div style={styles.radioGroup}>
                  <label style={styles.radioLabel}>
                    <input type="radio" name="image" value="image1" required style={styles.radioButton} />
                    Image 1
                  </label>
                   <label style={styles.radioLabel}>
                    <input type="radio" name="image" value="image2" required style={styles.radioButton} />
                    Image 2
                  </label>
                   <label style={styles.radioLabel}>
                    <input type="radio" name="image" value="image3" required style={styles.radioButton} />
                    Image 3
                  </label>
                  {/* Repeat for other images */}
                </div>
              </div>

              <div style={styles.tableContainer}>
                <table style={styles.table}>
                  <tbody>
                    {tableData.map((row, rowIndex) => (
                      <tr key={rowIndex}>
                        {row.map((cell, colIndex) => (
                          <td key={colIndex} style={styles.tableCell}>{cell}</td>
                        ))}
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>

              <div style={styles.formGroup}>
                <p style={styles.label}>Environment</p>
                <div style={styles.radioGroup}>
                  <label style={styles.radioLabel}>
                    <input type="radio" name="environment" value="LAB" required style={styles.radioButton} />
                    LAB
                  </label>
                  <label style={styles.radioLabel}>
                    <input type="radio" name="environment" value="FARM" style={styles.radioButton} />
                    FARM
                  </label>
                </div>
              </div>

              {/* User information inputs */}
              <div style={styles.inputGroup}>
                  <p style={styles.label}>Student ID</p>
                <input type="text" placeholder="e.g., 2018112032" required style={styles.inputField} />
                 <p style={styles.label}>Department</p>
                <input type="text" placeholder="e.g., Computer Science" required style={styles.inputField} />
                 <p style={styles.label}>Professor Name</p>
                <input type="text" placeholder="e.g., Dongho Kim" required style={styles.inputField} />
       <p style={styles.label}>Usage</p>
                 <textarea placeholder="eg.. Project name" required style={{...styles.inputField, height: '100px'}} />
              </div>

            <div style={styles.formGroup}>
                <p style={styles.label}>Expected Expiration Date (If you are AI Department students, expected graduation date)</p>
                <input type="date" required style={styles.inputField} />
            </div>

              {/* GPU Model selection */}
              <div style={styles.formGroup}>
                <p style={styles.label}>Choose a GPU Model</p>
                <div style={styles.radioGroup}>
                  <label style={styles.radioLabel}>
                      <input type="radio" name="gpu" value="RTX2080ti" required style={styles.radioButton} />
                      RTX 2080ti (only LAB)
                    </label>
                  <label style={styles.radioLabel}>
                    <input type="radio" name="gpu" value="RTX3090" required style={styles.radioButton} />
                    RTX 3090
                  </label>
                  <label style={styles.radioLabel}>
                    <input type="radio" name="gpu" value="5000" required style={styles.radioButton} />
                    RTX 5000 (only FARM)
                  </label>
                  <label style={styles.radioLabel}>
                    <input type="radio" name="gpu" value="6000" required style={styles.radioButton} />
                    RTX 6000 (only LAB)
                  </label>
                  {/* Repeat for other GPU models */}
                </div>
              </div>

                <p style={styles.cautionText}>
                <h3>** !Must read! **</h3>
                While using the server, you are responsible for any problems that arise due to your carelessness, lack of familiarity with the manual, or failure to respond to administrator requests, and future usage restrictions or disadvantages may apply.
                As data may be deleted while using the server, you should frequently back it up on your personal computer.
                After the server's scheduled expiration date, containers and storage will be deleted immediately, and the administrator is not responsible for any problems caused by users not backing up their data.
                If there is a reason to use it after the scheduled expiration date, you must apply for an extension at least 3 days before the scheduled expiration date.
                If the administrator does not separately notify you of the approval via slack DM, the approval will be rejected and you will not be notified of the reason for the rejection.
                <br/><br/>Are you signed up for Slack? You must sign up. If you have not signed up, please sign up using the link below.
                The name displayed must be your real name (in Korean). <br/>
                <a href="https://dguai-lab.slack.com/join/shared_invite/zt-2fthv9brr-ICV0xL7LyahdVARuazI8bA#/shared-invite/email" target="_blank">
                Join Slack
                </a>
                <br/><br/>
                Additionally, we will provide a manual for using the server. You can check it at the link below.
                <br/>
                <a href="https://docs.google.com/document/d/1PtMqJ25kKMZLJnOQY8vgWyTY3HBWUvP86vFaZ2ldMdg/edit" target="_blank">
                User Manual
                </a>
                </p>

              <button type="submit" style={styles.submitButton} onMouseEnter={hoverEffect} onMouseLeave={resetEffect}>Submit</button>
            </form>

            </main>
        <Footer/>
    </div>

  );
}

// Define your styles here
const styles = {
  container: {
    display: 'flex', // Flexbox 레이아웃 사용
    flexDirection: 'column', // 아이템들을 세로 방향으로 배열
    alignItems: 'center', // 가로 축에서 중앙에 정렬
    justifyContent: 'center', // 세로 축에서 중앙에 정렬 (필요한 경우)
    margin: '0 auto', // 자동 마진을 사용하여 좌우 중앙에 배치
    maxWidth: '800px', // 최대 너비를 800px로 설정
    width: '100%',
    padding: '20px',
    paddingBottom: '10vh',
  },
  heading: {
    textAlign: 'center',
    marginBottom: '20px',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
  },
  formGroup: {
    marginBottom: '20px',
  },
  label: {
    marginBottom: '10px',
    fontWeight: 'bold',
  },
  radioGroup: {
    display: 'flex',
    flexDirection: 'column',
  },
  radioLabel: {
    marginBottom: '5px',
  },
  radioButton: {
    marginRight: '10px',
  },
  inputGroup: {
    marginBottom: '20px',
  },
  inputField: {
      padding: '10px',
      marginBottom: '10px',
      borderRadius: '5px',
      border: '1px solid #ccc',
      outline: 'none',
      resize: 'none', // Disable resizing for textarea
      width: '300px',
    },
    submitButton: {
      padding: '15px 30px', // 적절한 패딩으로 편안한 클릭 영역 제공
      backgroundColor: '#555',
      color: 'white', // 버튼 내 텍스트 색상을 흰색으로 설정
      border: 'none', // 테두리 제거
      borderRadius: '5px', // 부드러운 모서리를 위한 경계 반경
      fontSize: '16px', // 충분히 큰 글꼴 크기로 가독성 향상
      cursor: 'pointer', // 마우스 오버 시 포인터 모양으로 변경하여 클릭 가능함을 나타냄
      transition: 'background-color 0.3s', // 배경색 변경 시 부드러운 전환 효과
      outline: 'none', // 포커스 시 발생하는 기본 윤곽선 제거
      fontWeight: 'bold', // 텍스트를 굵게하여 더 눈에 띄게 함
      textTransform: 'uppercase', // 버튼 텍스트를 대문자로 변경하여 더욱 돋보이게 함
      letterSpacing: '1px', // 문자 간격을 약간 넓혀서 디자인적 요소 강화
      boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.1)', // 약간의 그림자 효과로 입체감 추가
      margin: '0 auto', // 버튼을 중앙에 위치시킴
      display: 'block', // 블록 레벨 요소로 만들어 주변 요소와의 거리를 확보

    },


    table: {
      width: '100%',
      borderCollapse: 'collapse',
    },
    tableCell: {
      border: '1px solid #ccc',
      textAlign: 'center',
      padding: '8px'
    },
    cautionText: {
      color: 'red',
      fontWeight: 'bold',
      marginBottom: '20px',
      textAlign: 'center'
    },
  };

  export default ContainerRequestForm;