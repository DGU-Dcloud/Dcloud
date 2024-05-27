import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import NavigationBar from './NavigationBar';
import Footer from './Footer';
import axios from 'axios';

function Forum() {
  const navigate = useNavigate();
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // 세션 검증
    axios.get('/api/check-auth', { withCredentials: true })
      .then(response => {
        // 세션이 유효한 경우에만 서버 데이터 로딩
        console.log('Response:', response);
        fetchPosts();
      })
      .catch(error => {
        // 세션이 유효하지 않은 경우 로그인 페이지로 리디렉션
        console.error('Session not valid:', error);
        navigate('/');
      });
  }, [navigate]);

  const fetchPosts = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/posts');
      const data = await response.json();
      setPosts(data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching posts:', error);
    }
  };

  const createPostHandler = () => {
    navigate('/create-post');
  };

  const handlePostClick = (postId) => {
    navigate(`/post/${postId}`);
  };

  if (loading) {
    return <div>Loading...</div>; // 로딩 중 화면 표시
  }

  return (
    <div>
      <NavigationBar />
      <div style={{ height: '10vh' }}></div>
      <main style={styles.container}>
        <h1>Welcome to Dcloud Forum!</h1>
        <p>You can check the announcements on this page. Please check frequently. You can also check the error history so far.</p>
        <p>If you have a problem with your container, please take advantage of the forum.</p>

        <button onClick={createPostHandler} style={styles.createButton}>Create Post</button>

        <table style={styles.table}>
          <thead>
            <tr>
              <th style={styles.th}>No</th>
              <th style={styles.th}>Category</th>
              <th style={styles.th}>Title</th>
              <th style={styles.th}>Author</th>
              <th style={styles.th}>Date</th>
            </tr>
          </thead>
          <tbody>
            {posts.map((post, index) => (
              <tr key={index} onClick={() => handlePostClick(post.postID)} style={styles.tableRow}>
                <td style={styles.td}>{post.postID}</td>
                <td style={styles.td}>{post.category}</td>
                <td style={styles.td}>{post.title}</td>
                <td style={styles.td}>
                    {post.userId}
                </td>
                <td style={styles.td}>
                  {post.createdAt ? new Date(post.createdAt).toLocaleDateString() : "Invalid Date"}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
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
    maxWidth: '1200px',
    width: '100%',
    padding: '20px',
    paddingBottom: '10vh',
  },
  table: {
    borderCollapse: 'collapse',
    width: '80%',
    marginTop: '20px',
    marginBottom: '20px',
    boxShadow: '0 4px 8px 0 rgba(0,0,0,0.2)',
    textAlign: 'center',
    border: '1px solid #e0e0e0',
  },
  th: {
    backgroundColor: '#f5f5f5',
    color: '#333',
    fontWeight: 'bold',
    padding: '12px 10px',
    borderBottom: '2px solid #e0e0e0',
  },
  td: {
    textAlign: 'center',
    padding: '10px',
    borderBottom: '1px solid #e0e0e0',
    color: '#616161',
  },
  createButton: {
    padding: '10px 20px',
    marginBottom: '20px',
    background: '#4CAF50',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    fontWeight: 'bold'
  },
  tableRow: {
    cursor: 'pointer',
    transition: 'background-color 0.3s',
    '&:hover': {
      backgroundColor: '#f5f5f5',
    },
  },
};

export default Forum;