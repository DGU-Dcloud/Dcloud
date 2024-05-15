import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import NavigationBar from './NavigationBar';
import Footer from './Footer';
import axios from 'axios';

function CreatePost() {
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');

    const handleTitleChange = (event) => {
        setTitle(event.target.value);
    };

    const handleContentChange = (event) => {
        setContent(event.target.value);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post('/api/create-post', {
                title,
                content
            });
            if (response.status === 201) {
                // 글 작성이 성공하면 포럼 페이지로 이동
                navigate('/forum');
            } else {
                // 실패한 경우에 대한 처리
            }
        } catch (error) {
            // 에러 처리
        }
    };

    return (
        <div>
            <NavigationBar />
            <div style={{ height: '10vh' }}></div>
            <main style={styles.container}>
                <h1>Create New Post</h1>
                <form onSubmit={handleSubmit}>
                    <div style={styles.formGroup}>
                        <label htmlFor="title">Title:</label>
                        <input
                            type="text"
                            id="title"
                            value={title}
                            onChange={handleTitleChange}
                            required
                        />
                    </div>
                    <div style={styles.formGroup}>
                        <label htmlFor="content">Content:</label>
                        <textarea
                            id="content"
                            value={content}
                            onChange={handleContentChange}
                            required
                        ></textarea>
                    </div>
                    <button type="submit" style={styles.submitButton}>Submit</button>
                </form>
            </main>
            <Footer />
        </div>
    );
}

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
    },
    formGroup: {
        marginBottom: '20px',
    },
    submitButton: {
        padding: '10px 20px',
        background: '#4CAF50',
        color: 'white',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
        fontWeight: 'bold',
    },
};

export default CreatePost;
