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

    useEffect(() => {
        // 세션 검증
        axios.get('/api/check-auth', { withCredentials: true })
            .then(response => {
                // 포스트 데이터 가져오기
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

    if (!post) {
        return <div>Loading...</div>;
    }

    const handleGoBack = () => {
        navigate('/forum');
    };

    const handleCommentChange = (event) => {
        setComment(event.target.value);
    };

    const handleSubmitComment = async (event) => {
        event.preventDefault();

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

    return (
        <div>
            <NavigationBar />
            <div style={{ height: '10vh' }}></div>
            <main style={styles.container}>
                <h1 style={styles.heading}>{post.title}</h1>
                <div style={styles.metaInfo}>
                    <span style={styles.author}>
                        {post.userId} |{' '}
                        {new Date(post.createdAt).toLocaleDateString()}
                    </span>
                </div>
                <hr style={styles.separator} />
                <p style={styles.content}>{post.content}</p>
                <p style={styles.category}>Category: {post.category}</p>
            </main>
            <div style={styles.commentSection}>
                <hr style={styles.separator} />
                <h2 style={styles.commentTitle}>Comments</h2>
                {comments.map((comment, index) => (
                    <div key={index} style={styles.comment}>
                        <p style={styles.commentContent}>{comment.content}</p>
                        <p style={styles.commentAuthor}>
                            {comment.user} |{' '}
                            {new Date(comment.createdAt).toLocaleDateString()}
                        </p>
                    </div>
                ))}
                <form onSubmit={handleSubmitComment} style={styles.commentForm}>
                    <textarea
                        placeholder="Write a comment..."
                        value={comment}
                        onChange={handleCommentChange}
                        style={styles.commentInput}
                    />
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
        marginBottom: '10px',
        color: '#777',
    },
    date: {
        color: '#777',
    },
    metaInfo: {
        display: 'flex',
        justifyContent: 'flex-start',
        marginBottom: '10px',
        fontSize: '14px',
        color: '#777',
    },
    author: {
        fontWeight: 'bold',
    },
    separator: {
        border: 'none',
        borderTop: '1px solid #ccc',
        margin: '20px 0',
    },
    backButton: {
        position: 'absolute',
        top: '12vh',
        left: '20px',
        padding: '10px',
        backgroundColor: '#f0f0f0',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
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
    },
    backButton: {
        width: '48%',
        padding: '10px',
        backgroundColor: '#f44336',
        color: 'white',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
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
    },
    commentContent: {
        marginBottom: '5px',
    },
    commentAuthor: {
        fontSize: '12px',
        color: '#888',
    },
};

export default PostDetail;