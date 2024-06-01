import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import NavigationBar from './NavigationBar'; // 경로 확인 필요
import Footer from './Footer'; // 경로 확인 필요
import axios from 'axios'; // Axios import

function ContainerRequestForm() {
    const navigate = useNavigate();
    const [images, setImages] = useState([]); // DB에서 가져온 컨테이너 데이터를 저장할 상태
    const [selectedImageIndex, setSelectedImageIndex] = useState(null); // 선택된 이미지 인덱스 상태

    const hoverEffect = (e) => {
        e.target.style.background = '#777';
    };
    const resetEffect = (e) => {
        e.target.style.background = '#555';
    };
    const handleSelectImage = (index) => {
        setSelectedImageIndex(index); // 선택된 이미지 인덱스 업데이트
    };


    const requestSubmit = async (e) => {
        e.preventDefault(); // Prevent the default form submission behavior

        if (selectedImageIndex === null) {
            alert("Please select an image.");
            return;
        }

        // Gather form data
        const formData = {
            imageName: images[selectedImageIndex].imageName,
            imageTag: images[selectedImageIndex].imageTag,
            environment : document.querySelector('input[name="environment"]:checked').value,
            studentId: document.querySelector('input[name="studentid"]').value,
            department: document.querySelector('input[name="department"]').value,
            professorName: document.querySelector('input[name="professor"]').value,
            usageDescription: document.querySelector('textarea[name="usage"]').value,
            gpuModel: document.querySelector('input[name="gpu"]:checked').value,
            expectedExpirationDate: document.querySelector('input[type="date"]').value
            // Add other fields from your form as needed
        };

        try {
            const response = await axios.post('/api/containerrequest', formData, {
                withCredentials: true  // Ensure cookies are sent with the request if needed
            });
            // Handle response
            console.log(response.data);
            alert("Request submitted successfully!");
            navigate('/mainpage'); // Navigate to another route upon success
        } catch (error) {
            console.error("There was an error submitting the request:", error);
            alert("An error occurred while submitting the request.");
        }
    };



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
            const response = await fetch('http://localhost:8080/api/images');
            const data = await response.json();
            console.log("Received data:", data);
            setImages(data);
        };

    return (
        <div>
            <NavigationBar/>
            <div style={{ height: '10vh' }}></div>
            <main style={styles.container}>
                <h1 style={styles.heading}>Container Request Form</h1>
                    <form onSubmit={requestSubmit} style={styles.form}>
                      <div style={styles.formGroup}>
                        <p style={styles.label}>Choose a Container Image</p>
                      </div>



                      <div style={styles.tableContainer}>
                          <table style={styles.table}>
                              <thead>
                                  <tr>
                                      <th style={styles.tableCell}>Select</th> {/* 추가된 선택 열 헤더 */}
                                      <th style={styles.tableCell}>Image Name</th>
                                      <th style={styles.tableCell}>Image Tag</th>
                                      <th style={styles.tableCell}>OS Version</th>
                                      <th style={styles.tableCell}>TensorFlow Version</th>
                                      <th style={styles.tableCell}>CUDA Version</th>
                                  </tr>
                              </thead>
                              <tbody>
                                  {images.map((image, index) => ( // index 추가
                                      <tr key={index}>
                                          <td style={styles.tableCell}>
                                              <input
                                                  type="radio"
                                                  name="selectedImage"
                                                  value={index}
                                                  onChange={() => handleSelectImage(index)}
                                                  style={styles.radioButton} required
                                              />
                                          </td>
                                          <td style={styles.tableCell}>{image.imageName}</td>
                                          <td style={styles.tableCell}>{image.imageTag}</td>
                                          <td style={styles.tableCell}>{image.osVersion}</td>
                                          <td style={styles.tableCell}>{image.tfVersion}</td>
                                          <td style={styles.tableCell}>{image.cudaVersion}</td>
                                      </tr>
                                  ))}
                              </tbody>
                          </table>
                      </div>

                      <div style={{ height: '2vh' }}></div>

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
                        <input type="text" name="studentid" placeholder="e.g., 2018112032" required style={styles.inputField} />
                         <p style={styles.label}>Department</p>
                        <input type="text" name="department" placeholder="e.g., Computer Science" required style={styles.inputField} />
                         <p style={styles.label}>Professor Name</p>
                        <input type="text" name="professor" placeholder="e.g., Dongho Kim" required style={styles.inputField} />
                        <p style={styles.label}>Usage</p>
                         <textarea name="usage" placeholder="eg.. Project name" required style={{...styles.inputField, height: '100px'}} />
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
                              <input type="radio" name="gpu" value="2080" required style={styles.radioButton} />
                              RTX 2080ti (only LAB)
                            </label>
                          <label style={styles.radioLabel}>
                            <input type="radio" name="gpu" value="3090" required style={styles.radioButton} />
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
                        <h3>** !Please make sure to read the following! **</h3>
                        <br/>- During server usage, any issues arising from your own negligence, lack of manual understanding, or failure to respond to administrator requests are your sole responsibility, and future usage restrictions or penalties may be applied.
                        <br/><br/>- Data may be deleted during server usage, so please frequently back up your data on your personal computer.
                        <br/><br/>- After the server's scheduled expiration date, the container and storage will be immediately deleted, and the administrator is not responsible for any problems caused by the user's failure to back up data.
                        <br/><br/>- If you need to use the server after the expiration date, you must apply for an extension at least 3 days before the expiration date. Extension requests can be made through the "Report Errors or Contact Us" tab.
                        <br/><br/>- The results of container applications can be checked on "My Page," and you can check the results within a maximum of 24 hours. No separate notifications will be sent, so please check frequently.
                        <br/><br/>- Also We will provide a command to access the activated container.
                        <br/><br/>- If your application is rejected, no specific reason for rejection will be provided.
                        <br/><br/>- Additionally, we are providing a link to the manual for server usage. Please read the contents of the manual in detail.<br/>

                        <br/><a href="https://docs.google.com/document/d/1PtMqJ25kKMZLJnOQY8vgWyTY3HBWUvP86vFaZ2ldMdg/edit" target="_blank">
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