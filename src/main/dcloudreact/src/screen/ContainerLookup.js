import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom'; // 페이지 이동을 위해 useNavigate 훅을 import합니다.
import NavigationBar from './NavigationBar'; // 경로 확인 필요
import Footer from './Footer'; // 경로 확인 필요

function ContainerLookup() {
  const navigate = useNavigate();
  const [containers, setContainers] = useState([]); // DB에서 가져온 컨테이너 데이터를 저장할 상태

  useEffect(() => {
      // 데이터를 불러오는 함수
      const fetchData = async () => {
        const response = await fetch('http://localhost:8080/api/containers');
        const data = await response.json();
        setContainers(data);
      };

      fetchData().catch(console.error);
    }, []);

  return  (
         <div>
              <NavigationBar />
              <div style={{ height: '10vh' }}></div>
              <main style={styles.container}>
                <h1>Server Farm Container Lookup</h1>

                <table>
                    <thead>
                      <tr>
                        <th>Container ID</th>
                        <th>Container Name</th>
                        <th>Image Name</th>
                        <th>Image Tag</th>
                        <th>Server Name</th>
                        <th>SSH Port</th>
                        <th>Jupyter Port</th>
                        <th>Created At</th>
                        <th>Deleted At</th>
                        <th>Note</th>
                      </tr>
                    </thead>
                    <tbody>
                      {containers.map((container) => (
                        <tr key={container.containerID}>
                          <td style={{ textAlign: 'center' }}>{container.containerID}</td>
                          <td style={{ textAlign: 'center' }}>{container.containerName}</td>
                          <td style={{ textAlign: 'center' }}>{container.imageName}</td>
                          <td style={{ textAlign: 'center' }}>{container.imageTag}</td>
                          <td style={{ textAlign: 'center' }}>{container.serverName}</td>
                          <td style={{ textAlign: 'center' }}>{container.sshPort}</td>
                          <td style={{ textAlign: 'center' }}>{container.jupyterPort}</td>
                          <td style={{ textAlign: 'center' }}>{container.createdAt}</td>
                          <td style={{ textAlign: 'center' }}>{container.deletedAt}</td>
                          <td style={{ textAlign: 'center' }}>{container.note}</td>
                        </tr>
                      ))}
                    </tbody>

                  </table>
              </main>



            <Footer/>
         </div>
  );
}
const styles = {
  container: {
    display: 'flex', // Flexbox 레이아웃 사용
    flexDirection: 'column', // 아이템들을 세로 방향으로 배열
    alignItems: 'center', // 가로 축에서 중앙에 정렬
    justifyContent: 'center', // 세로 축에서 중앙에 정렬 (필요한 경우)
    margin: '0 auto', // 자동 마진을 사용하여 좌우 중앙에 배치
    maxWidth: '1000px', // 최대 너비를 800px로 설정
    width: '100%',
    padding: '20px',
    paddingBottom: '10vh',
  },
}
export default ContainerLookup;
