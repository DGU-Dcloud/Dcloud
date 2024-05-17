import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import NavigationBar from './NavigationBar';
import Footer from './Footer';
import axios from 'axios';

function CreatePost() {
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [category, setCategory] = useState('');

    useEffect(() => {
        // 세션 검증 및 사용자 ID 가져오기
        axios.get('/api/check-auth', { withCredentials: true })
            .then(response => {
                // 세션이 유효한 경우 사용자 데이터 로드
                console.log('Response:', response);
            })
            .catch(error => {
                // 세션이 유효하지 않은 경우 로그인 페이지로 리디렉션
                console.error('Session not valid:', error);
                navigate('/');
            });
    }, [navigate]);

    const handleTitleChange = (event) => {
        setTitle(event.target.value);
    };

    const handleContentChange = (event) => {
        setContent(event.target.value);
    };

    const handleCategoryChange = (event) => {
        setCategory(event.target.value);
    };

    const handleSubmit = async (event) => {
        event.preventDefault(); // Prevent the default form submission behavior

        try {
            const response = await axios.post('/api/create-post', {
                title,
                content,
                category
            });
            if (response.status === 201 || response.status === 200) {
                // 글 작성이 성공하면 포럼 페이지로 이동
                alert('Post created successfully!');
                navigate('/forum');
            } else {
                console.error('Failed to create post');
                alert('Failed to create post');
            }
        } catch (error) {
            console.error('There was an error creating the post:', error);
            alert('An error occurred while creating the post.');
        }
    };



    return (
        <div>
            <NavigationBar />
            <div style={{ height: '10vh' }}></div>
            <main style={styles.container}>
                <h1 style={styles.heading}>Create New Post</h1>
                <form onSubmit={handleSubmit} style={styles.form}>
                    <div style={styles.formGroup}>
                        <label htmlFor="title" style={styles.label}>Title:</label>
                        <input
                            type="text"
                            id="title"
                            value={title}
                            onChange={handleTitleChange}
                            required
                            style={styles.inputField}
                        />
                    </div>
                    <div style={styles.formGroup}>
                        <label htmlFor="content" style={styles.label}>Content:</label>
                        <textarea
                            id="content"
                            value={content}
                            onChange={handleContentChange}
                            required
                            style={{ ...styles.inputField, height: '100px' }}
                        ></textarea>
                    </div>
                    <div style={styles.formGroup}>
                        <label htmlFor="category" style={styles.label}>Category:</label>
                        <input
                            type="text"
                            id="category"
                            value={category}
                            onChange={handleCategoryChange}
                            required
                            style={styles.inputField}
                        />
                    </div>
                    <button type="submit" style={styles.submitButton}>Submit</button>
                </form>
            </main>
            <Footer />
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
    inputField: {
        padding: '10px',
        marginBottom: '10px',
        borderRadius: '5px',
        border: '1px solid #ccc',
        outline: 'none',
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
};

export default CreatePost;
