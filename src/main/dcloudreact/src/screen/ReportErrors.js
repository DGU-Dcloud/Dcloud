import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import NavigationBar from './NavigationBar'; // 경로 확인 필요
import Footer from './Footer'; // 경로 확인 필요
import axios from 'axios';

function ReportErrors() {
  const [selectedIssue, setSelectedIssue] = useState('');
  const navigate = useNavigate();
  const [file, setFile] = useState(null);

  const handleSubmit = async (event) => {
    event.preventDefault(); // 폼의 기본 제출 동작 방지
  // FormData 객체 생성
  const formData = new FormData();

  if (selectedIssue === 'Container Connection Error') {
    formData.append('name', event.target.name.value);
    formData.append('department', event.target.department.value);
    formData.append('studentId', event.target.studentId.value);
    formData.append('sshPort', event.target.sshPort.value);
    formData.append('category', event.target.category.value);
    formData.append('file', file);
  } else if (selectedIssue === 'Container Relocation Request') {
    formData.append('name', event.target.name.value);
    formData.append('department', event.target.department.value);
    formData.append('studentId', event.target.studentId.value);
    formData.append('sshPort', event.target.sshPort.value);
    formData.append('reason', event.target.reason.value);
    formData.append('category', event.target.category.value);
  } else if (selectedIssue === 'Extend Expiration Date') {
    formData.append('name', event.target.name.value);
    formData.append('department', event.target.department.value);
    formData.append('studentId', event.target.studentId.value);
    formData.append('sshPort', event.target.sshPort.value);
    formData.append('permission', event.target.permission.value);
    formData.append('expirationDate', event.target.expirationDate.value);
    formData.append('reason', event.target.reason.value);
    formData.append('category', event.target.category.value);
  } else if (selectedIssue === 'Just Inquiry') {
    formData.append('name', event.target.name.value);
    formData.append('department', event.target.department.value);
    formData.append('studentId', event.target.studentId.value);
    formData.append('inquiryDetails', event.target.inquiryDetails.value);
    formData.append('category', event.target.category.value);
    formData.append('file', file);
  }

//    // 폼 데이터 객체 생성
//    let formData = {};
//    if (selectedIssue === 'Container Connection Error') {
//      formData = {
//        name: event.target.name.value,
//        department: event.target.department.value,
//        studentId: event.target.studentId.value,
//        sshPort: event.target.sshPort.value,
//        category: event.target.category.value,
//      };
//      formData.append('file', file);
//    } else if (selectedIssue === 'Container Relocation Request') {
//      formData = {
//        name: event.target.name.value,
//        department: event.target.department.value,
//        studentId: event.target.studentId.value,
//        sshPort: event.target.sshPort.value,
//        reason: event.target.reason.value,
//        category: event.target.category.value,
//      };
//    } else if (selectedIssue === 'Extend Expiration Date') {
//      formData = {
//        name: event.target.name.value,
//        department: event.target.department.value,
//        studentId: event.target.studentId.value,
//        sshPort: event.target.sshPort.value,
//        permission: event.target.permission.value,
//        expirationDate: event.target.expirationDate.value,
//        reason: event.target.reason.value,
//        category: event.target.category.value,
//      };
//    } else if (selectedIssue === 'Just Inquiry') {
//      formData = {
//        name: event.target.name.value,
//        department: event.target.department.value,
//        studentId: event.target.studentId.value,
//        inquiryDetails: event.target.inquiryDetails.value,
//        category: event.target.category.value,
//      };
//      formData.append('file', file);
//    }
//    console.log('Submitting form data:', formData);
    try {
      const response = await axios.post('/api/reports', formData, {
        headers: {
          'Content-Type': 'application/json'
        },
        withCredentials: true // 쿠키 전송 설정 추가
      });
      console.log('Form submitted successfully:', response.data);
    } catch (error) {
      console.error('Failed to submit form:', error);
    }

    navigate('/mainpage');

  };

  useEffect(() => {
    // 세션 검증
    axios.get('/api/check-auth', { withCredentials: true })
      .then(response => {
        // 세션이 유효한 경우에만 서버 데이터 로딩
        console.log('Response:', response);
      })
      .catch(error => {
        // 세션이 유효하지 않은 경우 로그인 페이지로 리디렉션
        console.error('Session not valid:', error);
        navigate('/');
      });
  }, [navigate]);

  // 라디오 버튼의 값이 변경될 때 실행될 핸들러 함수
  const handleIssueChange = (e) => {
    setSelectedIssue(e.target.value);
  };

  const handleFileChange = (e) => {
      setFile(e.target.files[0]);
    };

  const hoverEffect = (e) => {
    e.target.style.background = '#777';
  };

  const resetEffect = (e) => {
    e.target.style.background = '#555';
  };

  // 선택된 이슈에 따라 표시될 폼을 반환하는 함수
  const renderFormForIssue = () => {
    switch (selectedIssue) {
      case 'Container Connection Error':
        return (
          <div>
            <div style={styles.inputGroup}>
              <p style={styles.label}>Name</p>
              <input type="text" placeholder="e.g., PARK MIN GYUN" required style={styles.inputField} name="name" />
              <p style={styles.label}>Department</p>
              <input type="text" placeholder="e.g., Computer Science" required style={styles.inputField} name="department" />
              <p style={styles.label}>Student ID</p>
              <input type="text" placeholder="e.g., 2018112032" required style={styles.inputField} name="studentId" />
              <p style={styles.label}>Container's SSH Port Number</p>
              <input type="text" placeholder="e.g., 9010" required style={styles.inputField} name="sshPort" />
              <p style={styles.label}>Attach File</p>
              <input type="file" style={styles.inputField} onChange={handleFileChange} accept=".png, .jpg, .jpeg" />
            </div>

            <p style={styles.cautionText}>
              <h3>** !Please make sure to read the following! **</h3>
              Please submit the error report category accurately.
              <br />⚠ If you apply for other "Contact Details," the report will be automatically ignored, and the error report may be deleted without separate notice. ⚠
              <br /><br />Due to firewall policies, container access is not possible from overseas.
              <br /><br />If you enter the ssh command and receive a "Connection Refused" result, please select this option.
              <br /><br />Please report errors only after performing all self-debugging for your error type in the Forum and if the problem persists. If you report an error without completing all steps, the report will be ignored and deleted.
              <br /><br />When reporting an error, you can check the progress of error handling on My Page, and you must respond to the administrator's requests or questions within 12 hours. If there is no response for more than 12 hours, the error report will be deleted without separate notice. (You can check the administrator's requests or questions by clicking on the error you reported in the Your Report section of My Page.)
              <br /><br />Without a detailed description of the error situation, the administrator cannot understand the situation. Error reports must include a copy or capture of the printed error message, the time the error occurred, and the situation in which the error occurred.
            </p>

            <div>
              <div>
                <div>
                  <p style={styles.label}>Do you agree with this statement? </p>
                  <div style={styles.radioGroup}>
                    <label style={styles.radioLabel}>
                      <input type="radio" name="agree1" value="image1" required style={styles.radioButton} />
                      Yes. I have completed self-debugging and is applying after understanding all the error reporting methods.
                    </label>
                  </div>
                </div>
              </div>
            </div>
          </div>
        );
      case 'Container Relocation Request':
        return (
          <div>
            <div style={styles.inputGroup}>
              <p style={styles.label}>Name</p>
              <input type="text" placeholder="e.g., PARK MIN GYUN" required style={styles.inputField} name="name" />
              <p style={styles.label}>Department</p>
              <input type="text" placeholder="e.g., Computer Science" required style={styles.inputField} name="department" />
              <p style={styles.label}>Student ID</p>
              <input type="text" placeholder="e.g., 2018112032" required style={styles.inputField} name="studentId" />
              <p style={styles.label}>Container's SSH Port Number</p>
              <input type="text" placeholder="e.g., 9010" required style={styles.inputField} name="sshPort" />
              <p style={styles.label}>Reason for Relocation</p>
              <textarea required style={{ ...styles.inputField, height: '100px', width: '700px' }} name="reason" />
            </div>

            <p style={styles.cautionText}>
              <h3>** !Please make sure to read the following! **</h3>
              Please submit the error report category accurately.
              <br /><br />⚠ If you apply for other "Contact Details," the report will be automatically ignored, and the error report may be deleted without separate notice. ⚠
              <br /><br />If you need to relocate the container due to issues such as GPU and memory usage, mismatched CUDA versions, etc., please submit a request.
              <br /><br />Please report errors only after performing all self-debugging for your error type in the Forum and if the problem persists. If you report an error without completing all steps, the report will be ignored and deleted.
              <br /><br />When reporting an error, you can check the progress of error handling on My Page, and you must respond to the administrator's requests or questions within 12 hours. If there is no response for more than 12 hours, the error report will be ignored and deleted. (You can check the administrator's requests or questions by clicking on the error you reported in the Your Report section of My Page.)
              <br /><br />Without a detailed description of the situation in the report, the administrator cannot understand the situation. Please provide as much detailed information as possible.
            </p>

            <div>
              <div>
                <p style={styles.label}>Do you agree with this statement? </p>
                <div style={styles.radioGroup}>
                  <label style={styles.radioLabel}>
                    <input type="radio" name="agree2" value="image2" required style={styles.radioButton} />
                    Yes. I have completed self-debugging and is applying after understanding all the error reporting methods.
                  </label>
                </div>
              </div>
            </div>
          </div>
        );
      case 'Extend Expiration Date':
        return (
          <div>
            <div style={styles.inputGroup}>
              <p style={styles.label}>Name</p>
              <input type="text" placeholder="e.g., PARK MIN GYUN" required style={styles.inputField} name="name" />
              <p style={styles.label}>Department</p>
              <input type="text" placeholder="e.g., Computer Science" required style={styles.inputField} name="department" />
              <p style={styles.label}>Student ID</p>
              <input type="text" placeholder="e.g., 2018112032" required style={styles.inputField} name="studentId" />
              <p style={styles.label}>Container's SSH Port Number</p>
              <input type="text" placeholder="e.g., 9010" required style={styles.inputField} name="sshPort" />
              <p style={styles.label}>Did you get permission from your professor?</p>
              <input type="checkbox" name="permission" /> Yes
              <p style={styles.label}>New Expiration Date</p>
              <input type="date" required style={styles.inputField} name="expirationDate" />
              <p style={styles.label}>Why should we extend your Container? Please write your reasons in as much detail as possible.</p>
              <textarea required style={{ ...styles.inputField, height: '100px', width: '700px' }} name="reason" />
            </div>

            <p style={styles.cautionText}>
              <h3>** !Please make sure to read the following! **</h3>
              Please submit the error report category accurately.
              <br /><br />⚠ If you apply for other "Contact Details," the report will be automatically ignored, and the error report may be deleted without separate notice. ⚠
              <br /><br />This category is exclusively for requesting an extension of the usage period for an existing container (i.e., before the container is deleted due to expiration). If the container has already expired, it is not possible to recover the storage and the server itself. If the expiration date has already passed, please apply for a new container through the "Container Request" tab.
              <br /><br />Without the approval of your supervising professor, it is not possible to extend the container's expiration date. You must obtain their approval.
              <br /><br />When reporting, you can check the progress of your report handling on My Page, and if there are any requests or questions from the administrator, you must respond within 12 hours. If there is no response for more than 12 hours, the report will be ignored and deleted. (You can check the administrator's requests or questions by clicking on the error you reported in the Your Report section of My Page.)
              <br /><br />Without a detailed description of the situation in the report, the administrator cannot understand the situation. Please provide as much detailed information as possible.
            </p>

            <div>
              <p style={styles.label}>Do you agree with this statement? </p>
              <div style={styles.radioGroup}>
                <label style={styles.radioLabel}>
                  <input type="radio" name="agree3" value="image3" required style={styles.radioButton} />
                  Yes. I have completed self-debugging and is applying after understanding all the error reporting methods.
                </label>
              </div>
            </div>
          </div>
        );
      case 'Just Inquiry':
        return (
          <div>
            <div style={styles.inputGroup}>
              <p style={styles.label}>Name</p>
              <input type="text" placeholder="e.g., PARK MIN GYUN" required style={styles.inputField} name="name" />
              <p style={styles.label}>Department</p>
              <input type="text" placeholder="e.g., Computer Science" required style={styles.inputField} name="department" />
              <p style={styles.label}>Student ID</p>
              <input type="text" placeholder="e.g., 2018112032" required style={styles.inputField} name="studentId" />
              <p style={styles.label}>Details of Inquiry</p>
              <textarea required style={{ ...styles.inputField, height: '100px', width: '700px' }} name="inquiryDetails" />
              <p style={styles.label}>Attach File</p>
              <input type="file" style={styles.inputField} onChange={handleFileChange} accept=".png, .jpg, .jpeg" />
            </div>

            <p style={styles.cautionText}>
              <h3>** !Please make sure to read the following! **</h3>
              Please submit the error report category accurately.
              <br /><br />⚠ If you apply for other "Contact Details," the report will be automatically ignored, and the error report may be deleted without separate notice. ⚠
              <br /><br />When reporting an error, you can check the progress of error handling on My Page, and you must respond to the administrator's requests or questions within 12 hours. If there is no response for more than 12 hours, the error report will be deleted without separate notice. (You can check the administrator's requests or questions by clicking on the error you reported in the Your Report section of My Page.)
              <br /><br />Without a detailed description of the error situation, the administrator cannot understand the situation. Error reports must include a copy or capture of the printed error message, the time the error occurred, and the situation in which the error occurred.
            </p>

            <p style={styles.label}>Do you agree with this statement? </p>
            <div style={styles.radioGroup}>
              <label style={styles.radioLabel}>
                <input type="radio" name="agree3" value="image3" required style={styles.radioButton} />
                Yes. I have completed self-debugging and is applying after understanding all the error reporting methods.
              </label>
            </div>
          </div>
        );
      default:
        return <div></div>;
    }
  };
  return (
    <div>
        <NavigationBar/>
        <div style={{ height: '10vh' }}></div>
        <main style={styles.container}>
      <h1 style={styles.heading}>Report Errors or Contact Us Anything</h1>
            <form onSubmit={handleSubmit} style={styles.form}>
              <div style={styles.formGroup}>
                      <p style={styles.label}>Contact Details</p>
                      <div style={styles.radioGroup}>
                        <label style={styles.radioLabel}>
                          <input type="radio" name="category" value="Container Connection Error" required style={styles.radioButton} onChange={handleIssueChange} />
                          Container Connection Error
                        </label>
                        <label style={styles.radioLabel}>
                          <input type="radio" name="category" value="Container Relocation Request" required style={styles.radioButton} onChange={handleIssueChange} />
                          Container Relocation Request
                        </label>
                        <label style={styles.radioLabel}>
                          <input type="radio" name="category" value="Extend Expiration Date" required style={styles.radioButton} onChange={handleIssueChange} />
                          Extend Expiration Date
                        </label>
                        <label style={styles.radioLabel}>
                          <input type="radio" name="category" value="Just Inquiry" required style={styles.radioButton} onChange={handleIssueChange} />
                          Just Inquiry
                        </label>
                      </div>
              </div>
              {renderFormForIssue()}

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
    width: '800px',
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

  export default ReportErrors;