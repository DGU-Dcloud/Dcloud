import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import NavigationBar from './NavigationBar';
import Footer from './Footer';
import axios from 'axios';

function PostDetail() {
    const navigate = useNavigate();
    const { postId } = useParams();
    const [post, setPost] = useState(null);
    const [comment, setComment] = useState('');
    const [comments, setComments] = useState([]);
    const [error, setError] = useState('');
    const [user, setUser] = useState(null);

    useEffect(() => {
        // 세션 검증
        axios.get('/api/check-auth', { withCredentials: true })
            .then(response => {
                const { userID } = response.data;
                setUser({ userID }); // 로그인된 사용자 정보 설정
                fetchPost();
                fetchComments();
            })
            .catch(error => {
                console.error('Session not valid:', error);
                navigate('/');
            });
    }, [navigate, postId]);

    const fetchPost = async () => {
        try {
            const response = await fetch(`/api/posts/${postId}`);
            const data = await response.json();
            setPost(data);
        } catch (error) {
            console.error('Error fetching post:', error);
        }
    };

    const fetchComments = async () => {
        try {
            const response = await fetch(`/api/posts/${postId}/comments`);
            const data = await response.json();
            setComments(data);
        } catch (error) {
            console.error('Error fetching comments:', error);
        }
    };

    const handleGoBack = () => {
        navigate('/forum');
    };

    const handleCommentChange = (event) => {
        setComment(event.target.value);
        if (event.target.value.length >= 10) {
            setError('');
        }
    };

    const handleSubmitComment = async (event) => {
        event.preventDefault();
        if (comment.length < 10) {
            setError('Comment must be at least 10 characters long.');
            return;
        }

        try {
            const response = await axios.post(`/api/posts/${postId}/comments`, { content: comment }, { withCredentials: true });
            if (response.status === 201 || response.status === 200) {
                alert('Comment added successfully!');
                setComment('');
                fetchComments();
            } else {
                console.error('Failed to add comment');
                alert('Failed to add comment');
            }
        } catch (error) {
            console.error('There was an error adding the comment:', error);
            alert('An error occurred while adding the comment.');
        }
    };

    const handleDeleteComment = async (commentId) => {
        try {
            const response = await axios.delete(`/api/posts/${postId}/comments/${commentId}`, { withCredentials: true });
            if (response.status === 204) {
                alert('Comment deleted successfully!');
                fetchComments();
            } else {
                console.error('Failed to delete comment');
                alert('Failed to delete comment');
            }
        } catch (error) {
            console.error('There was an error deleting the comment:', error);
            alert('An error occurred while deleting the comment.');
        }
    };

    const handleDeletePost = async () => {
        const confirmDelete = window.confirm("Are you sure you want to delete this post?");
            if (!confirmDelete) {
                return; // 사용자가 취소한 경우 함수 종료
            }
        try {
            const response = await axios.delete(`/api/postdelete/${postId}`, { withCredentials: true });
            if (response.status === 204) {
                alert('Post deleted successfully!');
                navigate('/forum');
            } else {
                console.error('Failed to delete post');
                alert('Failed to delete post');
            }
        } catch (error) {
            console.error('There was an error deleting the post:', error);
            alert('An error occurred while deleting the post.');
        }
    };

    if (!post) {
        return <div>Loading...</div>;
    }

    const extractFileNameFromPath = (path) => {
      const parts = path.split('/');
      return parts[parts.length - 1];
    };

    return (
        <div>
            <NavigationBar />
            <div style={{ height: '10vh' }}></div>
            <main style={styles.container}>
              {user && user.userID === post.userId && (
                <button style={styles.postDeleteButton} onClick={handleDeletePost}>x</button>
              )}
              <h1 style={styles.heading}>{post.title}</h1>
              {post.imagePath && (
                  <div style={styles.imageContainer}>
                    <a href={`/api/images/${post.imagePath}`} download style={styles.downloadLink}>
                      <i className="fas fa-download"></i> Download Image
                    </a>
                  </div>
                )}
              <div style={styles.metaInfo}>
                <span style={styles.author}>
                  {post.userId} |{' '}
                  {new Date(post.createdAt).toLocaleDateString()}
                </span>
              </div>
              <hr style={styles.separator} />

              <div style={styles.content}>
                {post.content.split('\n').map((line, index) => (
                  <p key={index}>{line}</p>
                ))}
              </div>

              <p style={styles.category}>Category: {post.category}</p>
            </main>
            <div style={styles.commentSection}>
                <hr style={styles.separator} />
                <h2 style={styles.commentTitle}>Comments</h2>
                {comments.map((comment, index) => (
                    <div key={index} style={styles.comment}>
                        <p style={styles.commentContent}>{comment.content}</p>
                        <p style={styles.commentAuthor}>
                            {comment.userId} |{' '}
                            {new Date(comment.createdAt).toLocaleString()}
                        </p>
                        {user && user.userID === comment.userId && (
                            <button style={styles.deleteButton} onClick={() => handleDeleteComment(comment.commentId)}>x</button>
                        )}
                    </div>
                ))}
                <form onSubmit={handleSubmitComment} style={styles.commentForm}>
                    <textarea
                        placeholder="Write a comment..."
                        value={comment}
                        onChange={handleCommentChange}
                        style={styles.commentInput}
                    />
                    {error && <p style={styles.error}>{error}</p>}
                    <div style={styles.buttonContainer}>
                        <button type="submit" style={styles.submitButton}>
                            Submit Comment
                        </button>
                        <button style={styles.backButton} onClick={handleGoBack}>
                            &lt; Back
                        </button>
                    </div>
                </form>
            </div>
            <div style={{ height: '10vh' }}></div>
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
    content: {
        marginBottom: '20px',
        lineHeight: '1.6',
        color: '#555',
    },
    category: {
        marginBottom: '10px',
        fontWeight: 'bold',
        color: '#777',
    },
    author: {
        fontWeight: 'bold',
        marginBottom: '10px',
        color: '#777',
    },
    date: {
        color: '#777',
    },
    metaInfo: {
        display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                marginBottom: '10px',
                fontSize: '14px',
                color: '#777',
                position: 'relative',
    },
    separator: {
        border: 'none',
        borderTop: '1px solid #ccc',
        margin: '20px 0',
    },

    commentForm: {
        display: 'flex',
        flexDirection: 'column',
        marginTop: '20px',
    },
    commentInput: {
        padding: '10px',
        marginBottom: '10px',
        borderRadius: '5px',
        border: '1px solid #ccc',
    },
    buttonContainer: {
        display: 'flex',
        justifyContent: 'space-between',
        marginTop: '10px',
    },
    submitButton: {
        width: '48%',
        padding: '10px',
        backgroundColor: '#4CAF50',
        color: 'white',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
        fontSize: '18px',
        fontWeight: 'bold',
        textTransform: 'uppercase',
        letterSpacing: '1px',
    },
    backButton: {
        width: '48%',
        padding: '10px',
        backgroundColor: '#f44336',
        color: 'white',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
        fontSize: '18px',
        fontWeight: 'bold',
        textTransform: 'uppercase',
        letterSpacing: '1px',
    },
    commentSection: {
        width: '70%',
        margin: '0 auto',
    },
    commentTitle: {
        fontSize: '20px',
        fontWeight: 'bold',
        marginBottom: '10px',
    },
    comment: {
        marginBottom: '20px',
        padding: '10px',
        backgroundColor: '#f0f0f0',
        borderRadius: '5px',
        position: 'relative',
    },
    commentContent: {
        marginBottom: '5px',
    },
    commentAuthor: {
        fontSize: '12px',
        color: '#888',
    },
    error: {
        color: 'red',
        marginBottom: '10px',
    },
    deleteButton: {
        position: 'absolute',
        top: '10px',
        right: '10px',
        backgroundColor: 'transparent',
        border: 'none',
        color: 'red',
        cursor: 'pointer',
        fontSize: '16px',
    },
     postDeleteButton: {
        backgroundColor: 'transparent',
        border: 'none',
        color: 'red',
        cursor: 'pointer',
        fontSize: '16px',
        },
    imageContainer: {
        marginBottom: '20px',
        textAlign: 'center',
      },
  image: {
    maxWidth: '100%',
    height: 'auto',
    marginBottom: '10px',
  },
  downloadLink: {
      display: 'inline-block',
      padding: '10px 20px',
      backgroundColor: '#f1f1f1',
      color: '#333',
      textDecoration: 'none',
      borderRadius: '4px',
      fontWeight: 'bold',
      transition: 'background-color 0.3s ease',
      marginTop: '10px',
      boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
      '&:hover': {
        backgroundColor: '#e1e1e1',
      },
    },
};

export default PostDetail;
