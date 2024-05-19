import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import NavigationBar from './NavigationBar';
import Footer from './Footer';
import axios from 'axios';

function Forum() {
  const navigate = useNavigate();
  const [posts, setPosts] = useState([]);
  const [filteredPosts, setFilteredPosts] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [postsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    axios.get('/api/check-auth', { withCredentials: true })
      .then(response => {
        fetchPosts();
      })
      .catch(error => {
        navigate('/');
      });
  }, [navigate]);

  const fetchPosts = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/posts');
      setPosts(response.data);
      setFilteredPosts(response.data);

    } catch (error) {

    }
  };
  const createPostHandler = () => {
      navigate('/create-post');
    };

  const handleSearchChange = (event) => {
      setSearchTerm(event.target.value);
    };

  const handleSearch = () => {
      const filtered = posts.filter(post => post.title.toLowerCase().includes(searchTerm.toLowerCase()));
      setFilteredPosts(filtered);
      setCurrentPage(1); // Reset to first page with new results
    };

  const indexOfLastPost = currentPage * postsPerPage;
  const indexOfFirstPost = indexOfLastPost - postsPerPage;
  const currentPosts = filteredPosts.slice(indexOfFirstPost, indexOfLastPost);

  const paginate = pageNumber => setCurrentPage(pageNumber);


    const renderPagination = () => {
      const pageCount = Math.ceil(filteredPosts.length / postsPerPage);
      if (pageCount === 0) {
        return <button style={styles.pageButton} disabled>1</button>;
      } else {
        return [...Array(pageCount).keys()].map(number => (
          <button key={number} onClick={() => paginate(number + 1)} style={styles.pageButton}>
            {number + 1}
          </button>
        ));
      }
    };


  return (
    <div>
      <NavigationBar />
      <div style={{ height: '10vh' }}></div>
      <main style={styles.container}>
        <h1>Welcome to Dcloud Forum!</h1>
        <p>You can check the announcements on this page. Please check frequently. You can also check the error history so far.</p>
        <p>If you have a problem with your container, please take advantage of the forum.</p>

        <div style={styles.searchBar}>
                  <input
                    type="text"
                    placeholder="Search"
                    value={searchTerm}
                    onChange={handleSearchChange}
                    style={{ flex: 1, marginRight: '10px' }}
                  />
                  <button onClick={handleSearch} style={styles.searchButton}>Search</button>
                  <button onClick={createPostHandler} style={styles.createButton}>Create Post</button> {/* 새 글 작성 버튼 추가 */}
                </div>
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
          {currentPosts.map((post, index) => (
              <tr key={index}>
                <td style={styles.td}>{post.postID}</td>
                <td style={styles.td}>{post.category}</td>
                <td style={styles.td}>{post.title}</td>
                <td style={styles.td}>
                  {post && post.user ? post.user.userName : "Unknown"}
                </td>
                <td style={styles.td}>
                  {post.createdAt ? new Date(post.createdAt).toLocaleDateString() : "Invalid Date"}
                </td>
              </tr>
          ))}
          </tbody>
        </table>
        <div style={styles.pagination}>
            {renderPagination()}
          </div>

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
  searchBar: {
    display: 'flex',
    padding: '10px',
    marginBottom: '20px',
    width: '100%',
    maxWidth: '500px',
    borderRadius: '5px',
    border: '1px solid #ccc'
  },
  pagination: {
    marginTop: '20px'
  },
  pageButton: {
    padding: '8px 16px',
    margin: '0 5px',
    borderRadius: '5px',
    cursor: 'pointer',
    background: '#f0f0f0',
    border: 'none'
  },
    searchButton: {
      padding: '10px 20px',
      background: '#555',
      color: 'white',
      border: 'none',
      borderRadius: '5px',
      cursor: 'pointer',
      fontWeight: 'bold'
    },
    createButton: {
        padding: '10px 20px',
        marginLeft: '10px', // 버튼 간 간격 조정
        background: '#4CAF50', // 버튼 색상 변경
        color: 'white',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
        fontWeight: 'bold'
      },

};

export default Forum;
