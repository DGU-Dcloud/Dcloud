import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import NavigationBar from './NavigationBar';
import Footer from './Footer';
import axios from 'axios';

function CreatePost() {
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [category, setCategory] = useState('General');
    const [userRole, setUserRole] = useState('');

    useEffect(() => {
        // 로그인 상태 확인 후 사용자의 권한을 설정합니다.
        axios.get('/api/user-role', { withCredentials: true })
            .then(response => {
                setUserRole(response.data.roleId === 1 ? 'manager' : '');
            })
            .catch(error => {
                console.log('Error fetching user role:', error);
            });
    }, []);

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
                        <select
                            id="category"
                            value={category}
                            onChange={handleCategoryChange}
                            style={styles.inputField}
                        >
                            <option value="General">General</option>
                            <option value="Question">Question</option>
                            {userRole === 'manager' && <option value="Notice">Notice</option>}
                        </select>
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
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        margin: '0 auto',
        maxWidth: '800px',
        width: '100%',
        padding: '20px',
        paddingBottom: '10vh',
        backgroundColor: '#f4f4f4',
        borderRadius: '10px',
        boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.1)',
    },
    heading: {
        textAlign: 'center',
        marginBottom: '20px',
        fontSize: '24px',
        color: '#333',
    },
    form: {
        display: 'flex',
        flexDirection: 'column',
        width: '100%',
        maxWidth: '500px',
    },
    formGroup: {
        marginBottom: '20px',
    },
    label: {
        marginBottom: '10px',
        fontWeight: 'bold',
        color: '#555',
    },
    inputField: {
        padding: '12px',
        marginBottom: '10px',
        borderRadius: '5px',
        border: '1px solid #ccc',
        outline: 'none',
        width: '100%',
        boxSizing: 'border-box',
        fontSize: '16px',
    },
    submitButton: {
        padding: '15px 0',
        backgroundColor: '#4CAF50',
        color: 'white',
        border: 'none',
        borderRadius: '5px',
        fontSize: '18px',
        fontWeight: 'bold',
        textTransform: 'uppercase',
        letterSpacing: '1px',
        width: '100%',
        cursor: 'pointer',
        transition: 'background-color 0.3s',
    },
};


export default CreatePost;
